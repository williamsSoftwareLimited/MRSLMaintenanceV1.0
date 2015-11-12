package org.codebehind.mrslmaintenance.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.R;

import java.text.DateFormat;
import java.util.ArrayList;

/**
 * Created by root on 11/11/15.
 */
public class ParameterAdapter extends ArrayAdapter<Parameter>{

    Activity _activity;

    public ParameterAdapter(ArrayList<Parameter> reports, Activity activity) {
        super(activity, android.R.layout.simple_list_item_1, reports);
        _activity = activity;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Parameter parameter;
        TextView textViewSite, textViewDate;
        // if we weren't given a view, inflate one
        if (null == convertView) {
            convertView = _activity.getLayoutInflater().inflate(R.layout.report_list_item, null);
        }
        parameter = getItem(position);
        textViewSite=(TextView)convertView.findViewById(R.id.report_list_site);
        textViewSite.setText(parameter.getName());

        textViewDate = (TextView)convertView.findViewById((R.id.report_list_date));
        textViewDate.setText(parameter.getType());
        return convertView;
    }

}
