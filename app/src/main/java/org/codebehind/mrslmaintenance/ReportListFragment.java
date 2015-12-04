package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.codebehind.mrslmaintenance.Adapters.ReportAdapter;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Models.ReportDbModel;

/**
 * Created by root on 05/11/15.
 */
public class ReportListFragment extends Fragment {

    private static final String LOG_TAG = "REPORT_LIST_FRAGMENT";
    private ListView listview;
    private ReportDbModel _reportModel;

    public ReportListFragment() {
        _reportModel = new ReportDbModel(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report_list, container, false);

        return rootView;
    }

    @Override
    public void onResume (){
        super.onResume();

        setControls(getView());
        setAttributes();
        setEvents();
    }

    private void setControls(View view){

        listview = (ListView) view.findViewById(R.id.report_listview);
    }

    private void setAttributes(){

        listview.setAdapter(new ReportAdapter(_reportModel.getAll(), getActivity()));
    }

    private void setEvents(){

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Report report;
                Intent intent;

                report = (Report)parent.getItemAtPosition(position);

                Log.d(LOG_TAG, "ReportId = " + report.getId());

                intent = new Intent(getActivity(), ReportActivity.class);
                intent.putExtra(StaticConstants.EXTRA_REPORT_ID, report.getId());
                startActivity(intent);
            }
        });

    }
}
