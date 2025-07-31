package com.geneticfittest.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.geneticfittest.model.TestModel;

public class SectionPagerAdapter extends FragmentStateAdapter {

    private final TestModel testModel;

    public SectionPagerAdapter(@NonNull FragmentActivity activity, TestModel model) {
        super(activity);
        this.testModel = model;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return SectionFragment.newInstance(position, testModel);
    }

    @Override
    public int getItemCount() {
        return testModel.getSections().size();
    }
}
