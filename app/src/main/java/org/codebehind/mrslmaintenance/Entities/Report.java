package org.codebehind.mrslmaintenance.Entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Gavin on 29/12/2014.
 */
public class Report implements Serializable {
    private int _id, _siteId;;
    private String _siteName, _engineerName;
    private ArrayList<SiteEquipment> _siteEquipmentList;
    private Date _reportDate;
    private Boolean _deleted;

    public Report(int siteId, String engineerName, ArrayList<SiteEquipment> siteEquipmentList, Date reportDate) {
        this(-1, siteId, engineerName, siteEquipmentList, reportDate);
    }
    public Report(int id, int siteId, String engineerName, ArrayList<SiteEquipment> siteEquipmentList, Date reportDate){
        setId(id);
        setSiteId(siteId);
        setEngineerName(engineerName);
        setSiteEquipmentList(siteEquipmentList);
        setReportDate(reportDate);
        setDeleted(false); //Explicitly set deleted if need for an update etc
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

    public ArrayList<SiteEquipment> getSiteEquipmentList() {
        return _siteEquipmentList;
    }

    public void setSiteEquipmentList(ArrayList<SiteEquipment> siteEquipmentList) {
        _siteEquipmentList = siteEquipmentList;
    }

    public Date getReportDate() {
        return _reportDate;
    }
    public void setReportDate(Date _reportDate) {
        this._reportDate = _reportDate;
    }

    public Boolean getDeleted(){
        return _deleted;
    }

    public void setDeleted(Boolean deleted){
        _deleted=deleted;
    }

}