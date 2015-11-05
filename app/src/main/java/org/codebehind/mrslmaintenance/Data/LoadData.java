package org.codebehind.mrslmaintenance.Data;

import android.content.Context;
import android.database.Cursor;

import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Entities.SiteEquipment;
import org.codebehind.mrslmaintenance.Models.EquipmentDbModel;
import org.codebehind.mrslmaintenance.Models.EquipmentModel;
import org.codebehind.mrslmaintenance.Models.ParameterModel;
import org.codebehind.mrslmaintenance.Models.ReportDbModel;
import org.codebehind.mrslmaintenance.Models.SiteDbModel;
import org.codebehind.mrslmaintenance.Models.SiteEquipmentDbModel;

import java.util.UUID;

/**
 * Created by Gavin on 11/02/2015.
 */
public class LoadData {
    public void load(){
        loadParameterData();
    }
    // ensure that this is called first
    public void popSiteData(Context c){
        SiteDbModel mod = new SiteDbModel(c);

        if (mod.getlist().size()>0) return;

        Site s = new Site();
        s.setAddress("Outer Mongolia, Spain.");
        s.setName("Mars Plant");
        //s.setImageId(1); // make sure the images exist
        mod.add(s);

        s = new Site();
        s.setAddress("Lost in space.");
        s.setName("Alien Plant");
        //s.setImageId(1); // make sure the images exist
        mod.add(s);

        s = new Site();
        s.setAddress("Banana land.");
        s.setName("Superman Plant");
        //s.setImageId(1); // make sure the images exist
        mod.add(s);

        s = new Site();
        s.setAddress("Who loves ya.");
        s.setName("Kojak Plant");
        //s.setImageId(1); // make sure the images exist
        mod.add(s);

        s = new Site();
        s.setAddress("Magic Roundabout.");
        s.setName("Dylan the Hippy Plant");
        //s.setImageId(1); // make sure the images exist
        mod.add(s);
        // the next bit was to test if there's any data in the db
        /*Cursor cur = mod.getcursor();
        cur.moveToFirst();
        while(cur.isAfterLast()==false){
            Site e=new Site();
            int x = cur.getInt(0);
            cur.moveToNext();
        }*/
        mod.close();
    }

    public void populateReportData(Context c) {

        ReportDbModel mod = new ReportDbModel(c);
        if (mod.getAll().size()>0) return;

        Report rep = new Report();
        rep.setSiteId(1);
        rep.setEngineerName("He Man");
        mod.add(rep);

        rep = new Report();
        rep.setSiteId(1);
        rep.setEngineerName("Dr Who");
        mod.add(rep);

        rep = new Report();
        rep.setSiteId(2);
        rep.setEngineerName("Sponge Bob Square Pants");
        mod.add(rep);

        rep = new Report();
        rep.setSiteId(3);
        rep.setEngineerName("Gordon the Gopher");
        mod.add(rep);

        rep = new Report();
        rep.setSiteId(4);
        rep.setEngineerName("Douglas Adams");
        mod.add(rep);

        rep = new Report();
        rep.setSiteId(5);
        rep.setEngineerName("Awful Man Pope");
        mod.add(rep);

        mod.close();
    }

    public void populateEquipmentDb(Context c) {
        EquipmentDbModel model;
        Equipment equipment;

        model = new EquipmentDbModel(c);

        // test if there's any data in the database populate if not
        if (model.getList().size()>0)return;

        equipment=new Equipment();
        equipment.setEquipmentName("COMPRESSOR - LT1");
        equipment.setImgId(1);
        model.add(equipment);

        equipment=new Equipment();
        equipment.setEquipmentName("COMPRESSOR - HT1");
        equipment.setImgId(2);
        model.add(equipment);

        equipment=new Equipment();
        equipment.setEquipmentName("VALVE STATION");
        model.add(equipment);

        equipment=new Equipment();
        equipment.setEquipmentName("Coolers in Rooms");
        model.add(equipment);

        equipment=new Equipment();
        equipment.setEquipmentName("Oil");
        model.add(equipment);

        equipment=new Equipment();
        equipment.setEquipmentName("Magic Oil");
        model.add(equipment);

        equipment=new Equipment();
        equipment.setEquipmentName("Chocolate makers");
        model.add(equipment);
        model.close();
    }

