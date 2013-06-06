/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import java.util.ArrayList;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 *
 * @author Marieta
 */
public class Entity {

    static ArrayList <Body> bodyList;

    float x, y;
    FixtureDef fd;
    BodyDef bd;
    Shape fdShape;
    Body body;

    public Entity(float x, float y, BodyType bdType, String bodyUserData ){
        this.x = x;
        this.y = y;
        
        fd = new FixtureDef();
        bd = new BodyDef();
        bd.type = bdType;
        bd.userData = bodyUserData;
    }

    public float GetX(){
        return x;
    }

    public float GetY(){
        return y;
    }

    public void SetX( float x ){
        this.x = x;
    }

    public void SetY( float y ){
        this.y = y;
    }

    public void SetNewCoordinates( float x, float y ){
        this.x = x;
        this.y = y;
    }

    public void CreateBodyInWorld( World world ){
        body = new Body(bd, world);
        body.createFixture(fd);
        bodyList.add(body);
    }

}
