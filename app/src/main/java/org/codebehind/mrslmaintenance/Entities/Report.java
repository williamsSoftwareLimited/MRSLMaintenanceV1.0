package org.codebehind.mrslmaintenance.Entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Gavin on 29/12/2014.
 */
public class Report {
    UUID _id;
    String _siteName;
    int _siteId;
    String _engineerName;
    ArrayList<Equipment> _equipmentList;
    ArrayList<Personnel> _emailList;
    Boolean _emailed; // committed
    Date _reportDate;
    ArrayList<ReportEquipmentParameters> ReportEquipmentParametersList;

    public Report getNew(){ // to get a brand new Report without the overhead in a constructor
        Report r = new Report();
        r.setEquipmentList(new ArrayList<Equipment>());
        r.setReportDate(new Date());
        r.setId(UUID.randomUUID());
        return r;
    }

    public UUID getId() {
        return _id;
    }
    public void setId(UUID _id) {
        this._id = _id;
    }
    public String getSiteName() {
        return _siteName;
    }
    public int getSiteId() {
        return _siteId;
    }
    public void setSiteId(int _siteId) {
        this._siteId = _siteId;
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
    public ArrayList<Personnel> getEmailList() {
        return _emailList;
    }
    public void setEmailList(ArrayList<Personnel> _emailList) {
        this._emailList = _emailList;
    }
    public Boolean getEmailed() {
        return _emailed;
    }
    public void setEmailed(Boolean _emailed) {
        this._emailed = _emailed;
    }
    public Date getReportDate() {
        return _reportDate;
    }
    public void setReportDate(Date _reportDate) {
        this._reportDate = _reportDate;
    }
}