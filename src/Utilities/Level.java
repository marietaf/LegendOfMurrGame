/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utilities;

import Entities.Enemy;
import Entities.Item;
import Entities.Platform;
import Entities.Player;
import Entities.Wall;
import java.util.ArrayList;
import legendofmurrgame.DebugDrawJ2D;
import org.jbox2d.common.IViewportTransform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author Marieta
 */
public class Level {

    public final int LEVEL_ID;
    //PHYSICS
    Vec2 gravity;
    World world;
    float timeStep = 1.0f / 60.0f;
    int velocityIterations = 6;
    int positionInterations = 2;
    DebugDrawJ2D debugDraw;
    boolean debugMode;
    IViewportTransform viewportTransform;
    boolean worldPause;
    //Slick2D variables
    TiledMap tiledMap;
    //Entities
    Player player;
    ArrayList<Item> itemBodies;
    ArrayList<Enemy> enemyBodies;
    ArrayList<Wall> wallBodies;
    ArrayList<Platform> platformBodies;

    public Level( int LEVEL_ID, GameContainer gc, Vec2 gravity, TiledMap tiledMap ){
        this.LEVEL_ID = LEVEL_ID;
        this.gravity = gravity;
        this.debugDraw = new DebugDrawJ2D(gc);
        this.tiledMap = tiledMap;

        world = new World(gravity);
        debugMode = false;
        viewportTransform = debugDraw.GetViewportTransform();
        worldPause = true;
    }

    public boolean GetDebugMode(){
        return debugMode;
    }

    public void SetDebugMode( boolean debugMode ){
        this.debugMode = debugMode;
    }

    public boolean GetWorldPause(){
        return worldPause;
    }

    public void SetWorldPause( boolean worldPause ){
        this.worldPause = worldPause;
    }

    public void Update(){
        if( !worldPause )
            world.step(timeStep, velocityIterations, positionInterations);
        else
            world.step(0, 0, 0);
    }

    public void AddPlayer( float x, float y, String animPathName, int[] duration ) throws SlickException{
        if( player == null ){
            player = new Player(x, y, animPathName, duration, "player");
            player.CreateBodyInWorld(world);
        }
    }

    public void AddItemBody(){
        
    }

    public void AddEnemyBody(){
        
    }

    public void AddPlatformBody(){

    }

    public void AddContainerBody(){

    }

}
