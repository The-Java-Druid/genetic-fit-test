package com.geneticfittest;

import androidx.viewpager2.widget.ViewPager2;

class SlideBannerAdCallback extends ViewPager2.OnPageChangeCallback {
    private final BannerAdManager bannerAdManager;

    public SlideBannerAdCallback(BannerAdManager bannerAdManager) {
        this.bannerAdManager = bannerAdManager;
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        // Slide out, then slide in
        bannerAdManager.slideOutAd();
        bannerAdManager.slideInAd();
    }

}
