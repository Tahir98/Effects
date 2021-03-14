package com.tahirkaplan.effects.Scenes;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.tahirkaplan.effects.Extras.Particle2;
import com.tahirkaplan.effects.Main;
import com.tahirkaplan.effects.ads.AdsController;

import java.util.ArrayList;
import java.util.Random;

public class Spiral extends Scene{

    private Game game;
    private AdsController adsController;

    private ArrayList<Particle2> particles;
    private Particle2 particle;

    private float x1,y1,x2,y2;
    private float degree = 0;
    private float radius = wh/6f;
    private float deletingPoint= wh/2f;

    private float spreadingSpeed = 0.7f*60f;
    private float density = 30f;
    private float turningSpeed = 1.6f;
    private float carry = 0f;

    private Stage stage;
    private ImageButton back;

    /***************************/

    private Label densityLabel;
    private Label turningSpeedLabel;
    private Label fps;

    private float counter = 0f;

    private boolean isTouched = false;
    private float  tempDensity;
    private float tempTurningSpeed;

    private Vector2 origin;
    private Vector2 currentPosition;

    private Sprite white;
    private Sprite black;

    private Random random;

    public Spiral(Game game,AdsController adsController){
        this.game = game;
        this.adsController = adsController;
    }

    @Override
    public void show() {
        super.show();

        particles = new ArrayList<Particle2>();
        particle = new Particle2();

        stage = new Stage(viewport);

        back = new ImageButton(new TextureRegionDrawable(new Texture("back.png")));
        back.setSize(wh / 8, wh / 8);
        back.setPosition(ww - wh / 8 - wh / 20, wh / 20);

        back.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MainMenu(game,adsController));
                return false;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        stage.addActor(back);

        white = new Sprite(new Texture("particle.png"));
        black = new Sprite(new Texture("black.png"));

        white.setSize(10, 10);
        black.setSize(9, 9);

        random = new Random();

        Skin skin = new Skin(Gdx.files.internal("glassy/skin/glassy-ui.json"));

        densityLabel = new Label("Density =", skin);
        turningSpeedLabel = new Label("Turning Speed =", skin);

        densityLabel.setFontScale(wh/360f);
        turningSpeedLabel.setFontScale(wh/360f);

        densityLabel.setSize(ww/3, wh/6);
        turningSpeedLabel.setSize(ww/3, wh/6);

        densityLabel.setColor(Color.BLACK);
        turningSpeedLabel.setColor(Color.BLACK);

        densityLabel.setAlignment(Align.center);
        turningSpeedLabel.setAlignment(Align.center);

        densityLabel.setPosition(0, wh - densityLabel.getHeight());
        turningSpeedLabel.setPosition(ww - turningSpeedLabel.getWidth(), wh - densityLabel.getHeight());

        densityLabel.setVisible(false);
        turningSpeedLabel.setVisible(false);

        stage.addActor(densityLabel);
        stage.addActor(turningSpeedLabel);

        fps = new Label("FPS = ",skin);
        fps.setPosition(ww/20,wh/20);
        fps.setSize(ww/8,wh/8);
        fps.setAlignment(Align.center);
        fps.setColor(Color.BLACK);
        fps.setFontScale(wh/400f);
        fps.setVisible(true);
        stage.addActor(fps);

        Gdx.input.setInputProcessor(stage);

