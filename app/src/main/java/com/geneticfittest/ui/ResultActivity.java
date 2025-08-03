package com.geneticfittest.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.geneticfittest.MainActivity;
import com.geneticfittest.R;
import com.geneticfittest.model.TestModel;

public class ResultActivity extends AppCompatActivity {

    private static final String EXTRA_SCORE = "extra_score";
    private static final String EXTRA_RESULT_TEXT = "extra_result";
    private static TestModel testModel;

    public static void start(Context context, TestModel testModel) {
        ResultActivity.testModel = testModel;
        final Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra(EXTRA_SCORE, testModel.calculateTotalScore());
        intent.putExtra(EXTRA_RESULT_TEXT, testModel.getResultText());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        final int score = getIntent().getIntExtra(EXTRA_SCORE, 0);
        final String resultText = getIntent().getStringExtra(EXTRA_RESULT_TEXT);

        final TextView resultView = findViewById(R.id.resultTextView);
        final String resultMessage = getString(R.string.your_score, score, resultText);
        resultView.setText(resultMessage);
        final Button btnShare = findViewById(R.id.btnShare);
        btnShare.setOnClickListener(v -> shareResult(resultMessage));
        final Button retakeButton = findViewById(R.id.buttonRetake);
        retakeButton.setOnClickListener(this::retakeTest);
    }

    private void shareResult(String resultMessage) {
        startActivity(Intent.createChooser(
            buildShareIntent(resultMessage), "Share your results via"));
    }

    @NonNull
    private static Intent buildShareIntent(String resultMessage) {
        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My Bodybuilding Genetics Score");
        shareIntent.putExtra(Intent.EXTRA_TEXT, resultMessage);
        return shareIntent;
    }

    private void retakeTest(View v) {
        if (testModel != null) {
            testModel.resetAnswers();
        }
        final Intent intent = new Intent(ResultActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
