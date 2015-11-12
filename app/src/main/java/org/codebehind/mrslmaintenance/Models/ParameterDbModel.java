package org.codebehind.mrslmaintenance.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

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

    public static final String TABLE="Parameter";
    public static final String[] FIELDS =  new String[]{"_id", "Name", "Type", "Timestamp", "Deleted"};
    public static final int ID=0, NAME=1, TYPE=2, TIMESTAMP=3, DELETED=4;

    public ParameterDbModel(Context context){
        super(context, TABLE);
    }

    public int add(Parameter parameter){
        ContentValues contentValues;

        if (parameter==null) return StaticConstants.BAD_DB;
        contentValues= new ContentValues();
        contentValues.put(FIELDS[NAME], parameter.getName());
        contentValues.put(FIELDS[TYPE], parameter.getType());
        contentValues.put(FIELDS[TIMESTAMP], new Date().getTime());
        return (int) DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE, null, contentValues);
    }

    public ArrayList<Parameter> getParameters(int equipmentId){
        ArrayList<Parameter> list = new ArrayList<>();

        String query = "select "
                +"p."+FIELDS[ID]+", "
                +"p."+FIELDS[NAME]+", "
                +"p."+FIELDS[TYPE]+", "
                +"p."+FIELDS[TIMESTAMP]
                +" from " + TABLE + " p"
                +" join " + EquipmentParamsDbModel.TABLE + " ep on p."+FIELDS[ID]+" = ep."+EquipmentParamsDbModel.FIELDS[EquipmentParamsDbModel.PARAMETER_ID]
                +" where ep."+EquipmentParamsDbModel.FIELDS[EquipmentParamsDbModel.EQUIPMENT_ID]+"="+equipmentId;

        Cursor c= DatabaseHelper.getInstance(_context).getReadableDatabase().rawQuery(query, null);
        c.moveToFirst();

        while(c.isAfterLast()==false){

            Parameter parameter=new Parameter(c.getInt(ID), c.getString(NAME), c.getString(TYPE));

            list.add(parameter);
            c.moveToNext();
        }
        return list;
    }
}
