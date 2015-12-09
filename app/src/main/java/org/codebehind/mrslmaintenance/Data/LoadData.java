package org.codebehind.mrslmaintenance.Data;

import android.content.Context;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.EquipmentParameters;
import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.Entities.ParameterType;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Entities.ReportEquipmentParameters;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Models.EquipmentDbModel;
import org.codebehind.mrslmaintenance.Models.EquipmentParamsDbModel;
import org.codebehind.mrslmaintenance.Models.ParameterDbModel;
import org.codebehind.mrslmaintenance.Models.ParameterTypeDbModel;
import org.codebehind.mrslmaintenance.Models.ReportDbModel;
import org.codebehind.mrslmaintenance.Models.ReportEquipmentParametersDbModel;
import org.codebehind.mrslmaintenance.Models.SiteDbModel;
import org.codebehind.mrslmaintenance.Singletons.ParameterTypesSingleton;

import java.util.Hashtable;

/**
 * Created by Gavin on 11/02/2015.
 */
public class LoadData {

    public void load(Context context){
        populateEquipmentData(context);
        popSiteData(context);
        populateReportData(context);
        populateParameterTypesData(context);
        populateParameterData(context);
        populateEquipmentParamsData(context);
        populateReportParametersData(context);

        DatabaseHelper.getInstance(context).close();
    }
    // ensure that this is called first
    public void popSiteData(Context c){
        SiteDbModel mod = new SiteDbModel(c);
        Site site;

        if (mod.getCount()>0) return;

        site = new Site("Mars Plant", "Outer Mongolia, Spain.");
        //s.setImageId(1); // make sure the images exist
        mod.add(site);

        site = new Site("Alien Plant", "Lost in space.");
        //s.setImageId(1); // make sure the images exist
        mod.add(site);

        site = new Site("Superman Plant", "Banana land.");
        //s.setImageId(1); // make sure the images exist
        mod.add(site);

        site = new Site("Kojak Plant", "Who loves ya.");
        //s.setImageId(1); // make sure the images exist
        mod.add(site);

        site = new Site("Dylan the Hippy Plant", "Magic Roundabout.");
        //s.setImageId(1); // make sure the images exist
        mod.add(site);
    }

    public void populateReportData(Context c) {

        ReportDbModel mod = new ReportDbModel(c);
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

        equipment=new Equipment(1, "COMPRESSOR - LT1", 1);
        model.add(equipment);

        equipment=new Equipment(1, "COMPRESSOR - HT1", 2);
        model.add(equipment);

        equipment=new Equipment(1, "VALVE STATION", 1);
        model.add(equipment);

        equipment=new Equipment(1, "Coolers in Rooms", 1);
        model.add(equipment);

        equipment=new Equipment(1, "Oil", 1);
        model.add(equipment);

        equipment=new Equipment(1, "Magic Oil", 1);
        model.add(equipment);

        equipment=new Equipment(1, "Chocolate makers", 1);
        model.add(equipment);

        equipment=new Equipment(2, "COMPRESSOR - LT1", 1);
        model.add(equipment);

        equipment=new Equipment(2, "COMPRESSOR - HT1", 2);
        model.add(equipment);

        equipment=new Equipment(2, "VALVE STATION", 1);
        model.add(equipment);

        equipment=new Equipment(2, "Coolers in Rooms", 1);
        model.add(equipment);

        equipment=new Equipment(2, "Oil", 1);
        model.add(equipment);

        equipment=new Equipment(3, "COMPRESSOR - LT1", 1);
        model.add(equipment);

        equipment=new Equipment(3, "COMPRESSOR - HT1", 2);
        model.add(equipment);

        equipment=new Equipment(3, "VALVE STATION", 1);
        model.add(equipment);

        equipment=new Equipment(4, "Coolers in Rooms", 1);
        model.add(equipment);

        equipment=new Equipment(4, "Oil", 1);
        model.add(equipment);
    }

