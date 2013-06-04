/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

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

    public Character( float x, float y,
                      String animPathName, int[] duration ) throws SlickException {
        super(x, y);
        this.animColor = "white";
        //FIX PATH NAMES ONCE IMAGES ARE DONE
        animationUp = new Animation(new Image[]{new Image(animPathName + animColor +"_bk1.gif"), new Image(animPathName + "_bk2.gif")}, duration, false);
        animationStill = new Animation(new Image[]{new Image(animPathName + "_fr1.gif"), new Image(animPathName + "_fr2.gif")}, duration, false);
        animationLeft = new Animation(new Image[]{new Image(animPathName + "_lf1.gif"), new Image(animPathName + "_lf2.gif")}, duration, false);
        animationRight = new Animation(new Image[]{new Image(animPathName + "_rt1.gif"), new Image(animPathName + "_rt2.gif")}, duration, false);
        currentAnimation = animationStill;
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
        currentAnimation.draw(x, y);
    }

}
