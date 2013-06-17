/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import Utilities.CommonCode;
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
//        float renderX = CommonCode.WorldToScreenX(body.getPosition().x);
//        float renderY = CommonCode.WorldToScreenY(body.getPosition().y);
        float renderX = body.getPosition().x - viewport.getCenter().x + legendofmurrgame.LegendOfMurr.WIDTH;
        float renderY = body.getPosition().y - viewport.getCenter().y + legendofmurrgame.LegendOfMurr.HEIGHT;
        currentAnimation.draw(renderX, renderY);
    }

}
