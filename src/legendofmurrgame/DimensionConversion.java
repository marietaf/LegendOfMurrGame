/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package legendofmurrgame;

/**
 *
 * @author root
 */
public class DimensionConversion {

    static float PIXELS_PER_METER;

    public DimensionConversion( float pixelsPerMeter ){
        this.PIXELS_PER_METER = pixelsPerMeter;
    }

    public static float PixelsToMeters( float pixels ){
        return pixels / PIXELS_PER_METER;
    }

    public static float MetersToPixels( float meters ){
        return meters * PIXELS_PER_METER;
    }

}
