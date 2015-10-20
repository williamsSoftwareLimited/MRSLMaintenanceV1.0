package org.codebehind.mrslmaintenance.Models;

import org.codebehind.mrslmaintenance.Entities.ReportEquipmentParameters;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Gavin on 29/01/2015.
 */
public class ReportEquipmentParametersModel {

    // this has a dependent the EquipmentModel
    public ReportEquipmentParametersModel(){}

    public ArrayList<ReportEquipmentParameters>getReportEquipmentParameters(UUID reportId, UUID equipmentId){
        // the data will change constantly so call here.
        // this needs to get the data from equipmentId->EquipmentParameter->Parameter and correspond this
        // to the entries in the ReportEquipmentParameters
        ArrayList<ReportEquipmentParameters> list;
        list=new ArrayList<ReportEquipmentParameters>();

        // create some fake data for now but really need to add database support to see how this fits together


        return list;
    }
}
