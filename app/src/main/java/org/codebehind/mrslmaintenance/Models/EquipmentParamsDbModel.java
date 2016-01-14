package org.codebehind.mrslmaintenance.Models;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.EquipmentParameters;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModelBase;
import org.codebehind.mrslmaintenance.StaticConstants;

/**
 * Created by root on 10/11/15.
 */
public class EquipmentParamsDbModel extends DbAbstractModelBase {

    private static final String LOG_TAG="EquipmentParamsDbModel";
    public static final String TABLE="EquipmentParameters";
    public static final String[] FIELDS =  new String[]{"_id", "EquipmentId", "ParameterId"};
    public static final int ID=0, EQUIPMENT_ID=1, PARAMETER_ID=2;

    public EquipmentParamsDbModel(Context context){
        super(context, TABLE);
    }

    public int add(EquipmentParameters equipmentParameters){
        ContentValues contentValues;
        int EquipParamId;

        if (equipmentParameters==null) {

            Log.wtf(LOG_TAG, "add: violation equipmentParameters arg is null.");
            return -1;
        }

        if (equipmentParameters.getId()>0) {

            Log.wtf(LOG_TAG, "add: violation equipmentParametersId arg > 0. This may not be a new equipParam.");
            return -1;
        }

        contentValues=mapContentVals(equipmentParameters);

        EquipParamId=(int) DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE, null, contentValues);

        if (EquipParamId<0) {

            Log.wtf(LOG_TAG, "add: violation equipmentParametersId < 0. Probably not added to the Db.");
        }

        return EquipParamId;
    }

    public int update(EquipmentParameters equipParams){
        ContentValues contentValues;
        int rowCount;
        String whereClause;

        if (equipParams==null) {

            Log.wtf(LOG_TAG, "update: violation equipmentParameters arg is null.");
            return -1;
        }

        if (equipParams.getId()>0) {

            Log.wtf(LOG_TAG, "update: violation equipmentParametersId arg > 0. This may not be a new equipParam.");
            return -1;
        }

        whereClause=FIELDS[ID]+"="+equipParams.getId();

        contentValues=mapContentVals(equipParams);

        rowCount=DatabaseHelper.getInstance(_context).getWritableDatabase().update(TABLE, contentValues, whereClause, null);

        return rowCount;
    }

    public int delete(int equipId, int paramId){
        // this carries out a full delete
        int rowCount;
        String whereClause;

        if (equipId<0) {

            Log.wtf(LOG_TAG, "delete: violation equipId < 0.");
            return -1;
        }

        if (paramId<0) {

            Log.wtf(LOG_TAG, "delete: violation paramId < 0.");
            return -1;
        }

        whereClause=FIELDS[EQUIPMENT_ID]+"="+equipId
                +" and "+FIELDS[PARAMETER_ID]+"="+paramId;

        rowCount=DatabaseHelper.getInstance(_context).getWritableDatabase().delete(TABLE, whereClause, null);

        return rowCount;
    }

    private ContentValues mapContentVals(EquipmentParameters equipParams){
        ContentValues contentValues;

        contentValues= new ContentValues();
        contentValues.put(FIELDS[EQUIPMENT_ID], equipParams.getEquipmentId());
        contentValues.put(FIELDS[PARAMETER_ID], equipParams.getParameterId());

        return contentValues;
    }

}
