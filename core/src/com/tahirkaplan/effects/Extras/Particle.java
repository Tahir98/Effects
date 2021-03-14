package com.tahirkaplan.effects.Extras;

public class Particle {

    public float x,y;
    public float deletingPoint;
    public int degree;

    public Particle(){

    }

    public Particle(float x,float y,int degree,float deletingPoint){
        this.x = x;
        this.y = y;
        this.degree = degree;
        this.deletingPoint = deletingPoint;
    }

}
