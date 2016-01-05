package org.codebehind.mrslmaintenance.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Adapters.Abstract.AbstractAdapter;
import org.codebehind.mrslmaintenance.Entities.ParameterType;
import org.codebehind.mrslmaintenance.R;
import org.codebehind.mrslmaintenance.ViewModels.TextViewViewModel;

import java.util.ArrayList;

/**
 * Created by root on 05/01/16.
 */
public class ParamSpinnerAdapter extends AbstractAdapter<ParameterType> {

    private ParameterType _param;
    private TextViewViewModel _paramSpinTvVm;

    public ParamSpinnerAdapter(ArrayList<ParameterType> params, Activity activity) {
        super(activity, android.R.layout.simple_list_item_1, params);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // if we weren't given a view, inflate one
        if (null == convertView)
            convertView = _activity.getLayoutInflater().inflate(R.layout.param_spin_list_item, null);

        _param = getItem(position);
        setControls(convertView);
        setText();
        //setEvents();

        return convertView;
    }

    private void setControls(View convertView){

        _paramSpinTvVm=new TextViewViewModel((TextView)convertView.findViewById(R.id.param_spin_list_item_type_tv));
    }

    private void setText(){

        _paramSpinTvVm.setText(_param.getName());
    }

    private void setEvents() {}

}
