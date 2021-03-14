package com.tahirkaplan.effects;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tahirkaplan.effects.Scenes.MainMenu;
import com.tahirkaplan.effects.Scenes.Sample;
import com.tahirkaplan.effects.ads.AdsController;

public class Main extends Game {

	private AdsController adsController;
	public static SpriteBatch batch;
	public static ShapeRenderer shapeRenderer;

	public Main(AdsController adsController){
		this.adsController = adsController;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		this.setScreen(new MainMenu(this,adsController));
	}
}
