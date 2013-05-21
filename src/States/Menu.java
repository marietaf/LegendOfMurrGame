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
 * @author root
 */
public class Menu extends BasicGameState {

    static int ID;

    public Menu(int ID){
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
        graphics.drawString("Legend of Murrrrr!", 50, 50);

        graphics.drawString("1. Play game!", 50, 120);
        graphics.drawString("2. Exit", 50, 140);
    }

    @Override
    public void keyReleased(int key, char c){
        switch( key ){
            case Input.KEY_1:
                break;

            case Input.KEY_2:
                System.exit(1);
                break;

            default:
                break;
        }
    }

}
