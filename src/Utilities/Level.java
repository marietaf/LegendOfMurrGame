/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import Entities.Door;
import Entities.Enemy;
import Entities.Item;
import Entities.Platform;
import Entities.Player;
import Entities.TransitionBlock;
import Entities.Wall;
import java.util.ArrayList;
import legendofmurrgame.DebugDrawJ2D;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.common.Color3f;
import org.jbox2d.common.IViewportTransform;
import org.jbox2d.common.Vec2;
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
    IViewportTransform viewport;
    boolean worldPause;
    ContactListener levelChangeListener;
    //Slick2D variables
    TiledMap tiledMap;
    int width, height;
    //Entities
    Player player;
    ArrayList<Item> itemBodies;
    ArrayList<Enemy> enemyBodies;
    ArrayList<Wall> wallBodies;
    ArrayList<Platform> platformBodies;
    ArrayList<Door> doorBodies;
    ArrayList<TransitionBlock> transitionBlockBodies;

    public Level(int LEVEL_ID, GameContainer gc, Vec2 gravity, TiledMap tiledMap) {
        this.LEVEL_ID = LEVEL_ID;
        this.gravity = gravity;
        this.debugDraw = new DebugDrawJ2D(gc);
        this.tiledMap = tiledMap;

        world = new World(gravity);
        //CHANGE DEBUG MODE ONCE TILEDMAPS ARE UP
        debugMode = true;
        viewport = debugDraw.GetViewportTransform();
        viewport.setCamera(0, 0, 10);
        world.setDebugDraw(debugDraw);
        worldPause = true;
        levelChangeListener = new ContactListener();
        world.setContactListener(levelChangeListener);
        width = tiledMap.getWidth();
        height = tiledMap.getHeight();

        //ArrayLists of entities
        itemBodies = new ArrayList();
        enemyBodies = new ArrayList();
        wallBodies = new ArrayList();
        platformBodies = new ArrayList();
        doorBodies = new ArrayList();
        transitionBlockBodies = new ArrayList();
    }

    public int GetLevelID() {
        return LEVEL_ID;
    }

    public boolean GetDebugMode() {
        return debugMode;
    }

    public void SetDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public boolean GetWorldPause() {
        return worldPause;
    }

    public void SetWorldPause(boolean worldPause) {
        this.worldPause = worldPause;
    }

    public Player GetPlayer() {
        return player;
    }

    public TiledMap GetTiledMap() {
        return tiledMap;
    }

    public ArrayList<TransitionBlock> GetTransitionBodies(){
        return transitionBlockBodies;
    }

    public void Update(){
        //PAUSE WORLD UPDATE
        if (!worldPause) {
            world.step(timeStep, velocityIterations, positionInterations);
        } else {
            world.step(0, 0, 0);
        }

        //DEBUG MODE UPDATE
        if (!debugMode) {
            debugDraw.clearFlags(DebugDraw.e_shapeBit);
            debugDraw.clearFlags(DebugDraw.e_aabbBit);
        } else if (debugMode) {
            debugDraw.setFlags(DebugDraw.e_shapeBit);
            debugDraw.setFlags(DebugDraw.e_aabbBit);
        }

        //VIEWPORT TRANSFORM / CAMERA UPDATE
        float mapWidth = tiledMap.getWidth();
        float mapHeight = tiledMap.getHeight();
        float tileSize = tiledMap.getTileWidth();
        Vec2 mapCoords = new Vec2(mapWidth * tileSize, mapHeight * tileSize);
        Vec2 origin = new Vec2(0, 0);
        viewport.getScreenToWorld(mapCoords, origin);
        //WORKS FOR GRASS MAP
//        float ltX = 100;
//        float rtX = 300;
//        float topY = -60;
//        float botY = -180;
        //KINDA WORKS FOR GRASS MAP
//        float ltX = 100;
//        float rtX = mapCoords.x - 800;
//        float topY = -60;
//        float botY = -180;
//        float ltX = 100;
//        float rtX = mapCoords.x - 800;
//        float topY = -60;
//        float botY = mapCoords.y + 60;
        float ltX = 100;
        float rtX = mapCoords.x / 4 - 100;
        float topY = -60;
        float botY;
        if (LEVEL_ID == 1) {    //plain
            botY = -60;
        }
        else if (LEVEL_ID == 2) //grass
        {
            botY = -180;
        }
        else if (LEVEL_ID == 3) //cave
        {
            botY = -180;
        }
        else if (LEVEL_ID == 4) //snow
        {
            botY = -420;
        }
        else
        {
            botY = -60;
        }

        //the first number is what you want the value of botY to be
        //the second value is the value of mapCoords.y
        //-60 for plain,480
        //-180 for grass/cave, 960
        //-100 for bonus, 640
        //-420 for snow, 1920


        if (player != null) {
            if (player.GetBody().getPosition().x > ltX
                    && player.GetBody().getPosition().x < rtX
                    && player.GetBody().getPosition().y > topY
                    && player.GetBody().getPosition().y < botY) {
                debugDraw.setCamera(player.GetBody().getPosition().x, player.GetBody().getPosition().y, 4);
            } else {
                debugDraw.setCamera(GetViewportX(ltX, rtX), GetViewportY(topY, botY), 4);
            }
        }

        for (Platform platform : platformBodies) {
            platform.UpdatePosition();
            platform.UpdatePlatform();
        }
    }

    public float GetViewportX(float ltX, float rtX) {
        if (player.GetBody().getPosition().x < ltX) {
            return ltX;
        } else if (player.GetBody().getPosition().x > rtX) {
            return rtX;
        }
        return player.GetBody().getPosition().x;
    }

    public float GetViewportY(float topY, float botY) {
        if (player.GetBody().getPosition().y > topY) {
            return -60;
        } else if (player.GetBody().getPosition().y < botY) {
            ;
            return botY;
        }
        return player.GetBody().getPosition().y;
    }

    public void AddPlayer(float x, float y, float width, float height, String animPathName, int[] duration) throws SlickException {
        x = CommonCode.ScreenToWorldX(x);
        y = CommonCode.ScreenToWorldY(y);
        width = CommonCode.ScreenToWorldX(width);
        height = CommonCode.ScreenToWorldX(height);
        if (player == null) {
            player = new Player(x, y, width, height, animPathName, duration, "player");
            player.CreateBodyInWorld(world);
            debugDraw.setCamera(player.GetBody().getPosition().x, player.GetBody().getPosition().y, 4);
        }
    }

    public void AddItemBody(float x, float y, float radius, String animPathName, String bodyUserData) throws SlickException {
        x = CommonCode.ScreenToWorldX(x);
        y = CommonCode.ScreenToWorldY(y);
        radius = CommonCode.ScreenToWorldX(radius);
        Item tempItem = new Item(x, y, radius, animPathName, bodyUserData);
        tempItem.CreateBodyInWorld(world);
        itemBodies.add(tempItem);
    }

    public void AddEnemyBody(float x, float y, float width, float height, String animPathName, int[] duration, String bodyUserData) throws SlickException {
        x = CommonCode.ScreenToWorldX(x);
        y = CommonCode.ScreenToWorldY(y);
        width = CommonCode.ScreenToWorldX(width);
        height = CommonCode.ScreenToWorldX(height);
        Enemy tempEnemy = new Enemy(x, y, width, height, animPathName, duration, bodyUserData);
        tempEnemy.CreateBodyInWorld(world);
        enemyBodies.add(tempEnemy);
    }

    public void AddPlatformBody(float x, float y, float width, float height, String imagePathName, String direction, float speed) throws SlickException {
        x = CommonCode.ScreenToWorldX(x);
        y = CommonCode.ScreenToWorldY(y);
        width = CommonCode.ScreenToWorldX(width);
        height = CommonCode.ScreenToWorldX(height);
        Platform tempPlatform = new Platform(x, y, width, height, imagePathName, direction, speed);
        tempPlatform.CreateBodyInWorld(world);
        platformBodies.add(tempPlatform);
    }

    public void AddWallBody(float x, float y, float width, float height, float friction, String bodyUserData) {
        x = CommonCode.ScreenToWorldX(x);
        y = CommonCode.ScreenToWorldY(y);
        width = CommonCode.ScreenToWorldX(width);
        height = CommonCode.ScreenToWorldX(height);
        Wall tempWall = new Wall(x, y, width, height, friction, bodyUserData);
        tempWall.CreateBodyInWorld(world);
        wallBodies.add(tempWall);
    }

    public void AddDoorBody(float x, float y, float width, float height, String bodyUserData) {
        x = CommonCode.ScreenToWorldX(x);
        y = CommonCode.ScreenToWorldY(y);
        width = CommonCode.ScreenToWorldX(width);
        height = CommonCode.ScreenToWorldX(height);
        Door tempDoor = new Door(x, y, width, height, bodyUserData);
        doorBodies.add(tempDoor);
    }

    public void Render() {
        //RENDER MAP BASED ON CHARACTER'S POSITION ON IT
        Vec2 tempVec2 = new Vec2(0, 0);
        viewport.getWorldToScreen(tempVec2, tempVec2);
        tiledMap.render((int) tempVec2.x, (int) tempVec2.y);
        debugDraw.drawCircle(new Vec2(0, 0), 2, Color3f.WHITE);
        world.drawDebugData();
        float viewportXPos = viewport.getExtents().x;
        float viewportYPos = viewport.getExtents().y;
        if (player != null) {
            player.Render(viewport);
        }
        for (Item item : itemBodies) {
            if (item.GetPosition().x > viewportXPos && item.GetPosition().x < (viewportXPos + width)
                    && item.GetPosition().y > viewportYPos && item.GetPosition().y < (viewportYPos + height)) {
                item.Render();
            }
        }
        for (Enemy enemy : enemyBodies) {
            if (enemy.GetPosition().x > viewportXPos && enemy.GetPosition().x < (viewportXPos + width)
                    && enemy.GetPosition().y > viewportYPos && enemy.GetPosition().y < (viewportYPos + height)) {
                enemy.Render();
            }
        }
        for (Platform platform : platformBodies) {
//            if( platform.GetPosition().x > viewportXPos && platform.GetPosition().x < (viewportXPos + width) &&
//                platform.GetPosition().y > viewportYPos && platform.GetPosition().y < (viewportYPos + height))
            platform.Render();
        }
    }
}
