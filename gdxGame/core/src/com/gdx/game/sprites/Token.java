/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.gdx.game.GdxGame;

/**
 *
 * @author fiefioor
 */
public class Token extends InteractiveTileObject {

    public Token(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(GdxGame.COIN_BIT);
    }
    
     @Override
    public void onHeadHit() {
        Gdx.app.log("Token", "Colison");
         setCategoryFilter(GdxGame.DESTROY_BIT);
         getCell().setTile(null);
    }

}
