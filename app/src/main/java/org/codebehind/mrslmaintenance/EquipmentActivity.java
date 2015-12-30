package org.codebehind.mrslmaintenance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Entities.SiteEquipment;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IViewPagerViewModelDelegate;
import org.codebehind.mrslmaintenance.ViewModels.ViewPagerViewModel;


public class EquipmentActivity  extends ActionBarActivityBase implements IViewPagerViewModelDelegate {
    ViewPagerViewModel _viewPagerVm;
    Report _report;
    SiteEquipment _siteEquipment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle;

        super.onCreate(savedInstanceState);

        bundle=getIntent().getExtras();
        _report = (Report)bundle.getSerializable(ReportFragment.BUNDLE_REPORT);
        _siteEquipment = (SiteEquipment)bundle.getSerializable(ReportFragment.BUNDLE_SITE_EQUIPMENT);

        _viewPagerVm=new ViewPagerViewModel(new ViewPager(this), this, _report.getSiteEquipmentList().size());
        setContentView(_viewPagerVm.getViewPager());

        for (int i = 0; i < _report.getSiteEquipmentList().size(); i++) {

            if (_report.getSiteEquipmentList().get(i).getId() ==_siteEquipment.getId()) {

                _viewPagerVm.setCurrentItem(i);
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_equipment, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Fragment getItem(int position) {
        SiteEquipment siteEquipment;

        siteEquipment = _report.getSiteEquipmentList().get(position);
        return EquipmentFragment.newInstance(_report, siteEquipment);
    }

    @Override
    public void onPageSelected(int position) {

    }

}