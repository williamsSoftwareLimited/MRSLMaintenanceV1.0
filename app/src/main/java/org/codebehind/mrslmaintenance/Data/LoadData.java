package org.codebehind.mrslmaintenance.Data;

import android.content.Context;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.Email;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.EquipmentParameters;
import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.Entities.ParameterType;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Entities.ReportEquipParams;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Entities.SiteEquipment;
import org.codebehind.mrslmaintenance.Models.EmailDbModel;
import org.codebehind.mrslmaintenance.Models.EquipmentDbModel;
import org.codebehind.mrslmaintenance.Models.EquipmentParamsDbModel;
import org.codebehind.mrslmaintenance.Models.ParameterDbModel;
import org.codebehind.mrslmaintenance.Models.ParameterTypeDbModel;
import org.codebehind.mrslmaintenance.Models.ReportDbModel;
import org.codebehind.mrslmaintenance.Models.ReportEquipParamsDbModel;
import org.codebehind.mrslmaintenance.Models.SiteDbModel;
import org.codebehind.mrslmaintenance.Models.SiteEquipmentDbModel;
import org.codebehind.mrslmaintenance.Singletons.ParameterTypesSingleton;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by Gavin on 11/02/2015.
 */
public class LoadData {

    public void load(Context context){

        populateEquipmentData(context);
        popSiteData(context);
        populateReportData(context);
        populateSiteEquipmentData(context);
        populateParameterTypesData(context);
        populateParameterData(context);
        populateEquipmentParamsData(context);
        populateReportParametersData(context);
        popEmailData(context);

        DatabaseHelper.getInstance(context).close();
    }
    // ensure that this is called first
    public void popSiteData(Context c){
        SiteDbModel mod = new SiteDbModel(c);
        Site site;

        if (mod.getCount()>0) return;

        site = new Site("SITE 1", "Outer Mongolia, Spain.");
        //s.setImageId(1); // make sure the images exist
        mod.add(site);

        site = new Site("SITE 2", "Lost in space.");
        //s.setImageId(1); // make sure the images exist
        mod.add(site);

        site = new Site("SITE 3", "Banana land.");
        //s.setImageId(1); // make sure the images exist
        mod.add(site);

        site = new Site("SITE 4", "Who loves ya.");
        //s.setImageId(1); // make sure the images exist
        mod.add(site);

        site = new Site("SITE 5", "Magic Roundabout.");
        //s.setImageId(1); // make sure the images exist
        mod.add(site);
    }

    public void populateReportData(Context c) {

        ReportDbModel mod = new ReportDbModel(c, new ReportEquipParamsDbModel(c));
        if (mod.getCount()>0) return;

        Report rep = new Report(1, "He Man", null, null);
        mod.add(rep);

        rep = new Report(1, "Dr Who", null, null);
        mod.add(rep);

        rep = new Report(2, "Sponge Bob Square Pants", null, null);
        mod.add(rep);

        rep = new Report(3, "Gordon the Gopher", null, null);
        mod.add(rep);

        rep = new Report(4, "Douglas Adams", null, null);
        mod.add(rep);

        rep = new Report(5, "Awful Man Pope", null, null);
        mod.add(rep);

    }

    public void populateEquipmentData(Context c) {
        EquipmentDbModel model;
        Equipment equipment;

        model = new EquipmentDbModel(c);

        // test if there's any data in the database populate if not
        if (model.getCount()>0)return;

        equipment=new Equipment("EQUIPMENT - 1", 1);
        model.add(equipment);

        equipment=new Equipment("EQUIPMENT - 2", 2);
        model.add(equipment);

        equipment=new Equipment("EQUIPMENT - 3", 1);
        model.add(equipment);

        equipment=new Equipment("EQUIPMENT - 4", 1);
        model.add(equipment);

        equipment=new Equipment("EQUIPMENT - 5", 1);
        model.add(equipment);

        equipment=new Equipment("EQUIPMENT - 6", 1);
        model.add(equipment);

        equipment=new Equipment("EQUIPMENT - 7", 1);
        model.add(equipment);

    }

