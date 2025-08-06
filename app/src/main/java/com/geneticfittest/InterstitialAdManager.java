package com.geneticfittest;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class InterstitialAdManager {

    private final View preAdOverlay;
    private final Activity activity;
    private InterstitialAd interstitialAd;

    private static final String[] loadingMessages = {
        "Calculating your score…",
        "Analyzing genetics…",
        "Checking muscle potential…",
        "Almost there!"
    };

    public InterstitialAdManager(View preAdOverlay, Activity activity) {
        this.preAdOverlay = preAdOverlay;
        this.activity = activity;
    }

    public void loadInterstitialAd() {
        final AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(activity,
            "ca-app-pub-5391740461770424/4658511219",
            adRequest,
            new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd ad) {
                    interstitialAd = ad;
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                    interstitialAd = null;
                }
            });
    }

    public void runAfterAd(final Runnable action) {
        showPreAdAnimation(() -> {
            if (interstitialAd != null) {
                interstitialAd.show(activity);
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        interstitialAd = null;
                        loadInterstitialAd(); // preload next ad
                        action.run();
                    }
                });
            } else {
                action.run();
            }
        });
    }

    private void showPreAdAnimation(Runnable onFinish) {
        preAdOverlay.setVisibility(View.VISIBLE);
        preAdOverlay.setAlpha(0f);
        preAdOverlay.setTranslationY(100f); // starts off-screen
        final TextView preAdMessage = preAdOverlay.findViewById(R.id.preAdMessage);

        // Cycle messages while overlay is shown
        final Handler handler = new Handler(Looper.getMainLooper());
        final int[] index = {0};
        final Runnable messageUpdater = new Runnable() {
            @Override
            public void run() {
                preAdMessage.setText(loadingMessages[index[0]]);
                index[0] = (index[0] + 1) % loadingMessages.length;
                handler.postDelayed(this, 400);
            }
        };
        // Start cycling messages
        handler.post(messageUpdater);
        // Slide in + fade in
        preAdOverlay.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(400)
            .withEndAction(() -> {
                // Hold for 0.7s, then hide and call onFinish
                preAdOverlay.postDelayed(() -> {
                    preAdOverlay.animate()
                        .alpha(0f)
                        .translationY(100f)
                        .setDuration(400)
                        .withEndAction(() -> {
                            preAdOverlay.setVisibility(View.GONE);
                            onFinish.run();
                        }).start();
                }, 700);
            }).start();
    }}
