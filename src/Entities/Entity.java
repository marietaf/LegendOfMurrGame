/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import org.jbox2d.dynamics.Body;

/**
 *
 * @author Marieta
 */
public class Entity {

    float x, y;
    Body body;

    public Entity(float x, float y){
        this.x = x;
        this.y = y;
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

}
