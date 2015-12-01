package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Adapters.ReportAdapter;
import org.codebehind.mrslmaintenance.Data.LoadData;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Models.EquipmentDbModel;
import org.codebehind.mrslmaintenance.Models.ImageModel;
import org.codebehind.mrslmaintenance.Models.ReportModel;
import org.codebehind.mrslmaintenance.Singletons.ReportSingleton;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

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
        int id = item.getItemId();

        switch(id){

            case R.id.menu_new_report:
                i = new Intent(this,ReportNewActivity.class);
                ReportSingleton.getInstance().clearReport(); // ensure there's no stored report
                startActivityForResult(i,0);
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