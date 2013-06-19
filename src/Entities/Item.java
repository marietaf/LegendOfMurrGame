/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Marieta - This is a subclass of the entity class
 * Items have not yet been implemented into the game
 */
public class Item extends Entity {

    Image image;
    String imagePathName;
    CircleShape shape;

    public Item( float x, float y, float radius,
                 String imagePathName, String bodyUserData ) throws SlickException{
        //Constructor for the item
        super(x, y, BodyType.KINEMATIC, bodyUserData);
        this.imagePathName = imagePathName;
        image = new Image( imagePathName );

        shape = new CircleShape();
        shape.setRadius(radius);
        fd.shape = shape;
    }

    public void Render(){
        image.draw(x, y);
    }

}
