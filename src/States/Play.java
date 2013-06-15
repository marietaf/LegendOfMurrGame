/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package States;

import Utilities.Level;
import java.util.logging.Logger;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author root
 */
public class Play extends BasicGameState {

    int ID;
    StateBasedGame game;
    GameContainer gc;
    Level levelTest;

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

    public void Initialize() throws SlickException {
        //INITIALIZE ALL VARIABLES HERE
        levelTest = new Level(00, gc, new Vec2(0.0f, -9.81f), new TiledMap("data/LOM maps.v2/LOM_cavelevel.tmx"));
        levelTest.AddWallBody(0, 0, 40, 2, 10.0f);
        levelTest.SetWorldPause(false);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        this.game = sbg;
        levelTest.Update();
        UpdatePause();

    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics graphics) throws SlickException {
        levelTest.Render();
        graphics.setColor(Color.lightGray);
        graphics.drawString("Playing game...!", 50, 50);
        graphics.drawString("Debug Mode: " + levelTest.GetDebugMode(), 50, 70);
    }

    public void UpdatePause() {
        if (levelTest.GetWorldPause()) {
            game.enterState(legendofmurrgame.LegendOfMurr.GAMEPAUSE_ID);
            levelTest.SetWorldPause(false);
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        switch (key) {
            case Input.KEY_P:
                levelTest.SetWorldPause(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        switch (key) {
            case Input.KEY_Q:
                if( levelTest.GetDebugMode() )
                    levelTest.SetDebugMode(false);
                else
                    levelTest.SetDebugMode(true);
                break;
                
            case Input.KEY_ESCAPE:
                try {
                    Initialize();
                } catch (SlickException ex) {
                    Logger.getLogger(Play.class.getName()).log(java.util.logging.Level.SEVERE, "Unable to initalize again~", ex);
                }
                game.enterState(legendofmurrgame.LegendOfMurr.MENU_ID);
                break;

            default:
                break;
        }
    }
}
