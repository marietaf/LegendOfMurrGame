/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyType;

/**
 *
 * @author Marieta - This is a subclass the extends the entity constructor
 */
public class Wall extends Entity {

    PolygonShape shape;
    float friction;

    public Wall(float x, float y, float width, float height, float friction, String bodyUserData ){
        super( x, y, BodyType.STATIC, bodyUserData);
        //Basic constructor for the walls
        this.friction = friction;

        shape = new PolygonShape();
        shape.setAsBox(width, height);
        fd.shape = shape;
        fd.friction = friction;
    }
    //No methods are needed for the walls since they are static

}
