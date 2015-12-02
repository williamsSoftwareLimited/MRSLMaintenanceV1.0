package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Models.ReportDbModel;
import org.codebehind.mrslmaintenance.Singletons.ReportSingleton;

import java.util.ArrayList;


public class ReportActivity  extends ActionBarActivityBase {
    private static final String EDIT_REPORT="Edit Report";
    private ViewPager _viewPager;
    private Report _report;
    private ReportDbModel _model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final ArrayList<Report> reports;
        FragmentManager fragmentManager;
        final int reportId;

        super.onCreate(savedInstanceState);

        _model = new ReportDbModel(this);
        reportId=getIntent().getIntExtra(StaticConstants.EXTRA_REPORT_ID, -1);
        _report=_model.getReport(reportId);

        _viewPager = new ViewPager(this);
        _viewPager.setId(R.id.viewPager);
        setContentView(_viewPager);
        if (savedInstanceState != null) return; // if ths has been created before then don't recreate

        reports = _model.getAll();

        fragmentManager = getSupportFragmentManager();

        _viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public int getCount() {
                return reports.size();
            }

            @Override
            public Fragment getItem(int pos) {
                return ReportFragment.newInstance(reports.get(pos).getId());
            }
        });

        _viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageScrollStateChanged(int state) {}

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {  }

            public void onPageSelected(int position) {

                _report=reports.get(position);
            }

        });

        for (int i = 0; i < reports.size(); i++) {

            if (reports.get(i).getId() == _report.getId()) {
                _viewPager.setCurrentItem(i);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        int id = item.getItemId();

        switch(id) {

            case R.id.menu_report_edit:

                Toast.makeText(this, "Report " + _report.getId() + " to be edited.", Toast.LENGTH_SHORT).show();
                ReportSingleton.getInstance().setReport(_report);
                ReportSingleton.getInstance().setTitle(EDIT_REPORT);
                intent=new Intent(this, ReportNewActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