    public void populateSiteEquipmentDb(Context c) {
        SiteEquipmentDbModel model;
        SiteEquipment siteEquip;

        model = new SiteEquipmentDbModel(c);

        // test if there's any data in the database populate if not
        if (model.getAllList().size()>0)return;

        siteEquip = new  SiteEquipment();
        siteEquip.setSiteid(1);
        siteEquip.setEquipid(1);
        model.add(siteEquip);
        siteEquip.setEquipid(2);
        model.add(siteEquip);

        siteEquip = new  SiteEquipment();
        siteEquip.setSiteid(2);
        siteEquip.setEquipid(3);
        model.add(siteEquip);
        siteEquip.setEquipid(4);
        model.add(siteEquip);
        siteEquip.setEquipid(5);
        model.add(siteEquip);

        siteEquip = new  SiteEquipment();
        siteEquip.setSiteid(3);
        siteEquip.setEquipid(6);
        model.add(siteEquip);

        siteEquip = new  SiteEquipment();
        siteEquip.setSiteid(4);
        siteEquip.setEquipid(1);
        model.add(siteEquip);
        siteEquip.setEquipid(2);
        model.add(siteEquip);
        siteEquip.setEquipid(7);
        model.add(siteEquip);

        siteEquip = new  SiteEquipment();
        siteEquip.setSiteid(5);
        siteEquip.setEquipid(3);
        model.add(siteEquip);
        siteEquip.setEquipid(5);
        model.add(siteEquip);
        model.close();
    }

    private void loadParameterData() {
        Parameter parameter;
        ParameterModel model;

        model=ParameterModel.getInstance();

        parameter=new Parameter(UUID.randomUUID(),"Hours Run", 0);
        model.add(parameter);
        parameter=new Parameter(UUID.randomUUID(),"Suction Pressure", 0);
        model.add(parameter);
        parameter=new Parameter(UUID.randomUUID(),"Suction Temperature", 0);
        model.add(parameter);
        parameter=new Parameter(UUID.randomUUID(),"Suction Superheat", 0);
        model.add(parameter);
        parameter=new Parameter(UUID.randomUUID(),"Discharge Pressure", 0);
        model.add(parameter);
        parameter=new Parameter(UUID.randomUUID(),"Discharge Temperature", 0);
        model.add(parameter);
        parameter=new Parameter(UUID.randomUUID(),"Discharge Superheat", 0);
        model.add(parameter);
        parameter=new Parameter(UUID.randomUUID(),"Oil Pressure", 0);
        model.add(parameter);
        parameter=new Parameter(UUID.randomUUID(),"Oil Level", 0);
        model.add(parameter);
        parameter=new Parameter(UUID.randomUUID(),"Check McD Freezer Tunnel 1", 0);
        model.add(parameter);
        parameter=new Parameter(UUID.randomUUID(),"Check Conveneince freezer Tunnel 1 VS per visit", 0);
        model.add(parameter);
        parameter=new Parameter(UUID.randomUUID(),"Other Valve stations 2 Other per Visit", 0);
        model.add(parameter);
        parameter=new Parameter(UUID.randomUUID(),"Inspect Internally 1 VS per visit, replace gaskets", 0);
        model.add(parameter);
        parameter=new Parameter(UUID.randomUUID(),"Check 2 Room Coolers per Visit", 0);
        model.add(parameter);
        parameter=new Parameter(UUID.randomUUID(),"Motor Current", 0);
        model.add(parameter);
        parameter=new Parameter(UUID.randomUUID(),"Room Temperature Controls", 0);
        model.add(parameter);
        parameter=new Parameter(UUID.randomUUID(),"Check Oil in HT, LT, Econ Vessels", 0);
        model.add(parameter);
        parameter=new Parameter(UUID.randomUUID(),"Check Freezer Level Column", 0);
        model.add(parameter);
        parameter=new Parameter(UUID.randomUUID(),"Drain Oil from Freezer Tunnel Evaporator (6 months)", 0);
        model.add(parameter);
    }

}