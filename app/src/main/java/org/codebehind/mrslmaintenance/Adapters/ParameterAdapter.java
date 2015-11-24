package org.codebehind.mrslmaintenance.Adapters;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.R;
import org.codebehind.mrslmaintenance.ViewModels.EditTextViewModel;
import org.codebehind.mrslmaintenance.ViewModels.TextViewViewModel;

import java.util.ArrayList;

/**
 * Created by root on 11/11/15.
 */
public class ParameterAdapter extends ArrayAdapter<Parameter>{

    private Activity _activity;
    private Parameter _parameter;
    private TextViewViewModel _textViewId, _textViewName,_textViewType;
    private EditTextViewModel _editTextViewModel;
    int count;

    public ParameterAdapter(ArrayList<Parameter> reports, Activity activity) {
        super(activity, android.R.layout.simple_list_item_1, reports);
        _activity = activity;
        count=1;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // if we weren't given a view, inflate one
        if (null == convertView)
            convertView = _activity.getLayoutInflater().inflate(R.layout.parameter_list_item, null);

        _parameter = getItem(position);
        setControls(convertView);
        setText();
        setEvents();

        return convertView;
    }

    private void setControls(View convertView){

        _textViewId=new TextViewViewModel((TextView)convertView.findViewById(R.id.parameter_list_item_id));
        _textViewName=new TextViewViewModel((TextView)convertView.findViewById((R.id.parameter_list_item_name)));
        _editTextViewModel=new EditTextViewModel((EditText)convertView.findViewById(R.id.parameter_list_item_value));
        _textViewType=new TextViewViewModel((TextView)convertView.findViewById((R.id.parameter_list_item_units)));
    }

    private void setText(){

        _textViewId.setText(""+_parameter.getId());
        _textViewName.setText(_parameter.getName());
        _editTextViewModel.setText(_parameter.getNewValue());
        _editTextViewModel.setType(_parameter.getParameterTypeId());
        _textViewType.setText(_parameter.getUnits());
    }

    private void setEvents() {

    }

}
