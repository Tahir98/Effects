package com.tahirkaplan.effects.Scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.tahirkaplan.effects.Extras.Circle;
import com.tahirkaplan.effects.Main;
import com.tahirkaplan.effects.ads.AdsController;

import java.util.ArrayList;
import java.util.Random;

public class CircleEng extends Scene {

    private Game game;
    private AdsController adsController;

    private float x,y;
    private float radius = 15;
    private float d;

    private float gravity = -0.15f;
    private float airFriction = 0.001f;

    private ArrayList<Circle> circles;
    private Circle circle;
    private Circle circle2;
    private Circle temp;

    private float width,height;

    private Stage stage;

    private ImageButton back;
    private Label elements;

    private Random random;

    public CircleEng(Game game,AdsController adsController){
        this.game = game;
        this.adsController = adsController;
    }

    @Override
    public void show() {
        super.show();

        circles = new ArrayList<Circle>();
        circle = new Circle();
        Main.shapeRenderer.setColor(Color.RED);

        stage = new Stage(viewport,Main.batch);

        back = new ImageButton(new TextureRegionDrawable(new Texture("back.png")));
        back.setSize(wh/8,wh/8);
        back.setPosition( wh/20,wh - back.getHeight() - wh/20);

        back.addListener(new InputListener(){
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

        elements = new Label("Elements",new Skin(Gdx.files.internal("glassy/skin/glassy-ui.json")));
        elements.setSize(ww/5,wh/8);
        elements.setPosition(ww - elements.getWidth(),wh - elements.getHeight());
        elements.setAlignment(Align.center);
        elements.setFontScale(wh/400);
        elements.setColor(Color.BLACK);

        stage.addActor(elements);

        Gdx.input.setInputProcessor(stage);
        random = new Random();
    }

    @Override
    public void render(float delta) {
        camera.update();
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        input(delta);
        update(delta);
        collision(delta);

        Main.shapeRenderer.setProjectionMatrix(camera.combined);
        Main.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        draw();

        Main.shapeRenderer.end();

        elements.setText(circles.size());

        stage.act(200);
        stage.draw();


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

        if (Gdx.input.isKeyPressed(Input.Keys.UP)){
            for (int i=0;i<circles.size();i++){
                circle = circles.get(i);
                if (circle.isControllable){
                    circle.velocity.y += (0.2f - gravity)*delta*60f;
                    circles.set(i,circle);
                }

            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            for (int i=0;i<circles.size();i++){
                circle = circles.get(i);
                if (circle.isControllable){
                    circle.velocity.y += (-0.2f + gravity)*delta*60f;
                    circles.set(i,circle);
                }

            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            for (int i=0;i<circles.size();i++){
                circle = circles.get(i);
                if (circle.isControllable){
                    circle.velocity.x += -0.1f*delta*60f;
                    circles.set(i,circle);
                }

            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            for (int i=0;i<circles.size();i++){
                circle = circles.get(i);
                if (circle.isControllable){
                    circle.velocity.x += 0.1f*delta*60f;
                    circles.set(i,circle);
                }

            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.C))
            circles.clear();

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            game.setScreen(new MainMenu(game,adsController));

        for (int j=0;j<5;j++){
            if (Gdx.input.isTouched(j)){
                x = Gdx.input.getX(j)*ww/Px;
                y = (Py - Gdx.input.getY(j))*wh/Py;

                float width,height;
                boolean isEmpty = true;

                for (int i=0;i<circles.size();i++){
                    circle = circles.get(i);
                    width = Math.abs(x - circle.x);
                    height = Math.abs(y - circle.y);
                    if (width*width + height*height < (2*radius)*(2*radius)){
                        isEmpty = false;
                        break;
                    }
                }
                if (isEmpty)
                    circles.add(new Circle(x,y,radius,new Vector2(0,0),random.nextBoolean(),
                            new Color(0.25f + random.nextFloat()*0.75f,0.25f + random.nextFloat()*0.75f,0.25f + random.nextFloat()*0.75f,1)));
            }
        }

    }

    private void update(float delta){

       for (int i=0;i<circles.size();){

            if (circle.y <= circle.radius){
                circle.y = circle.radius + (circle.radius - circle.y);
                circle.velocity.y  *= -1f;
            }

            circle = circles.get(i);
            circle.velocity.y += gravity*delta*60f;
            circle.y += circle.velocity.y*delta*60f;
            circle.x += circle.velocity.x*delta*60f;

           if (circle.y < circle.radius){
               circle.y = circle.radius + (circle.radius - circle.y)*0.3f;
               circle.velocity.y  *= -0.3f;
               if (Math.abs(circle.velocity.y) < -gravity*delta*60f)
                   circle.velocity.y = 0f;
           }

            if (circle.x + circle.radius < 0 || circle.x - circle.radius > ww){
                circles.remove(i);
                continue;
            }
            circles.set(i++,circle);
       }

    }

    private void draw(){
        for (int i=0;i<circles.size();i++){
            Main.shapeRenderer.setColor(circles.get(i).color);
            Main.shapeRenderer.circle(circles.get(i).x,circles.get(i).y,circles.get(i).radius,30);
        }
    }

    private void collision(float delta){

        for (int i = 0; i < circles.size(); i++) {

            circle = circles.get(i);

            for (int j = i + 1; j < circles.size(); j++) {

                if (i == j) continue;
                circle2 = circles.get(j);
                width = circle2.x - circle.x;
                height = circle2.y - circle.y;

                d = (float) Math.sqrt(width * width + height * height);

                if (width * width + height * height < (2 * radius) * (2 * radius)) {
                    temp = new Circle(circle);
                    circle.velocity.x = circle2.velocity.len() * (-width / d) * 0.95f;
                    circle.velocity.y = circle2.velocity.len() * (-height / d) * 0.95f;

                    circle2.velocity.y = temp.velocity.len() * (height / d) * 0.95f;
                    circle2.velocity.x = temp.velocity.len() * (width / d) * 0.95f;

                    circle.x += (2 * radius - d) / 2 * (-width / d);
                    circle.y += (2 * radius - d) / 2 * (-height / d);

                    circle2.x += (2 * radius - d) / 2 * (width / d);
                    circle2.y += (2 * radius - d) / 2 * (height / d);

                    circles.set(j, circle2);
                }
            }

            /*if (circle.y < circle.radius){
                circle.y = circle.radius + (circle.radius - circle.y)*1f;
                circle.velocity.y  *= -1f;
            }*/
            circles.set(i, circle);
        }

    }


}





