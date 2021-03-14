package com.tahirkaplan.effects.Extras;

public class Particle2 {
    public float x,y;
    public float degree;
    public float deletingPoint;
    public boolean isWhite;

    public Particle2(){

    }

    public Particle2(Particle2 particle2){
        this.x = particle2.x;
        this.y = particle2.y;
        this.degree = particle2.degree;
        this.deletingPoint = particle2.deletingPoint;
        this.isWhite = particle2.isWhite;
    }

    public Particle2(float x,float y,float degree,float deletingPoint,boolean isWhite){
        this.x = x;
        this.y = y;
        this.degree = degree;
        this.deletingPoint = deletingPoint;
        this.isWhite = isWhite;
    }


}

