package org.codebehind.mrslmaintenance.Entities;

import java.util.UUID;

/**
 * Created by Gavin on 09/02/2015.
 */
public class Parameter {
    private int _id;
    private String _name;
    private String _type;

    public Parameter(String name, String type) {
        this(-1,name,type);
    }
    public Parameter(int id, String name, String type){
        setID(id);
        setName(name);
        setType(type);
    }

    public int getID() {
        return _id;
    }
    public void setID(int id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }
    public void setName(String name) {
        _name = name;
    }

    public String getType() {
        return _type;
    }
    public void setType(String type) {
        _type = type;
    }
    @Override
    public String toString() {
        return getName();
    }
}
