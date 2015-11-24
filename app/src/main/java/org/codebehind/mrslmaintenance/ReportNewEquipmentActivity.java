package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Entities.ReportEquipmentParameters;
import org.codebehind.mrslmaintenance.Models.ParameterDbModel;
import org.codebehind.mrslmaintenance.Models.ReportDbModel;
import org.codebehind.mrslmaintenance.Models.ReportEquipmentParametersDbModel;

import java.util.ArrayList;

public class ReportNewEquipmentActivity extends ActionBarActivityBase {
    ViewPager _viewPager;
    Report _report;
    Equipment _equipment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle;
        FragmentManager fragmentManager;

        super.onCreate(savedInstanceState);

        _viewPager=new ViewPager(this);
        _viewPager.setId(R.id.report_new_equipment_view_pager);
        setContentView(_viewPager);
        if (savedInstanceState != null) return; // if ths has been created before then don't recreate

        bundle=getIntent().getExtras();
        _report=(Report)bundle.getSerializable(ReportNewFragment.BUNDLE_REPORT);
        _equipment=(Equipment)bundle.getSerializable(ReportNewFragment.BUNDLE_EQUIPMENT);

        fragmentManager=getSupportFragmentManager();
        _viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Equipment equipment;

                equipment=_report.getEquipmentList().get(position);
                return ReportNewEquipmentFragment.newInstance(equipment);
            }

            @Override
            public int getCount() {
                return _report.getEquipmentList().size();
            }
        });

        for (int i = 0; i < _report.getEquipmentList().size(); i++) {
            if (_report.getEquipmentList().get(i).getId() == _equipment.getId()) {
                _viewPager.setCurrentItem(i);
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_report_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent i;

        switch(id) {

            case R.id.menu_report_new_save:
                saveReport();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*@Override
    public void onBackPressed() {

        saveReport();
    }*/

    private void saveReport(){
        ListView listview;
        EditText editText;
        String value;
        ReportDbModel reportDbModel;
        ReportEquipmentParametersDbModel reportEquipmentParametersDbModel;
        ReportEquipmentParameters reportEquipmentParameters;
        int reportId, equipmentId, parameterId;

        reportDbModel=new ReportDbModel(this);
        reportId = reportDbModel.add(_report);

        reportEquipmentParametersDbModel=new ReportEquipmentParametersDbModel(this);

        for(int i=0;i<_viewPager.getChildCount(); i++) { // Lopp through all the ViewPages

            listview = (ListView) _viewPager.getChildAt(i).findViewById(R.id.report_new_equipment_params);

            for(int j=0;j<listview.getChildCount();j++) { // Loop through all the parameters in the listView

                editText = (EditText) listview.getChildAt(j).findViewById(R.id.parameter_list_item_value);
                value=editText.getText().toString();

                equipmentId=_report.getEquipmentList().get(i).getId();
                parameterId=_report.getEquipmentList().get(i).getParameterList().get(j).getId();
                reportEquipmentParameters=new ReportEquipmentParameters(reportId, equipmentId, parameterId, value);
                reportEquipmentParametersDbModel.add(reportEquipmentParameters);
            }
        }

        Toast.makeText(this, "Report saved.", Toast.LENGTH_SHORT).show();
    }
}
