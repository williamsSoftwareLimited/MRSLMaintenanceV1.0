package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Singletons.ReportSingleton;

public class ReportNewActivity extends ActionBarActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report_new);

        if (savedInstanceState != null) return; // if ths has been created before then don't recreate

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

                mapParameters();

                ReportSingleton.getInstance().saveReport(this);
                Toast.makeText(ReportNewActivity.this, "Report saved.", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void mapParameters(){
        FragmentManager fragmentManager;

        fragmentManager=getSupportFragmentManager();
        ReportNewFragment reportNewFragment= (ReportNewFragment)fragmentManager.getFragments().get(0);
        reportNewFragment.setSiteSpinnerVmEnabled(false);
        ReportSingleton.getInstance().getReport().setEngineerName(reportNewFragment.getEngineersName());
    }
}
