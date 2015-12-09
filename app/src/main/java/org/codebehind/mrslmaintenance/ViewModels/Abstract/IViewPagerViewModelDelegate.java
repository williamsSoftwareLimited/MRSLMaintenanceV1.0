package org.codebehind.mrslmaintenance.ViewModels.Abstract;

import android.support.v4.app.Fragment;

/**
 * Created by root on 09/12/15.
 */
public interface IViewPagerViewModelDelegate {
    Fragment getItem(int position);
    void onPageSelected(int position);
}
