package com.geneticfittest.ui;

import androidx.viewpager2.widget.ViewPager2;

class ValidateAnswersCallback extends ViewPager2.OnPageChangeCallback {

    private final SectionFragment sectionFragment;
    private final int sectionIndex;
    private final ViewPager2 viewPager;

    public ValidateAnswersCallback(SectionFragment sectionFragment, int sectionIndex, ViewPager2 viewPager) {
        this.sectionFragment = sectionFragment;
        this.sectionIndex = sectionIndex;
        this.viewPager = viewPager;
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        if (position > sectionIndex) {
            // User swiped forward, run same validation as goToNextPage()
            if (!sectionFragment.onGoToNextPage()) {
                // If invalid, keep on lastPosition
                viewPager.setCurrentItem(sectionIndex);
                return;
            }
        }
    }

}
