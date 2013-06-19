/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.awt.Image;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyType;

/**
 *
 * @author Aron Yoo
 */
public class Door extends Entity {



    PolygonShape shape;
    String imagePathName;
    Image image;

    public Door(float x, float y, float width, float height){
        super( x, y, BodyType.STATIC, "door");

        shape = new PolygonShape();
        shape.setAsBox(width, height);
        fd.shape = shape;
        

        }
               

}
