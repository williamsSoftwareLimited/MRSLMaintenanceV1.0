package org.codebehind.mrslmaintenance.ViewModels;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.FragmentMode;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IViewPagerViewModelDelegate;

import java.util.ArrayList;

/**
 * Created by root on 09/12/15.
 */
public class ViewPagerViewModel<tEntity> {

    private ViewPager _viewPager;
    private ActionBarActivityBase _activity;
    private int _listSize;
    private IViewPagerViewModelDelegate _viewPagerViewModelDelegate;


    public ViewPager getViewPager(){
        return _viewPager;
    }

    public ViewPagerViewModel(ViewPager viewPager, ActionBarActivityBase activity, int listSize){

        _viewPager=viewPager;
        _activity=activity;
        _listSize=listSize;
        _viewPagerViewModelDelegate=(IViewPagerViewModelDelegate)activity;
    }

    private void setEvents(){
        FragmentManager fragmentManager;

        fragmentManager=_activity.getSupportFragmentManager();

        _viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public int getCount() {
                return _listSize;
            }

            @Override
            public Fragment getItem(int pos) {
                return _viewPagerViewModelDelegate.getItem(pos);
            }
        });

        _viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                _viewPagerViewModelDelegate.onPageSelected(position);
            }

        });
    }
}
