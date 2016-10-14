/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.gdx.game.GdxGame;
import com.gdx.game.scenes.Hud;

/**
 *
 * @author fiefioor
 */
public class Token extends InteractiveTileObject {

    public static TiledMapTileSet tileSet;
    private AssetManager assetManager;
    //tile id is 93 (first id 0 and tiledmaptileset is incrementing from 1)
    private final int BLANK_TILE = 94;
    
    public Token(World world, TiledMap map, Rectangle bounds, AssetManager assetManager) {
        super(world, map, bounds);
        this.assetManager = assetManager;
        tileSet = map.getTileSets().getTileSet("Tileset");
        fixture.setUserData(this);
        setCategoryFilter(GdxGame.COIN_BIT);
    }
    
     @Override
    public void onHeadHit() {
        Gdx.app.log("Token", "Colison");
         setCategoryFilter(GdxGame.DESTROY_BIT);
         getCell().setTile(null);
         Hud.addScore(200);
         assetManager.get("audio/sounds/breakblock.wav", Sound.class).play();
    }

}
