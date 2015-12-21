package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Entities.SiteEquipment;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IViewPagerViewModelDelegate;
import org.codebehind.mrslmaintenance.ViewModels.ViewPagerViewModel;

public class ReportNewEquipmentActivity extends ActionBarActivityBase implements IViewPagerViewModelDelegate {

    private ViewPagerViewModel _viewPagerVm;
    private Report _report;
    private SiteEquipment _siteEquipment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle;

        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) return; // if ths has been created before then don't recreate

        bundle=getIntent().getExtras();

        _report=(Report)bundle.getSerializable(ReportNewFragment.REPORT_BUNDLE);
        _siteEquipment=(SiteEquipment)bundle.getSerializable(ReportNewFragment.SITE_EQUIPMENT_BUNDLE);

        _viewPagerVm=new ViewPagerViewModel(new ViewPager(this), this, _report.getSiteEquipmentList().size());
        setContentView(_viewPagerVm.getViewPager());

        for (int i = 0; i < _report.getSiteEquipmentList().size(); i++) {

            if (_report.getSiteEquipmentList().get(i).getId() == _siteEquipment.getId()) {

                _viewPagerVm.setCurrentItem(i);
                break;
            }
        } // end for loop
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_equipment_new_parameters, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id;
        Intent intent;
        Bundle bundle;

        id = item.getItemId();

        switch(id) {

            case R.id.menu_new_equipment_return:

                intent=new Intent(this, ReportNewActivity.class);
                bundle=new Bundle();

                bundle.putSerializable(ReportNewActivity.REPORT_BUNDLE, _report);

                intent.putExtras(bundle);
                startActivity(intent);

                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Fragment getItem(int position) {
        SiteEquipment siteEquipment;

        siteEquipment = _report.getSiteEquipmentList().get(position);

        return ReportNewEquipmentFragment.newInstance(siteEquipment);
    }

    @Override
    public void onPageSelected(int position) {

    }
}
