package org.codebehind.mrslmaintenance;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Entities.Report;

public class ReportNewActivity extends ActionBarActivityBase {

    public static final String REPORT_NEW_ACTIVITY_BUNDLE="REPORT_NEW_ACTIVITY_BUNDLE",
                                NEW_REPORT="New Report",
                                EDIT_REPORT="Edit Report";
    private Report _report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report_new);

        if (savedInstanceState != null) return; // if ths has been created before then don't recreate


        _report = (Report)getIntent().getSerializableExtra(REPORT_NEW_ACTIVITY_BUNDLE);

        if (_report.getId()==-1) setTitle(NEW_REPORT);
        else setTitle(EDIT_REPORT);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.activity_report_new_container, ReportNewFragment.newInstance(_report));
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_report_new, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {

            case R.id.menu_report_new_save:

                Toast.makeText(ReportNewActivity.this, "Report saved.", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
