package com.geneticfittest;

import android.widget.Toast;

import androidx.viewpager2.widget.ViewPager2;

import com.geneticfittest.model.TestModel;

class SlideBannerAdCallback extends ViewPager2.OnPageChangeCallback {
    private final MainActivity mainActivity;

    public SlideBannerAdCallback(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        // Slide out, then slide in
        mainActivity.slideOutAd();
        mainActivity.slideInAd();
    }

}
