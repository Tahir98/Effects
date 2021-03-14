package com.tahirkaplan.effects.Scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tahirkaplan.effects.Main;
import com.tahirkaplan.effects.ads.AdsController;

import java.util.ArrayList;

public class Sample extends Scene{

    private Game game;
    private AdsController controller;

    private Texture texture;

    private ArrayList<Vector3> positions;
    private Vector2 size;

    public Sample(Game game,AdsController controller){
        this.game = game;
        this.controller = controller;
    }

    @Override
    public void show() {
        super.show();

        texture = new Texture("back.png");
        positions = new ArrayList<Vector3>();
        size = new Vector2(500,500);

        float x = ww/2 ,z = 50;

        for (int i=0;i<50;i++){
            positions.add(new Vector3(x,wh/2,z));
            x += 10*Math.cos(Math.toRadians(45));
            z += 10*Math.cos(Math.toRadians(45));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Main.batch.setProjectionMatrix(camera.combined);
        Main.batch.begin();

        Vector3 position = new Vector3();

        for (int i=0;i<positions.size();i++){
            position = positions.get(i);
            Main.batch.draw(texture,(position.x - size.x/2),(position.y - size.y/2),
                    size.x,size.y);
        }

        Main.batch.draw(texture,200,200,100,100);

        Main.batch.end();

        camera.update();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            game.setScreen(new MainMenu(game,controller));
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}