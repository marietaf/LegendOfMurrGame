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
    Level currentLevel, level1, level2, level3;
    GameContainer gc;

    public Levels( GameContainer gc ) throws SlickException{
        this.gc = gc;
        InitalizeLevels();
    }

    public void InitalizeLevels() throws SlickException{
        levels = new ArrayList<Level>();
        {//LEVEL 1 - grass level
            level1 = new Level(01, gc, new Vec2(0.0f, -9.81f * 2), new TiledMap("data/LOM maps.v2/LOM_grasslevel.tmx"));
            CreateBoundaries(level1);
            AddEntitiesFromProperties(level1);
            levels.add(level1);
        }
        currentLevel = level1;
        currentLevel.SetWorldPause(false);
    }

    public Level GetCurrentLevel(){
        return currentLevel;
    }

    public void ChangeLevel(){

    }

    public void ChangeLevel( int levelID ){
        for( Level level: levels ){
            if( level.GetLevelID() == levelID ){
                currentLevel = level;
                return;
            }
        }
    }

    public void CreateBoundaries(Level level){
        //Find map width and height
        float mapWidth = level1.GetTiledMap().getWidth();
        float mapHeight = level1.GetTiledMap().getHeight();
        float tileSize = level.GetTiledMap().getTileWidth();
        //Bottom, Top, Left, Right wall
        //AddWallBody: x, y, width, height, friction
        level.AddWallBody(mapWidth*tileSize, 0, mapWidth*tileSize, 0.5f, 0.2f, "boundary");                     //BOTTOM
        level.AddWallBody(mapWidth*tileSize, mapHeight*tileSize*2, mapWidth*tileSize, 0.5f, 0.2f, "boundary");  //TOP
        level.AddWallBody(0, mapHeight*tileSize, 0.5f, mapHeight*tileSize, 0.2f, "boundary");                   //LEFT
        level.AddWallBody(mapWidth*tileSize*2, mapHeight*tileSize, 0.5f, mapHeight*tileSize, 0.2f, "boundary"); //RIGHT
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
                    case wall:
                        level.AddWallBody(2*row*tileSize+tileSize, 2*col*tileSize+tileSize, tileSize, tileSize, 0.8f, "wall");
                        break;

                    case player:
                        if( level.GetPlayer() == null )
                            level.AddPlayer(2*row*tileSize+tileSize, 2*col*tileSize+tileSize, tileSize, tileSize, "data/char", CommonCode.DURATION);
                        break;

                    case door:
                        level.AddDoorBody(2*row*tileSize+tileSize, 2*col*tileSize+tileSize, tileSize, tileSize, "door");
                        
                        break;

                    case platform:
                        level.AddPlatformBody(2*row*tileSize+tileSize, 2*col*tileSize+tileSize, tileSize, tileSize, "data/moving_platform.png", "vertical", 1);               
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
        //ELSE RETURN NO PROPERTY
        return CommonCode.TileProperty.noproperty;
    }

}
