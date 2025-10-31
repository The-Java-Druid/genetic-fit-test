package com.geneticfittest.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.HashMap;
import java.util.Map;

public class TestViewModel extends ViewModel {
    private final MutableLiveData<Map<Integer, Integer>> selectedAnswers = new MutableLiveData<>(new HashMap<>());

    public LiveData<Map<Integer, Integer>> getSelectedAnswers() {
        return selectedAnswers;
    }

    public void setAnswer(int questionId, int answerValue) {
        Map<Integer, Integer> answers = selectedAnswers.getValue();
        if (answers != null) {
            answers.put(questionId, answerValue);
            selectedAnswers.setValue(answers);
        }
    }

    public Integer getAnswer(int questionId) {
        Map<Integer, Integer> answers = selectedAnswers.getValue();
        return (answers != null) ? answers.get(questionId) : null;
    }

    public void resetAnswers() {
        selectedAnswers.setValue(new HashMap<>());
    }
}
