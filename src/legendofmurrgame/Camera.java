/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package legendofmurrgame;

import org.jbox2d.common.Vec2;

/**
 *
 * @author Marieta
 */
public class Camera {

    public int x, y, sx, sy;
    //number of pixels per meter (because jbox2d uses meters)
    public static final float PIXEL_PER_METER = 50.0f;
    //Use to flip between coordinate system of JBox2D and screen (they have opposite y directions)
    float yFlip = -1.0f;

    public Camera(){
        x = 0;
        y = 0;
        sx = x + LegendOfMurr.WIDTH;
        sy = y + LegendOfMurr.HEIGHT;
    }

    public Camera( int x, int y, float pixelsPerMeter ){
        this.x = x;
        this.y = y;
        sx = x + LegendOfMurr.WIDTH;
    }

    public void updateCamera(){
        sx = x + LegendOfMurr.WIDTH;
        sy = y + LegendOfMurr.HEIGHT;
    }

    public void moveCamera( int mx, int my ){
        //If the coordinates are near 80 pixels of the border, move the camera accordingly
        if( mx >= 0 && mx <= 80 ){
            this.x -= 2;
        }
        else if( mx <= LegendOfMurr.WIDTH && mx >= LegendOfMurr.WIDTH - 80 ){
            this.x += 2;
        }
        //Also move on the y axis if near the border
        if( my >= 0 && my <= 80 ){
            this.y += 2;
        }
        else if( my <= LegendOfMurr.HEIGHT && my >= LegendOfMurr.HEIGHT - 80 ){
            this.y -= 2;
        }
        this.updateCamera();
    }

    //CONVERSIONS - between the physics world and pixels (because JBox2D works in meters, not pixels)

    public float pixelToMeter( float pixel ){
        return pixel / PIXEL_PER_METER;
    }

    public float meterToPixel( float meter ){
        return meter * PIXEL_PER_METER;
    }

    public Vec2 screenToWorld( Vec2 screenVector ){
        return new Vec2( this.x + screenVector.x / PIXEL_PER_METER,
                yFlip * ( this.y + screenVector.y / PIXEL_PER_METER ) );
    }

    public Vec2 worldToScreen( Vec2 worldVector ){
        return new Vec2( ( worldVector.x - this.x ) * PIXEL_PER_METER ,
                ( yFlip * worldVector.y - this.y ) * PIXEL_PER_METER );
    }

}
