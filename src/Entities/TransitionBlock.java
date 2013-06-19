/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

/**
 *
 * @author Marieta
 */
public class TransitionBlock extends Wall {

    public TransitionBlock(float x, float y, float width, float height){
        super( x, y, width, height, 0.0f, "transition");
        fd.isSensor = true;
    }

}
