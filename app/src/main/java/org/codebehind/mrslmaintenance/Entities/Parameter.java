package org.codebehind.mrslmaintenance.Entities;

import java.util.UUID;

/**
 * Created by Gavin on 09/02/2015.
 */
public class Parameter {
    private int _id;
    private String _name;
    private String _units;
    private String _newValue;

    public Parameter(String name, String type) {
        this(-1,name,type);
    }
    public Parameter(int id, String name, String units){
        setId(id);
        setName(name);
        setUnits(units);
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
