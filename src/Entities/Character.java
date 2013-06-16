/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import Utilities.CommonCode;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Marieta
 */
public class Character extends Entity {

    Animation animationUp, animationStill, animationLeft, animationRight;
    protected Animation currentAnimation;
    //CHANGE ANIM COLOR TO ENUM!
    String animColor;
    PolygonShape shape;

    public Character( float x, float y, float width, float height,
                      String animPathName, int[] duration, String bodyUserData ) throws SlickException {
        super( x, y, BodyType.DYNAMIC, bodyUserData );
        this.animColor = "white";
        //FIX PATH NAMES ONCE IMAGES ARE DONE
        animationUp = new Animation(new Image[]{new Image(animPathName + "_jump_" + animColor +".png"), new Image(animPathName + "_jump_" + animColor + "2.png")}, duration, false);
        animationStill = new Animation(new Image[]{new Image(animPathName + "_stand_" + animColor + ".png"), new Image(animPathName + "_stand_" + animColor + "2.png")}, duration, false);
        animationRight = new Animation(new Image[]{new Image(animPathName + "_run1_" + animColor + "_rt.png"), new Image(animPathName + "_run2_" + animColor +"_rt.png")}, duration, false);
        animationLeft = new Animation(new Image[]{new Image(animPathName + "_run1_" + animColor + "_lt.png"), new Image(animPathName + "_run2_" + animColor + "_lt.png")}, duration, false);
        currentAnimation = animationStill;

        shape = new PolygonShape();
        shape.setAsBox(width, height);
        fd.shape = shape;
    }

    public Animation GetCurrentAnimation(){
        return currentAnimation;
    }

    public void SetCurrentAnimation( Animation currentAnimation ){
        this.currentAnimation = currentAnimation;
    }

    public String GetAnimColor(){
        return animColor;
    }

    public void SetAnimColor( String animColor ){
        this.animColor = animColor;
    }

    public void Update( long delta ){
        currentAnimation.update( delta );
    }

    public void Render(){
        currentAnimation.draw(body.getPosition().x * CommonCode.SCALE, body.getPosition().y * CommonCode.SCALE);
    }

}
