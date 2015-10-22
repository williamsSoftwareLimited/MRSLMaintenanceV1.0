package org.codebehind.mrslmaintenance;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Models.ParameterModel;
import org.codebehind.mrslmaintenance.Models.SiteDbModel;
import org.codebehind.mrslmaintenance.Models.SiteModel;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReportNewActivityFragment extends Fragment {

    Spinner _siteSpinner;

    public ReportNewActivityFragment() {
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
    }
    private void setText(){
        _siteSpinner.setAdapter(new siteSpinnerAdaptor(new SiteDbModel(getActivity()).getlist()));
    }
    private void setEvents(){}

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
