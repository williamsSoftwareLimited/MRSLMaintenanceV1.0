package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Data.LoadData;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Entities.SiteEquipment;

import java.util.ArrayList;

// this is currently the entry Activity
public class ReportListActivity extends ActionBarActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // this initialises the database with some test data
        // =================================================
        LoadData loadData=new LoadData();
        loadData.load(this);
        // =================================================

        setContentView(R.layout.activity_report_list);

        if (savedInstanceState != null) return; // if ths has been created before then don't recreate

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.activity_report_list_container, new ReportListFragment()); //loads ReportListFragment (below) into the tags activity_report_list_container in activity_report_list
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_report_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        Bundle bundle;
        int id = item.getItemId();

        switch(id){

            case R.id.menu_new_report:
                i = new Intent(this,ReportNewActivity.class);

                bundle=new Bundle();
                bundle.putSerializable(ReportNewActivity.REPORT_BUNDLE, new Report(-1, "", null, null));

                i.putExtras(bundle);
                startActivity(i);

                return true;

            case R.id.menu_sites:
                i = new Intent(this,SiteListActivity.class);
                startActivity(i);
                return true;

            case R.id.menu_equipment:
                i = new Intent(this,EquipmentListActivity.class);
                startActivity(i);
                return true;

            case R.id.menu_camera:
                i = new Intent(this, EquipmentCameraActivity.class);
                startActivity(i);
                return true;

            default:return super.onOptionsItemSelected(item);
        }
    }
}