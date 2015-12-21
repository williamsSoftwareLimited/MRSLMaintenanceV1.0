package org.codebehind.mrslmaintenance;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Models.ReportDbModel;
import org.codebehind.mrslmaintenance.Models.ReportEquipParamsDbModel;

public class ReportNewActivity extends ActionBarActivityBase {

    public static final String REPORT_BUNDLE="REPORT_NEW_ACTIVITY_REPORT_BUNDLE",
                                NEW_REPORT="New Report",
                                EDIT_REPORT="Edit Report";
    private Report _report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FragmentTransaction fragmentTransaction;

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report_new);

        if (savedInstanceState != null) return; // if ths has been created before then don't recreate


        _report = (Report)getIntent().getSerializableExtra(REPORT_BUNDLE);

        if (_report.getId()<0) setTitle(NEW_REPORT);
        else setTitle(EDIT_REPORT);

        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.activity_report_new_container, ReportNewFragment.newInstance(_report));
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_report_new, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id;
        ReportDbModel model;

        id = item.getItemId();

        switch(id) {

            case R.id.menu_report_new_save:

                Toast.makeText(ReportNewActivity.this, "Report saved.", Toast.LENGTH_SHORT).show();

                model=new ReportDbModel(this, new ReportEquipParamsDbModel(this));

                if (_report.getId()<0) {
                    model.add(_report);
                }else{
                    model.update(_report);
                }

                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
