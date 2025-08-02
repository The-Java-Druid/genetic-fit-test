package com.geneticfittest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.geneticfittest.model.TestModel;
import com.geneticfittest.serialisation.TestLoader;
import com.geneticfittest.ui.ResultActivity;
import com.geneticfittest.ui.SectionPagerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final TestLoader YAML = new TestLoader();
    private final String[] loadingMessages = {
        "Calculating your score…",
        "Analyzing genetics…",
        "Checking muscle potential…",
        "Almost there!"
    };
    private TestModel testModel;
    private AdView adView;
    private InterstitialAd interstitialAd;
    private View preAdOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testModel = loadTestModelFromYaml();
        adView = findViewById(R.id.adView);
        preAdOverlay = findViewById(R.id.preAdOverlay);
        new Thread(this::initializeAds).start();
        setupViewPager();
    }

    public void showFinalResult() {
        showPreAdAnimation(() -> {
            if (interstitialAd != null) {
                interstitialAd.show(this);
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        interstitialAd = null;
                        loadInterstitialAd(); // preload next ad
                        openResultActivity();
                    }
                });
            } else {
                openResultActivity();
            }
        });
    }

    private void openResultActivity() {
        ResultActivity.start(
            this, testModel.calculateTotalScore(), testModel.getResultText());
    }

    private TestModel loadTestModelFromYaml() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("test.yml")) {
            return YAML.load(is);
        } catch (Exception e) {
            Log.e("MainActivity", "Failed to load test", e);
            Toast.makeText(this, "Failed to load test: " + e.getMessage(), Toast.LENGTH_LONG).show();
            throw new RuntimeException(e);
        }
    }

    private void initializeAds() {
        MobileAds.initialize(this, initializationStatus -> {
        });
        runOnUiThread(() -> {
            loadInterstitialAd();
            loadBannerAd();
            refreshAd();
        });
    }

    private void loadBannerAd() {
        adView.loadAd(new AdRequest.Builder().build());
    }

    private void scheduleAdRefresh() {
        new Handler(Looper.getMainLooper())
            .postDelayed(this::refreshAd, 60000); // refresh every 60s
    }

    private void setupViewPager() {
        final ViewPager2 viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new SectionPagerAdapter(this, testModel));
        viewPager.registerOnPageChangeCallback(new MyOnPageChangeCallback(viewPager, testModel, this));
    }

    private void refreshAd() {
        loadBannerAd();
        scheduleAdRefresh(); // schedule again
    }

    private void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,
            "ca-app-pub-3940256099942544/1033173712", // TODO: Replace with your Interstitial Ad Unit ID
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
    }

}
