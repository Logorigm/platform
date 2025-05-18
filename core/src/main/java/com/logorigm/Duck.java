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
    private Texture duckTexture = new Texture("Dug.png");

    private float dash_time = 0;
    private float jump_time = 0;
    private float jump_block = 0;
    private int ch = 0;


    public void input(TiledMap map) {
        spikes(map);
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
            duckRectangle.y += 100f;
        }
        else if (ren>0 && d!=0 && ch!=1) {
            duckRectangle.y+=(-dspeed * delta);
        }
        /*if(ch == 1 && jump_time < 2){
            duckRectangle.y += 10f;
            jump_time += delta;
        }
        if(jump_time >= 2){
            ch = 2;
            jump_time -= delta;
        }
        if(ch == 2 && jump_time <= 0){
            ch = 0;
        }*/
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
    public void spikes(TiledMap map){
        int ls=0,rs=0,hs=0,ds=0;
        TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get("Spikes");
        for(int i=0;i<4;i++) {
            TiledMapTileLayer.Cell cell = layer.getCell((int)(duckRectangle.x+20)/32, (int)(duckRectangle.y-20)/32);
            if(cell == null){
                ds = 1;
            }
            cell = layer.getCell((int)(duckRectangle.x)/32, (int)(duckRectangle.y)/32);
            if(cell == null){
                ls = 1;
            }
            cell = layer.getCell((int)(duckRectangle.x)/32, (int)(duckRectangle.y)/32);
            if(cell == null){
                rs = 1;
            }
            else{
                duckRectangle.x-=0.5f;
            }
            cell = layer.getCell((int)(duckRectangle.x)/32, (int)(duckRectangle.y)/32);
            if(cell == null){
                hs = 1;
            }
            else{
                duckRectangle.y+=0.5f;
            }
        }
        if(hs == 0 || rs == 0 || ls == 0 || ds ==0){
            //duckTexture = new Texture("DeadDuck.png");
        }
        else{
            duckTexture = new Texture("Dug.png");
        }
    }
}
