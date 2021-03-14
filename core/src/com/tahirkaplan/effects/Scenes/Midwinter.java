package com.tahirkaplan.effects.Scenes;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.tahirkaplan.effects.Extras.Snow;
import com.tahirkaplan.effects.Main;
import com.tahirkaplan.effects.ads.AdsController;

import java.util.Random;

public class Midwinter extends Scene{

    private Game game;
    private AdsController adsController;

    private Texture background1;
    private Texture background2;
    private Texture particle;

    private Stage stage;
    private Button back;
    private float visibilityCounter = 0f;
    private float visibilityTime = 1.5f;

    private Label densityLabel;
    private Label windLabel;

    /*****************************/
    private float density = 10f;/**snow density*/
    private float carry = 0;

    private float wind = 5f;
    private float ratio = 30f;

    private float[] fallingSpeed;
    private float[] speed;
    private Snow snow;
    private Array<Snow> snows;

    private Random random;
    /*****************************/

    private boolean touched = false;
    private float x0,x,y0,y;
    private float tempDensity;
    private float tempWind;
    private float w;

    public Midwinter(Game game,AdsController adsController){
        this.game = game;
        this.adsController = adsController;
    }


    @Override
    public void show() {
        super.show();
        System.out.println("Show method worked");

        background1 = new Texture("winter1.png");
        background2 = new Texture("winter2.png");
        particle = new Texture("particle.png");

        stage = new Stage(viewport);

        back = new ImageButton(new TextureRegionDrawable(new Texture("back.png")));
        back.setSize(wh/9,wh/9);
        back.setPosition(ww - back.getWidth() - wh/20,wh - back.getHeight() - wh/20);

        back.addListener(new InputListener(){

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                game.setScreen(new MainMenu(game,adsController));
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            }
        });

        stage.addActor(back);

        snow = new Snow();
        random = new Random();

        densityLabel = new Label("Density = ",new Skin(Gdx.files.internal("glassy/skin/glassy-ui.json")));
        windLabel = new Label("Wind = ",new Skin(Gdx.files.internal("glassy/skin/glassy-ui.json")));

        densityLabel.setFontScale(wh/250f);
        windLabel.setFontScale(wh/250f);

        densityLabel.setSize(ww/3,wh/6);
        windLabel.setSize(ww/3,wh/6);

        densityLabel.setAlignment(Align.top);
        windLabel.setAlignment(Align.bottom);


        densityLabel.setPosition(ww/3,wh/2 - densityLabel.getHeight()/2);
        windLabel.setPosition(ww/3,wh/2 - windLabel.getHeight()/2);

        densityLabel.setColor(Color.BLACK);
        windLabel.setColor(Color.BLACK);

        densityLabel.setVisible(false);
        windLabel.setVisible(false);

        stage.addActor(densityLabel);
        stage.addActor(windLabel);

        fallingSpeed = new float[3];
        fallingSpeed[0] = 1.00f;
        fallingSpeed[1] = 1.35f;
        fallingSpeed[2] = 1.70f;

        speed = new float[3];
        speed[0] = (fallingSpeed[2] - 0.10f)*1.5f;
        speed[1] = (fallingSpeed[1])*1.5f;
        speed[2] = (fallingSpeed[0] + 0.10f)*1.5f;

        w = aspectRatio/(16f/9f);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        input(delta);
        addSnow(delta);
        update(delta);

        Main.batch.setProjectionMatrix(camera.combined);
        Main.batch.begin();

        /*if(aspectRatio > 16f/9f){
            Main.batch.draw(background1,0,0,720,wh);
            Main.batch.draw(background1,718,0,720,wh);
        }else{
            Main.batch.draw(background2,0,0,ww,wh);
        }*/

        if(aspectRatio > 16f/9f){
            for (int i=0;i<=aspectRatio;i++){
                Main.batch.draw(background1,i*718,0,720,wh);
            }
        }else{
            Main.batch.draw(background2,0,0,ww,wh);
        }

        drawSnow();
        Main.batch.end();

        if (back.isVisible()){
            ((ImageButton) back).getImage().setColor(1,1,1,(visibilityTime-visibilityCounter)/visibilityTime);
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);

        addBeginningSnow();

        back.setSize(wh/9,wh/9);
        back.setPosition(ww - back.getWidth() - wh/20,wh - back.getHeight() - wh/20);

        densityLabel.setSize(ww/3,wh/6);
        windLabel.setSize(ww/3,wh/6);

        densityLabel.setPosition(ww/3,wh/2 - densityLabel.getHeight()/2);
        windLabel.setPosition(ww/3,wh/2 - windLabel.getHeight()/2);

