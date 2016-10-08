/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.gdx.game.GdxGame;
import com.gdx.game.scenes.Hud;
import com.gdx.game.sprites.Player;
import com.gdx.game.tools.b2WorldCreator;

/**
 *
 * @author fiefioor
 */
public class PlayScreen implements Screen {

    private GdxGame game;
    private TextureAtlas atlas;

    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    // Tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private Player player;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;

    public PlayScreen(GdxGame game) {
        atlas = new TextureAtlas("Mario_and_Enemies.pack");

        this.game = game;
        this.gameCam = new OrthographicCamera();
        this.gamePort = new FitViewport(GdxGame.V_WIDTH / GdxGame.PPM, GdxGame.V_HEIGHT / GdxGame.PPM, gameCam);

        // create HUD
        this.hud = new Hud(game.getBatch());

        // load map and setup renderer
        this.mapLoader = new TmxMapLoader();
        this.map = mapLoader.load("level1.tmx");
        this.renderer = new OrthogonalTiledMapRenderer(this.map, 1 / GdxGame.PPM);

        this.gameCam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        this.world = new World(new Vector2(0, -10), true);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        new b2WorldCreator(world, map);

        this.player = new Player(this.world, this);

    }

    public TextureAtlas getAtlas() {
        return this.atlas;
    }

    @Override
    public void show() {

    }

    /**
     * updating game world
     *
     * @param dt
     */
    public void update(float dt) {
        handleInput(dt);

        this.world.step(1 / 60f, 6, 2);
        
        player.update(dt);

        gameCam.position.x = player.b2body.getPosition().x;

        this.gameCam.update();

        this.renderer.setView(this.gameCam);
    }

    @Override
    public void render(float delta) {
        this.update(delta);
        // Clear the game screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render game map
        renderer.render();

        this.box2DDebugRenderer.render(this.world, this.gameCam.combined);

        game.getBatch().setProjectionMatrix(gameCam.combined);
        game.getBatch().begin();
        player.draw(game.getBatch());
        game.getBatch().end();
        
        game.getBatch().setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        this.map.dispose();
        this.renderer.dispose();
        this.world.dispose();
        this.box2DDebugRenderer.dispose();
        this.hud.dispose();
    }

    private void handleInput(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.b2body.applyLinearImpulse(new Vector2(0, 5f), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) {
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) {
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
        }

    }

}
