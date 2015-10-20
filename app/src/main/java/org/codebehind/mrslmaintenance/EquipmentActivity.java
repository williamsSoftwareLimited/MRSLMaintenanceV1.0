package org.codebehind.mrslmaintenance;

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
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Models.ReportModel;

import java.util.ArrayList;
import java.util.UUID;


public class EquipmentActivity  extends ActionBarActivityBase {
    public final static String EQUIPMENT_ID ="org.CodeBehind.EquipmentId";
    public final static String EQUIPMENT_REPORT_ID ="org.CodeBehind.EquipmentReportId";
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UUID reportId;
        int equipId;
        final Report report;
        FragmentManager fm;

        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.viewPager);
        setContentView(mViewPager);

        equipId=getIntent().getIntExtra(EQUIPMENT_ID, -1);
        reportId=(UUID)getIntent().getSerializableExtra(EQUIPMENT_REPORT_ID);
        report= ReportModel.getInstance().getItem(reportId);

        fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public int getCount() {
                return report.getEquipmentList().size();
            }
            @Override
            public Fragment getItem(int pos) {
                return EquipmentFragment.newInstance(report.getEquipmentList().get(pos).getId());
            }
        });
        for (int i = 0; i < report.getEquipmentList().size(); i++) {
            if (report.getEquipmentList().get(i).getId()==equipId) {
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