        w = aspectRatio/(16f/9f);

    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }

    /**adds initial snows when the scenes is first time opened or when the user touched up his finger*/
    private void addBeginningSnow(){

        snows = new Array<Snow>();

        for(int i=0;i<density*(wh/fallingSpeed[1])*w;i++){
            snow.size = 2 + 2*random.nextInt(3);
            snow.deletingPoint = (6 - snow.size) * wh/30 + random.nextInt((int)wh/30);
            snow.y = snow.deletingPoint + random.nextInt((int)(wh + 10 - snow.deletingPoint));

            if(wind > 0){
                snow.x = random.nextInt((int)(ww + (snow.y- snow.deletingPoint)/fallingSpeed[((int)snow.size)/2-1]*wind/ratio*speed[(6 - (int)snow.size)/2]))
                        - (snow.y- snow.deletingPoint)/fallingSpeed[((int)snow.size)/2-1]*wind/ratio*speed[(6 - (int)snow.size)/2];
            }else{
                snow.x = random.nextInt((int)(ww + (snow.y- snow.deletingPoint)/fallingSpeed[((int)snow.size)/2-1]*-wind/ratio*speed[(6 - (int)snow.size)/2]));
            }
            snows.add(new Snow(snow.x,snow.y,snow.size,snow.deletingPoint));
        }
    }

    /**controls inputs*/
    private void input(float delta){
        if(Gdx.input.isTouched()){
            if (!back.isVisible()){
                back.setVisible(true);
                visibilityCounter = 0f;
            }else {
                visibilityCounter = 0f;
            }

            if (!touched){
                touched = true;
                x0 = Gdx.input.getX()*(ww/Px);
                y0 = Py - Gdx.input.getY()*(wh/Py);

                densityLabel.setVisible(true);
                windLabel.setVisible(true);
            }else{
                x = Gdx.input.getX()*(ww/Px);
                y = Py - Gdx.input.getY()*(wh/Py);

                tempDensity = density + (y - y0)/25f;
                tempWind = wind + (x - x0)/20f;

                if(tempDensity > 100){
                    tempDensity = 100;
                }else if (tempDensity < 0) {
                    tempDensity = 0;
                }

                if (tempWind > 100) {
                    tempWind = 100;
                }else if (tempWind < -100){
                    tempWind = -100;
                }

                densityLabel.setText(String.format("Density = %.2f",tempDensity));
                windLabel.setText(String.format("Wind = %.2f",tempWind));
            }
        }else {
            if (visibilityCounter > visibilityTime){
                if (back.isVisible())
                    back.setVisible(false);
            }else{
                visibilityCounter += delta;
            }

            if(touched){
                touched = false;
                density = tempDensity;
                wind = tempWind;
                addBeginningSnow();

                densityLabel.setVisible(false);
                windLabel.setVisible(false);
            }
        }
    }


    /**adds snows to the arraylist*/
    private void addSnow(float delta){
        /**for  loop can not execute the fractional part of density so it is added up to a carry variable.When the carry reach one,
         * the method add an additional snow particle*/
        carry += (density*delta*60f*w)%1f;

        for(int i=0;i<(int)(density*delta*60f*w);i++){
            snow.size = 2 + 2*random.nextInt(3);
            snow.deletingPoint = (6 - snow.size) * wh/30 + random.nextInt((int)wh/30);
            snow.y = wh + random.nextInt(10);

            if(wind > 0){
                snow.x = random.nextInt((int)(ww + (wh- snow.deletingPoint)/fallingSpeed[((int)snow.size)/2-1]*wind/ratio*speed[(6 - (int)snow.size)/2]))
                        - (wh- snow.deletingPoint)/fallingSpeed[((int)snow.size)/2-1]*wind/ratio*speed[(6 - (int)snow.size)/2];
            }else{
                snow.x = random.nextInt((int)(ww + (wh- snow.deletingPoint)/fallingSpeed[((int)snow.size)/2-1]*-wind/ratio*speed[(6 - (int)snow.size)/2]));
            }

            snows.add(new Snow(snow.x,snow.y,snow.size,snow.deletingPoint));
        }

        /**additional snow particle when the carry reach one*/
        if(carry >= 1f){
            snow.size = 2 + 2*random.nextInt(3);
            snow.deletingPoint = (6 - snow.size) * wh/30 + random.nextInt((int)wh/30);
            snow.y = wh + random.nextInt(10);

            if(wind > 0){
                snow.x = random.nextInt((int)(ww + (wh- snow.deletingPoint)/fallingSpeed[((int)snow.size)/2-1]*wind/ratio*speed[(6 - (int)snow.size)/2]))
                        - (wh- snow.deletingPoint)/fallingSpeed[((int)snow.size)/2-1]*wind/ratio*speed[(6 - (int)snow.size)/2];

            }else{
                snow.x = random.nextInt((int)(ww + (wh- snow.deletingPoint)/fallingSpeed[((int)snow.size)/2-1]*-wind/ratio*speed[(6 - (int)snow.size)/2]));
            }

            snows.add(new Snow(snow.x,snow.y,snow.size,snow.deletingPoint));
            carry -= 1f;
        }

    }

    /**updates and deletes snow particles */
    private void update(float delta){
        for(int i=0;i < snows.size;){
            snow = snows.get(i);
            snow.y -= fallingSpeed[(int)snow.size/2-1]*delta*60f;
            snow.x += wind/ratio*(speed[(6 - (int)snow.size)/2])*delta*60f;

            if(wind > 0 && snow.x > ww + 5){
                snows.removeIndex(i);
                continue;
            }else if (wind < 0 && snow.x < -5){
                snows.removeIndex(i);
                continue;
            }

            if(snow.y < snow.deletingPoint){
                snows.removeIndex(i);
                continue;
            }
            snows.set(i++,snow);
        }

    }

    /**draws snow particles*/
    private void drawSnow(){
        for(int i=0;i<snows.size;i++){
            if (snows.get(i).x > -10 && snows.get(i).x < ww )
                Main.batch.draw(particle,snows.get(i).x,snows.get(i).y,snows.get(i).size*0.95f,snows.get(i).size*0.95f);
        }
    }
}




