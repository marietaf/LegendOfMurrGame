/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import Utilities.CommonCode;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Marieta - This is a subclass of the character class
 */
public class Enemy extends Character {

    public Enemy(float x, float y, float width, float height,
            String animPathName, int[] duration, String bodyUserData ) throws SlickException {
        super(x, y, width, height, animPathName, duration, bodyUserData);
    }

    public void Render(){
        //assigns the image to its stating location in the level
        currentAnimation.draw(CommonCode.WorldToScreenVec2(position).x, CommonCode.WorldToScreenVec2(position).y);
    }
}
