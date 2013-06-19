/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Marieta
 */
public class Platform extends Entity {

    float startX, startY, endX, endY, speed, timer;
    final int MOVEMENT_DELAY = 500;
    Vec2 velocity;
    PolygonShape shape;
    String imagePathName;
    Image image;
    String direction;

    public Platform( float x, float y, float width, float height, String imagePathName,
                     String direction, float speed ) throws SlickException{
        super( x, y, BodyType.KINEMATIC, "platform");
        this.imagePathName = imagePathName;
        this.startX = x;
        this.startY = y;
        this.direction = direction;
        if( direction.equalsIgnoreCase("vertical") ){
            this.endX = x;
            this.endY = 4*y;
        }
        else if( direction.equalsIgnoreCase("horizontal") ){
            this.endX = 4*x;
            this.endY = y;
        }
        else{
            this.endX = startX;
            this.endY = startY;
        }
        this.speed = speed;

        shape = new PolygonShape();
        shape.setAsBox(width, height);
        fd.shape = shape;
        image = new Image("data/moving_platform.png");
    }

    public float GetStartX(){
        return startX;
    }

    public float GetStartY(){
        return startY;
    }

    public float GetSpeed(){
        return speed;
    }


    public void UpdatePlatform(){
        if( x == startX && y == startY ){
            velocity.set(( endX - x ), ( endY - y ));
            body.setLinearVelocity(velocity);
        }
        else if( x == endX && y == endY ){
            velocity.set(( startX - x ), ( startY - y ));
            body.setLinearVelocity(velocity);
        }
    }

    public void Render(){
        image.draw(x, y);
    }

}