    public void populateSiteEquipmentData(Context c) {
        SiteEquipmentDbModel model;
        SiteEquipment siteEquip;

        model = new SiteEquipmentDbModel(c);

        // test if there's any data in the database populate if not
        if (model.getCount()>0)return;

        siteEquip = new  SiteEquipment(1, 1, "Site1_Equipment1");
        model.add(siteEquip);

        siteEquip.setEquipmentId(2);
        siteEquip.setName("Site1_Equipment2");
        model.add(siteEquip);

        siteEquip.setEquipmentId(3);
        siteEquip.setName("Site1_Equipment3");
        model.add(siteEquip);

        siteEquip.setEquipmentId(4);
        siteEquip.setName("Site1_Equipment4");
        model.add(siteEquip);

        siteEquip.setEquipmentId(5);
        siteEquip.setName("Site1_Equipment5");
        model.add(siteEquip);


        siteEquip.setSiteId(2);
        siteEquip.setEquipmentId(1);
        siteEquip.setName("Site2_Equipment1");
        model.add(siteEquip);

        siteEquip.setEquipmentId(2);
        siteEquip.setName("Site2_Equipment2");
        model.add(siteEquip);

        siteEquip.setEquipmentId(3);
        siteEquip.setName("Site2_Equipment3");
        model.add(siteEquip);

        siteEquip.setEquipmentId(4);
        siteEquip.setName("Site2_Equipment4");
        model.add(siteEquip);

        siteEquip.setEquipmentId(5);
        siteEquip.setName("Site2_Equipment5");
        model.add(siteEquip);


        siteEquip.setSiteId(3);
        siteEquip.setEquipmentId(6);
        siteEquip.setName("Site3_Equipment6");
        model.add(siteEquip);


        siteEquip.setSiteId(4);
        siteEquip.setEquipmentId(1);
        siteEquip.setName("Site4_Equipment1");
        model.add(siteEquip);

        siteEquip.setEquipmentId(2);
        siteEquip.setName("Site4_Equipment2");
        model.add(siteEquip);

        siteEquip.setEquipmentId(7);
        siteEquip.setName("Site4_Equipment7");
        model.add(siteEquip);


        siteEquip.setSiteId(5);
        siteEquip.setEquipmentId(3);
        siteEquip.setName("Site5_Equipment3");
        model.add(siteEquip);

        siteEquip.setEquipmentId(5);
        siteEquip.setName("Site5_Equipment5");
        model.add(siteEquip);
    }


    private void populateParameterTypesData(Context context){
        ParameterTypeDbModel parameterTypeDbModel;

        parameterTypeDbModel=new ParameterTypeDbModel(context);
        if (parameterTypeDbModel.getCount()>0)return;

        for (ParameterType pt : ParameterTypesSingleton.getInstance().getParamTypes()) {

            parameterTypeDbModel.add(pt);
        }

    }

    private void populateParameterData(Context context) {
        Parameter parameter;
        ParameterDbModel model;

        model=new ParameterDbModel(context);

        if (model.getCount()>0)return;

        parameter=new Parameter("Hours Run", "Hrs", 2);
        model.add(parameter);
        parameter=new Parameter("Suction Pressure", "Psi", 2);
        model.add(parameter);
        parameter=new Parameter("Suction Temperature", "C", 2);
        model.add(parameter);
        parameter=new Parameter("Suction Superheat", "C", 1);
        model.add(parameter);
        parameter=new Parameter("Discharge Pressure", "Psi", 2);
        model.add(parameter);
        parameter=new Parameter("Discharge Temperature", "C", 1);
        model.add(parameter);
        parameter=new Parameter("Discharge Superheat", "C", 1);
        model.add(parameter);
        parameter=new Parameter("Oil Pressure", "Psi", 1);
        model.add(parameter);
        parameter=new Parameter("Oil Level", "CC", 2);
        model.add(parameter);
        parameter=new Parameter("Check McD Freezer Tunnel 1", "", 5);
        model.add(parameter);
        parameter=new Parameter("Check Conveneince freezer Tunnel 1 VS per visit", "", 5);
        model.add(parameter);
        parameter=new Parameter("Other Valve stations 2 Other per Visit", "", 5);
        model.add(parameter);
        parameter=new Parameter("Inspect Internally 1 VS per visit, replace gaskets", "", 5);
        model.add(parameter);
        parameter=new Parameter("Check 2 Room Coolers per Visit", "", 5);
        model.add(parameter);
        parameter=new Parameter("Motor Current", "Amps", 1);
        model.add(parameter);
        parameter=new Parameter("Room Temperature Controls", "", 1);
        model.add(parameter);
        parameter=new Parameter("Check Oil in HT, LT, Econ Vessels", "", 1);
        model.add(parameter);
        parameter=new Parameter("Check Freezer Level Column", "", 1);
        model.add(parameter);
        parameter=new Parameter("Drain Oil from Freezer Tunnel Evaporator (6 months)", "", 5);
        model.add(parameter);
    }

