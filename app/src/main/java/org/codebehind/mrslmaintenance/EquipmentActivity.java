package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Models.ReportDbModel;
import org.codebehind.mrslmaintenance.Models.ReportModel;

import java.util.ArrayList;
import java.util.UUID;


public class EquipmentActivity  extends ActionBarActivityBase {
    ViewPager mViewPager;
    Report _report;
    Equipment _equipment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FragmentManager fm;

        super.onCreate(savedInstanceState);

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        Bundle bundle=getIntent().getExtras();
        _report = (Report)bundle.getSerializable(ReportFragment.BUNDLE_REPORT);
        _equipment = (Equipment)bundle.getSerializable(ReportFragment.BUNDLE_EQUIPMENT);

        fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return _report.getEquipmentList().size();
            }
            @Override
            public Fragment getItem(int pos) {
                return EquipmentFragment.newInstance(_report, _equipment);
            }
        });
        for (int i = 0; i < _report.getEquipmentList().size(); i++) {
            if (_report.getEquipmentList().get(i).getId() ==_equipment.getId()) {
                mViewPager.setCurrentItem(i);
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
}