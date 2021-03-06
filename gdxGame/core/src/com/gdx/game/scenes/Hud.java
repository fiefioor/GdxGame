/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdx.game.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.game.GdxGame;

/**
 *
 * @author fiefioor
 */
public class Hud implements Disposable{

    public Stage stage;
    public Viewport viewPort;

    private Integer worldTimer;
    private float timeCount;
    private static Integer score;

    Label countDownLabel;
    static Label  scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label charLabel;

    public Hud(SpriteBatch sb) {
        this.worldTimer = 300;
        this.timeCount = 0;
        this.score = 0;

        this.viewPort = new FitViewport(GdxGame.V_WIDTH, GdxGame.V_HEIGHT, new OrthographicCamera());
        this.stage = new Stage(this.viewPort, sb);

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        
        this.countDownLabel = new Label(String.format("%03d", this.worldTimer), new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        this.scoreLabel = new Label(String.format("%06d", this.score), new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        this.timeLabel = new Label( "TIME", new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        this.levelLabel = new Label( "1-1", new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        this.worldLabel = new Label( "WORLD", new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        this.charLabel = new Label( "char", new Label.LabelStyle(new BitmapFont(),Color.WHITE));
        
        table.add(this.charLabel).expandX().padTop(10);
        table.add(this.worldLabel).expandX().padTop(10);
        table.add(this.timeLabel).expandX().padTop(10);
        
        table.row();
        table.add(this.scoreLabel).expandX();
        table.add(this.levelLabel).expandX();
        table.add(this.countDownLabel).expandX();
        
        stage.addActor(table);
    }

    public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            worldTimer--;
            countDownLabel.setText(String.format("%03d", this.worldTimer));
            timeCount = 0;
        }
    }
    
    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }
    
    @Override
    public void dispose() {
        this.dispose();
    }
}
