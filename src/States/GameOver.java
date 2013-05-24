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
public class GameOver extends BasicGameState {

    int ID;
    StateBasedGame game;

    public GameOver(int ID){
        this.ID = ID;
    }

    @Override
    public int getID() {
        return ID;
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        this.game = sbg;
    }

    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException {

    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics graphics) throws SlickException {
        int appWidth = legendofmurrgame.LegendOfMurr.WIDTH;
        int appHeight = legendofmurrgame.LegendOfMurr.HEIGHT;
        graphics.setColor(Color.lightGray);
        graphics.drawString("Game Over!", (appWidth/2) - 40, (appHeight/2) - 20);
        graphics.drawString("Please try again.", (appWidth/2) - 75, (appHeight/2));
    }

    @Override
    public void keyReleased(int key, char c){
        switch( key ){
            case Input.KEY_ESCAPE:
                game.enterState(legendofmurrgame.LegendOfMurr.MENU_ID);
                break;

            default:
                break;
        }
    }

}
