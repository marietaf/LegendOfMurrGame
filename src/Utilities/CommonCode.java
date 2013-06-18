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

    public final static int SCALE = 8;
    public final static int[] DURATION = {300, 300};

    public static Vec2 ScreenToWorldVec2( Vec2 coords ){
        return new Vec2( coords.x / SCALE, coords.y / SCALE * -1);
    }

    public static float ScreenToWorldX( float num ){
        return ( num / SCALE );
    }

    public static float ScreenToWorldY( float num ){
        return ( num / SCALE * -1 );
    }

    public static Vec2 WorldToScreenVec2( Vec2 coords ){
        return new Vec2( coords.x * SCALE, coords.y * SCALE * -1);
    }

    public static float WorldToScreenX( float num ){
        return (num * SCALE );
    }

    public static float WorldToScreenY( float num ){
        return (num * SCALE * -1 );

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
