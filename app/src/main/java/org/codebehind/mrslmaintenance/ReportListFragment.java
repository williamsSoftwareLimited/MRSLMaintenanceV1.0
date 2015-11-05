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
import org.codebehind.mrslmaintenance.Models.ReportModel;

/**
 * Created by root on 05/11/15.
 */
public class ReportListFragment extends Fragment {
    ListView listview;
    String TAG = "report_activity_placeholder";
    ReportDbModel _reportModel;

    public ReportListFragment() {
        _reportModel = new ReportDbModel(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report_list, container, false);

        setHasOptionsMenu(true); // ensures the fragment knows it has a menu
        setControls(rootView);
        setText();
        setEvents();

        return rootView;
    }
    @Override
    public void onResume (){
        super.onResume();
        listview = (ListView) getActivity().findViewById(R.id.report_listview);
        listview.setAdapter(new ReportAdapter(_reportModel.getAll(), getActivity()));
    }
    private void setControls(View view){
        listview = (ListView) view.findViewById(R.id.report_listview);
    }
    private void setText(){
        listview.setAdapter(new ReportAdapter(_reportModel.getAll(), getActivity()));
    }
    private void setEvents(){
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
    }
}