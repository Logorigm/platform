package com.logorigm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private boolean bullet = false;
    FitViewport viewport;

    private Texture duckTexture;

    Texture backgroundTexture;
    Texture duckbulletTexture;
    MapRenderer mapRenderer;

    TiledMap map;

    public Duck duck;

    Rectangle duckbulletRectangle;

    SpriteBatch spriteBatch;
    private OrthographicCamera camera;

    @Override
    public void create() {
        batch = new SpriteBatch();
        backgroundTexture = new Texture("background.png");
        duckbulletTexture = new Texture("duckbullet.png");


        map = new TmxMapLoader().load("testmap.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        duckTexture = new Texture("Dug.png");
        {
            duck = new Duck();
        }

        duckbulletRectangle = new Rectangle();
        spriteBatch = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new FitViewport(320, 180, camera);
        mapRenderer.setView(viewport.getCamera().projection, 0, 0, 1, 1);

    }

    private void input() {
        duck.input(map);
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if(!bullet) {
                duckbulletRectangle.x = duck.duckRectangle.x;
                duckbulletRectangle.y = duck.duckRectangle.y;
            }
            bullet = true;
        }



        camera.position.set(duck.duckRectangle.x, duck.duckRectangle.y, 0);
    }
    @Override
    public void render() {
        input();
        logic();
        draw();

    }
    private void logic() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        float duckWidth = duck.duckRectangle.getWidth();
        float duckHeight = duck.duckRectangle.getHeight();


        duck.duckRectangle.set(duck.duckRectangle.getX(), duck.duckRectangle.getY(), duckWidth, duckHeight);

        float duckbulletWidth = duckbulletRectangle.getWidth();
        float duckbulletHeight = duckbulletRectangle.getHeight();

        //duck.logic();

        float delta = Gdx.graphics.getDeltaTime();


        duckbulletRectangle.set(duckbulletRectangle.getX(), duckbulletRectangle.getY(), duckbulletWidth, duckbulletHeight);

        duckbulletRectangle.y +=(-150f * delta);
        if(duckbulletRectangle.getY() < 0){
            bullet = false;
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }
    private void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        spriteBatch.begin();
        spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
        spriteBatch.end();

        mapRenderer.setView(camera);
        mapRenderer.render();

        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();


        //duck.draw(spriteBatch);\
        spriteBatch.draw(duckTexture, duck.duckRectangle.x, duck.duckRectangle.y, 32, 32);

        if(bullet){
            spriteBatch.draw(duckbulletTexture, duckbulletRectangle.getX(), duckbulletRectangle.getY(),20,18);
        }

        spriteBatch.end();
    }
    @Override
    public void dispose() {
        batch.dispose();
        backgroundTexture.dispose();
        duckTexture.dispose();
        //duck.dispose();
        //image.dispose();
    }
}
