/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utilities;

import org.jbox2d.common.Vec2;

/**
 *
 * @author Marieta
 */
public class CommonCode {

    public final static int SCALE = 10;
    public final static int[] DURATION = {300, 300};

    public static Vec2 ScreenToWorld( Vec2 coords ){
        return new Vec2( coords.x * SCALE, coords.y * -1 * SCALE);
    }

    public static Vec2 WorldToScreen( Vec2 coords ){
        return new Vec2( coords.x / SCALE, coords.y * -1 / SCALE);
    }

    public enum CharacterMovement{
        stand, jump, left, right;
    }

    public enum PlayerColour{
        white, red, green, blue, cyan, yellow, pink, gray, orange, purple;
    }

}
