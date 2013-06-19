/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package States;

import Utilities.Level;
import Utilities.Levels;
import java.util.logging.Logger;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
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
public class Play extends BasicGameState {

    //State variables
    int ID;
    StateBasedGame game;
    GameContainer gc;
    Levels levels;
    Graphics graphics;
    Level currentLevel;
    //Player movement variables
    Body playerBody;
    boolean keyWPressed, keyAPressed,keyDPressed;
    Vec2 f, p;
    float velChange, impulse;
    float verticalVel;

    public Play(int ID) {
        this.ID = ID;
    }

    @Override
    public int getID() {
        return ID;
    }

    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
        this.game = sbg;
        this.gc = gc;

        Initialize();
    }

    public void Initialize() throws SlickException {
        //INITIALIZE ALL VARIABLES HERE
        levels = new Levels(gc);
        levels.InitalizeLevels();
        currentLevel = levels.GetCurrentLevel();
        playerBody = currentLevel.GetPlayer().GetBody();
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        this.game = sbg;
        currentLevel.Update();
        UpdateCurrentLevel();
        UpdatePause();
        UpdatePlayer();
        UpdateEntityAnimations(delta);
        CheckLevelChange();
        UpdateGameOver();
    }

//    public void TransitionBegin() throws SlickException{
//        Transition.setTransitionText(currentLevel.LEVEL_ID);
//        game.enterState(5);
//    }


    public void UpdateCurrentLevel() throws SlickException{
//        if( currentLevel != levels.GetCurrentLevel() )
//            TransitionBegin();
        currentLevel = levels.GetCurrentLevel();
        playerBody = currentLevel.GetPlayer().GetBody();
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics graphics) throws SlickException {
        currentLevel.Render();
        System.out.println("currentlevel: " + currentLevel.GetLevelID());
        graphics.setColor(Color.lightGray);
        graphics.drawString("Debug Mode: " + currentLevel.GetDebugMode(), 50, 70);
    }

    public void UpdateGameOver()
    {
        if (currentLevel.getGameOver())
        {
            currentLevel.getWorld().clearForces();
            try {
                    Initialize();
                } catch (SlickException ex) {
                    Logger.getLogger(Play.class.getName()).log(java.util.logging.Level.SEVERE, "Unable to initalize again~", ex);
                }
            game.enterState(legendofmurrgame.LegendOfMurr.GAMEOVER_ID);
        }
    }

    public void UpdatePause() {
        if (currentLevel.GetWorldPause()) {
            game.enterState(legendofmurrgame.LegendOfMurr.GAMEPAUSE_ID);
            currentLevel.SetWorldPause(false);
        }
    }

    public void UpdatePlayer(){
        //Changes the player's horizontal velocity when a button is pressed
        if (keyAPressed) {
            velChange = Math.max(-30 - playerBody.getLinearVelocity().x, -10.0f);
            impulse = playerBody.getMass() * velChange * 0.5f;
            f = playerBody.getWorldVector(new Vec2(impulse, 0f));
            p = playerBody.getWorldPoint(playerBody.getLocalCenter().addLocal(0, 0));
            playerBody.applyLinearImpulse(f, p);
        }
        if (keyDPressed) {
            velChange = 30 - playerBody.getLinearVelocity().x;
            impulse = playerBody.getMass() * velChange * 0.5f;
            f = playerBody.getWorldVector(new Vec2(impulse, 0f));
            p = playerBody.getWorldPoint(playerBody.getLocalCenter());
            playerBody.applyLinearImpulse(f, p);
        }
        //This boolean checks to prevent jumping while mid-air
        if (playerBody.getLinearVelocity().y == 0)
            keyWPressed = false;
        else
            keyWPressed = true;
    }

    public void UpdateEntityAnimations( int delta ){
        currentLevel.GetPlayer().Update(delta);
    }

    public void CheckLevelChange(){
        if( currentLevel.GetLevelChangeListener().GetLevelChange() )
            levels.ChangeLevel();
    }

    @Override
    public void keyPressed(int key, char c) {
        switch (key) {
            case Input.KEY_W:
                //Applies a force to jump instead of changing the velocity of the player
                if ( !keyWPressed ) {
                    f = playerBody.getWorldVector(new Vec2(0f, 45f));
                    p = playerBody.getWorldPoint(playerBody.getLocalCenter().addLocal(0, 0));
                    playerBody.applyLinearImpulse(f, p);
                }
                break;

        }
        switch (key) {
            case Input.KEY_D:
                if (playerBody.getLinearVelocity().x < 0) {
                    verticalVel = playerBody.getLinearVelocity().y;
                    playerBody.setLinearVelocity(new Vec2(0, verticalVel));
                }
                keyDPressed = true;
                break;

            case Input.KEY_A:
                if (playerBody.getLinearVelocity().x > 0) {
                    verticalVel = playerBody.getLinearVelocity().y;
                    playerBody.setLinearVelocity(new Vec2(0, verticalVel));
                }
                keyAPressed = true;
                break;

                //Pause function
            case Input.KEY_P:
                currentLevel.SetWorldPause(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        switch (key) {
            //Toggles debugmode
            case Input.KEY_Q:
                if( currentLevel.GetDebugMode() )
                    currentLevel.SetDebugMode(false);
                else
                    currentLevel.SetDebugMode(true);
                break;

                //Exits the play State
            case Input.KEY_ESCAPE:
                try {
                    Initialize();
                } catch (SlickException ex) {
                    Logger.getLogger(Play.class.getName()).log(java.util.logging.Level.SEVERE, "Unable to initalize again~", ex);
                }
                game.enterState(legendofmurrgame.LegendOfMurr.MENU_ID);
                break;

            default:
                break;
        }
        switch (key) {
            case Input.KEY_D:
                keyDPressed = false;
                break;

            case Input.KEY_A:
                keyAPressed = false;
                break;

            default:
                break;
        }
    }
}
