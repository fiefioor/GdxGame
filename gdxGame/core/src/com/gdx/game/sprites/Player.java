/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.gdx.game.GdxGame;
import com.gdx.game.screens.PlayScreen;

/**
 *
 * @author fiefioor
 */
public class Player extends Sprite{
    public enum State { FALLING, JUMPING, STANDING, RUNNING };
    public State currentState;
    public State previousState;
    
    public World world;
    public Body b2body;
    
    private TextureRegion marioStand;
    private Animation marioRun;
    private Animation marioJump;
    private float stateTimer;
    private boolean runningRight;
    
    public Player(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = world;
        
        currentState = State.STANDING;
        currentState = State.STANDING;   
        stateTimer = 0;
        runningRight = true;
       
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i = 1; i < 4; i++){
            frames.add(new TextureRegion(getTexture(), i*16,10,16,16));
        }
        marioRun = new Animation(0.1f, frames);
        frames.clear();
        
        // jumping animation starts at 4th texture in pack
        for(int i = 4; i < 6; i++){
            frames.add(new TextureRegion(getTexture(), i*16,10,16,16));
        }
        marioJump = new Animation(0.1f, frames);
        frames.clear();
        
        definePlayer();
        this.marioStand = new TextureRegion(getTexture(), 0,10 ,16,16);
        setBounds(0, 0, 16 / GdxGame.PPM, 16 / GdxGame.PPM);
        setRegion(this.marioStand);
    }

    private void definePlayer() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32 / GdxGame.PPM ,32 / GdxGame.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        this.b2body = world.createBody(bodyDef);
       
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / GdxGame.PPM);
        fdef.filter.categoryBits = GdxGame.MARIO_BIT;
        fdef.filter.maskBits = GdxGame.DEFAULT_BIT | GdxGame.BRICK_BIT | GdxGame.COIN_BIT;
        
        fdef.shape = shape;
        b2body.createFixture(fdef);
                
        
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / GdxGame.PPM, 6 / GdxGame.PPM), new Vector2(2 / GdxGame.PPM, 6 / GdxGame.PPM));
        fdef.shape = head;
        fdef.isSensor = true;
        
        b2body.createFixture(fdef).setUserData("head");
    }
    
    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight()/ 2);
        setRegion(this.getFrame(dt));
    }
    
    public TextureRegion getFrame(float dt){
        currentState = getState();
        
        TextureRegion region;
        switch(currentState){
            case JUMPING:
                region = marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = marioRun.getKeyFrame(stateTimer,true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = marioStand;
                break;
        }
        
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX() ){
            region.flip(true, false);
            runningRight = false;
        }
        else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX() ){
            region.flip(true, false);
            runningRight = true;
        }
        
        stateTimer = (currentState == previousState) ? stateTimer + dt: 0;
        previousState = currentState;
        return region;
    }
    
    public State getState(){
        if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y < 0){
            return State.FALLING;
        }       
        else if(b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.STANDING;
        
    }
}
