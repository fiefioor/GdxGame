/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.gdx.game.GdxGame;

/**
 *
 * @author fiefioor
 */
public class Player extends Sprite{
    public World world;
    public Body b2body;
    
    public Player(World world){
        this.world = world;
        definePlayer();
    }

    private void definePlayer() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32 / GdxGame.PPM ,32 / GdxGame.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        this.b2body = world.createBody(bodyDef);
       
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / GdxGame.PPM);
        
        fdef.shape = shape;
        b2body.createFixture(fdef);
                
    }
    
    
}
