/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import org.newdawn.slick.SlickException;

/**
 *
 * @author Marieta
 */
public class Enemy extends Character {

    public Enemy(float x, float y, float width, float height,
            String animPathName, int[] duration, String bodyUserData ) throws SlickException {
        super(x, y, width, height, animPathName, duration, bodyUserData);
    }
}
