/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package States;

import Utilities.Levels;
import java.util.logging.Level;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Aron Yoo - THIS HAS NOT BEEN IMPLEMENTED YET
 */
public class Transition extends BasicGameState {

    static int levelID;
    int ID;
    StateBasedGame game;
    GameContainer gc;
    Levels levels;
    Level currentLevel;
    int time=0;
    static String text1 = "";
    
    public Transition(int ID) {
        this.ID = ID;
    }

    @Override
    public int getID() {
        return ID;
    }

    public static void setTransitionText(int levelID) {

        if (levelID == 2)//grass level to cave level
        {
            //render black screen with text saying:
            text1 = "The red button brought Murr to a sinister cave";
        }

        else if (levelID == 3)//cave level to snow level
        {
            //render black screen with text saying:
            text1 = "The blue button lead Murr to an icy land";
        }

        else if (levelID == 4)//after pressing red or yellow button on snow level
        {
            //render black screen with text saying:
            text1 = "The Legend of Murr continues...";
        }
        
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics graphics) throws SlickException {
        graphics.setColor(Color.lightGray);
        graphics.drawString(text1+"", 50, 50);
    }

    public void updatetimer()
    {
        time++;
    }

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        updatetimer();
        if (time>=300)
        {
            game.enterState(1);
        }
    }
}
