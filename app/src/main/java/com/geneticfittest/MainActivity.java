package com.geneticfittest;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.geneticfittest.model.TestModel;
import com.geneticfittest.serialisation.TestLoader;
import com.geneticfittest.ui.ResultActivity;
import com.geneticfittest.ui.SectionPagerAdapter;
import com.google.android.gms.ads.MobileAds;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final TestLoader YAML = new TestLoader();
    private TestModel testModel;
    private InterstitialAdManager interstitialAdManager;
    private BannerAdManager bannerAdManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testModel = loadTestModelFromYaml();
        interstitialAdManager = new InterstitialAdManager(findViewById(R.id.preAdOverlay), this);
        bannerAdManager = new BannerAdManager(findViewById(R.id.adView));
        new Thread(this::initializeAds).start();
        setupViewPager();
    }

    public void showFinalResult() {
        interstitialAdManager.runAfterAd(this::openResultActivity);
    }

    private void openResultActivity() {
        ResultActivity.start(this, testModel);
    }

    private TestModel loadTestModelFromYaml() {
        try (final InputStream is = getClass().getClassLoader().getResourceAsStream(getFileName())) {
            return YAML.load(is);
        } catch (Exception e) {
            Log.e("MainActivity", "Failed to load test", e);
            Toast.makeText(this, getString(R.string.failed_to_load_test) + e.getMessage(), Toast.LENGTH_LONG).show();
            throw new RuntimeException(e);
        }
    }

    @NonNull
    private static String getFileName() {
        final String languageCode = Locale.getDefault().getLanguage(); // e.g., "en", "es"
        final String fileName = languageCode + "/test.yml";
        final URL testFile = MainActivity.class.getClassLoader().getResource(fileName);
        if (testFile != null) {
            return fileName;
        } else {
            return "en/test.yml";
        }
    }

    private void initializeAds() {
        MobileAds.initialize(this, initializationStatus -> {});
        runOnUiThread(() -> {
            interstitialAdManager.loadInterstitialAd();
            bannerAdManager.loadBannerAd();
            bannerAdManager.refreshAd();
        });
    }


    private void setupViewPager() {
        final ViewPager2 viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new SectionPagerAdapter(this, testModel));
        viewPager.registerOnPageChangeCallback(new ResultsViewMyOnPageChangeCallback(viewPager, testModel, this));
        viewPager.registerOnPageChangeCallback(new SlideBannerAdCallback(bannerAdManager));
    }

}
