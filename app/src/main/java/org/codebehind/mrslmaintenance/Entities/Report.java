package org.codebehind.mrslmaintenance.Entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.io.Serializable;

/**
 * Created by Gavin on 29/12/2014.
 */
public class Report implements Serializable {
    int _id;
    UUID _uuid;
    String _siteName;
    int _siteId;
    String _engineerName;
    ArrayList<Equipment> _equipmentList;

    Date _reportDate;

    public Report getNew(){ // to get a brand new Report without the overhead in a constructor
        Report r = new Report();
        r.setEquipmentList(new ArrayList<Equipment>());
        r.setReportDate(new Date());
        r.setUiid(UUID.randomUUID());
        return r;
    }

    public int getId() {
        return _id;
    }
    public void setId(int _id) {
        this._id = _id;
    }

    public int getSiteId() {
        return _siteId;
    }
    public void setSiteId(int _siteId) {
        this._siteId = _siteId;
    }

    public String getSiteName() {
        return _siteName;
    }
    public void setSiteName(String _siteName) {
        this._siteName = _siteName;
    }

    public String getEngineerName() {
        return _engineerName;
    }
    public void setEngineerName(String _engineerName) {
        this._engineerName = _engineerName;
    }

    public ArrayList<Equipment> getEquipmentList() {
        return _equipmentList;
    }
    public void setEquipmentList(ArrayList<Equipment> _equipmentList) {
        this._equipmentList = _equipmentList;
    }

    public Date getReportDate() {
        return _reportDate;
    }
    public void setReportDate(Date _reportDate) {
        this._reportDate = _reportDate;
    }

    public void setUiid(UUID uiid){
        _uuid=uiid;
    }
    public UUID getUuid(){
        return _uuid;
    }
}