package org.codebehind.mrslmaintenance;

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
import android.widget.Toast;

import org.codebehind.mrslmaintenance.Adapters.EquipmentAdapter;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModel;
import org.codebehind.mrslmaintenance.Models.EquipmentDbModel;
import org.codebehind.mrslmaintenance.Models.EquipmentModel;
import org.codebehind.mrslmaintenance.Models.ParameterModel;
import org.codebehind.mrslmaintenance.Models.ReportModel;
import org.codebehind.mrslmaintenance.Models.SiteDbModel;
import org.codebehind.mrslmaintenance.Models.SiteModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReportNewActivityFragment extends Fragment {

    Spinner _siteSpinner;
    siteSpinnerAdaptor _siteSpinnerAdaptor;
    EditText _dateEditText;
    ListView _equipListView;
    DbAbstractModel<Equipment> _equipModel;
    EquipmentAdapter _equipAdapter;

    public ReportNewActivityFragment() {
        _equipModel = new EquipmentDbModel(getActivity());
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
        _equipListView=(ListView)rootView.findViewById(R.id.report_new_equipment_ListView);
    }
    private void setText(){
        _siteSpinnerAdaptor = new siteSpinnerAdaptor(new SiteDbModel(getActivity()).getlist());
        _siteSpinner.setAdapter(_siteSpinnerAdaptor);
        _dateEditText.setText(DateFormat.getDateInstance().format(new Date().getTime()));
        setEquipmentListView("1");
    }

    private void setEquipmentListView(String siteId){
        ArrayList<String> params = new ArrayList<String>();
        params.add(siteId);
        _equipAdapter = new EquipmentAdapter(_equipModel.getList(params), getActivity());
        _equipListView.setAdapter(_equipAdapter);
    }

    private void setEvents(){
        _siteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // this changes the equipment list on the fly
                if (position > 0) {
                    Site s = (Site)_siteSpinnerAdaptor.getItem(position);
                    //Toast.makeText(getActivity(), "SiteId=" + s.getId(), Toast.LENGTH_SHORT).show();
                    setEquipmentListView(""+s.getId());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {       }
        });
    }

    private class siteSpinnerAdaptor extends ArrayAdapter<Site> {
        public siteSpinnerAdaptor(ArrayList<Site> arraylist) {
            super(getActivity(), R.layout.spinner_parameters, arraylist);
        }
        @Override public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
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
