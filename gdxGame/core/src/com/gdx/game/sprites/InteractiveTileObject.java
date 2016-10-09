/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdx.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.gdx.game.GdxGame;

/**
 *
 * @author fiefioor
 */
public abstract class InteractiveTileObject {
   protected World world;
   protected TiledMap map;
   protected TiledMapTile tile;
   protected Rectangle bounds;
   protected Body body;
   protected Fixture fixture;
    
    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds){
        this.world = world;
        this.map = map;
        this.bounds = bounds;
        
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / GdxGame.PPM, (bounds.getY() + bounds.getHeight() / 2) / GdxGame.PPM);

        body = world.createBody(bdef);
        shape.setAsBox(bounds.getWidth() / 2 / GdxGame.PPM, bounds.getHeight() / 2 / GdxGame.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
    }
    
    public abstract void onHeadHit();
    
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }
    
    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        return layer.getCell ((int)(body.getPosition().x * GdxGame.PPM / 16), (int)(body.getPosition().y * GdxGame.PPM / 16));
    }
}