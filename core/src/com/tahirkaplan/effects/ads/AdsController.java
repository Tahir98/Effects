package com.tahirkaplan.effects.ads;

public interface AdsController {
    public boolean isClosed = false;
    public abstract boolean isWifiConnected();
    public abstract boolean isInterstitialLoaded();
    public void showBannerAd();
    public void hideBannerAd();
    public void showInterstitialAd(Runnable then);
}
