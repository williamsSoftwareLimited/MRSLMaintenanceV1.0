package org.codebehind.mrslmaintenance.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Adapters.Abstract.ASpinnerAdapter;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.R;

import java.util.ArrayList;

/**
 * Created by root on 26/11/15.
 */
public class SiteAdapter extends ASpinnerAdapter<Site> {

    public SiteAdapter(ArrayList<Site> siteList, Activity activity) {
        super(activity, R.layout.spinner_parameters, siteList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Site site;
        TextView nameTextView, idTextView;
        View row;

        row = _activity.getLayoutInflater().inflate(R.layout.spinner_parameters, null);
        site = getItem(position);
        nameTextView = (TextView) row.findViewById(R.id.spinner_parameters_name);
        nameTextView.setText(site.getName());
        idTextView = (TextView) row.findViewById(R.id.spinner_parameters_id);
        idTextView.setText("" + site.getId());

        return row;
    }

}
