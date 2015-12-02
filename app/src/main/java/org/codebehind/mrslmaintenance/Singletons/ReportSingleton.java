package org.codebehind.mrslmaintenance.Singletons;

import android.content.Context;
import android.util.Log;

import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Entities.ReportEquipmentParameters;
import org.codebehind.mrslmaintenance.Models.EquipmentDbModel;
import org.codebehind.mrslmaintenance.Models.ParameterDbModel;
import org.codebehind.mrslmaintenance.Models.ReportDbModel;
import org.codebehind.mrslmaintenance.Models.ReportEquipmentParametersDbModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by root on 24/11/15.
 */
public class ReportSingleton {
    // This will be a singleton for persistence between layers
    private static final String LOG_TAG="ReportSingleton";
    private static final String NEW_REPORT="New Report";
    private static ReportSingleton _instance;
    private Report _report;
    private Equipment _equipment; // The Equipment that's predominant
    private String _title;

    public static ReportSingleton getInstance(){
        if (_instance==null) _instance=new ReportSingleton();
        return _instance;
    }

    public void setReport(Report report){
        _report=report;
    }

    public Report getReport(){
        return _report;
    }

    public void setEquipment(Equipment equipment){
        _equipment=equipment;
    }

    public Equipment getEquipment(){
        return _equipment;
    }

    public void setSiteId(Context context, int siteId){

        _report.setSiteId(siteId);
        _report.setEquipmentList(getEquipmentList(context, siteId));
    }

    public String getTitle(){
        return _title;
    }

    public void setTitle(String title){
        _title=title;
    }

    private ReportSingleton(){}

    public void saveReport(Context context){
        ReportDbModel reportDbModel;
        ReportEquipmentParametersDbModel reportEquipmentParametersDbModel;
        ReportEquipmentParameters reportEquipmentParameters;
        int reportId, rowsUpdated;

        reportDbModel=new ReportDbModel(context);
        reportEquipmentParametersDbModel=new ReportEquipmentParametersDbModel(context);

        if (_report.getId()==-1) {
            reportId = reportDbModel.add(getReport());
            _report.setId(reportId);
            Log.d(LOG_TAG,"saveReport: Adding a new report, with reportId="+reportId);
        }else{
            reportId=_report.getId();
            rowsUpdated = reportDbModel.update(getReport());
            Log.d(LOG_TAG, "saveReport: Updating a report, with reportId=" + reportId+", rows update="+rowsUpdated);
        }

        for(Equipment equipment:_report.getEquipmentList()){ // Loop through all the equipments

            for (Parameter parameter: equipment.getParameterList()){ // Loop through all the parameters

                // try to update first if comes back as no rows (0) then add instead. NB id there's more than one row than there's a problem!
                reportEquipmentParameters=new ReportEquipmentParameters( reportId, equipment.getId(), parameter.getId(), parameter.getNewValue());
                rowsUpdated=reportEquipmentParametersDbModel.update(reportEquipmentParameters);

                if (rowsUpdated==0) reportEquipmentParametersDbModel.add(reportEquipmentParameters);
            }
        }
    }

    public Report initializeEquipmentList(Context context){
        EquipmentDbModel equipmentDbModel;
        ArrayList<Equipment> equipmentList;

        equipmentDbModel=new EquipmentDbModel(context);
        equipmentList=equipmentDbModel.getEquipmentListForReport(_report.getId());

        _report.setEquipmentList(equipmentList);
        return _report;
    }

    // This is a convenience method but it's not in best practices
    public Report initializeReport(Context context, int id, int siteId, String engineerName,  String reportDateAsString){
        Date reportDate;

        try {
            reportDate = DateFormat.getDateInstance().parse(reportDateAsString);
        }catch(ParseException parseException){
            reportDate=new Date();
        }

        _report=new Report(id, siteId, engineerName, getEquipmentList(context, siteId), reportDate);

        return _report;
    }

    public void clearReport(){

        setReport(null);
        setTitle(NEW_REPORT);
    }

    private ArrayList<Equipment>  getEquipmentList(Context context, int siteId ){
        EquipmentDbModel equipmentModel;
        ArrayList<Equipment> equipmentList;
        ParameterDbModel parameterModel;

        equipmentModel=new EquipmentDbModel(context);
        equipmentList = equipmentModel.getList(siteId);

        parameterModel=new ParameterDbModel(context);

        for (Equipment equipment: equipmentList) {
            equipment.setParameterList(parameterModel.getNewParameters(equipment.getId()));
        }
        return equipmentList;
    }
}
