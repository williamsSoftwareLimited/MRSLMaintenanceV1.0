package org.codebehind.mrslmaintenance.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Entities.ReportEquipmentParameters;
import org.codebehind.mrslmaintenance.R;

import java.util.ArrayList;

/**
 * Created by root on 12/11/15.
 */
public class ReportEquipmentParamsAdapter extends ArrayAdapter<ReportEquipmentParameters> {
    Activity _activity;

    public ReportEquipmentParamsAdapter(ArrayList<ReportEquipmentParameters> reportEquipmentParameters, Activity activity) {
        super(activity, android.R.layout.simple_list_item_1, reportEquipmentParameters);
        _activity = activity;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReportEquipmentParameters reportEquipmentParameters;
        TextView textViewId, textViewName,textViewType;
        EditText editTextValue;

        // if we weren't given a view, inflate one
        if (null == convertView) {
            convertView = _activity.getLayoutInflater().inflate(R.layout.parameter_list_item, null);
        }

        reportEquipmentParameters = getItem(position);

        textViewId=(TextView)convertView.findViewById(R.id.parameter_list_item_id);
        textViewId.setText(""+reportEquipmentParameters.getId());

        textViewName = (TextView)convertView.findViewById((R.id.parameter_list_item_name));
        textViewName.setText(reportEquipmentParameters.getParameter().getName());

        editTextValue = (EditText)convertView.findViewById((R.id.parameter_list_item_value));
        editTextValue.setText(reportEquipmentParameters.getValue());
        editTextValue.setEnabled(false);

        textViewType = (TextView)convertView.findViewById((R.id.parameter_list_item_units));
        textViewType.setText(reportEquipmentParameters.getParameter().getUnits());

        return convertView;
    }
}
