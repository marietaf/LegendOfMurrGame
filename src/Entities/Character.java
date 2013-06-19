/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import Utilities.CommonCode;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.IViewportTransform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Marieta - This class serves as the basic constructor for all characters
 * (players and enemies)
 */
public class Character extends Entity {

    Animation animationJump, animationStill, animationLeft, animationRight;
    protected Animation currentAnimation;
    //CHANGE ANIM COLOR TO ENUM!
    String animColor;
    PolygonShape shape;
    float height, width;

    public Character( float x, float y, float width, float height,
                      String animPathName, int[] duration, String bodyUserData ) throws SlickException {
        super( x, y, BodyType.DYNAMIC, bodyUserData );
        this.animColor = "white";
        //FIX PATH NAMES ONCE IMAGES ARE DONE
        animationJump = new Animation(new Image[]{new Image(animPathName + "_jump_" + animColor +".png"), new Image(animPathName + "_jump_" + animColor + "2.png")}, duration, false);
        animationStill = new Animation(new Image[]{new Image(animPathName + "_stand_" + animColor + ".png"), new Image(animPathName + "_stand_" + animColor + "2.png")}, duration, false);
        animationRight = new Animation(new Image[]{new Image(animPathName + "_run1_" + animColor + "_rt.png"), new Image(animPathName + "_run2_" + animColor +"_rt.png")}, duration, false);
        animationLeft = new Animation(new Image[]{new Image(animPathName + "_run1_" + animColor + "_lt.png"), new Image(animPathName + "_run2_" + animColor + "_lt.png")}, duration, false);
        currentAnimation = animationStill;

        shape = new PolygonShape();
        this.height = height;
        this.width = width;
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
        //Changes the image of the character  based on its motion
        if( body.getLinearVelocity().y != 0 )
            currentAnimation = animationJump;
        else if( body.getLinearVelocity().x > 0 )
            currentAnimation = animationRight;
        else if( body.getLinearVelocity().x < 0 )
            currentAnimation = animationLeft;
        else
            currentAnimation = animationStill;
        currentAnimation.update( delta );
    }

    public void Render( IViewportTransform viewport ){
        //FIX RENDERING FOR PLAYER (in the middle unless viewport has stopped moving)
//        float renderX = body.getPosition().x - viewport.getCenter().x + legendofmurrgame.LegendOfMurr.WIDTH/2 - 32;
//        float renderY = body.getPosition().y - viewport.getCenter().y + legendofmurrgame.LegendOfMurr.HEIGHT/2 - (animationJump.getImage(0).getHeight()-height)/2 - 5;
        Vec2 renderCoords = new Vec2(body.getPosition().x, body.getPosition().y);
        viewport.getWorldToScreen(renderCoords, renderCoords);
        currentAnimation.draw(renderCoords.x - 32, renderCoords.y - 32);
    }

}
