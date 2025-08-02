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
import com.geneticfittest.ui.InterstitialAdManager;
import com.geneticfittest.ui.ResultActivity;
import com.geneticfittest.ui.SectionPagerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final TestLoader YAML = new TestLoader();
    private TestModel testModel;
    private AdView adView;
    private InterstitialAdManager interstitialAdManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testModel = loadTestModelFromYaml();
        adView = findViewById(R.id.adView);
        interstitialAdManager = new InterstitialAdManager(findViewById(R.id.preAdOverlay), this);
        new Thread(this::initializeAds).start();
        setupViewPager();
    }

    public void showFinalResult() {
        interstitialAdManager.runAfterAd(this::openResultActivity);
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
        MobileAds.initialize(this, initializationStatus -> {});
        runOnUiThread(() -> {
            interstitialAdManager.loadInterstitialAd();
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
        viewPager.registerOnPageChangeCallback(new ResultsViewMyOnPageChangeCallback(viewPager, testModel, this));
        viewPager.registerOnPageChangeCallback(new SlideBannerAdCallback(this));
    }

    private void refreshAd() {
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
