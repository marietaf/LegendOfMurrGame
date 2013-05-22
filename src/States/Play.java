/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package States;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author root
 */
public class Play extends BasicGameState {

    int ID;

    public Play(int ID){
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
        graphics.drawString("Playing game...!", 50, 50);
    }

}
