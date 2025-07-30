package com.geneticfittest.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.geneticfittest.MainActivity;
import com.geneticfittest.R;
import com.geneticfittest.model.Answer;
import com.geneticfittest.model.Question;
import com.geneticfittest.model.Section;
import com.geneticfittest.model.TestModel;

public class SectionFragment extends Fragment {

    private static final String ARG_SECTION_INDEX = "section_index";
    private TestModel testModel;
    private int sectionIndex;
    private TextView sectionTitle;
    private TextView questionText;
    private RadioGroup answersGroup;

    public static SectionFragment newInstance(int index, TestModel model) {
        SectionFragment fragment = new SectionFragment();
        Bundle args = new Bundle();
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
        questionText = view.findViewById(R.id.questionText);
        answersGroup = view.findViewById(R.id.answersGroup);
        final Button btnNext = view.findViewById(R.id.btnNext);
        final Button btnPrevious = view.findViewById(R.id.btnPrevious);
        final Bundle arguments = getArguments();
        if (arguments != null) {
            sectionIndex = arguments.getInt(ARG_SECTION_INDEX);
            loadSection();
        }
        btnNext.setOnClickListener(this::onClickNext);
        btnPrevious.setOnClickListener(this::onClickPrevious);
    }

    private void loadSection() {
        final Section section = testModel.getSections().get(sectionIndex);
        sectionTitle.setText(section.getName());

        if (!section.getQuestions().isEmpty()) {
            Question question = section.getQuestion(0); // MVP: only 1 question per section
            questionText.setText(question.getText());

            answersGroup.removeAllViews();
            int answerIndex = 0;
            for (Answer answer : question.getAnswers()) {
                RadioButton rb = new RadioButton(getContext());
                rb.setText(answer.getText());
                rb.setId(answerIndex);
                answersGroup.addView(rb);
                answerIndex++;
            }

            // restore selection
            int selected = question.getSelectedAnswerIndex();
            if (selected != -1 && selected < answersGroup.getChildCount()) {
                answersGroup.check(selected);
            }
        }
    }

    private void saveSelectedAnswer(int selectedId) {
        testModel.getSection(sectionIndex).getQuestion(0).setSelectedAnswerIndex(selectedId);
    }

    private void onClickPrevious(View v) {
        if (getActivity() instanceof MainActivity a) {
            a.goToPreviousSection();
        }
    }

    private void onClickNext(View v) {
        final int selectedId = answersGroup.getCheckedRadioButtonId();
        if (selectedId >= 0) {
            saveSelectedAnswer(selectedId);
            if (getActivity() instanceof MainActivity a) {
                a.goToNextSection();
            }
        } else Toast.makeText(getContext(), "Please select an answer", Toast.LENGTH_SHORT).show();
    }

}