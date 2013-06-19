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
public class GamePause extends BasicGameState{
    int ID;
    StateBasedGame game;

    public GamePause(int ID){
        this.ID = ID;
    }

    @Override
    public int getID() {
        return ID;
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        this.game = sbg;
    }

    public void update(GameContainer gc, StateBasedGame sbc, int arg2) throws SlickException {
    
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics graphics) throws SlickException {

        int appWidth = legendofmurrgame.LegendOfMurr.WIDTH;
        int appHeight = legendofmurrgame.LegendOfMurr.HEIGHT;
        graphics.setColor(Color.lightGray);
        graphics.drawString("Game Paused!", (appWidth/2) - 40, (appHeight/2) - 20);
        graphics.drawString("Press 'P' to unpause!", (appWidth/2) - 75, (appHeight/2));
    }
    
    @Override
    public void keyPressed(int key, char c){
        switch( key ){
            case Input.KEY_P:
                game.enterState(legendofmurrgame.LegendOfMurr.PLAY_ID);
                break;

            default:
                break;
        }
    }
}
