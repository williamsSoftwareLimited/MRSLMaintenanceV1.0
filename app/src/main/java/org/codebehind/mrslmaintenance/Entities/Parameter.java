package org.codebehind.mrslmaintenance.Entities;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Gavin on 09/02/2015.
 */
public class Parameter implements Serializable {
    private int _id;
    private String _name;
    private String _units;
    private int _parameterTypeId;

    private String _newValue; // outlier this has nothing to do with the database, it's for the UI - EquipmentNewFragment

    public Parameter(String name, String units , int parameterTypeId) {
        this(-1,name, units, parameterTypeId);
    }

    public Parameter(int id, String name, String units, int parameterTypeId){
        setId(id);
        setName(name);
        setUnits(units);
        setParameterTypeId(parameterTypeId);
    }

    public int getId() {
        return _id;
    }
    public void setId(int id) {
        _id = id;
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
