package org.codebehind.mrslmaintenance.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.R;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IEditTextViewModelDelegate;
import org.codebehind.mrslmaintenance.ViewModels.ParameterEditTextViewModel;
import org.codebehind.mrslmaintenance.ViewModels.TextViewViewModel;

import java.util.ArrayList;

/**
 * Created by root on 11/11/15.
 */
public class ParameterAdapter extends ArrayAdapter<Parameter> implements IEditTextViewModelDelegate{

    private Activity _activity;
    private Parameter _parameter;
    private TextViewViewModel _textViewId, _textViewName,_textViewType;
    private ParameterEditTextViewModel _parameterEditTextViewModel;

    public ParameterAdapter(ArrayList<Parameter> reports, Activity activity) {
        super(activity, android.R.layout.simple_list_item_1, reports);
        _activity = activity;
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

        _parameterEditTextViewModel.setParameter(_parameter);

        return convertView;
    }

    private void setControls(View convertView){

        _textViewId=new TextViewViewModel((TextView)convertView.findViewById(R.id.parameter_list_item_id));
        _textViewName=new TextViewViewModel((TextView)convertView.findViewById((R.id.parameter_list_item_name)));
        _parameterEditTextViewModel=new ParameterEditTextViewModel((EditText)convertView.findViewById(R.id.parameter_list_item_value), this);
        _textViewType=new TextViewViewModel((TextView)convertView.findViewById((R.id.parameter_list_item_units)));
    }

    private void setText(){

        _textViewId.setText(""+_parameter.getId());
        _textViewName.setText(_parameter.getName());
        _parameterEditTextViewModel.setText(_parameter.getNewValue());
        _parameterEditTextViewModel.setType(_parameter.getParameterTypeId());
        _textViewType.setText(_parameter.getUnits());
    }

    private void setEvents() {

    }

    @Override
    public void textUpdated(int uniqueId, String text) {

    }
}
