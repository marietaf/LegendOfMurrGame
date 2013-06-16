/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package States;

import legendofmurrgame.DebugDrawJ2D;       //Imports from classes within the code
import org.jbox2d.callbacks.DebugDraw;      //JBox2D imports
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.IViewportTransform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Color;         //Slick 2D imports
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author choik8962
 */
public class Tutorial extends BasicGameState {

    //State
    int ID;
    StateBasedGame game;
    GameContainer gc;
    //Physics
    double timestart, timeend, timedif;
    float timeStep = 1.0f / 60.0f;
    int velocityIterations = 6;
    int positionInterations = 2;
    World tutorialWorld;
    Body player, circle, star;
    DebugDrawJ2D debugDrawJ2D;
    boolean debugMode;
    IViewportTransform viewportTransform;
    //Key presseing + releasing
    boolean worldPause;
    boolean keyAPressed, keyDPressed;
    Vec2 f, p;
    float velChange, impulse;
    float verticalVel;
    int keyW = 0;

    public Tutorial(int ID) {
        this.ID = ID;
    }

    @Override
    public int getID() {
        return ID;
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        this.game = sbg;
        this.gc = gc;
        reset();

    }

    public void reset() {
        worldPause = true;

        //TEST JBOX2D for Tutorial!
        Vec2 gravity = new Vec2(0.0f, -9.81f * 2f);
        tutorialWorld = new World(gravity);
        tutorialWorld.step(0, 0, 0);

        debugDrawJ2D = new DebugDrawJ2D(gc);
        tutorialWorld.setDebugDraw(debugDrawJ2D);

        viewportTransform = debugDrawJ2D.getViewportTranform();
        {  //WALLS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            //BOTTOM
            PolygonShape psBot = new PolygonShape();
            psBot.setAsBox(40, 0.1f);  //Dimensions of the wall

            BodyDef bdBot = new BodyDef();
            bdBot.type = BodyType.STATIC;
            bdBot.position.set(40.0f, 0.0f); //The coordinates for the position of the bottom wall
            //Starts with the bottom right corner
            FixtureDef fdBot = new FixtureDef();
            fdBot.shape = psBot;
            fdBot.userData = "wall";

            Body bottomWall = tutorialWorld.createBody(bdBot);
            bottomWall.createFixture(fdBot);

            //TOP
            PolygonShape psTop = new PolygonShape();
            psTop.setAsBox(40, 1);

            BodyDef bdTop = new BodyDef();
            bdTop.type = BodyType.STATIC;
            bdTop.position.set(40.0f, 60.0f);

            FixtureDef fdTop = new FixtureDef();
            fdTop.shape = psTop;
            fdTop.userData = "wall";
            fdTop.friction = 0.0f;

            Body topWall = tutorialWorld.createBody(bdTop);
            topWall.createFixture(fdTop);

            //LEFT
            PolygonShape psLeft = new PolygonShape();
            psLeft.setAsBox(2, 30);

            BodyDef bdLeft = new BodyDef();
            bdLeft.type = BodyType.STATIC;
            bdLeft.position.set(0.0f, 30.0f);

            FixtureDef fdLeft = new FixtureDef();
            fdLeft.shape = psLeft;
            fdLeft.userData = "wall";
            fdLeft.friction = 0.0f;

            Body leftWall = tutorialWorld.createBody(bdLeft);
            leftWall.createFixture(fdLeft);

            //RIGHT
            PolygonShape psRight = new PolygonShape();
            psRight.setAsBox(1, 30);

            BodyDef bdRight = new BodyDef();
            bdRight.type = BodyType.STATIC;
            bdRight.position.set(80.0f, 30.0f);

            FixtureDef fdRight = new FixtureDef();
            fdRight.shape = psRight;
            fdRight.userData = "wall";
            fdRight.friction = 0.0f;

            Body rightWall = tutorialWorld.createBody(bdRight);
            rightWall.createFixture(fdRight);
        }

        {  // PLAYER ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(3, 5);

            BodyDef bd = new BodyDef();
            bd.type = BodyType.DYNAMIC;
            bd.position.set(50.0f, 20.0f);

            FixtureDef fd = new FixtureDef();
            fd.friction = 10.0f;
            fd.shape = polygonShape;
            fd.userData = "player";

            player = tutorialWorld.createBody(bd);
            player.createFixture(fd);
        }
        //test Star
        {
            CircleShape pointShape = new CircleShape();
            pointShape.m_radius = 1.0f;

            BodyDef bd = new BodyDef();
            bd.type = BodyType.STATIC;
            bd.position.set(12.0f, 20.0f);

            FixtureDef fd = new FixtureDef();
            fd.userData = "circle1";
            fd.isSensor = false;
            fd.shape = pointShape;

            star = tutorialWorld.createBody(bd);
            star.createFixture(fd);

        }

        {   //Circle ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            CircleShape circleShape = new CircleShape();
            circleShape.m_radius = 5.0f;

            BodyDef bd = new BodyDef();
            bd.type = BodyType.DYNAMIC;
            bd.position.set(10.0f, 50.0f);

            FixtureDef fd = new FixtureDef();
            fd.shape = circleShape;
            fd.userData = "circle";
            fd.restitution = 1.0f;

            circle = tutorialWorld.createBody(bd);
            circle.createFixture(fd);
        }

        //Debug Mode on automatically
        tutorialWorld.setDebugDraw(debugDrawJ2D);
        debugDrawJ2D.setFlags(DebugDraw.e_shapeBit);
        debugMode = true;
        worldPause = false;
    }

