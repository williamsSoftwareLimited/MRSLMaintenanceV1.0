package org.codebehind.mrslmaintenance.Entities;

import org.codebehind.mrslmaintenance.Entities.Abstract.AEntity;
import org.codebehind.mrslmaintenance.Models.ParameterModel;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Gavin on 23/12/2014.
 */
public class Equipment extends AEntity implements Comparable<Equipment>{

    private String _equipmentName;
    private int _imageId;
    private ArrayList<Parameter> _parameterList;
    private Image _image;

    // this is probably unnecessary
    public Equipment(){
        setParameterList(new ArrayList<Parameter>());
    }

    public Equipment( String equipmentName, int imageId){
        this(-1,  equipmentName, imageId);
    }

    public Equipment(int id, String equipmentName, int imageId){
        setId(id);
        setEquipmentName(equipmentName);
        setImgId(imageId);
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
        return _imageId;
    }

    public void setImgId(int imgId) {
        _imageId = imgId;
    }

    public Image getImage(){
        return _image;
    }

    public void setImage(Image image){
        _image=image;
    }

    @Override
    public int compareTo(Equipment equipment) {

        if (equipment==null)return -1;

        return getId()-equipment.getId();
    }
}