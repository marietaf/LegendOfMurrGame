/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyType;

/**
 *
 * @author Marieta
 */
public class Wall extends Entity {

    PolygonShape shape;
    float friction;

    public Wall(float x, float y, float width, float height, float friction, String bodyUserData ){
        super( x, y, BodyType.STATIC, bodyUserData);
        this.friction = friction;

        shape = new PolygonShape();
        shape.setAsBox(width, height);
        fd.shape = shape;
        fd.friction = friction;
    }

}
