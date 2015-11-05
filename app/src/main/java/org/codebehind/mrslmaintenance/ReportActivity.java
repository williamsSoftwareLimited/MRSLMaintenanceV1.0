package org.codebehind.mrslmaintenance;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Models.EquipmentModel;
import org.codebehind.mrslmaintenance.Models.ReportModel;

import java.util.ArrayList;
import java.util.UUID;


public class ReportActivity  extends ActionBarActivityBase {
    ViewPager mViewPager;
    Report _report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ArrayList<Report> reports;
        FragmentManager fm;
        UUID reportId;

        super.onCreate(savedInstanceState);
        reportId=(UUID)getIntent() .getSerializableExtra(StaticConstants.EXTRA_REPORT_ID);
        _report=ReportModel.getInstance().getItem(reportId);

        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        reports = ReportModel.getInstance().getList();
        fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return reports.size();
            }
            @Override
            public Fragment getItem(int pos) {
                return ReportFragment.newInstance(reports.get(pos).getUuid());
            }
        });
        for (int i = 0; i < reports.size(); i++) {
            if (reports.get(i).getUuid().equals(reportId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_report, menu);
        return true;
    }
}
