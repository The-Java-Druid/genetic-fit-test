package com.geneticfittest.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.geneticfittest.R;

public class ResultActivity extends AppCompatActivity {

    private static final String EXTRA_SCORE = "extra_score";
    private static final String EXTRA_RESULT_TEXT = "extra_result";

    public static void start(Context context, int score, String resultText) {
        final Intent intent = new Intent(context, ResultActivity.class);
        intent.putExtra(EXTRA_SCORE, score);
        intent.putExtra(EXTRA_RESULT_TEXT, resultText);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        final int score = getIntent().getIntExtra(EXTRA_SCORE, 0);
        final String resultText = getIntent().getStringExtra(EXTRA_RESULT_TEXT);

        final TextView resultView = findViewById(R.id.resultTextView);
        resultView.setText(getString(R.string.your_score, score, resultText));
    }
}
