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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testModel = loadTestModelFromYaml();
        final ViewPager2 viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new SectionPagerAdapter(this, testModel));
// detect when user reaches the last page
        viewPager.registerOnPageChangeCallback(new MyOnPageChangeCallback(viewPager, testModel, this));
    }

    public void showFinalResult() {
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
