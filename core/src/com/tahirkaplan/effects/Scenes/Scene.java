package com.tahirkaplan.effects.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class Scene extends ScreenAdapter {

    protected Camera camera;
    protected Viewport viewport;

    protected float ww ,wh = 360;
    protected float aspectRatio;

    protected float Px,Py;

    @Override
    public void show() {
        camera = new OrthographicCamera();

        aspectRatio = (float)Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
        ww = wh * aspectRatio;

        System.out.println("Widtd : "+ Gdx.graphics.getWidth() + "    Height : " + Gdx.graphics.getHeight());

        viewport = new ScalingViewport(Scaling.stretch,ww,wh,camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);


        Px = Gdx.graphics.getWidth();
        Py = Gdx.graphics.getHeight();

    }

    @Override
    public void render(float delta) {
        camera.update();
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }

    @Override
    public void resize(int width, int height) {
        Px = width;
        Py = height;
        aspectRatio = (float) width / (float) height;
        ww = aspectRatio * wh;
        viewport.setWorldSize(ww, wh);
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }

    @Override
    public void dispose() {
    }


}
