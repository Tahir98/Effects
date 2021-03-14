package com.tahirkaplan.effects.Scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.tahirkaplan.effects.Main;
import com.tahirkaplan.effects.Extras.Particle;
import com.tahirkaplan.effects.ads.AdsController;

import java.util.ArrayList;
import java.util.Random;

public class Pureness extends Scene {

    private Game game;
    private AdsController adsController;

    private ArrayList<Particle> particles;
    private Particle particle;

    private Random random;

    private float density = 20;
    private float deletingPoint = 100;
    private float spreadingSpeed = 1.2f;

    private float sin[];
    private float cos[];

    private float x,y;

    private Sprite sprite;
    private Texture texture;

    private Stage stage;
    private Button back;

    public Pureness(Game game,AdsController adsController){
        this.game = game;
        this.adsController = adsController;
    }

    @Override
    public void show() {
        super.show();

        particles = new ArrayList<Particle>();
        particle = new Particle();

        stage = new Stage(viewport);

        random = new Random();

        texture = new Texture("black.png");
        sprite = new Sprite(texture);
        sprite.setSize(9,9);

        sin = new float[360];
        cos = new float[360];

        for(int i=0;i<360;i++){
            sin[i] = (float)Math.sin(Math.toRadians(i));
            cos[i] = (float)Math.cos(Math.toRadians(i));
        }

        back = new ImageButton(new TextureRegionDrawable(new Texture("back.png")));
        back.setSize(wh/9,wh/9);
        back.setPosition(ww - back.getWidth() - wh/20,wh/20);

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

        stage.addActor(back);

        Gdx.input.setInputProcessor(stage);


    }

    @Override
    public void render(float delta) {
        camera.update();
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        input(delta);
        update(delta);

        Main.batch.setProjectionMatrix(camera.combined);
        Main.batch.begin();

        for (int i=0;i<particles.size();i++){
            sprite.setPosition(particles.get(i).x,particles.get(i).y);
            sprite.draw(Main.batch, (spreadingSpeed*delta*60f+deletingPoint - particles.get(i).deletingPoint)/deletingPoint);
        }

        Main.batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        back.setSize(wh/9,wh/9);
        back.setPosition(ww - back.getWidth() - wh/20,wh/20);

        Px = width;
        Py = height;
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }

    private void input(float delta){

        for (int i=0;i<5;i++){
            if(Gdx.input.isTouched(i)){
                x = Gdx.input.getX(i)/Px*ww;
                y = (Py - Gdx.input.getY(i))/Py*wh;
                for(int j=0;j<density*60*delta;j++){
                    particles.add(new Particle(x,y,random.nextInt(360),0));
                }
            }
        }


        if(Gdx.input.isKeyPressed(Input.Keys.T)){
            Gdx.input.setOnscreenKeyboardVisible(true);
        }

    }

    private void update(float delta){
        for(int i=0;i<particles.size();){
            particle = particles.get(i);

            if(particle.deletingPoint > deletingPoint){
                particles.remove(i);
                continue;
            }

            particle.x += cos[particle.degree]*spreadingSpeed*delta*60f;
            particle.y += sin[particle.degree]*spreadingSpeed*delta*60f;
            particle.deletingPoint += spreadingSpeed*delta*60f;
            particles.set(i,particle);

            i++;
        }
    }
}
