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

    public Report(int siteId, String engineerName, ArrayList<Equipment> equipmentList, Date reportDate) {
        this(-1, siteId, engineerName, equipmentList, reportDate);
    }
    public Report(int id, int siteId, String engineerName, ArrayList<Equipment> equipmentList, Date reportDate){
        setId(id);
        setSiteId(siteId);
        setEngineerName(engineerName);
        setEquipmentList(equipmentList);
        setReportDate(reportDate);
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