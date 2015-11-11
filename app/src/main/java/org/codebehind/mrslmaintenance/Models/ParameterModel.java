package org.codebehind.mrslmaintenance.Models;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Gavin on 11/02/2015.
 */
public class ParameterModel {
    private ArrayList<Parameter> _list;
    private static ParameterModel _instance;
    private ParameterModel(){
        _list=new ArrayList<>();
    }
    public static ParameterModel getInstance(){
        if (_instance==null)_instance=new ParameterModel();
        return _instance;
    }
    public ArrayList<Parameter> getList() {
        return _list;
    }

    public Parameter getItem(UUID id) {
        for (Parameter param : _list) if (id.equals(param.getID())) return param;
        return null;
    }
    public Parameter add(Parameter parameter) {
        _list.add(parameter);
        return parameter;
    }
    public Parameter delete(Parameter parameter) {
        _list.remove(parameter);
        return parameter;
    }
    public Parameter update(Parameter parameter) {
        delete(parameter);
        add(parameter);
        return null;
    }

    public void clear() {
        _list.clear();
    }

}
