package org.codebehind.mrslmaintenance.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.Entities.ReportEquipmentParameters;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModelBase;
import org.codebehind.mrslmaintenance.StaticConstants;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by root on 11/11/15.
 */
public class ReportEquipmentParametersDbModel extends DbAbstractModelBase {

    public static final String TABLE="ReportEquipmentParameters";
    public static final String[] FIELDS =  new String[]{"_id", "ReportId", "EquipmentId", "ParameterId", "Value", "Timestamp", "Deleted"};
    public static final int ID=0, REPORT_ID=1, EQUIPMENT_ID=2, PARAMETER_ID=3, VALUE=4, TIMESTAMP=5, DELETED=6;
    private static final int PARAMETER_NAME=7, PARAMETER_TYPE=8;

    public ReportEquipmentParametersDbModel(Context context) {
        super(context, TABLE);
    }

    public int add(ReportEquipmentParameters reportEquipmentParameters){
        ContentValues contentValues;

        if (reportEquipmentParameters==null) return StaticConstants.BAD_DB;
        contentValues= new ContentValues();
        contentValues.put(FIELDS[REPORT_ID], reportEquipmentParameters.getReportId());
        contentValues.put(FIELDS[EQUIPMENT_ID], reportEquipmentParameters.getEquipmentId());
        contentValues.put(FIELDS[PARAMETER_ID], reportEquipmentParameters.getParameterId());
        contentValues.put(FIELDS[VALUE], reportEquipmentParameters.getValue());
        contentValues.put(FIELDS[TIMESTAMP], new Date().getTime());

        return (int) DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE, null, contentValues);
    }

    public ArrayList<ReportEquipmentParameters> getReportEquipmentParameters(int reportId, int equipmentId){
        ArrayList<ReportEquipmentParameters> list = new ArrayList<>();

        String query = "select "
                +"rep."+FIELDS[ID]+", "
                +"rep."+FIELDS[REPORT_ID]+", "
                +"rep."+FIELDS[EQUIPMENT_ID]+", "
                +"rep."+FIELDS[PARAMETER_ID]+", "
                +"rep."+FIELDS[VALUE]+", "
                +"rep."+FIELDS[TIMESTAMP]+", "
                +"rep."+FIELDS[DELETED]+", "
                +"p."+ParameterDbModel.FIELDS[ParameterDbModel.NAME]+", "
                +"p."+ParameterDbModel.FIELDS[ParameterDbModel.TYPE]
                +" from " + TABLE + " rep"
                +" join " + ParameterDbModel.TABLE + " p on rep."+FIELDS[PARAMETER_ID]+" = p."+ParameterDbModel.FIELDS[ParameterDbModel.ID]
                +" where rep."+FIELDS[REPORT_ID]+"="+reportId
                +" and rep."+FIELDS[EQUIPMENT_ID]+"="+equipmentId;

        Cursor cursor= DatabaseHelper.getInstance(_context).getReadableDatabase().rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast()==false){
            Parameter parameter = new Parameter(cursor.getString(PARAMETER_NAME), cursor.getString(PARAMETER_TYPE));
            ReportEquipmentParameters reportEquipmentParameters=new ReportEquipmentParameters(cursor.getInt(ID), cursor.getInt(cursor.getInt(REPORT_ID)), cursor.getInt(EQUIPMENT_ID)
                    , cursor.getInt(PARAMETER_ID), cursor.getString(VALUE), parameter);

            list.add(reportEquipmentParameters);
            cursor.moveToNext();
        }
        return list;
    }
}
