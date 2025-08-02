package com.geneticfittest;

import android.widget.Toast;

import androidx.viewpager2.widget.ViewPager2;

import com.geneticfittest.model.TestModel;

class MyOnPageChangeCallback extends ViewPager2.OnPageChangeCallback {
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
        // Slide out, then slide in
        mainActivity.slideOutAd();
        mainActivity.slideInAd();
        if (position == lastIndex) {
            Toast.makeText(mainActivity, R.string.swipe_again, Toast.LENGTH_SHORT).show();
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
