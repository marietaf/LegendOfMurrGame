/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package legendofmurrgame;

import Utilities.CommonCode;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.IViewportTransform;
import org.jbox2d.common.MathUtils;
import org.jbox2d.common.OBBViewportTransform;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.pooling.arrays.IntArray;
import org.jbox2d.pooling.arrays.Vec2Array;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

/**
 *
 * @author Marieta
 */
public class DebugDrawJ2D extends DebugDraw {

    final public static int CIRCLE_POINTS = 20;
    GameContainer gameContainer;
    Graphics g;
    int scale;
    //drawSolidPolygon
    private final Vec2 temp = new Vec2();
    private final Vec2 temp2 = new Vec2();
    private final Vec2 sp1 = new Vec2();
    private final Vec2 sp2 = new Vec2();
    private final static IntArray xIntsPool = new IntArray();
    private final static IntArray yIntsPool = new IntArray();
    //drawCircle
    private final Vec2Array vec2Array = new Vec2Array();
    //drawSolidCircle
    private final Vec2 saxis = new Vec2();

    public DebugDrawJ2D(GameContainer gameContainer) {
        super(new OBBViewportTransform());
        this.gameContainer = gameContainer;
        this.g = gameContainer.getGraphics();
        this.scale = CommonCode.SCALE;

        this.viewportTransform.setYFlip(true);
        this.viewportTransform.setCamera(legendofmurrgame.LegendOfMurr.WIDTH / 2 / scale, legendofmurrgame.LegendOfMurr.HEIGHT / 2 / scale, scale);
        this.viewportTransform.setExtents(legendofmurrgame.LegendOfMurr.WIDTH / 2, legendofmurrgame.LegendOfMurr.HEIGHT / 2);
    }

    public IViewportTransform GetViewportTransform(){
        return viewportTransform;
    }

    @Override
    public void drawPoint(Vec2 argPoint, float argRadiusOnScreen, Color3f color) {
        getWorldToScreenToOut(argPoint, sp1);
            g.setColor(new Color(color.x,color.y,color.z));

            sp1.x -= argRadiusOnScreen;
            sp1.y -= argRadiusOnScreen;
            g.fillOval((int)sp1.x, (int)sp1.y, (int)argRadiusOnScreen*2, (int)argRadiusOnScreen*2);
            g.setColor(Color.white);
    }

    @Override
    public void drawSolidPolygon(Vec2[] vertices, int vertexCount, Color3f color) {
        // inside
        int[] xInts = xIntsPool.get(vertexCount);
        int[] yInts = yIntsPool.get(vertexCount);
        Polygon polygon = new Polygon();

        for (int i = 0; i < vertexCount; i++) {
            getWorldToScreenToOut(vertices[i], temp);
            xInts[i] = (int) temp.x;
            yInts[i] = (int) temp.y;
            polygon.addPoint(xInts[i], yInts[i]);
        }

        g.setColor(new Color(color.x, color.y, color.z));
        g.fill(polygon);
        g.setColor(Color.white);

        // outside
        drawPolygon(vertices, vertexCount, color);
    }

    @Override
    public void drawCircle(Vec2 center, float radius, Color3f color) {
        final Vec2[] vecs = vec2Array.get(CIRCLE_POINTS );
        generateCircle(center, radius, vecs, CIRCLE_POINTS);
        drawPolygon(vecs, CIRCLE_POINTS, color);
    }

    @Override
    public void drawSolidCircle(Vec2 center, float radius, Vec2 axis, Color3f color) {
        Vec2[] vecs = vec2Array.get(CIRCLE_POINTS);
        generateCircle(center, radius, vecs, CIRCLE_POINTS);
        drawSolidPolygon(vecs, CIRCLE_POINTS, color);
        if (axis != null) {
            saxis.set(axis).mulLocal(radius).addLocal(center);
            drawSegment(center, saxis, color);
        }
        g.setColor(Color.white);
    }

    @Override
    public void drawSegment(Vec2 p1, Vec2 p2, Color3f color) {
        getWorldToScreenToOut(p1, sp1);
        getWorldToScreenToOut(p2, sp2);
        g.setColor(new Color(color.x,color.y,color.z));

        g.drawLine((int)sp1.x, (int)sp1.y, (int)sp2.x, (int)sp2.y);
        g.setColor(Color.white);
    }

    @Override
    public void drawTransform(final Transform xf) {
        getWorldToScreen(xf.p.x, temp.x);
        temp2.setZero();
        final float k_axisScale = 0.4f;

        g.setColor(new Color(1, 0, 0));
        //things missing - check https://gist.github.com/liamzebedee/3074904
        temp2.x = xf.p.x + k_axisScale;
        temp2.y = xf.p.y + k_axisScale;
        getWorldToScreenToOut(temp2, temp2);
        g.drawLine((int)temp.x, (int)temp.y, (int)temp2.x, (int)temp2.y);

        g.setColor(new Color(0, 1, 0));
        temp2.x = xf.p.x + k_axisScale;
        temp2.y = xf.p.y + k_axisScale;
        getWorldToScreenToOut(temp2, temp2);
        g.drawLine((int)temp.x, (int)temp.y, (int)temp2.x, (int)temp2.y);
        g.setColor(Color.white);

    }

    @Override
    public void drawString(float x, float y, String s, Color3f color) {
        g.setColor(new Color(color.x, color.y, color.z));
        g.drawString(s, x, y);
        g.setColor(Color.white);
    }

    private void generateCircle(Vec2 argCenter, float argRadius, Vec2[] argPoints, int argNumPoints) {
        float inc = MathUtils.TWOPI / argNumPoints;

        for (int i = 0; i < argNumPoints; i++) {
            argPoints[i].x = (argCenter.x + MathUtils.cos(i * inc) * argRadius);
            argPoints[i].y = (argCenter.y + MathUtils.sin(i * inc) * argRadius);
        }
        g.setColor(Color.white);
    }
}