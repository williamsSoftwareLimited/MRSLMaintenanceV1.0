package org.codebehind.mrslmaintenance.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Entities.ReportEquipmentParameters;
import org.codebehind.mrslmaintenance.R;

import java.util.ArrayList;

/**
 * Created by root on 12/11/15.
 */
public class ReportEquipmentParamsAdapter extends ArrayAdapter<ReportEquipmentParameters> {
    Activity _activity;

    public ReportEquipmentParamsAdapter(ArrayList<ReportEquipmentParameters> reports, Activity activity) {
        super(activity, android.R.layout.simple_list_item_1, reports);
        _activity = activity;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReportEquipmentParameters reportEquipmentParameters;
        TextView textViewSite, textViewDate;
        // if we weren't given a view, inflate one
        if (null == convertView) {
            convertView = _activity.getLayoutInflater().inflate(R.layout.report_list_item, null);
        }
        reportEquipmentParameters = getItem(position);
        textViewSite=(TextView)convertView.findViewById(R.id.report_list_site);
        textViewSite.setText(reportEquipmentParameters.getParameter().getName());

        textViewDate = (TextView)convertView.findViewById((R.id.report_list_date));
        textViewDate.setText(reportEquipmentParameters.getValue()+" "+reportEquipmentParameters.getParameter().getType());

        return convertView;
    }
}
