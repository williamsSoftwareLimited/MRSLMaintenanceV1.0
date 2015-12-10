package org.codebehind.mrslmaintenance.ViewModels;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import org.codebehind.mrslmaintenance.R;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IViewPagerViewModelDelegate;


/**
 * Created by root on 09/12/15.
 */
public class ViewPagerViewModel {

    // The interface IViewPagerViewModelDelegate is implemented on whatever uses this.

    private ViewPager _viewPager;
    private int _listSize;
    private IViewPagerViewModelDelegate _viewPagerViewModelDelegate;


    public ViewPager getViewPager(){
        return _viewPager;
    }

    public ViewPagerViewModel(ViewPager viewPager, IViewPagerViewModelDelegate viewPagerViewModelDelegate, int listSize){

        _viewPager=viewPager;
        _viewPager.setId(R.id.viewPager);
        _viewPagerViewModelDelegate=viewPagerViewModelDelegate;
        _listSize=listSize;
        setEvents();
    }

    public void setCurrentItem(int item){
        _viewPager.setCurrentItem(item);
    }

    private void setEvents(){
        FragmentManager fragmentManager;

        fragmentManager=_viewPagerViewModelDelegate.getSupportFragmentManager();

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
