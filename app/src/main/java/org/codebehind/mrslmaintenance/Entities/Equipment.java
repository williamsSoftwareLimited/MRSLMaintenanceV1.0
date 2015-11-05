package org.codebehind.mrslmaintenance.Entities;

import org.codebehind.mrslmaintenance.Models.IRepository;
import org.codebehind.mrslmaintenance.Models.ParameterModel;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Gavin on 23/12/2014.
 */
public class Equipment {
    private int _id;
    private String _equipmentName;
    private int imgId;
    private byte[] img;
    private ArrayList<Parameter> _parameterList;

    public Equipment(){
        setParameterList(new ArrayList<Parameter>());
    }

    public int getId() {
        return _id;
    }
    public void setId(int _id) {
        this._id = _id;
    }
    public String getEquipmentName() {
        return _equipmentName;
    }
    public void setEquipmentName(String equipmentName) {
        _equipmentName = equipmentName;
    }
    public String toString(){ return getEquipmentName();}
    public ArrayList<Parameter> getParameterList() {
        return _parameterList;
    }
    public void setParameterList(ArrayList<Parameter> parameterList) {
        _parameterList = parameterList;
    }
    // if the parameter can't be found returns false
    public boolean addParameter(UUID paramId){
        Parameter parameter;

        parameter = ParameterModel.getInstance().getItem(paramId);
        if (parameter==null) return false;

        _parameterList.add(parameter);
        return true;
    }

    public int getImgId() {
        return imgId;
    }
    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public byte[] getImg() {
        return img;
    }
    public void setImg(byte[] img) {
        this.img = img;
    }

}