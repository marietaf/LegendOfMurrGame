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

    public static Vec2 ScreenToWorldVec2( Vec2 coords ){
        return new Vec2( coords.x / SCALE, coords.y / SCALE);
    }

    public static float ScreenToWorldNum( float num ){
        return ( num / SCALE );
    }

    public static Vec2 WorldToScreenVec2( Vec2 coords ){
        return new Vec2( coords.x * SCALE, coords.y * SCALE);
    }

    public enum CharacterMovement{
        stand, jump, left, right;
    }

    public enum PlayerColour{
        white, red, green, blue, cyan, yellow, pink, gray, orange, purple;
    }

    public enum TileProperty{
        wall, player, item, platform, enemy, noproperty;
    }

}
