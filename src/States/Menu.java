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

    int ID;
    StateBasedGame game;

    public Menu(int ID){
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
        graphics.setColor(Color.lightGray);
        graphics.drawString("Legend of Murrrrr!", 200, 100);

        graphics.drawString("1. Play game!", 100, 200);
        graphics.drawString("2. Tutorial (Run this one!)", 100, 220);
        graphics.drawString("3. Exit", 100, 240);

        graphics.drawString("4. Test Game Over State", 100, 280);
    }

    @Override
    public void keyReleased(int key, char c){
        switch( key ){
            case Input.KEY_1:
                game.enterState(legendofmurrgame.LegendOfMurr.PLAY_ID);
                break;

            case Input.KEY_2:
                game.enterState(legendofmurrgame.LegendOfMurr.TUTORIAL_ID);
                break;

            case Input.KEY_3:
                System.exit(1);
                break;

            case Input.KEY_4:
                game.enterState(legendofmurrgame.LegendOfMurr.GAMEOVER_ID);
                break;

            case Input.KEY_ESCAPE:
                System.exit(1);
                break;

            default:
                break;
        }
    }

}
