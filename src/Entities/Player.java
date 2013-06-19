/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import org.jbox2d.common.IViewportTransform;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Marieta - This is a subclass of the character constructor
 * A lot of the initializing is not done in this class but within the different states and utilities
 */
public class Player extends Character {


    public Player(  float x, float y, float width, float height,
                    String animPathName, int[] duration, String bodyUserData ) throws SlickException{
        super(x, y, width, height, animPathName, duration, "player");
    }
}
