package org.codebehind.mrslmaintenance.Models;

import android.content.ContentValues;
import android.content.Context;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.EquipmentParameters;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModelBase;
import org.codebehind.mrslmaintenance.StaticConstants;

/**
 * Created by root on 10/11/15.
 */
public class EquipmentParamsDbModel extends DbAbstractModelBase {

    public static final String TABLE="EquipmentParameters";
    public static final String[] FIELDS =  new String[]{"_id", "EquipmentId", "ParameterId"};
    public static final int ID=0, EQUIPMENT_ID=1, PARAMETER_ID=2;

    public EquipmentParamsDbModel(Context context){
        super(context, TABLE);
    }

    public int add(EquipmentParameters equipmentParameters){
        ContentValues contentValues;

        if (equipmentParameters==null) return StaticConstants.BAD_DB;
        contentValues= new ContentValues();
        contentValues.put(FIELDS[EQUIPMENT_ID], equipmentParameters.getEquipmentId());
        contentValues.put(FIELDS[PARAMETER_ID], equipmentParameters.getParameterId());
        return (int) DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE, null, contentValues);
    }

}
