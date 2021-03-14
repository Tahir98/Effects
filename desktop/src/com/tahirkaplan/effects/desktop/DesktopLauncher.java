package com.tahirkaplan.effects.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.tahirkaplan.effects.Main;
import com.tahirkaplan.effects.ads.AdsController;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new Main(new AdsController() {
			@Override
			public boolean isWifiConnected() {
				return false;
			}

			@Override
			public boolean isInterstitialLoaded() {
				return false;
			}

			@Override
			public void showBannerAd() {

			}

			@Override
			public void hideBannerAd() {

			}

			@Override
			public void showInterstitialAd(Runnable then) {

			}
		}), config);
		config.width = 1280;
		config.height = 720;
		config.vSyncEnabled = false;
		config.fullscreen = false;
	}
}