    private void populateEquipmentParamsData(Context context){
        EquipmentParamsDbModel model;

        model=new EquipmentParamsDbModel(context);

        if (model.getCount()>0)return;

        model.add(new EquipmentParameters(1, 1));
        model.add(new EquipmentParameters(1, 2));
        model.add(new EquipmentParameters(1, 3));
        model.add(new EquipmentParameters(1, 4));
        model.add(new EquipmentParameters(1, 5));
        model.add(new EquipmentParameters(2, 1));
        model.add(new EquipmentParameters(2, 2));
        model.add(new EquipmentParameters(2, 3));
        model.add(new EquipmentParameters(2, 4));
        model.add(new EquipmentParameters(2, 5));
        model.add(new EquipmentParameters(3, 1));
        model.add(new EquipmentParameters(3, 5));
        model.add(new EquipmentParameters(4, 5));
        model.add(new EquipmentParameters(5, 1));
        model.add(new EquipmentParameters(5, 2));
        model.add(new EquipmentParameters(5, 5));
        model.add(new EquipmentParameters(6, 1));
        model.add(new EquipmentParameters(6, 2));
        model.add(new EquipmentParameters(7, 5));
    }

    private void populateReportParametersData(Context context){
        ReportEquipParamsDbModel reportEquipParamsDbModel = new ReportEquipParamsDbModel(context);
        ReportEquipParams reportEquipParams;

        if (reportEquipParamsDbModel.getCount()>0) return;

        reportEquipParams = new ReportEquipParams(1, 1, 1, "111");
        reportEquipParamsDbModel.add(reportEquipParams);
        reportEquipParams = new ReportEquipParams(1, 1, 2, "112");
        reportEquipParamsDbModel.add(reportEquipParams);
        reportEquipParams = new ReportEquipParams(1, 1, 5, "115");
        reportEquipParamsDbModel.add(reportEquipParams);

        reportEquipParams = new ReportEquipParams(1, 2, 1, "121");
        reportEquipParamsDbModel.add(reportEquipParams);
        reportEquipParams = new ReportEquipParams(1, 2, 3, "123");
        reportEquipParamsDbModel.add(reportEquipParams);
        reportEquipParams = new ReportEquipParams(1, 2, 5, "125");
        reportEquipParamsDbModel.add(reportEquipParams);

        reportEquipParams = new ReportEquipParams(2, 1, 1, "211");
        reportEquipParamsDbModel.add(reportEquipParams);
        reportEquipParams = new ReportEquipParams(2, 1, 2, "212");
        reportEquipParamsDbModel.add(reportEquipParams);
        reportEquipParams = new ReportEquipParams(2, 1, 5, "215");
        reportEquipParamsDbModel.add(reportEquipParams);

        reportEquipParams = new ReportEquipParams(2, 2, 1, "221");
        reportEquipParamsDbModel.add(reportEquipParams);
        reportEquipParams = new ReportEquipParams(2, 2, 3, "223");
        reportEquipParamsDbModel.add(reportEquipParams);
        reportEquipParams = new ReportEquipParams(2, 2, 5, "225");
        reportEquipParamsDbModel.add(reportEquipParams);

        reportEquipParams = new ReportEquipParams(3, 3, 1, "331");
        reportEquipParamsDbModel.add(reportEquipParams);
        reportEquipParams = new ReportEquipParams(3, 3, 5, "335");
        reportEquipParamsDbModel.add(reportEquipParams);

        reportEquipParams = new ReportEquipParams(3, 4, 5, "345");
        reportEquipParamsDbModel.add(reportEquipParams);

        reportEquipParams = new ReportEquipParams(3, 5, 1, "351");
        reportEquipParamsDbModel.add(reportEquipParams);
        reportEquipParams = new ReportEquipParams(3, 5, 2, "352");
        reportEquipParamsDbModel.add(reportEquipParams);
        reportEquipParams = new ReportEquipParams(3, 5, 5, "355");
        reportEquipParamsDbModel.add(reportEquipParams);

        reportEquipParams = new ReportEquipParams(4, 6, 1, "461");
        reportEquipParamsDbModel.add(reportEquipParams);
        reportEquipParams = new ReportEquipParams(4, 6, 2, "462");
        reportEquipParamsDbModel.add(reportEquipParams);

    }

    private void popEmailData(Context context){
        EmailDbModel m;
        Email e;

        m=new EmailDbModel(context);

        if (m.getCount()>0) return;

        e=new Email("gavinwilliams_69@hotmail.com", true);
        m.add(e);

        e=new Email("g@f.com", false);
        m.add(e);

        e=new Email("mrsl@codebehind.org", true);
        m.add(e);

    }

}