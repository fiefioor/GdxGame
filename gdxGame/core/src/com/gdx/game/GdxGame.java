package com.gdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdx.game.screens.PlayScreen;

public class GdxGame extends Game {
    
    public static final int V_WIDTH = 400;
    public static final int V_HEIGHT = 208;
    public static final float PPM = 100;
    
    SpriteBatch batch;
    
    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new PlayScreen(this));
    }
    
    @Override
    public void render() {
        super.render();
    }
    
//    @Override
//    public void dispose() {
//        batch.dispose();
//        img.dispose();
//    }
    
    public SpriteBatch getBatch() {
        return batch;
    }
    
    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }
}