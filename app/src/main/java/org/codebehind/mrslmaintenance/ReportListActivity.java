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
import org.codebehind.mrslmaintenance.Data.LoadData;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Models.ImageModel;
import org.codebehind.mrslmaintenance.Models.ReportModel;

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
        loadData.load();
        loadData.populateEquipmentDb(this);
        loadData.popSiteData(this);
        loadData.populateReportData(this);
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
        int id = item.getItemId();
        Intent i;

        switch(id){
            case R.id.menu_new_report:
                // todo: ok loser start here this needs to open the new report with a wizard
                // look 4 lines down and you can see that your not using the Report DB Model
                //Report r = new Report();
                //r.setEquipmentList(new ArrayList<Equipment>());
                //r.setReportDate(new Date());
                //r.setId(UUID.randomUUID());
                //ReportModel.getInstance().add(r);
                i = new Intent(this,ReportNewActivity.class);
                //i.putExtra(StaticConstants.EXTRA_REPORT_ID, r.getId());
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
    public static class ReportListFragment extends Fragment {
        ListView listview;
        String TAG = "report_activity_placeholder";

        public ReportListFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_report_list, container, false);

            setHasOptionsMenu(true); // ensures the fragment knows it has a menu

            listview = (ListView) rootView.findViewById(R.id.report_listview);
            listview.setAdapter(new ReportAdapter(ReportModel.getInstance().getList()));
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d(TAG, "position = " + position);
                    Report r = (Report)parent.getItemAtPosition(position);
                    Intent i = new Intent(getActivity(), ReportActivity.class);
                    i.putExtra(StaticConstants.EXTRA_REPORT_ID, r.getId());
                    startActivity(i);
                }
            });
            return rootView;
        }
        @Override
        public void onResume (){
            super.onResume();
            listview = (ListView) getActivity().findViewById(R.id.report_listview);
            listview.setAdapter(new ReportAdapter(ReportModel.getInstance().getList()));
        }

        private class ReportAdapter extends ArrayAdapter<Report> {
            public ReportAdapter(ArrayList<Report> reports) {
                super(getActivity(), android.R.layout.simple_list_item_1, reports);
            }
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                Report r;
                TextView tvSite, tvDate;
                // if we weren't given a view, inflate one
                if (null == convertView) {
                    convertView = getActivity().getLayoutInflater()
                            .inflate(R.layout.report_list_item, null);
                }
                r = getItem(position);
                tvSite=(TextView)convertView.findViewById(R.id.report_list_site);
                tvSite.setText(r.getSiteName());
                tvDate = (TextView)convertView.findViewById((R.id.report_list_date));
                tvDate.setText(DateFormat.getDateInstance().format(r.getReportDate()));
                return convertView;
            }
        }
    }
}