package org.codebehind.mrslmaintenance.Models;

import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Report;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Gavin on 29/12/2014.
 */
public class ReportModel {
    ArrayList<Report> _list;
    static ReportModel _instance;
    public static final String TABLE="report", FILTER_SELECTION_START="siteId like '%",FILTER_SELECTION_END="%'";
    private static final String[] FIELDS = new String[]{"_id", "siteId", "ts","del" };
    public static final int ID=0, SITEID=1, TS=2, DEL=3;
    private ReportModel() {
        this._list = new ArrayList<>();
        ArrayList<Equipment> equipList = EquipmentModel.getInstance().getList(), reportEquips;
        Equipment equip;
        Report r;
        for(int i=1; i<=5; i++){
            r = new Report(i, i, "Name " + i, null, new Date());

            reportEquips = new  ArrayList<Equipment>();
            for (int j=0; j<i%equipList.size(); j++) {
                equip = new Equipment();
                equip.setId(equipList.get(j).getId());
                equip.setEquipmentName(equipList.get(j).getEquipmentName());
                reportEquips.add(equip);
            }
            //r.setEquipmentList(reportEquips);
            _list.add(r);
        }
    }
    public static ReportModel getInstance(){
        if (_instance== null) _instance=new ReportModel();
        return _instance;
    }

    public ArrayList<Report> getList() {
        return _list;
    }

    public Report getItem(UUID id) {
        for( Report r : getList()){
            if (r.getUuid().equals(id))return r;
        }
        return null;
    }
    public Report add(Report item) {
        _list.add(item);
        return item;
    }

    public Report delete(Report item) {
        return null;
    }
    public Report update(Report item) {
        return null;
    }

    public void setSite(UUID reportId, UUID siteId){

        getItem(reportId).setSiteName(SiteModel.getInstance().getSite(siteId).getName());
    }

    public Report getTemplate(Report report){
        return report;
    }
}