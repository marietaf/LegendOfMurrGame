/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utilities;

import java.util.ArrayList;
import org.jbox2d.common.Vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author Marieta
 */
public final class Levels {

    ArrayList<Level> levels;
    Level currentLevel, level1, level2, level3, level4;
    GameContainer gc;

    public Levels( GameContainer gc ) throws SlickException{
        this.gc = gc;
        InitalizeLevels();
    }

    public void InitalizeLevels() throws SlickException{
        levels = new ArrayList<Level>();
        {//LEVEL 1 - plain level
            level1 = new Level(1, gc, new Vec2(0.0f, -9.81f * 2), new TiledMap("data/LOM maps.v2/LOM_plainlevel.tmx"));
            CreateBoundaries(level1);
            AddEntitiesFromProperties(level1);
            levels.add(level1);
        }
        {//LEVEL 2 - grass level
            level2 = new Level(2, gc, new Vec2(0.0f, -9.81f * 2), new TiledMap("data/LOM maps.v2/LOM_grasslevel.tmx"));
            CreateBoundaries(level2);
            AddEntitiesFromProperties(level2);
            levels.add(level2);
        }
        {//LEVEL 3 - cave level
            level3 = new Level(3, gc, new Vec2(0.0f, -9.81f * 2), new TiledMap("data/LOM maps.v2/LOM_cavelevel.tmx"));
            CreateBoundaries(level3);
            AddEntitiesFromProperties(level3);
            levels.add(level3);
        }
        {//LEVEL 4 - snow level
            level4 = new Level(4, gc, new Vec2(0.0f, -9.81f * 2), new TiledMap("data/LOM maps.v2/LOM_snowlevel.tmx"));
            CreateBoundaries(level4);
            AddEntitiesFromProperties(level4);
            levels.add(level4);
        }
        currentLevel = level1;
        currentLevel.SetWorldPause(false);
    }

    public Level GetCurrentLevel(){     //returns the current level
        return currentLevel;
    }

    public void ChangeLevel(){
        int tempID = currentLevel.GetLevelID() + 1;
        System.out.println("tempID:" + tempID);
        for( Level level: levels ){
            if( level.GetLevelID() == tempID )
                currentLevel = level;
        }
        currentLevel.SetWorldPause(false);
    }

    public void ChangeLevel( int levelID ){
        for( Level level: levels ){
            if( level.GetLevelID() == levelID ){
                currentLevel = level;
                return;
            }
        }
        currentLevel.SetWorldPause(false);
    }

    public void CreateBoundaries(Level level){
        //Find map width and height
        float mapWidth = level.GetTiledMap().getWidth();
        float mapHeight = level.GetTiledMap().getHeight();
        float tileSize = level.GetTiledMap().getTileWidth();
        //Bottom, Top, Left, Right wall
        //AddWallBody: x, y, width, height, friction
        level.AddWallBody(mapWidth*tileSize, 0, mapWidth*tileSize, 0.5f, 500.0f, "boundary");                     //BOTTOM
        level.AddWallBody(mapWidth*tileSize, mapHeight*tileSize*2, mapWidth*tileSize, 0.5f, 500.0f, "boundary");  //TOP
        level.AddWallBody(0, mapHeight*tileSize, 0.5f, mapHeight*tileSize, 0.2f, "boundary");                   //LEFT
        level.AddWallBody(mapWidth*tileSize*2, mapHeight*tileSize, 0.5f, mapHeight*tileSize, 500.0f, "boundary"); //RIGHT
    }

    public void AddEntitiesFromProperties( Level level ) throws SlickException{
        //Find map width and height
        float mapWidth = level.GetTiledMap().getWidth();
        float mapHeight = level.GetTiledMap().getHeight();
        float tileSize = level.GetTiledMap().getTileWidth();
        //Check each tile's property + add appropriate entity to level
        for( int row = 0; row < mapWidth; row++ ){
            for( int col = 0; col < mapHeight; col++ ){
                CommonCode.TileProperty tileProperty = CheckTileProperty(level, row, col);
                switch( tileProperty ){
                    case wall:      //assigns the tile properties to the wall
                        level.AddWallBody(2*row*tileSize+tileSize, 2*col*tileSize+tileSize, tileSize, tileSize, 500.0f, "wall");
                        break;

                    case player:    //assigns the tile properties to the player
                        if( level.GetPlayer() == null )
                            level.AddPlayer(2*row*tileSize+tileSize, 2*col*tileSize+tileSize, tileSize, tileSize, "data/char", CommonCode.DURATION);
                        break;

                    case door:      //assigns the tile properties for the door
                        level.AddDoorBody(2*row*tileSize+tileSize, 2*col*tileSize+tileSize, tileSize, tileSize, "door");
                        break;

                    case platform:  //assigns tile properties for platforms
                        level.AddPlatformBody(2*row*tileSize+tileSize, 2*col*tileSize+tileSize, tileSize, tileSize, "data/moving_platform.png", "vertical", 1);               
                        break;

                    case transition:
                        level.AddTransitionBody(2*row*tileSize+tileSize, 2*col*tileSize+tileSize, tileSize, tileSize);
                        break;

                    default:
                        break;
                }
            }
        }
    }

    public CommonCode.TileProperty CheckTileProperty( Level level, int row, int col ){
        //IF WALL, RETURN WALL
        if( level.GetTiledMap().getTileProperty(level.GetTiledMap().getTileId(row, col, 0), "wall", "noproperty").equalsIgnoreCase("true") )
            return CommonCode.TileProperty.wall;
        //IF PLAYER, RETURN PLAYER
        else if(level.GetTiledMap().getTileProperty(level.GetTiledMap().getTileId(row, col, 0), "player", "noproperty").equalsIgnoreCase("true"))
            return CommonCode.TileProperty.player;
        //IF ITEM, RETURN ITEM
        else if(level.GetTiledMap().getTileProperty(level.GetTiledMap().getTileId(row, col, 0), "item", "noproperty").equalsIgnoreCase("true"))
            return CommonCode.TileProperty.item;
        //IF PLATFORM, RETURN PLATFORM
        else if(level.GetTiledMap().getTileProperty(level.GetTiledMap().getTileId(row, col, 0), "platform", "noproperty").equalsIgnoreCase("true"))
            return CommonCode.TileProperty.platform;
        //IF ENEMY, RETURN ENEMY
        else if(level.GetTiledMap().getTileProperty(level.GetTiledMap().getTileId(row, col, 0), "enemy", "noproperty").equalsIgnoreCase("true"))
            return CommonCode.TileProperty.enemy;
        //IF DOOR, RETURN DOOR
        else if(level.GetTiledMap().getTileProperty(level.GetTiledMap().getTileId(row, col, 0), "door", "noproperty").equalsIgnoreCase("true"))
            return CommonCode.TileProperty.door;
        //IF TRANSITION, RETURN TRANSITION
        else if(level.GetTiledMap().getTileProperty(level.GetTiledMap().getTileId(row, col, 0), "transition", "noproperty").equalsIgnoreCase("true"))
            return CommonCode.TileProperty.transition;
        //ELSE RETURN NO PROPERTY
        return CommonCode.TileProperty.noproperty;
    }

}
