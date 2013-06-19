/*
 * By Marieta Farova, Kadin Choi and Aron Yoo
 * During the month of May 2013 and onward.
 * Created as a project for Computer Science ICS4UI to show development of skills
 * and to present knowledge of object oriented programming learned in class.
 */

package legendofmurrgame;

import States.GameOver;
import States.Menu;
import States.Play;
import States.Tutorial;
import States.GamePause;
import States.Transition;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Log;

/**
 *
 * @author Marieta
 */
public class LegendOfMurr extends StateBasedGame {

    //DECLARE FINAL VARIABLES
    //State numbers
    public final static int MENU_ID = 0;
    public final static int PLAY_ID = 1;
    public final static int GAMEOVER_ID = 2;
    public final static int TUTORIAL_ID = 3;
    public final static int GAMEPAUSE_ID = 4;
    public final static int TRANSITION_ID = 5;
    //Application specific final variables
    public final static int WIDTH = 800;
    public final static int HEIGHT = 480;


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SlickException {
        //WOOOHHH the game has begun!

        Log.info("Game start!");

        AppGameContainer app = new AppGameContainer(new LegendOfMurr("Legend of Murr"));
        app.setDisplayMode(WIDTH, HEIGHT, false);
        app.setTargetFrameRate(120);
//        app.setShowFPS(false);
//        app.setFullscreen(true);
        app.start();

        Log.info("App successfully created.");
    }

    public LegendOfMurr(String name){
        super(name);
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {
        addState(new Menu(MENU_ID));
        addState(new Play(PLAY_ID));
        addState(new GameOver(GAMEOVER_ID));
        addState(new Tutorial(TUTORIAL_ID));
        addState(new GamePause(GAMEPAUSE_ID));
        addState(new Transition(TRANSITION_ID));

        //Enter the main menu at first
        this.enterState(MENU_ID);
    }

}
