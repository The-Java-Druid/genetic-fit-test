package com.geneticfittest.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.geneticfittest.MainActivity;
import com.geneticfittest.R;
import com.geneticfittest.model.Answer;
import com.geneticfittest.model.Question;
import com.geneticfittest.model.Section;
import com.geneticfittest.model.TestModel;

import java.util.List;

public class SectionFragment extends Fragment {

    private static final String ARG_SECTION_INDEX = "section_index";
    private TestModel testModel;
    private int sectionIndex;
    private TextView sectionTitle;
    private Button btnNext;
    private Button btnPrevious;
    private Button btnFinish;
    private LinearLayout questionsContainer;

    public static SectionFragment newInstance(int index, TestModel model) {
        final SectionFragment fragment = new SectionFragment();
        final Bundle args = new Bundle();
        args.putInt(ARG_SECTION_INDEX, index);
        fragment.setArguments(args);
        fragment.setTestModel(model);
        return fragment;
    }

    public void setTestModel(TestModel model) {
        this.testModel = model;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_section, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sectionTitle = view.findViewById(R.id.sectionTitle);
        questionsContainer = view.findViewById(R.id.questionsContainer);
        btnNext = view.findViewById(R.id.btnNext);
        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnFinish = view.findViewById(R.id.btnFinish);
        final Bundle arguments = getArguments();
        if (arguments != null) {
            sectionIndex = arguments.getInt(ARG_SECTION_INDEX);
            loadSection();
        }
        btnNext.setOnClickListener(this::onClickNext);
        btnPrevious.setOnClickListener(this::onClickPrevious);
        btnFinish.setOnClickListener(this::onClickFinish);
    }

    private void loadSection() {
        final Section section = testModel.getSections().get(sectionIndex);
        sectionTitle.setText(section.getName());
        questionsContainer.removeAllViews();

        // Create UI for each question in the section
        for (Question question : section.getQuestions()) {
            // Question label
            questionsContainer.addView(buildQuestionTextView(question));

            // RadioGroup for answers
            final RadioGroup rg = buildAnswersRadioGroup();
            questionsContainer.addView(rg);

            // Add answers dynamically
            final List<Answer> answers = question.getAnswers();
            for (int aIndex = 0; aIndex < answers.size(); aIndex++) {
                final Answer answer = answers.get(aIndex);
                final RadioButton rb = new RadioButton(getContext());
                rb.setText(answer.getText());
                rb.setId(aIndex);
                rg.addView(rb);
                // Restore previously saved answer
                final int saved = question.getSelectedAnswerIndex();
                if (saved == aIndex) rb.setChecked(true);
            }
        }

        // Show Finish button on last section
        final boolean isLast = (sectionIndex == testModel.getSections().size() - 1);
        btnFinish.setVisibility(isLast ? View.VISIBLE : View.GONE);
        btnNext.setVisibility(isLast ? View.GONE : View.VISIBLE);
    }

    @NonNull
    private RadioGroup buildAnswersRadioGroup() {
        final RadioGroup rg = new RadioGroup(getContext());
        rg.setId(View.generateViewId());
        return rg;
    }

    @NonNull
    private TextView buildQuestionTextView(Question question) {
        final TextView qText = new TextView(getContext());
        qText.setText(question.getText());
        qText.setTextSize(16);
        qText.setPadding(0, 16, 0, 8);
        return qText;
    }

    private void saveSelectedAnswers() {
        int viewIndex = 0;
        for (Question question : testModel.getSection(sectionIndex).getQuestions()) {
            viewIndex++; // skip question TextView
            final RadioGroup rg = (RadioGroup) questionsContainer.getChildAt(viewIndex);
            int selected = rg.getCheckedRadioButtonId();
            question.setSelectedAnswerIndex(selected);
            viewIndex++;
        }
    }

    private void onClickPrevious(View v) {
        final ViewPager2 pager = requireActivity().findViewById(R.id.viewPager);
        pager.setCurrentItem(pager.getCurrentItem() - 1, true);
    }

    private void onClickNext(View v) {
        try {
            saveSelectedAnswers();
            ViewPager2 pager = (ViewPager2) requireActivity().findViewById(R.id.viewPager);
            pager.setCurrentItem(pager.getCurrentItem() + 1, true);
        } catch (IllegalArgumentException e) {
            Toast.makeText(getContext(), "Please select answers for all questions", Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickFinish(View v) {
        saveSelectedAnswers();
        ((MainActivity) getActivity()).showFinalResult();
    }
}