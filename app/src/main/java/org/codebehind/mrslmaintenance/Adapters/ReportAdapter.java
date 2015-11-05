package org.codebehind.mrslmaintenance.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.R;

import java.text.DateFormat;
import java.util.ArrayList;

/**
 * Created by root on 05/11/15.
 */
public class ReportAdapter extends ArrayAdapter<Report> {

    Activity _activity;

    public ReportAdapter(ArrayList<Report> reports, Activity activity) {
        super(activity, android.R.layout.simple_list_item_1, reports);
        _activity = activity;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Report r;
        TextView tvSite, tvDate;
        // if we weren't given a view, inflate one
        if (null == convertView) {
            convertView = _activity.getLayoutInflater()
                    .inflate(R.layout.report_list_item, null);
        }
        r = getItem(position);
        tvSite=(TextView)convertView.findViewById(R.id.report_list_site);
        tvSite.setText(r.getSiteName());
        tvDate = (TextView)convertView.findViewById((R.id.report_list_date));
        if (r.getReportDate()!=null) tvDate.setText(DateFormat.getDateInstance().format(r.getReportDate()));
        return convertView;
    }
}
