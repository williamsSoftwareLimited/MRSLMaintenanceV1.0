package org.codebehind.mrslmaintenance;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Models.ParameterDbModel;

public class ReportNewEquipmentActivity extends ActionBarActivityBase {
    ViewPager _viewPager;
    Report _report;
    Equipment _equipment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle;
        FragmentManager fragmentManager;

        super.onCreate(savedInstanceState);

        _viewPager=new ViewPager(this);
        _viewPager.setId(R.id.viewPager);
        setContentView(_viewPager);
        if (savedInstanceState != null) return; // if ths has been created before then don't recreate

        bundle=getIntent().getExtras();
        _report=(Report)bundle.getSerializable(ReportNewFragment.BUNDLE_REPORT);
        _equipment=(Equipment)bundle.getSerializable(ReportNewFragment.BUNDLE_EQUIPMENT);

        fragmentManager=getSupportFragmentManager();
        _viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Equipment equipment;

                equipment=_report.getEquipmentList().get(position);
                return ReportNewEquipmentFragment.newInstance(equipment);
            }

            @Override
            public int getCount() {
                return _report.getEquipmentList().size();
            }
        });

        for (int i = 0; i < _report.getEquipmentList().size(); i++) {
            if (_report.getEquipmentList().get(i).getId() == _equipment.getId()) {
                _viewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
