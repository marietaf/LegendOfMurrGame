/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import org.jbox2d.common.IViewportTransform;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Marieta
 */
public class Player extends Character {


    public Player(  float x, float y, float width, float height,
                    String animPathName, int[] duration, String bodyUserData ) throws SlickException{
        super(x, y, width, height, animPathName, duration, "player");
    }

    public void Render( IViewportTransform viewport ){
        //FIX RENDERING FOR PLAYER (in the middle unless viewport has stopped moving)
        currentAnimation.draw(0, 0);
    }

}
