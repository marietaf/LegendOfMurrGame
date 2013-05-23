/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package States;

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

     public Tutorial(int ID)
     {
         this.ID = ID;
     }

     @Override
    public int getID() {
        return ID;
    }

     public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

    }

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {

    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics graphics) throws SlickException {
        graphics.setColor(Color.lightGray);
        graphics.drawString("Use 'W', 'A', 'S', 'D' to control your character!", 100, 200);
    }

    @Override
    public void keyPressed(int key, char c){
        switch( key ){
            case Input.KEY_ESCAPE:
                System.exit(1);
                break;

            default:
                break;
        }
    }

}