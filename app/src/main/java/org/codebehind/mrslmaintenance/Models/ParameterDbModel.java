package org.codebehind.mrslmaintenance.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModelBase;
import org.codebehind.mrslmaintenance.StaticConstants;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by root on 10/11/15.
 */
public class ParameterDbModel extends DbAbstractModelBase {

    private static final String LOG_TAG="ParameterDbModel";
    public static final String TABLE="Parameter";
    public static final String[] FIELDS =  new String[]{"_id", "Name", "Units", "ParameterTypeId", "Timestamp", "Deleted"};
    public static final int ID=0, NAME=1, UNITS=2, PARAMETER_TYPE_ID=3, TIMESTAMP=4, DELETED=5;

    public ParameterDbModel(Context context){
        super(context, TABLE);
    }

    public int add(Parameter parameter){
        ContentValues contentValues;

        if(parameter==null) {

            Log.wtf(LOG_TAG, "add: violation param arg is null.");
            return -1;
        }
        if (parameter.getId()>0){

            Log.wtf(LOG_TAG, "add: violation paramId > 0. Is this an existing param?");
            return -1;
        }

        contentValues=mapContentVals(parameter, false);

        return (int) DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE, null, contentValues);
    }

    public int update(Parameter param){

        if(param==null) {

            Log.wtf(LOG_TAG, "update: violation param arg is null.");
            return -1;
        }

        if (param.getId()<1){

            Log.wtf(LOG_TAG, "update: violation paramId < 1.");
            return -1;
        }

        return updateWithDelete(param, false);
    }

    public int delete(Parameter param){

        if(param==null) {

            Log.wtf(LOG_TAG, "delete: violation param arg is null.");
            return -1;
        }

        if (param.getId()<1){

            Log.wtf(LOG_TAG, "delete: violation paramId < 1.");
            return -1;
        }

        return updateWithDelete(param, true);
    }

    public ArrayList<Parameter> getNewParameters(int equipmentId){
        ArrayList<Parameter> list = new ArrayList<>();

        String query = "select "
                +"p."+FIELDS[ID]+", "
                +"p."+FIELDS[NAME]+", "
                +"p."+FIELDS[UNITS]+", "
                +"p."+FIELDS[PARAMETER_TYPE_ID]+", "
                +"p."+FIELDS[TIMESTAMP]
                +" from " + TABLE + " p"
                +" join " + EquipmentParamsDbModel.TABLE + " ep on p."+FIELDS[ID]+" = ep."+EquipmentParamsDbModel.FIELDS[EquipmentParamsDbModel.PARAMETER_ID]
                +" where ep."+EquipmentParamsDbModel.FIELDS[EquipmentParamsDbModel.EQUIPMENT_ID]+"="+equipmentId;

        Cursor cursor= DatabaseHelper.getInstance(_context).getReadableDatabase().rawQuery(query, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast()==false){

            Parameter parameter=new Parameter(cursor.getInt(ID), cursor.getString(NAME), cursor.getString(UNITS), cursor.getInt(PARAMETER_TYPE_ID));

            list.add(parameter);
            cursor.moveToNext();
        }

        return list;
    }

    private ContentValues mapContentVals(Parameter param, Boolean delete){
        ContentValues contentValues;

        contentValues=new ContentValues();
        contentValues.put(FIELDS[NAME], param.getName());
        contentValues.put(FIELDS[UNITS], param.getUnits());
        contentValues.put(FIELDS[PARAMETER_TYPE_ID], param.getParameterTypeId());
        contentValues.put(FIELDS[TIMESTAMP], new Date().getTime());
        contentValues.put(FIELDS[DELETED], delete);

        return contentValues;
    }

    private int updateWithDelete(Parameter param, Boolean delete){
        ContentValues cvs;
        String whereClause;
        int rowcount;

        cvs=mapContentVals(param, delete);
        whereClause=FIELDS[ID]+"="+param.getId();

        rowcount=DatabaseHelper.getInstance(_context).getWritableDatabase().update(TABLE,cvs,whereClause,null);

        return rowcount;
    }

}
