package com.geneticfittest;

import android.os.Handler;
import android.os.Looper;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class BannerAdManager {
    private final AdView adView;

    public BannerAdManager(AdView adView) {
        this.adView = adView;
    }

    public void loadBannerAd() {
        adView.loadAd(new AdRequest.Builder().build());
    }

    public void scheduleAdRefresh() {
        new Handler(Looper.getMainLooper())
            .postDelayed(this::refreshAd, 60000); // refresh every 60s
    }

    public void refreshAd() {
        loadBannerAd();
        scheduleAdRefresh(); // schedule again
    }

    public void slideInAd() {
        adView.postDelayed(() -> {
            adView.animate()
                .translationY(0)
                .setDuration(300)
                .start();
        }, 400);
    }

    public void slideOutAd() {
        adView.animate()
            .translationY(adView.getHeight())
            .setDuration(300)
            .start();
    }
}
