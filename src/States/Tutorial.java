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
    float timeStep = 1.0f / 60.0f;
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
        tutorialWorld.step(timeStep, velocityIterations, positionInterations);


        debugDrawJ2D = new DebugDrawJ2D(gc);
        tutorialWorld.setDebugDraw(debugDrawJ2D);

        {  //GROUND
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(40, 1);

            BodyDef bd = new BodyDef();
            bd.type = BodyType.STATIC;
            bd.position.set(40.0f, 1.0f);

            FixtureDef fd = new FixtureDef();
            fd.shape = polygonShape;
            fd.userData = "ground";

            Body groundBody = tutorialWorld.createBody(bd);
            groundBody.createFixture(fd);
        }

        {  // PLAYER
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(5, 10);

            BodyDef bd = new BodyDef();
            bd.type = BodyType.DYNAMIC;
            bd.position.set(50.0f, 1.0f);

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

            circle = tutorialWorld.createBody(bd);
            circle.createFixture(fd);
        }
        
        {   //X and Y axis lines
            PolygonShape polygonShape = new PolygonShape();
            polygonShape.setAsBox(5, 0);

            BodyDef bd = new BodyDef();
            bd.type = BodyType.DYNAMIC;
            bd.position.set(0.0f, 0.0f);

            FixtureDef fd = new FixtureDef();
            fd.shape = polygonShape;
            fd.userData = "xaxis";

            circle = tutorialWorld.createBody(bd);
            circle.createFixture(fd);
            
            polygonShape.setAsBox(5, 0);
            fd.userData = "yaxis";
            circle = tutorialWorld.createBody(bd);
            circle.createFixture(fd);
        }

        tutorialWorld.setDebugDraw(debugDrawJ2D);
        
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        this.game = sbg;
        for (int j = 0; j < 60; j++) {
            Vec2 playerPosition = player.getPosition();
            System.out.println("Player Position: " + playerPosition);
            Vec2 circlePosition = circle.getPosition();
            System.out.println("Circle Position: " + circlePosition);
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
                }
                else if( !worldPause ){
                    tutorialWorld.step(0, 0, 0);
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
