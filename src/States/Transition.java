/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package States;

import Utilities.Levels;
import java.util.logging.Level;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Aron Yoo
 */
public class Transition extends BasicGameState{
    int ID;
    StateBasedGame game;
    GameContainer gc;
    Levels levels;
    Level currentLevel;

     public int getID() {
        return ID;
    }

     

     public void TransitionText(int ID)
    {

         if(ID==0)//Before the plain level right after the game is initialized
         {
             //Render black screen with text saying:
             //Murr was just an ordinary guy. He laughed. He jumped for joy. (W) He loved to run. (A, D)
             //He knew that whatever he did, there was always more to the right.

         }

         if(ID==1)//transition between plain level and grass level
         {
            //Render black screen with text saying:
            //When Murr opened the chest, the world filled with hues
            //Vivid colours now make up this world.

         }

         if(ID==2)//grass level to cave level
         {
             //render black screen with text saying:
             //The red button brought Murr to a sinister cave
         }

         if(ID==3)//cave level to snow level
         {
             //render black screen with text saying:
             //The blue button lead Murr to an icy land
         }

         if(ID==4)//after pressing red or yellow button on snow level
         {
             //render black screen with text saying:
             //The Legend of Murr continues...
         }
     }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void render(GameContainer gc, StateBasedGame sbg, org.newdawn.slick.Graphics grphcs) throws SlickException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
