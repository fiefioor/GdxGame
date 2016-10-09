/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdx.game.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.gdx.game.GdxGame;
import com.gdx.game.sprites.Token;

/**
 *
 * @author fiefioor
 */
public class b2WorldCreator {

    private BodyDef bodyDef;
    private PolygonShape shape;
    private FixtureDef fixtureDef;
    private Body body;
    
    public b2WorldCreator(World world, TiledMap map){
        this.bodyDef = new BodyDef();
        this.shape = new PolygonShape();
        this.fixtureDef = new FixtureDef();
       
        this.createGroundRed(world, bodyDef, map);
        this.createGroundGreen(world, bodyDef, map);
        this.createGroundBlue(world, bodyDef, map);
        this.createTokenBrix(world, bodyDef, map);
        
    }
    
    private void createGroundRed(World world, BodyDef bodyDef, TiledMap map){
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
           bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / GdxGame.PPM, (rect.getY() + rect.getHeight() / 2) / GdxGame.PPM);

            this.body = world.createBody(bodyDef);
            this.shape.setAsBox(rect.getWidth() / 2 / GdxGame.PPM, rect.getHeight() / 2 / GdxGame.PPM);
            this.fixtureDef.shape = shape;
            this.body.createFixture(fixtureDef);
        }
    }
    
    private void createGroundGreen(World world, BodyDef bodyDef, TiledMap map){
        // ground green
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / GdxGame.PPM, (rect.getY() + rect.getHeight() / 2) / GdxGame.PPM);

            body = world.createBody(bodyDef);
            shape.setAsBox(rect.getWidth() / 2 / GdxGame.PPM, rect.getHeight() / 2 / GdxGame.PPM);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }
    }
    
    private void createGroundBlue(World world, BodyDef bodyDef, TiledMap map){
        // ground blue
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            this.bodyDef.type = BodyDef.BodyType.StaticBody;
            this.bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / GdxGame.PPM, (rect.getY() + rect.getHeight() / 2) / GdxGame.PPM);

            this.body = world.createBody(bodyDef);
            this.shape.setAsBox(rect.getWidth() / 2 / GdxGame.PPM, rect.getHeight() / 2 / GdxGame.PPM);
            this.fixtureDef.shape = shape;
            this.body.createFixture(fixtureDef);
        }
    }
    
    private void createTokenBrix(World world, BodyDef bodyDef, TiledMap map){
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)){

            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Token(world, map,rect );
        }
    }
    
}