        origin = new Vector2();
        currentPosition = new Vector2();

    }

    @Override
    public void render(float delta) {
        camera.update();
        Gdx.gl.glClearColor(0,1,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        counter += delta*60f;
        if (counter >= 20f){
            counter %= 20f;
            fps.setText(String.format("FPS = %.2f",1f/delta));
        }

        input(delta);
        addParticles(delta);
        update(delta);

        Main.batch.setProjectionMatrix(camera.combined);
        Main.batch.begin();
        draw();
        Main.batch.end();

        stage.act();
        stage.draw();

        if (Gdx.app.getType() == Application.ApplicationType.Desktop)
            if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
                game.setScreen(new MainMenu(game,adsController));
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    private void input(float delta){

        if (Gdx.input.isTouched()){
            if (!isTouched){
                isTouched = true;

                origin.x = Gdx.input.getX()*ww/Px;
                origin.y = (Py - Gdx.input.getY())*wh/Py;

                densityLabel.setVisible(true);
                turningSpeedLabel.setVisible(true);
            }else {
                currentPosition.x = Gdx.input.getX()*ww/Px;
                currentPosition.y = (Py - Gdx.input.getY())*wh/Py;

                tempDensity = density + (currentPosition.y - origin.y)/30f;
                tempTurningSpeed = turningSpeed + (currentPosition.x - origin.x)/100f;

                if (tempDensity < 0f){
                    tempDensity = 0f;
                }else if (tempDensity > 120f){
                    tempDensity = 120f;
                }

                if (tempTurningSpeed < -180f){
                    tempTurningSpeed = -180f;
                }else if (tempTurningSpeed > 180f){
                    tempTurningSpeed = 180f;
                }

                densityLabel.setText(String.format("Density = %.2f",tempDensity));
                turningSpeedLabel.setText(String.format("Turning Speed = %.2f",tempTurningSpeed));
            }

        }else {
            if (isTouched){
                isTouched = false;

                //particles.clear();
                density = tempDensity;
                turningSpeed = tempTurningSpeed;

                densityLabel.setVisible(false);
                turningSpeedLabel.setVisible(false);
            }

        }

    }

    public void addParticles(float delta){
        degree += turningSpeed*delta*60f;
        if (degree >= 360f)
            degree %= 360f;

        x1 = ww/2 + radius*(float)Math.cos(Math.toRadians(degree));
        y1 = wh/2 + radius*(float)Math.sin(Math.toRadians(degree));

        x2 = ww/2 + radius*(float)Math.cos(Math.toRadians(degree + 180));
        y2 = wh/2 + radius*(float)Math.sin(Math.toRadians(degree + 180));

        carry += density*delta*60f % 1f;


        for (int i=0;i<(int)(2*density*delta*60f);i++){
            particle.isWhite = random.nextBoolean();
            if (particle.isWhite){
                particle.x = x1;
                particle.y = y1;
            }else{
                particle.x = x2;
                particle.y = y2;
            }

            particle.degree = random.nextInt(360);
            particle.deletingPoint = 0;

            particles.add(new Particle2(particle));
        }

        if (carry >= 1f){
            particle.isWhite = random.nextBoolean();
            if (particle.isWhite){
                particle.x = x1;
                particle.y = y1;
            }else{
                particle.x = x2;
                particle.y = y2;
            }

            particle.degree = random.nextInt(360);
            particle.deletingPoint = 0;

            particles.add(new Particle2(particle));

            carry += -1f;
        }

    }

    private void update(float delta){
        for (int i=0;i<particles.size();){
            particle = particles.get(i);
            particle.x += spreadingSpeed*delta*(float)Math.cos(Math.toRadians(particle.degree));
            particle.y += spreadingSpeed*delta*(float)Math.sin(Math.toRadians(particle.degree));
            particle.deletingPoint += spreadingSpeed*delta;

            if (particle.deletingPoint > deletingPoint){
                particles.remove(i);
                continue;
            }

            particles.set(i++,particle);
        }
    }

    public void draw(){
        for (int i=0;i<particles.size();i++){
            if (particles.get(i).isWhite){
                white.setPosition(particles.get(i).x,particles.get(i).y);
                white.draw(Main.batch,(deletingPoint - particles.get(i).deletingPoint)/deletingPoint);
            }else {
                black.setPosition(particles.get(i).x,particles.get(i).y);
                black.draw(Main.batch,(deletingPoint - particles.get(i).deletingPoint)/deletingPoint);
            }
        }
    }

}












