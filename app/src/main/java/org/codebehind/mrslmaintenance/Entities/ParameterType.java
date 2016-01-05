package org.codebehind.mrslmaintenance.Entities;

/**
 * Created by root on 17/11/15.
 */
public class ParameterType {
    private int _id;
    private String _name;

    public ParameterType(String name) {
        this(-1,name);
    }

    public ParameterType(int id, String name){
        setId(id);
        setName(name);
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
}
