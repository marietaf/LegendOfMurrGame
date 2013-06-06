/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Marieta
 */
public class Item extends Entity {

    Image image;
    String imagePathName;

    public Item( float x, float y,
                 String imagePathName, String bodyUserData ) throws SlickException{
        super(x, y, BodyType.KINEMATIC, bodyUserData);
        this.imagePathName = imagePathName;
        image = new Image( imagePathName );
    }

}
