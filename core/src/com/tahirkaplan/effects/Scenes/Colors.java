package com.tahirkaplan.effects.Scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.tahirkaplan.effects.Main;
import com.tahirkaplan.effects.ads.AdsController;

import java.util.Random;

public class Colors extends ScreenAdapter {

    private Viewport HudViewport;
    private Camera HudCamera;

    private Viewport colorViewport;
    private Camera colorCamera;

    private float Px,Py;
    private float ww,wh;
    private float aspectRatio;

    private Game game;
    private AdsController adsController;

    private Color[] colors;
    private float[][] speeds;
    private Random random;
    private float ratio = 1f/100f;

    private float rMax = 1f;
    private float rMin = 0f;

    private float gMax = 1f;
    private float gMin = 0f;

    private float bMax = 1f;
    private float bMin = 0f;

    private Slider r1;
    private Slider r2;

    private Slider g1;
    private Slider g2;

    private Slider b1;
    private Slider b2;

    private Slider speed;
    private Stage stage;

    private Label rLabel;
    private Label gLabel;
    private Label bLabel;
    private Label speedLabel;


    private Label max1;
    private Label min1;

    private Label max2;
    private Label min2;

    private Label max3;
    private Label min3;

    private ImageButton back;
    private CheckBox checkBox;
    private boolean isVisible = true;
    private final float visibilityTime = 1.6f;
    private float visibilityCounter = 0;

    public Colors(Game game,AdsController adsController){
        this.game = game;
        this.adsController = adsController;
    }


