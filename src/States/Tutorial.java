/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package States;

import legendofmurrgame.Camera;
import legendofmurrgame.DebugDrawJ2D;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Color;
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

    int ID;
    StateBasedGame game;
    float timeStep = 1.0f / 120.0f;
    int velocityIterations = 6;
    int positionInterations = 2;
    World tutorialWorld;
    Body player, circle;
    DebugDrawJ2D debugDrawJ2D;
    boolean debugMode;
    Camera camera = new Camera(400, 300, 60);
    boolean worldPause;

    public Tutorial(int ID) {
        this.ID = ID;
    }

    @Override
    public int getID() {
        return ID;
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        this.game = sbg;
        debugMode = true;
        worldPause = true;

        //TEST JBOX2D for Tutorial!
        Vec2 gravity = new Vec2(0.0f, -9.81f);
        tutorialWorld = new World(gravity);
        tutorialWorld.step(0, 0, 0);


        debugDrawJ2D = new DebugDrawJ2D(gc);
        tutorialWorld.setDebugDraw(debugDrawJ2D);

        {  //WALLS
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(40, 1);

            BodyDef bd = new BodyDef();
            bd.type = BodyType.STATIC;
            bd.position.set(40.0f, 1.0f);

            FixtureDef fd = new FixtureDef();
            fd.shape = polygonShape;
            fd.userData = "wall";

            Body bottomWall = tutorialWorld.createBody(bd);
            bottomWall.createFixture(fd);

            //TOP
            polygonShape.setAsBox(40, 1);
            bd.position.set(40.0f, 2.0f);
            Body topWall = tutorialWorld.createBody(bd);
            topWall.createFixture(fd);

            //LEFT
            polygonShape.setAsBox(1, 40);
            bd.position.set(0.0f, 0.0f);
            tutorialWorld.createBody(bd);
            Body leftWall = tutorialWorld.createBody(bd);
            topWall.createFixture(fd);

            //RIGHT
            polygonShape.setAsBox(1, 20);
            bd.position.set(160.0f, 0.0f);
            tutorialWorld.createBody(bd);
            Body rightWall = tutorialWorld.createBody(bd);
            topWall.createFixture(fd);
        }

        {  // PLAYER
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(3, 5);

            BodyDef bd = new BodyDef();
            bd.type = BodyType.DYNAMIC;
            bd.position.set(50.0f, 20.0f);

            FixtureDef fd = new FixtureDef();
            fd.shape = polygonShape;
            fd.userData = "player";

            player = tutorialWorld.createBody(bd);
            player.createFixture(fd);
        }

        {   //test circle
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

        tutorialWorld.setDebugDraw(debugDrawJ2D);
        
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        this.game = sbg;
        if( !worldPause ){
            tutorialWorld.step(timeStep, velocityIterations, positionInterations);
        }
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics graphics) throws SlickException {
        graphics.setColor(Color.lightGray);
        graphics.drawString("Use 'W', 'A', 'S', 'D' to control your character!", 100, 200);
        graphics.drawString("Press Q to view debug mode.", 100, 220);
        debugDrawJ2D.drawCircle(new Vec2(0, 0), 2, Color3f.WHITE);
        tutorialWorld.drawDebugData();
    }

    @Override
    public void keyPressed(int key, char c){
        Vec2 f, p;
        float velChange, impulse;
        float verticalVel = player.getLinearVelocity().y;
        switch(key){
            case Input.KEY_W:
                f = player.getWorldVector(new Vec2(0f, 15f));
                p = player.getWorldPoint(player.getLocalCenter().addLocal(0, 0));
                player.applyLinearImpulse(f, p);
                break;

            case Input.KEY_D:
                if (player.getLinearVelocity().x<0)
                {
                    player.setLinearVelocity(new Vec2(0.0f,player.getLinearVelocity().y));
                    break;
                }
                player.setLinearVelocity(new Vec2(0, verticalVel));

                velChange = 10 - player.getLinearVelocity().x;
                impulse = player.getMass() * velChange*0.5f;
                f = player.getWorldVector(new Vec2(impulse, 0f));
                p = player.getWorldPoint(player.getLocalCenter());
                player.applyLinearImpulse(f, p);
                break;

            case Input.KEY_A:
                if (player.getLinearVelocity().x>0)
                {
                    player.setLinearVelocity(new Vec2(0.0f,player.getLinearVelocity().y));
                    break;
                }
                player.setLinearVelocity(new Vec2(0, verticalVel));
                velChange = Math.max( -10 - player.getLinearVelocity().x, -10.0f );
                impulse = player.getMass() * velChange*0.5f;
                f = player.getWorldVector(new Vec2(impulse, 0f));
                p = player.getWorldPoint(player.getLocalCenter().addLocal(0, 0));
                player.applyLinearImpulse(f, p);
                break;
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        switch (key) {
            case Input.KEY_Q:
                if( debugMode ){
                    debugDrawJ2D.clearFlags( DebugDraw.e_shapeBit );
                    debugMode = false;
                }
                else if( !debugMode ){
                    debugDrawJ2D.setFlags( DebugDraw.e_shapeBit );
                    debugMode = true;
                }
                break;

            case Input.KEY_P:
                if( worldPause ){
                    tutorialWorld.step(timeStep, velocityIterations, positionInterations);
                    worldPause = false;
                }
                else if( !worldPause ){
                    tutorialWorld.step(0, velocityIterations, positionInterations);
                    worldPause = true;
                }
                break;

            case Input.KEY_ESCAPE:
                game.enterState(legendofmurrgame.LegendOfMurr.MENU_ID);
                break;

            default:
                break;
        }
    }
}
