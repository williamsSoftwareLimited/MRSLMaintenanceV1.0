package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Models.ReportDbModel;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IViewPagerViewModelDelegate;
import org.codebehind.mrslmaintenance.ViewModels.ViewPagerViewModel;

import java.util.ArrayList;


public class ReportActivity  extends ActionBarActivityBase implements IViewPagerViewModelDelegate {
    private static final String EDIT_REPORT="Edit Report";
    private ViewPagerViewModel _viewPagerVm;
    private Report _report;
    ArrayList<Report> _reports;
    private ReportDbModel _reportDbmodel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final int reportId;

        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) return; // if ths has been created before then don't recreate

        _reportDbmodel = new ReportDbModel(this);
        reportId=getIntent().getIntExtra(StaticConstants.EXTRA_REPORT_ID, -1);
        _report=_reportDbmodel.getReport(reportId);
        _reports = _reportDbmodel.getAll();

        _viewPagerVm = new ViewPagerViewModel(new ViewPager(this), this, _reports.size());
        setContentView(_viewPagerVm.getViewPager());

        for (int i = 0; i < _reports.size(); i++) {

            if (_reports.get(i).getId() == _report.getId()) {
                _viewPagerVm.setCurrentItem(i);
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
        Bundle bundle;
        int id = item.getItemId();

        switch(id) {

            case R.id.menu_report_edit:

                Toast.makeText(this, "Report " + _report.getId() + " to be edited.", Toast.LENGTH_SHORT).show();

                bundle = new Bundle();
                bundle.putSerializable(ReportNewActivity.REPORT_NEW_ACTIVITY_BUNDLE, _report);

                intent=new Intent(this, ReportNewActivity.class);
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
        return ReportFragment.newInstance(_reports.get(position).getId());
    }

    @Override
    public void onPageSelected(int position) {
        _report=_reports.get(position);
    }
}
