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

    Level level1, level2, level3;
    GameContainer gc;

    public Levels( GameContainer gc ) throws SlickException{
        this.gc = gc;
        InitalizeLevels();
    }

    public void InitalizeLevels() throws SlickException{
        level1 = new Level(01, gc, new Vec2(0.0f, -9.81f), new TiledMap(""));
    }

}
