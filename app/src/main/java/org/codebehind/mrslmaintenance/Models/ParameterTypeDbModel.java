package org.codebehind.mrslmaintenance.Models;

import android.content.ContentValues;
import android.content.Context;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.ParameterType;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModelBase;
import org.codebehind.mrslmaintenance.StaticConstants;

/**
 * Created by root on 17/11/15.
 */
public class ParameterTypeDbModel extends DbAbstractModelBase {
    public static final String TABLE="ParameterType";
    public static final String[] FIELDS =  new String[]{"_id", "Name"};
    public static final int ID=0, NAME=1;

    public ParameterTypeDbModel(Context context){
        super(context, TABLE);
    }

    public int add(ParameterType parameterType){
        ContentValues contentValues;

        if (parameterType==null) return StaticConstants.BAD_DB;
        contentValues= new ContentValues();
        contentValues.put(FIELDS[NAME], parameterType.getName());
        return (int) DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE, null, contentValues);
    }
}
