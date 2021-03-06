package org.codebehind.mrslmaintenance.Entities;

import org.codebehind.mrslmaintenance.Entities.Abstract.AEntity;

import java.io.Serializable;

/**
 * Created by Gavin on 09/02/2015.
 */
public class Parameter extends AEntity implements Serializable {
    private int _parameterTypeId;
    private String _name, _units;

    private String _newValue; // outlier this is for the ReportEquipmentParameter table and it's for the UI - EquipmentNewFragment

    public Parameter(String name, String units ,int parameterTypeId) {
        this(-1,name, units, parameterTypeId);
    }

    public Parameter(int id, String name, String units, int parameterTypeId){
        setId(id);
        setName(name);
        setUnits(units);
        setParameterTypeId(parameterTypeId);
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getUnits() {
        return _units;
    }

    public void setUnits(String units) {
        _units=units;
    }

    public int getParameterTypeId(){
        return _parameterTypeId;
    }

    public void setParameterTypeId(int parameterTypeId){
        _parameterTypeId=parameterTypeId;
    }

    public String getNewValue(){
        if (_newValue==null)_newValue=""; // a bit of protection as this is for the UI
        return _newValue;
    }

    public void setNewValue(String newValue){
        _newValue=newValue;
    }

    @Override
    public String toString() {
        return getName();
    }

}