    @Override
    public void show() {
        Px = Gdx.graphics.getWidth();
        Py = Gdx.graphics.getHeight();

        float aspectRatio = Py/Px;

        wh = 720;
        ww = wh/aspectRatio;

        HudCamera = new OrthographicCamera();
        HudViewport = new StretchViewport(ww,wh,HudCamera);
        HudCamera.position.set(HudCamera.viewportWidth/2,HudCamera.viewportHeight/2,0);
        HudViewport.apply();

        colorCamera = new OrthographicCamera();
        colorViewport = new ScreenViewport(colorCamera);
        colorCamera.position.set(colorCamera.viewportWidth/2,colorCamera.viewportHeight/2,0);
        colorViewport.apply();

        colors = new Color[4];
        speeds = new float[colors.length][3];
        random = new Random();
        for (int i=0;i<colors.length;i++){
            colors[i] = new Color(random.nextFloat(),random.nextFloat(),random.nextFloat(),1f);
            for (int j=0;j<speeds[i].length;j++){
                speeds[i][j] = random.nextFloat();
            }
        }

        Skin skin = new Skin(Gdx.files.internal("glassy/skin/glassy-ui.json")) ;

        r1 = new Slider(0f,1f,0.025f,true,skin);
        r1.setValue(rMin);
        r1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rMin = r1.getValue();
                if (rMin > rMax){
                    rMax = rMin;
                    r2.setValue(rMax);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                rMin = r1.getValue();
                if (rMin > rMax){
                    rMax = rMin;
                    r2.setValue(rMax);
                }
                super.touchDragged(event, x, y, pointer);
            }
        });


        r2 = new Slider(0f,1f,0.025f,true,skin);
        r2.setValue(rMax);
        r2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rMax = r2.getValue();
                if (rMax < rMin){
                    rMin = rMax;
                    r1.setValue(rMin);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                rMax = r2.getValue();
                if (rMax < rMin){
                    rMin = rMax;
                    r1.setValue(rMin);
                }
                super.touchDragged(event, x, y, pointer);
            }
        });

        g1 = new Slider(0f,1f,0.025f,true,skin);
        g1.setValue(gMin);
        g1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gMin = g1.getValue();
                if (gMin > gMax){
                    gMax = gMin;
                    g2.setValue(gMax);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                gMin = g1.getValue();
                if (gMin > gMax){
                    gMax = gMin;
                    g2.setValue(gMax);
                }
                super.touchDragged(event, x, y, pointer);
            }
        });

        g2 = new Slider(0f,1f,0.025f,true,skin);
        g2.setValue(gMax);
        g2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gMax = g2.getValue();
                if (gMax < gMin){
                    gMin = gMax;
                    g1.setValue(gMin);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                gMax = g2.getValue();
                if (gMax < gMin){
                    gMin = gMax;
                    g1.setValue(gMin);
                }
                super.touchDragged(event, x, y, pointer);
            }
        });

        b1 = new Slider(0f,1f,0.025f,true,skin);
        b1.setValue(bMin);
        b1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bMin = b1.getValue();
                if (bMin > bMax){
                    bMax = bMin;
                    b2.setValue(bMax);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                bMin = b1.getValue();
                if (bMin > bMax){
                    bMax = bMin;
                    b2.setValue(bMax);
                }
                super.touchDragged(event, x, y, pointer);
            }
        });

        b2 = new Slider(0f,1f,0.025f,true,skin);
        b2.setValue(bMax);
        b2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                bMax = b2.getValue();
                if (bMax < bMin){
                    bMin = bMax;
                    b1.setValue(bMin);
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                bMax = b2.getValue();
                if (bMax < bMin){
                    bMin = bMax;
                    b1.setValue(bMin);
                }
                super.touchDragged(event, x, y, pointer);
            }
        });

        speed = new Slider(0f,1f/15f,0.001f,true,skin);
        speed.setValue(ratio);
        speed.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ratio = speed.getValue();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                ratio = speed.getValue();
                super.touchDragged(event, x, y, pointer);
            }
        });


        rLabel = new Label("R",skin);
        rLabel.setColor(Color.RED);
        rLabel.setAlignment(Align.center);
        rLabel.setFontScale(1.5f);

        gLabel = new Label("G",skin);
        gLabel.setColor(Color.GREEN);
        gLabel.setAlignment(Align.center);
        gLabel.setFontScale(1.5f);

        bLabel = new Label("B",skin);
        bLabel.setColor(Color.BLUE);
        bLabel.setAlignment(Align.center);
        bLabel.setFontScale(1.5f);

        speedLabel = new Label("Speed",skin);
        speedLabel.setAlignment(Align.center);
        speedLabel.setFontScale(1.5f);

        min1 = new Label("Min",skin);
        min1.setAlignment(Align.center);
        min1.setFontScale(1.5f);

        max1 = new Label("Max",skin);
        max1.setAlignment(Align.center);
        max1.setFontScale(1.5f);


        min2 = new Label("Min",skin);
        min2.setAlignment(Align.center);
        min2.setFontScale(1.5f);

        max2 = new Label("Max",skin);
        max2.setAlignment(Align.center);
        max2.setFontScale(1.5f);

        min3 = new Label("Min",skin);
        min3.setAlignment(Align.center);
        min3.setFontScale(1.5f);

        max3 = new Label("Max",skin);
        max3.setAlignment(Align.center);
        max3.setFontScale(1.5f);


        back = new ImageButton(new TextureRegionDrawable(new Texture("back.png")));
        back.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenu(game,adsController));
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        checkBox = new CheckBox("Hide",skin);
        checkBox.setSize(wh/8*aspectRatio,wh/8*aspectRatio);
        checkBox.setPosition(wh/10,wh - wh/10);
        checkBox.getImage().setScaling(Scaling.fill);
        checkBox.getImageCell().size(wh/20);
        checkBox.getStyle().font.getData().setScale(1.5f*aspectRatio);


        r1.setSize(ww/20,wh/1.8f);
        r1.setPosition(ww/30,wh/20);
        r2.setSize(ww/20,wh/1.8f);
        r2.setPosition(r1.getX() + r1.getWidth(),wh/20);

        g1.setSize(ww/20,wh/1.8f);
        g1.setPosition(ww/30*5,wh/20);
        g2.setSize(ww/20,wh/1.8f);
        g2.setPosition(g1.getX() + g1.getWidth(),wh/20);

        b1.setSize(ww/20,wh/1.8f);
        b1.setPosition(ww/30*9,wh/20);
        b2.setSize(ww/20,wh/1.8f);
        b2.setPosition(b1.getX() + b1.getWidth(),wh/20);

        speed.setSize(ww/20,wh/1.8f);
        speed.setPosition(ww/30*13f,wh/20);

        rLabel.setPosition(r1.getX(),r1.getY() + r1.getHeight());
        rLabel.setSize(2*r1.getWidth(),wh/10);
        gLabel.setPosition(g1.getX(),g1.getY() + g1.getHeight());
        gLabel.setSize(2*g1.getWidth() ,wh/10);
        bLabel.setPosition(b1.getX(),b1.getY() + b1.getHeight());
        bLabel.setSize(2*b1.getWidth(),wh/10);
        speedLabel.setPosition(speed.getX(),speed.getY() + speed.getHeight());
        speedLabel.setSize(speed.getWidth(),wh/10);

        min1.setPosition(r1.getX(),0);
        min1.setSize(r1.getWidth(),r1.getY());
        max1.setPosition(r2.getX(),0);
        max1.setSize(r2.getWidth(),r2.getY());
        min2.setPosition(g1.getX(),0);
        min2.setSize(g1.getWidth(),g1.getY());
        max2.setPosition(g2.getX(),0);
        max2.setSize(g2.getWidth(),g2.getY());
        min3.setPosition(b1.getX(),0);
        min3.setSize(b1.getWidth(),b1.getY());
        max3.setPosition(b2.getX(),0);
        max3.setSize(b2.getWidth(),b2.getY());

        back.setSize(wh/9,wh/9);
        back.setPosition(ww - back.getWidth()/2*3,wh - back.getHeight()/2*3);

        checkBox.getImage().setScaling(Scaling.fill);
        checkBox.getImageCell().size(wh/20);
        checkBox.getStyle().font.getData().setScale(1.5f);
        checkBox.setPosition(wh/15,wh - checkBox.getImageCell().getMaxHeight() - wh/15);


        stage = new Stage(HudViewport);
        stage.addActor(r1);
        stage.addActor(r2);

        stage.addActor(g1);
        stage.addActor(g2);

        stage.addActor(b1);
        stage.addActor(b2);

        stage.addActor(speed);

        stage.addActor(rLabel);
        stage.addActor(gLabel);
        stage.addActor(bLabel);
        stage.addActor(speedLabel);

        stage.addActor(min1);
        stage.addActor(max1);

        stage.addActor(min2);
        stage.addActor(max2);

        stage.addActor(min3);
        stage.addActor(max3);

        stage.addActor(back);

        stage.addActor(checkBox);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        HudCamera.update();
        colorCamera.update();

        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (checkBox.isChecked() && isVisible){
            for (Actor actor : stage.getActors()){
                if (!(actor instanceof  CheckBox))
                    actor.setVisible(false);
            }
            isVisible = false;
        }
        else if (!checkBox.isChecked() && !isVisible){
            for (Actor actor : stage.getActors()){
                if (!(actor instanceof  CheckBox))
                    actor.setVisible(true);
            }
            isVisible = true;
            visibilityCounter = 0;
            checkBox.setColor(1,1,1,1);
        }

        if (Gdx.input.isTouched() && !isVisible  && !checkBox.isVisible()){
           checkBox.setVisible(true);
           visibilityCounter = 0f;
        }else if (!isVisible  && checkBox.isVisible()){
            visibilityCounter += delta;
            if (visibilityCounter > visibilityTime){
                checkBox.setVisible(false);
            }
        }

        if (checkBox.isVisible())
            checkBox.setColor(1,1,1,(visibilityTime - visibilityCounter)/visibilityTime);

        update(delta);

        Main.shapeRenderer.setProjectionMatrix(colorCamera.combined);
        Main.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Main.shapeRenderer.rect(0,0,Px,Py,colors[0],colors[1],colors[2],colors[3]);
        Main.shapeRenderer.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        aspectRatio = (float)width/(float)height;
        ww = wh*aspectRatio;

        Px = width;
        Py = height;

        System.out.println("Width :" + width + " Height :" + height);

        HudViewport.update(width,height);
        HudCamera.position.set(HudCamera.viewportWidth/2,HudCamera.viewportHeight/2,0);

        colorViewport.update(width,height);
        colorCamera.position.set(colorCamera.viewportWidth/2,colorCamera.viewportHeight/2,0);
    }


    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }

    private void update(float delta){
        for (int i=0;i<colors.length;i++){
            colors[i].r += speeds[i][0]*ratio*delta*60f;
            if (colors[i].r >= rMax){
                colors[i].r = rMax;
                speeds[i][0] = -random.nextFloat();
            }else if (colors[i].r <= rMin){
                colors[i].r = rMin;
                speeds[i][0] = random.nextFloat();
            }

            colors[i].g += speeds[i][1]*ratio*delta*60f;
            if (colors[i].g >= gMax){
                colors[i].g = gMax;
                speeds[i][1] = -random.nextFloat();
            }else if (colors[i].g <= gMin){
                colors[i].g = gMin;
                speeds[i][1] = random.nextFloat();
            }

            colors[i].b += speeds[i][2]*ratio*delta*60f;
            if (colors[i].b >= bMax){
                colors[i].b = bMax;
                speeds[i][2] = -random.nextFloat();
            }else if (colors[i].b <= bMin){
                colors[i].b = bMin;
                speeds[i][2] = random.nextFloat();
            }
        }
    }
}