/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import Utilities.CommonCode;
import java.util.ArrayList;
import org.jbox2d.common.IViewportTransform;
import org.jbox2d.common.Vec2;
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

    //X and Y values are in WORLD
    float x, y;
    FixtureDef fd;
    BodyDef bd;
    Body body;
    Vec2 position;

    //Rectangle Shape
    public Entity(float x, float y, BodyType bdType, String bodyUserData ){
        position = new Vec2(x, y);
        
        fd = new FixtureDef();
        bd = new BodyDef();
        
        bd.type = bdType;
        bd.fixedRotation = true;
        bd.userData = bodyUserData;

        bodyList = new ArrayList<Body>();
    }

    public Vec2 GetPosition(){
        return position;
    }

    public Body GetBody(){
        return body;
    }

    public void UpdatePosition(){
        position = body.getPosition();
    }

    public void CreateBodyInWorld( World world ){
        bd.position = position;
        //NEVER use = new Body() to create a new body in a world
        body = world.createBody(bd);
        body.createFixture(fd);
        bodyList.add(body);
    }
    

}
