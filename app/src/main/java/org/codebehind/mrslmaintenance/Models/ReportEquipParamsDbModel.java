package org.codebehind.mrslmaintenance.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.Entities.ReportEquipParams;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModelBase;
import org.codebehind.mrslmaintenance.Models.Abstract.IReportEquipParamsModel;
import org.codebehind.mrslmaintenance.StaticConstants;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by root on 11/11/15.
 */
public class ReportEquipParamsDbModel extends DbAbstractModelBase implements IReportEquipParamsModel {

    public static final String TABLE="ReportEquipParams";
    public static final String[] FIELDS =  new String[]{"_id", "ReportId", "SiteEquipId", "ParameterId", "Value", "Timestamp", "Deleted"};
    public static final int ID=0, REPORT_ID=1, SITE_EQUIP_ID =2, PARAMETER_ID=3, VALUE=4, TIMESTAMP=5, DELETED=6;
    private static final int PARAMETER_NAME=7, PARAMETER_TYPE=8, PARAMETER_TYPE_ID=9;

    public ReportEquipParamsDbModel(Context context) {
        super(context, TABLE);
    }

    public int add(ReportEquipParams reportEquipParams){
        ContentValues contentValues;

        if (reportEquipParams ==null) return StaticConstants.BAD_DB;
        contentValues= new ContentValues();
        contentValues.put(FIELDS[REPORT_ID], reportEquipParams.getReportId());
        contentValues.put(FIELDS[SITE_EQUIP_ID], reportEquipParams.getSiteEquipId());
        contentValues.put(FIELDS[PARAMETER_ID], reportEquipParams.getParameterId());
        contentValues.put(FIELDS[VALUE], reportEquipParams.getValue());
        contentValues.put(FIELDS[TIMESTAMP], new Date().getTime());

        return (int) DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE, null, contentValues);
    }

    public int update(ReportEquipParams reportEquipParams){
        ContentValues contentValues;
        String whereClause;
        int updateCount;

        if (reportEquipParams ==null) return StaticConstants.BAD_DB;
        if (reportEquipParams.getReportId()==-1) return 0;

        contentValues= new ContentValues();
        contentValues.put(FIELDS[VALUE], reportEquipParams.getValue());
        contentValues.put(FIELDS[TIMESTAMP], new Date().getTime());

        whereClause=FIELDS[REPORT_ID]+"="+ reportEquipParams.getReportId()+" and "
                +FIELDS[SITE_EQUIP_ID]+"="+ reportEquipParams.getSiteEquipId()+" and "
                +FIELDS[PARAMETER_ID]+"="+ reportEquipParams.getParameterId();

        updateCount=DatabaseHelper.getInstance(_context).getWritableDatabase().update(TABLE,contentValues,whereClause,null); // return's number of rows affected

        return updateCount;
    }

    public ArrayList<ReportEquipParams> getReportEquipmentParameters(int reportId, int equipmentId){
        ArrayList<ReportEquipParams> list = new ArrayList<>();

        String query = "select "
                +"rep."+FIELDS[ID]+", "
                +"rep."+FIELDS[REPORT_ID]+", "
                +"rep."+FIELDS[SITE_EQUIP_ID]+", "
                +"rep."+FIELDS[PARAMETER_ID]+", "
                +"rep."+FIELDS[VALUE]+", "
                +"rep."+FIELDS[TIMESTAMP]+", "
                +"rep."+FIELDS[DELETED]+", "
                +"p."+ParameterDbModel.FIELDS[ParameterDbModel.NAME]+", "
                +"p."+ParameterDbModel.FIELDS[ParameterDbModel.UNITS]+", "
                +"p."+ParameterDbModel.FIELDS[ParameterDbModel.PARAMETER_TYPE_ID]
                +" from " + TABLE + " rep"
                +" join " + ParameterDbModel.TABLE + " p on rep."+FIELDS[PARAMETER_ID]+" = p."+ParameterDbModel.FIELDS[ParameterDbModel.ID]
                +" where rep."+FIELDS[REPORT_ID]+"="+reportId
                +" and rep."+FIELDS[SITE_EQUIP_ID]+"="+equipmentId;

        Cursor cursor= DatabaseHelper.getInstance(_context).getReadableDatabase().rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast()==false){
            String parameterName, parameterType, value;
            int parameterTypeId, id, reportId2, equipmentId2, parameterId;
            Parameter parameter;

            id=cursor.getInt(ID);
            reportId2=cursor.getInt(REPORT_ID);
            equipmentId2=cursor.getInt(SITE_EQUIP_ID);
            parameterId=cursor.getInt(PARAMETER_ID);
            value = cursor.getString(VALUE);
            parameterName = cursor.getString(PARAMETER_NAME);
            parameterType = cursor.getString(PARAMETER_TYPE);
            parameterTypeId=cursor.getInt(PARAMETER_TYPE_ID);

            parameter = new Parameter(parameterId, parameterName, parameterType, parameterTypeId);
            parameter.setNewValue(value);

            ReportEquipParams reportEquipParams =new ReportEquipParams(id, reportId2, equipmentId2, parameterId, value, parameter);

            list.add(reportEquipParams);
            cursor.moveToNext();
        }
        return list;
    }
}
