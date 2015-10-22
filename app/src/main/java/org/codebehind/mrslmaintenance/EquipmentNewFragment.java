package org.codebehind.mrslmaintenance;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.Models.EquipmentModel;
import org.codebehind.mrslmaintenance.Models.ParameterModel;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Gavin on 09/02/2015.
 */
public class EquipmentNewFragment extends Fragment {
    private UUID _existingParameterId ,
            _holdingParameterId;// if this isn't null then the text hasn't been changes and the existing is added
    int  _equipmentId;
    private String _existingParameterName;
    private Equipment _equipment;
    private ArrayList<Parameter> equipmentParamList;
    private int _mode;
    private EditText _nameEditText, _addParamEditText;
    private ListView _paramListView;
    private Spinner _selectParamSpinner;

    public void setEquipmentId(int id){
        _equipmentId=id;
    }
    public void setMode(int mode){
        _mode=mode;
    }
    public Equipment getEquipment(){return _equipment;}
    public void showParameterSpinner(){
        // show the spinner and edit text
        _selectParamSpinner.setVisibility(View.VISIBLE);
        _addParamEditText.setVisibility(View.VISIBLE);
        _existingParameterId=null;
    }

    // the new parameter is the text in the add parameter edit text, after adding clear and hide the edittext
    public void addParameter(){
        Parameter parameter;

        _selectParamSpinner.setVisibility(View.INVISIBLE);
        _addParamEditText.setVisibility(View.INVISIBLE);
        if (_existingParameterId==null) {
            _existingParameterId = UUID.randomUUID();
            parameter = new Parameter(_existingParameterId, _addParamEditText.getText().toString(), 0);
            ParameterModel.getInstance().add(parameter);
        }
        _equipment.addParameter(_existingParameterId);
        setText();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView;
        setHasOptionsMenu(true); // ensures the fragment knows it has a menu

        rootView = inflater.inflate(R.layout.fragment_equipment_new, container, false);
        _equipment = EquipmentModel.getInstance().getEquipment(_equipmentId);
        equipmentParamList=_equipment.getParameterList();
        setControls(rootView);
        setText();
        setEvents();
        return rootView;
    }
    private void setControls(View rootView){
        _nameEditText=(EditText)rootView.findViewById(R.id.equipment_new_name_edittext);
        _paramListView=(ListView)rootView.findViewById(R.id.equipment_new_param_listview);
        _addParamEditText=(EditText)rootView.findViewById(R.id.equipment_new_param_addParamEditText);
        _selectParamSpinner=(Spinner)rootView.findViewById(R.id.equipment_new_param_selectParamSpinner);
    }
    private void setText(){
        _existingParameterId=null;
        _nameEditText.setText(_equipment.getEquipmentName());
        _paramListView.setAdapter(new paramList(equipmentParamList));
        _selectParamSpinner.setAdapter(new paramSpinnerAdaptor(ParameterModel.getInstance().getList()));
    }
    private void setEvents(){
        _selectParamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Parameter parameter;
                if (position>0) {
                    parameter=ParameterModel.getInstance().getList().get(position);
                    _existingParameterId=parameter.getID();
                    _holdingParameterId=parameter.getID();
                    _existingParameterName=parameter.getName();
                    _addParamEditText.setText(parameter.getName()); // this will call the listener onTextChanged
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {       }
        });
        _addParamEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // if the text is changed even with a space
                if (_existingParameterName.trim().equalsIgnoreCase(s.toString()))
                    _existingParameterId=_holdingParameterId;
                else
                    _existingParameterId = null;
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private class paramList extends ArrayAdapter<Parameter> {
        public paramList(ArrayList<Parameter>arraylist) {
            super(getActivity(), R.layout.equipment_param_item, arraylist);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Parameter parameter;
            EditText paramEditTest;

            if (null == convertView) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.equipment_param_item, null);
            }
            parameter = getItem(position);
            paramEditTest=(EditText)convertView.findViewById(R.id.equipment_param_item_edittext);
            paramEditTest.setText(parameter.getName());
            return convertView;
        }
    }
    private class paramSpinnerAdaptor extends ArrayAdapter<Parameter> {
        public paramSpinnerAdaptor(ArrayList<Parameter>arraylist) {
            super(getActivity(), R.layout.spinner_parameters, arraylist);
        }
        @Override public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
            return getView(position, cnvtView, prnt);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Parameter parameter;
            TextView nameTextView, idTextView;
            View row;

            row=getActivity().getLayoutInflater().inflate(R.layout.spinner_parameters, null);
            parameter = getItem(position);
            nameTextView=(TextView)row.findViewById(R.id.spinner_parameters_name);
            nameTextView.setText(parameter.getName());
            idTextView=(TextView)row.findViewById(R.id.spinner_parameters_id);
            idTextView.setText(parameter.getID().toString());
            return row;
        }
    }
}
