/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import org.jbox2d.common.IViewportTransform;
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
        float renderX = body.getPosition().x - viewport.getCenter().x + legendofmurrgame.LegendOfMurr.WIDTH/2 - 32;
        float renderY = body.getPosition().y - viewport.getCenter().y + legendofmurrgame.LegendOfMurr.HEIGHT/2 - 48;
        System.out.println("Renderx: " + renderX);
        System.out.println("Rendery: " + renderY);
        currentAnimation.draw(renderX, renderY);
    }

}
