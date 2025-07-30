package com.geneticfittest;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.geneticfittest.model.TestModel;
import com.geneticfittest.serialisation.TestLoader;
import com.geneticfittest.ui.SectionFragment;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final TestLoader YAML = new TestLoader();
    private TestModel testModel;
    private int currentSectionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testModel = loadTestModelFromYaml();
        setContentView(R.layout.activity_main);
        showSection(currentSectionIndex);
    }

    public void goToNextSection() {
        if (currentSectionIndex < testModel.getSections().size() - 1) {
            showSection(++currentSectionIndex);
        } else {
            showFinalResult();
        }
    }

    public void goToPreviousSection() {
        if (currentSectionIndex > 0) {
            showSection(--currentSectionIndex);
        }
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

    private void showSection(int index) {
        final SectionFragment fragment = SectionFragment.newInstance(index, testModel);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void showFinalResult() {
        final int totalScore = testModel.calculateTotalScore();
        final String resultText = testModel.getResultText();
        // TODO: Replace this with a ResultFragment or custom screen
        Toast.makeText(this, "Your Score: " + totalScore + "\n" + resultText, Toast.LENGTH_LONG).show();
    }
}
