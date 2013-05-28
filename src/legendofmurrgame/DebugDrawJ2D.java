/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package legendofmurrgame;

import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Color3f;
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
        this.scale = 10;

        this.viewportTransform.setYFlip(true);
        this.viewportTransform.setCamera(legendofmurrgame.LegendOfMurr.WIDTH / 2 / scale, legendofmurrgame.LegendOfMurr.HEIGHT / 2 / scale, scale);
        this.viewportTransform.setExtents(legendofmurrgame.LegendOfMurr.WIDTH / 2, legendofmurrgame.LegendOfMurr.HEIGHT / 2);
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
///**
// * Implementation of {@link DebugDraw} that uses Java2D! Hooray!</br>
// *
// * @author Daniel Murphy
// */
//public class DebugDrawJ2D extends DebugDraw {
//
//    Graphics2D g;
//    private final float scale;
//    public static int CIRCLE_POINTS = 13;
//    private final Vec2Array vec2Array = new Vec2Array();
//    private final Vec2 temp2 = new Vec2();
//
//    public DebugDrawJ2D(int width, int height, float scale) {
//        super(new OBBViewportTransform());
//        this.scale = scale;
//        viewportTransform.setCamera(width / 2 / scale, height / 2 / scale, scale);
//        viewportTransform.setExtents(width / 2, height / 2);
//    }
//
//    @Override
//    public void drawCircle(Vec2 center, float radius, Color3f color) {
////        Vec2[] vecs = vec2Array.get(CIRCLE_POINTS);
////        generateCirle(center, radius, vecs, CIRCLE_POINTS);
////        drawPolygon(vecs, CIRCLE_POINTS, color);
//
//        Color oldColor = g.getColor();
//        g.setColor(new Color(color.x / 255, color.y / 255, color.z / 255));
//        Vec2 v = new Vec2(center.x - radius, center.y - radius);
//        viewportTransform.getWorldToScreen(v, v);
//        int r = (int) (radius * scale);
//        g.drawArc((int) v.x, (int) v.y, 2 * r, 2 * r, 0, 360);
//        g.setColor(oldColor);
//    }
//
//    @Override
//    public void drawPoint(Vec2 argPoint, float argRadiusOnScreen, Color3f argColor) {
//        getWorldToScreenToOut(argPoint, sp1);
//        Graphics2D g = getGraphics();
//
//        g.setColor(Color.CYAN);
//        sp1.x -= argRadiusOnScreen;
//        sp1.y -= argRadiusOnScreen;
//        g.fillOval((int) sp1.x, (int) sp1.y, (int) argRadiusOnScreen * 2, (int) argRadiusOnScreen * 2);
//    }
//    private final Vec2 sp1 = new Vec2();
//    private final Vec2 sp2 = new Vec2();
//
//    @Override
//    public void drawSegment(Vec2 p1, Vec2 p2, Color3f color) {
//        getWorldToScreenToOut(p1, sp1);
//        getWorldToScreenToOut(p2, sp2);
//
//        Graphics2D g = getGraphics();
//        g.setColor(Color.GREEN);
//
//        g.drawLine((int) sp1.x, (int) sp1.y, (int) sp2.x, (int) sp2.y);
//    }
//
//    public void drawAABB(AABB argAABB, Color3f color) {
//        Vec2 vecs[] = vec2Array.get(4);
//        argAABB.getVertices(vecs);
//        drawPolygon(vecs, 4, color);
//    }
//    private final Vec2 saxis = new Vec2();
//
//    @Override
//    public void drawSolidCircle(Vec2 center, float radius, Vec2 axis, Color3f color) {
//        Vec2[] vecs = vec2Array.get(CIRCLE_POINTS);
//        generateCirle(center, radius, vecs, CIRCLE_POINTS);
//        drawSolidPolygon(vecs, CIRCLE_POINTS, color);
//        if (axis != null) {
//            saxis.set(axis).mulLocal(radius).addLocal(center);
//            drawSegment(center, saxis, color);
//        }
//    }
//    // TODO change IntegerArray to a specific class for int[] arrays
//    private final Vec2 temp = new Vec2();
//    private final static IntArray xIntsPool = new IntArray();
//    private final static IntArray yIntsPool = new IntArray();
//
//    @Override
//    public void drawSolidPolygon(Vec2[] vertices, int vertexCount, Color3f color) {
//
//        // inside
//        int[] xInts = xIntsPool.get(vertexCount);
//        int[] yInts = yIntsPool.get(vertexCount);
//
//        for (int i = 0; i < vertexCount; i++) {
//            getWorldToScreenToOut(vertices[i], temp);
//            xInts[i] = (int) temp.x;
//            yInts[i] = (int) temp.y;
//        }
//
//        g.setColor(Color.ORANGE);
//        g.fillPolygon(xInts, yInts, vertexCount);
//
//        // outside
//        drawPolygon(vertices, vertexCount, color);
//    }
//
//    @Override
//    public void drawString(float x, float y, String s, Color3f color) {
//        if (g == null) {
//            return;
//        }
//        g.setColor(Color.PINK);
//        g.drawString(s, x, y);
//    }
//
//    private Graphics2D getGraphics() {
//        return g;
//    }
//
//    public void setGraphics(Graphics2D g){
//        this.g = g;
//    }
//
//    @Override
//    public void drawTransform(Transform xf) {
//        getWorldToScreenToOut(xf.p, temp);
//        temp2.setZero();
//        float k_axisScale = 0.4f;
//
//        g.setColor(Color.CYAN);
//
//        temp2.x = xf.p.x + k_axisScale * xf.q.c;
//        temp2.y = xf.p.y + k_axisScale * xf.q.s;
//        getWorldToScreenToOut(temp2, temp2);
//        g.drawLine((int) temp.x, (int) temp.y, (int) temp2.x, (int) temp2.y);
//
//        g.setColor(Color.GRAY);
//        temp2.x = xf.p.x + -k_axisScale * xf.q.s;
//        temp2.y = xf.p.y + k_axisScale * xf.q.c;
//        getWorldToScreenToOut(temp2, temp2);
//        g.drawLine((int) temp.x, (int) temp.y, (int) temp2.x, (int) temp2.y);
//    }
//
//    // CIRCLE GENERATOR
//    private void generateCirle(Vec2 argCenter, float argRadius, Vec2[] argPoints, int argNumPoints) {
//        float inc = MathUtils.TWOPI / argNumPoints;
//
//        for (int i = 0; i < argNumPoints; i++) {
//            argPoints[i].x = (argCenter.x + MathUtils.cos(i * inc) * argRadius);
//            argPoints[i].y = (argCenter.y + MathUtils.sin(i * inc) * argRadius);
//        }
//    }
//}

