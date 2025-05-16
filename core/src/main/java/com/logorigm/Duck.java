package com.logorigm;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

public class Duck {

    public Rectangle duckRectangle = new Rectangle(38, 65, 32, 32);
    private final Texture duckTexture = new Texture("Dug.png");

    private float dash_time = 0;
    private float jump_time = 0;
    private float jump_block = 0;


    public void input(TiledMap map) {
        float dspeed = 100f;
        float delta = Gdx.graphics.getDeltaTime();
        float speed = 200f;
        if(dash_time>0){
            dash_time-=delta;
        }
        int l=0,r=0,h=0,d=0;
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("Solids");
        for(int i=0;i<4;i++) {
            TiledMapTileLayer.Cell cell = layer.getCell((int)(duckRectangle.x)/32, (int)(duckRectangle.y-1)/32);
            if(cell == null){
                d = 1;
            }
            cell = layer.getCell((int)(duckRectangle.x-1)/32, (int)(duckRectangle.y)/32);
            if(cell == null){
                l = 1;
            }
            cell = layer.getCell((int)(duckRectangle.x+27)/32, (int)(duckRectangle.y+5)/32);
            if(cell == null){
                r = 1;
            }
            else{
                duckRectangle.x-=0.5f;
            }
            cell = layer.getCell((int)(duckRectangle.x)/32, (int)(duckRectangle.y+30)/32);
            if(cell == null){
                h = 1;
            }
            else{
                duckRectangle.y+=0.5f;
            }
        }

        var ren = duckRectangle.getY();
        if (Gdx.input.isKeyPressed(Input.Keys.D) && r!=0) {
            duckRectangle.x += (speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && l!=0) {
            duckRectangle.x += (-speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) && h!=0 && d==0) {
            jump_time += delta;
            duckRectangle.y += 10f;
        }
        else if (ren>0 && d!=0) {
            duckRectangle.y+=(-dspeed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E) && dash_time <= 0) {
            duckRectangle.x += 50;
            dash_time += 50*delta;
        }
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(duckTexture, duckRectangle.x, duckRectangle.y, 32, 32);
    }

    public void logic() {
        float duckWidth = duckRectangle.getWidth();
        float duckHeight = duckRectangle.getHeight();


        duckRectangle.set(duckRectangle.getX(), duckRectangle.getY(), duckWidth, duckHeight);
    }
}
