/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package States;

import legendofmurrgame.DebugDrawJ2D;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.IViewportTransform;
import org.jbox2d.common.Vec2;
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
 * @author root
 */
public class Play extends BasicGameState {

    int ID;
    StateBasedGame game;
    GameContainer gc;
    // Physics ~~~~~~~~~~~~~~~~~
    float timeStep = 1.0f / 60.0f;
    int velocityIterations = 6;
    int positionInterations = 2;
    World world;
    DebugDrawJ2D debugDraw;
    boolean debugMode;
    private final Vec2 gravity = new Vec2(0.0f, -9.81f);
    IViewportTransform viewportTransform;
    // Key Pressing ~~~~~~~~~~~~
    boolean worldPause;

    public Play(int ID) {
        this.ID = ID;
    }

    @Override
    public int getID() {
        return ID;
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        this.game = sbg;
        this.gc = gc;

        Initialize();
    }

    public void Initialize() {
        //INITIALIZE ALL VARIABLES HERE
        worldPause = true;

        world = new World(gravity);
        world.step(0, 0, 0);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        this.game = sbg;
        if (!worldPause) {
            world.step(timeStep, velocityIterations, positionInterations);
        }

    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics graphics) throws SlickException {
        graphics.setColor(Color.lightGray);
        graphics.drawString("Playing game...!", 50, 50);
    }

    public void Pause() {
        if (worldPause) {
            world.step(timeStep, velocityIterations, positionInterations);
            worldPause = false;
        } else if (!worldPause) {
            world.step(0, velocityIterations, positionInterations);
            worldPause = true;
            game.enterState(legendofmurrgame.LegendOfMurr.GAMEPAUSE_ID);
            worldPause = false;
        }
    }

    public void UpdateDebugMode() {
        if (debugMode) {
            debugDraw.clearFlags(DebugDraw.e_shapeBit);
            debugMode = false;
        } else if (!debugMode) {
            debugDraw.setFlags(DebugDraw.e_shapeBit);
            debugMode = true;
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        switch (key) {
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
                Initialize();
                game.enterState(legendofmurrgame.LegendOfMurr.MENU_ID);
                break;

            default:
                break;
        }
    }
}
