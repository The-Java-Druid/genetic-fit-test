package com.geneticfittest;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.geneticfittest.model.TestModel;
import com.geneticfittest.serialisation.TestLoader;
import com.geneticfittest.ui.ResultActivity;
import com.geneticfittest.ui.SectionPagerAdapter;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final TestLoader YAML = new TestLoader();
    private TestModel testModel;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testModel = loadTestModelFromYaml();
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new SectionPagerAdapter(this, testModel));
// detect when user reaches the last page
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                int lastIndex = testModel.getSections().size() - 1;
                if (position == lastIndex) {
                    Toast.makeText(MainActivity.this, "Swipe again to finish", Toast.LENGTH_SHORT).show();
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
                        showFinalResult(); // user tried to go past last
                    }
                }
            }
        });
    }

    public void showFinalResult() {
        final int totalScore = testModel.calculateTotalScore();
        final String resultText = testModel.getResultText();
        ResultActivity.start(this, totalScore, resultText);
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

}
