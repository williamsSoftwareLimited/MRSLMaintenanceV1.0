package org.codebehind.mrslmaintenance.Entities;

import java.io.Serializable;

/**
 * Created by root on 30/10/15.
 */
public class SiteEquipment implements Serializable, Comparable<SiteEquipment> {
    private int _id;
    private int _siteId;
    private String _name;

    private int _equipmentId;
    private Equipment _equipment;

    public SiteEquipment(int siteId, int equipmentId, String name){
        this(-1, siteId, equipmentId, name);
    }

    public SiteEquipment(int id, int siteId, int equipmentId, String name){

        setId(id);
        setSiteId(siteId);
        setEquipmentId(equipmentId);
        setName(name);
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public int getSiteId() {
        return _siteId;
    }

    public void setSiteId(int siteId) {
        _siteId = siteId;
    }

    public String getName(){
        return _name;
    }

    public void setName(String name){
        _name=name;
    }

    public int getEquipmentId() {
        return _equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        _equipmentId = equipmentId;
    }

    public Equipment getEquipment(){
        return _equipment;
    }

    public void setEquipment(Equipment equipment){
        if (equipment==null) return;
        setEquipmentId(equipment.getId());
        _equipment=equipment;
    }

    @Override
    public int compareTo(SiteEquipment anotherSiteEquipment) {

        if (anotherSiteEquipment==null) return -1;

        return getId()-anotherSiteEquipment.getId();
    }
}
