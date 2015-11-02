package org.codebehind.mrslmaintenance.Entities;

/**
 * Created by root on 30/10/15.
 */
public class SiteEquipment {
    int _id;
    int _siteid;
    int _equipid;

    public int getId() {
        return _id;
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public int getSiteid() {
        return _siteid;
    }

    public void setSiteid(int _siteid) {
        this._siteid = _siteid;
    }

    public int getEquipid() {
        return _equipid;
    }

    public void setEquipid(int _equipid) {
        this._equipid = _equipid;
    }
}