    public void UpdateDebugMode() {
        if (debugMode) {
            debugDrawJ2D.clearFlags(DebugDraw.e_shapeBit);
            debugMode = false;
        } else if (!debugMode) {
            debugDrawJ2D.setFlags(DebugDraw.e_shapeBit);
            debugMode = true;
        }
    }

    public void Pause() {
        if (worldPause) {
            tutorialWorld.step(timeStep, velocityIterations, positionInterations);
            worldPause = false;
        } else if (!worldPause) {
            tutorialWorld.step(0, velocityIterations, positionInterations);
            worldPause = true;
            game.enterState(legendofmurrgame.LegendOfMurr.GAMEPAUSE_ID);
            worldPause = false;
        }
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        this.game = sbg;

        if (keyAPressed) {
            velChange = Math.max(-15 - player.getLinearVelocity().x, -10.0f);
            impulse = player.getMass() * velChange * 0.5f;
            f = player.getWorldVector(new Vec2(impulse, 0f));
            p = player.getWorldPoint(player.getLocalCenter().addLocal(0, 0));
            player.applyLinearImpulse(f, p);
        }
        if (keyDPressed) {
            velChange = 15 - player.getLinearVelocity().x;
            impulse = player.getMass() * velChange * 0.5f;
            f = player.getWorldVector(new Vec2(impulse, 0f));
            p = player.getWorldPoint(player.getLocalCenter());
            player.applyLinearImpulse(f, p);
        }

        if (!worldPause) {
            tutorialWorld.step(timeStep, velocityIterations, positionInterations);
        }

        debugDrawJ2D.setCamera(setX(),setY(), 10.0f);
        
        if (player.getLinearVelocity().y == 0 && (player.getPosition().y)<=48.0f) {
            keyW = 0;
        }
    }

    public float setX()
    {
        if ((player.getPosition().x)>=40.0f && (player.getPosition().x) <=40.0f)
            return player.getPosition().x;
        else
        {
            return 40.0f;
        }
    }

    public float setY()
    {
        if ((player.getPosition().y)<=24.0f)
        {
            return 24.0f;
        }
        else if ((player.getPosition().y)>=36.0f)
        {
            return 36.0f;
        }
        else{
            return player.getPosition().y;
        }

    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics graphics) throws SlickException {
        timestart = System.currentTimeMillis();
        graphics.setColor(Color.lightGray);
        graphics.drawString("Use 'W', 'A', 'S', 'D' to control your character!", 100, 200);
        graphics.drawString("Press Q to view debug mode.", 100, 220);
        graphics.drawString("Press 'P' to toggle the pause.", 100, 240);
        debugDrawJ2D.drawCircle(new Vec2(0, 0), 2, Color3f.WHITE);
        tutorialWorld.drawDebugData();
    }

    @Override
    public void keyPressed(int key, char c) {
        switch (key) {
            case Input.KEY_W:
                if (keyW == 0) {
                    f = player.getWorldVector(new Vec2(0f, 35f));
                    p = player.getWorldPoint(player.getLocalCenter().addLocal(0, 0));
                    player.applyLinearImpulse(f, p);
                    keyW = keyW + 1;
                }
                break;

        }
        switch (key) {
            case Input.KEY_D:
                if (player.getLinearVelocity().x < 0) {
                    verticalVel = player.getLinearVelocity().y;
                    player.setLinearVelocity(new Vec2(0, verticalVel));
                }
                keyDPressed = true;
                break;

            case Input.KEY_A:
                if (player.getLinearVelocity().x > 0) {
                    verticalVel = player.getLinearVelocity().y;
                    player.setLinearVelocity(new Vec2(0, verticalVel));
                }
                keyAPressed = true;
                break;

            case Input.KEY_P:
                Pause();
                break;

            default:
                break;
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        switch (key) {
            case Input.KEY_Q:
                UpdateDebugMode();
                break;

            case Input.KEY_ESCAPE:
                reset();
                game.enterState(legendofmurrgame.LegendOfMurr.MENU_ID);
                break;

            default:
                break;
        }
        switch (key) {
            case Input.KEY_D:
                keyDPressed = false;
                break;

            case Input.KEY_A:
                keyAPressed = false;
                break;

            default:
                break;
        }
    }
}
