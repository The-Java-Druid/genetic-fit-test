package com.geneticfittest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

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
    private TestModel testModel;
    private AdView adView;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testModel = loadTestModelFromYaml();
        adView = findViewById(R.id.adView);
        new Thread(this::initializeAds).start();
        setupViewPager();
    }

    public void showFinalResult() {
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
                public void onAdLoaded(InterstitialAd ad) {
                    interstitialAd = ad;
                }

                @Override
                public void onAdFailedToLoad(LoadAdError adError) {
                    interstitialAd = null;
                }
            });
    }

    private void slideInAd() {
        adView.postDelayed(() -> {
            adView.animate()
                .translationY(0)
                .setDuration(300)
                .start();
        }, 400);
    }

    private void slideOutAd() {
        adView.animate()
            .translationY(adView.getHeight())
            .setDuration(300)
            .start();
    }

    private static class MyOnPageChangeCallback extends ViewPager2.OnPageChangeCallback {
        private final ViewPager2 viewPager;
        private final TestModel testModel;
        private final MainActivity mainActivity;

        public MyOnPageChangeCallback(ViewPager2 viewPager, TestModel testModel, MainActivity mainActivity) {
            this.viewPager = viewPager;
            this.testModel = testModel;
            this.mainActivity = mainActivity;
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            int lastIndex = testModel.getSections().size() - 1;
            // Slide out, then slide in
            mainActivity.slideOutAd();
            mainActivity.slideInAd();
            if (position == lastIndex) {
                Toast.makeText(mainActivity, "Swipe again to finish", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
            // detect when user tries to scroll past last page
            if (state == ViewPager2.SCROLL_STATE_IDLE) {
                int current = viewPager.getCurrentItem();
                int lastIndex = testModel.getSections().size() - 1;
                if (current > lastIndex) {
                    mainActivity.showFinalResult(); // user tried to go past last
                }
            }
        }
    }
}
