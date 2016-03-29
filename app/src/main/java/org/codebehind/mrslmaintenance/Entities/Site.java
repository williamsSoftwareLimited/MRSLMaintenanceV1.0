package org.codebehind.mrslmaintenance.Entities;

import com.google.android.gms.maps.model.LatLng;

import org.codebehind.mrslmaintenance.Entities.Abstract.AEntity;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Gavin on 23/12/2014.
 */
public class Site extends AEntity implements Serializable{


    private String _name;
    private String _system;
    private String _address;
    private String _description;
    private String _plantId;
    private LatLng _latLng; // not sure how this will keep in the database
    private int _imageId;

    public Site(String name, String address){
        this(-1,name,address);
    }

    public Site(int id, String name, String address){
        setId(id);
        setName(name);
        setAddress(address);
    }

    public String getName() {
        return _name;
    }
    public void setName(String _name) {
        this._name = _name;
    }
    public String getSystem() {
        return _system;
    }
    public void setSystem(String _system) {
        this._system = _system;
    }
    public String getAddress() {
        return _address;
    }
    public void setAddress(String _address) {
        this._address = _address;
    }
    public String getDescription() {
        return _description;
    }
    public void setDescription(String _description) {
        this._description = _description;
    }
    public String getPlantId() {
        return _plantId;
    }
    public void setPlantId(String _plantId) {
        this._plantId = _plantId;
    }
    public LatLng getLatLng(){
        return _latLng;
    }
    public void setLatLng(LatLng latLng){
        _latLng=latLng;
    }
    public int getImageId() {
        return _imageId;
    }
    public void setImageId(int _imageId) {
        this._imageId = _imageId;
    }

    // actually don't know what this is for?
    public String toString(){
        return getName();
    }
}