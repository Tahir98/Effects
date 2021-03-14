package com.tahirkaplan.effects.Extras;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

public class Circle {

    public float x,y;
    public float radius;
    public Vector2 velocity;
    public boolean isControllable;
    public Color color;

    public Circle(){
        velocity = new Vector2(0,0);
    }

    public Circle(float x,float y,float radius){
        this.x = x;
        this.y = y;
        this.radius = radius;
        velocity = new Vector2(0,0);
        isControllable = false;
        color = new Color(0,0,0,1);
    }

    public Circle(float x,float y,float radius,Vector2 velocity){
        this.velocity = new Vector2();
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.velocity.x = velocity.x;
        this.velocity.y = velocity.y;
        isControllable = false;
        color = new Color(0,0,0,1);
    }

    public Circle(float x,float y,float radius,Vector2 velocity,boolean isControllable,Color color){
        this.velocity = new Vector2();
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.velocity.x = velocity.x;
        this.velocity.y = velocity.y;
        this.isControllable = isControllable;
        this.color = color;
    }

    public Circle(Circle circle){
        velocity = new Vector2();
        this.x = circle.x;
        this.y = circle.y;
        this.radius = circle.radius;
        this.velocity.x = circle.velocity.x;
        this.velocity.y = circle.velocity.y;
        this.isControllable = circle.isControllable;
        this.color = circle.color;
    }

}
