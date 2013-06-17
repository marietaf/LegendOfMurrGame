/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Utilities;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author Marieta
 */
public final class Levels {

    Level currentLevel, level1, level2, level3;
    GameContainer gc;

    public Levels( GameContainer gc ) throws SlickException{
        this.gc = gc;
        InitalizeLevels();
    }

    public void InitalizeLevels() throws SlickException{
        {//LEVEL 1 - grass level
            level1 = new Level(01, gc, new Vec2(0.0f, -9.81f * 2), new TiledMap("data/LOM maps.v2/LOM_plainlevel.tmx"));
            CreateBoundaries(level1);
            AddEntitiesFromProperties(level1);
        }
        currentLevel = level1;
        currentLevel.SetWorldPause(false);
    }

    public Level GetCurrentLevel(){
        return currentLevel;
    }

    public void CreateBoundaries(Level level){
        //Find map width and height
        float mapWidth = level1.GetTiledMap().getWidth();
        float mapHeight = level1.GetTiledMap().getHeight();
        //Bottom, Top, Left, Right wall
        //AddWallBody: x, y, width, height, friction
        level.AddWallBody(mapWidth/2, 0, mapWidth, 0.5f, 0.2f);
        level.AddWallBody(mapWidth/2, mapHeight, mapWidth, 0.5f, 0.2f);
        level.AddWallBody(0, mapHeight/2, 0.5f, mapHeight, 0.2f);
        level.AddWallBody(mapWidth, mapHeight/2, 0.5f, mapHeight, 0.2f);
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
                        level.AddWallBody(row*tileSize, col*tileSize, tileSize, tileSize, 0.5f);
                        break;

                    case player:
                        if( level.GetPlayer() == null )
                            level.AddPlayer(row*tileSize, col*tileSize, tileSize, tileSize, "data/char", CommonCode.DURATION);
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
