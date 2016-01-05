package org.codebehind.mrslmaintenance.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Adapters.Abstract.AbstractAdapter;
import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.Entities.ParameterType;
import org.codebehind.mrslmaintenance.R;
import org.codebehind.mrslmaintenance.Singletons.ParameterTypesSingleton;
import org.codebehind.mrslmaintenance.ViewModels.TextViewViewModel;

import java.util.ArrayList;

/**
 * Created by root on 31/12/15.
 */
public class EquipParamAdapter extends AbstractAdapter<Parameter> {
    private Parameter _parameter;
    private TextViewViewModel _nameTextViewVm, _typeTextViewNameVm,_unitTextViewVm;

    public EquipParamAdapter(ArrayList<Parameter> params, Activity activity) {
        super(activity, android.R.layout.simple_list_item_1, params);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // if we weren't given a view, inflate one
        if (null == convertView)
            convertView = _activity.getLayoutInflater().inflate(R.layout.equipment_param_item, null);

        _parameter = getItem(position);
        setControls(convertView);
        setText();
        //setEvents();

        return convertView;
    }

    private void setControls(View convertView){

        _nameTextViewVm=new TextViewViewModel((TextView)convertView.findViewById(R.id.equipment_param_name_tv));
        _typeTextViewNameVm=new TextViewViewModel((TextView)convertView.findViewById(R.id.equipment_param_type_tv));
        _unitTextViewVm=new TextViewViewModel((TextView)convertView.findViewById((R.id.equipment_param_unit_tv)));
    }

    private void setText(){
        ParameterType type;

        type= ParameterTypesSingleton.getInstance().getParamTypes().get(_parameter.getParameterTypeId());

        _nameTextViewVm.setText(_parameter.getName());
        _typeTextViewNameVm.setText(type.getName());
        _unitTextViewVm.setText(_parameter.getUnits());
    }

    private void setEvents() {}
}
