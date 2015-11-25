package org.codebehind.mrslmaintenance.Singletons;

import android.content.Context;

import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModel;
import org.codebehind.mrslmaintenance.Models.EquipmentDbModel;
import org.codebehind.mrslmaintenance.Models.ParameterDbModel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by root on 24/11/15.
 */
public class ReportSingleton {
    // This will be a singleton for persistence between layers
    private static ReportSingleton _instance;
    private Report _report;
    private Equipment _equipment; // The Equipment that's predominant

    public static ReportSingleton getInstance(){
        if (_instance==null) _instance=new ReportSingleton();
        return _instance;
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

    // This is a convenience method but it's not in best practices
    public void initializeReport(Context context, int id, int siteId, String engineerName,  Date reportDate){
        ArrayList<String> siteParameters;
        DbAbstractModel<Equipment> equipmentModel;
        ArrayList<Equipment> equipmentList;
        ParameterDbModel parameterModel;

        siteParameters = new ArrayList<String>();
        siteParameters.add(""+siteId);

        equipmentModel=new EquipmentDbModel(context);
        equipmentList = equipmentModel.getList(siteParameters);

        _report=new Report(id, siteId, engineerName, equipmentList, reportDate);

        parameterModel=new ParameterDbModel(context);

        for (Equipment equipment: equipmentList) {
            equipment.setParameterList(parameterModel.getParameters(equipment.getId()));
        }
    }
}
