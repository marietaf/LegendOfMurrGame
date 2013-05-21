/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package legendofmurrgame;

import States.Menu;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author Marieta
 */
public class LegendOfMurr extends StateBasedGame {


    final static int MENU_ID = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SlickException {
        //WOOOHHH the game has begun!
        AppGameContainer app = new AppGameContainer(new LegendOfMurr("Legend of Murr"));
        app.setDisplayMode(500, 400, false);
        //app.setShowFPS(false);
        app.start();
    }

    public LegendOfMurr(String name){
        super(name);
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        addState(new Menu(MENU_ID));

        this.enterState(MENU_ID);
    }

}
