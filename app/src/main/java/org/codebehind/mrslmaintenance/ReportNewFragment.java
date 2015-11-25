package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Adapters.EquipmentAdapter;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Singletons.ReportSingleton;
import org.codebehind.mrslmaintenance.Models.SiteDbModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReportNewFragment extends Fragment {

    private Spinner _siteSpinner;
    private siteSpinnerAdaptor _siteSpinnerAdaptor;
    private EditText _dateEditText;
    private ListView _equipmentListView;

    private EquipmentAdapter _equipAdapter;
    private Report _report;
    private ReportSingleton _reportSingleton;

    public ReportNewFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report_new, container, false);

        setHasOptionsMenu(true); // ensures the fragment knows it has a menu
        setControls(rootView);
        setText();
        setEvents();

        return rootView;
    }

    private void setControls(View rootView){
        _siteSpinner=(Spinner)rootView.findViewById(R.id.report_new_spinner);
        _dateEditText = (EditText)rootView.findViewById(R.id.report_new_date);
        _equipmentListView=(ListView)rootView.findViewById(R.id.report_new_equipment_ListView);
    }

    private void setText(){

        _siteSpinnerAdaptor = new siteSpinnerAdaptor(new SiteDbModel(getActivity()).getlist());
        _siteSpinner.setAdapter(_siteSpinnerAdaptor);
        _dateEditText.setText(DateFormat.getDateInstance().format(new Date().getTime()));

        setReport("1");
    }

    // The siteId needs to be an integer the check will be here but nothing will happen if it isn't
    private void setReport(String siteId){
        ArrayList<String> params;

        if (siteId==null || siteId=="") return;

        params = new ArrayList<String>();
        params.add(siteId);

        _reportSingleton=ReportSingleton.getInstance();
        _reportSingleton.initializeReport(getActivity(), -1, Integer.parseInt(siteId), "Need to complete", null /*_dateEditText.getText()*/);
        _report=_reportSingleton.getReport();

        _equipAdapter = new EquipmentAdapter(_report.getEquipmentList(), getActivity());
        _equipmentListView.setAdapter(_equipAdapter);
    }

    private void setEvents(){

        _siteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // this changes the equipment list on the fly
                if (position > 0) {
                    Site s = (Site)_siteSpinnerAdaptor.getItem(position);
                    setReport("" + s.getId());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {       }
        });

        _equipmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                Equipment equipment;

                equipment = (Equipment) parent.getItemAtPosition(position);
                _reportSingleton.setEquipment(equipment);

                intent = new Intent(getActivity(), ReportNewEquipmentActivity.class);
                startActivity(intent);
            }
        });
    }

    private class siteSpinnerAdaptor extends ArrayAdapter<Site> {

        public siteSpinnerAdaptor(ArrayList<Site> arraylist) {

            super(getActivity(), R.layout.spinner_parameters, arraylist);
        }

        @Override
        public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
            return getView(position, cnvtView, prnt);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Site site;
            TextView nameTextView, idTextView;
            View row;

            row=getActivity().getLayoutInflater().inflate(R.layout.spinner_parameters, null);
            site = getItem(position);
            nameTextView=(TextView)row.findViewById(R.id.spinner_parameters_name);
            nameTextView.setText(site.getName());
            idTextView=(TextView)row.findViewById(R.id.spinner_parameters_id);
            idTextView.setText(""+site.getId());

            return row;
        }
    }
}