    private void populateParameterTypesData(Context context){
        ParameterType parameterType;
        ParameterTypeDbModel parameterTypeDbModel;
        Hashtable<Integer, String> parameterTypesHashTable;

        parameterTypeDbModel=new ParameterTypeDbModel(context);
        parameterTypesHashTable = ParameterTypesSingleton.getInstance().getParameterTypeList();

        if (parameterTypeDbModel.getCount()>0)return;

        for (int i=1;i<=parameterTypesHashTable.values().size();i++) {
            parameterType = new ParameterType(i, parameterTypesHashTable.get(i));
            parameterTypeDbModel.add(parameterType);
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
        ReportEquipmentParametersDbModel reportEquipmentParametersDbModel = new ReportEquipmentParametersDbModel(context);
        ReportEquipmentParameters reportEquipmentParameters;

        if (reportEquipmentParametersDbModel.getCount()>0) return;

        reportEquipmentParameters = new ReportEquipmentParameters(1, 1, 1, "111");
        reportEquipmentParametersDbModel.add(reportEquipmentParameters);
        reportEquipmentParameters = new ReportEquipmentParameters(1, 1, 2, "112");
        reportEquipmentParametersDbModel.add(reportEquipmentParameters);
        reportEquipmentParameters = new ReportEquipmentParameters(1, 1, 5, "115");
        reportEquipmentParametersDbModel.add(reportEquipmentParameters);

        reportEquipmentParameters = new ReportEquipmentParameters(1, 2, 1, "121");
        reportEquipmentParametersDbModel.add(reportEquipmentParameters);
        reportEquipmentParameters = new ReportEquipmentParameters(1, 2, 3, "123");
        reportEquipmentParametersDbModel.add(reportEquipmentParameters);
        reportEquipmentParameters = new ReportEquipmentParameters(1, 2, 5, "125");
        reportEquipmentParametersDbModel.add(reportEquipmentParameters);

        reportEquipmentParameters = new ReportEquipmentParameters(2, 1, 1, "211");
        reportEquipmentParametersDbModel.add(reportEquipmentParameters);
        reportEquipmentParameters = new ReportEquipmentParameters(2, 1, 2, "212");
        reportEquipmentParametersDbModel.add(reportEquipmentParameters);
        reportEquipmentParameters = new ReportEquipmentParameters(2, 1, 5, "215");
        reportEquipmentParametersDbModel.add(reportEquipmentParameters);

        reportEquipmentParameters = new ReportEquipmentParameters(2, 2, 1, "221");
        reportEquipmentParametersDbModel.add(reportEquipmentParameters);
        reportEquipmentParameters = new ReportEquipmentParameters(2, 2, 3, "223");
        reportEquipmentParametersDbModel.add(reportEquipmentParameters);
        reportEquipmentParameters = new ReportEquipmentParameters(2, 2, 5, "225");
        reportEquipmentParametersDbModel.add(reportEquipmentParameters);

        reportEquipmentParameters = new ReportEquipmentParameters(3, 3, 1, "331");
        reportEquipmentParametersDbModel.add(reportEquipmentParameters);
        reportEquipmentParameters = new ReportEquipmentParameters(3, 3, 5, "335");
        reportEquipmentParametersDbModel.add(reportEquipmentParameters);

        reportEquipmentParameters = new ReportEquipmentParameters(3, 4, 5, "345");
        reportEquipmentParametersDbModel.add(reportEquipmentParameters);

        reportEquipmentParameters = new ReportEquipmentParameters(3, 5, 1, "351");
        reportEquipmentParametersDbModel.add(reportEquipmentParameters);
        reportEquipmentParameters = new ReportEquipmentParameters(3, 5, 2, "352");
        reportEquipmentParametersDbModel.add(reportEquipmentParameters);
        reportEquipmentParameters = new ReportEquipmentParameters(3, 5, 5, "355");
        reportEquipmentParametersDbModel.add(reportEquipmentParameters);

        reportEquipmentParameters = new ReportEquipmentParameters(4, 6, 1, "461");
        reportEquipmentParametersDbModel.add(reportEquipmentParameters);
        reportEquipmentParameters = new ReportEquipmentParameters(4, 6, 2, "462");
        reportEquipmentParametersDbModel.add(reportEquipmentParameters);

    }
}