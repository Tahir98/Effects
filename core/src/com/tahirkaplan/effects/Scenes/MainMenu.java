package com.tahirkaplan.effects.Scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tahirkaplan.effects.Main;
import com.tahirkaplan.effects.ads.AdsController;

public class MainMenu  extends ScreenAdapter {

    private Game game;
    private AdsController adsController;

    private Camera camera;
    private Viewport viewport;
    private float aspectRatio;

    private float ww, wh = 720;

    private TextButton button1;
    private TextButton button2;
    private TextButton button3;
    private TextButton button4;
    private TextButton button5;
    private TextButton button6;

    private Stage stage;

    public MainMenu(Game game,AdsController adsController){
        this.game = game;
        this.adsController = adsController;

        if (adsController.isWifiConnected())
            adsController.showBannerAd();
    }

    @Override
    public void show() {
        ww = wh * (float)Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
        aspectRatio = ww/wh;

        camera = new OrthographicCamera();
        viewport = new ScalingViewport(Scaling.stretch,ww,wh,camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);

        Skin skin = new Skin(Gdx.files.internal("glassy/skin/glassy-ui.json"));

        button1 = new TextButton("Midwinter",skin);
        button2 = new TextButton("Pureness",skin);
        button3 = new TextButton("Colors",skin);
        button4 = new TextButton("Sample",skin);
        button5 = new TextButton("For",skin);
        button6 = new TextButton("Now",skin);


        button1.setSize(ww/4,wh/4);
        button2.setSize(ww/4,wh/4);
        button3.setSize(ww/4,wh/4);
        button4.setSize(ww/4,wh/4);
        button5.setSize(ww/4,wh/4);
        button6.setSize(ww/4,wh/4);

        button1.setPosition(ww/16,wh/5*3);
        button2.setPosition(ww/16*6,wh/5*3);
        button3.setPosition(ww/16*11,wh/5*3);
        button4.setPosition(ww/16,wh/5);
        button5.setPosition(ww/16*6,wh/5);
        button6.setPosition(ww/16*11,wh/5);

        if (aspectRatio < 16f/9f){
            button1.getLabel().setFontScale(ww/1280);
            button2.getLabel().setFontScale(ww/1280);
            button3.getLabel().setFontScale(ww/1280);
            button4.getLabel().setFontScale(ww/1280);
            button5.getLabel().setFontScale(ww/1280);
            button6.getLabel().setFontScale(ww/1280);
        }else{
            button1.getLabel().setFontScale(0.8f);
            button2.getLabel().setFontScale(0.8f);
            button3.getLabel().setFontScale(0.8f);
            button4.getLabel().setFontScale(0.8f);
            button5.getLabel().setFontScale(0.8f);
            button6.getLabel().setFontScale(0.8f);
        }

        button1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                adsController.hideBannerAd();
                game.setScreen(new Midwinter(game,adsController));
                stage.dispose();

            }
        });

        button2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                adsController.hideBannerAd();
                game.setScreen(new Pureness(game,adsController));
                stage.dispose();
            }
        });

        button3.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                adsController.hideBannerAd();
                game.setScreen(new Colors(game,adsController));
                stage.dispose();
            }
        });

        button4.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new Sample(game,adsController));
            }
        });

        button5.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new CircleEng(game,adsController));
                stage.dispose();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        button6.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        stage = new Stage(viewport,Main.batch);
        stage.addActor(button1);
        stage.addActor(button2);
        stage.addActor(button3);
        stage.addActor(button4);
        stage.addActor(button5);
        stage.addActor(button6);


        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        camera.update();
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
        aspectRatio = (float)width/height;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
