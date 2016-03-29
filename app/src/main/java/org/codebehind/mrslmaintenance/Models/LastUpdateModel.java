package org.codebehind.mrslmaintenance.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.LastUpdate;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModelBase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by root on 29/03/16.
 */
public class LastUpdateModel extends DbAbstractModelBase {
    public static final String TABLE="LastUpdate";
    public static final String[] FIELDS = new String[]{"_id", "timestamp", "uuid" };
    public static final int ID=0, TIMESTAMP=1,UUID=2;
    private static final String LOG_TAG="LastUpdateModel";

    public LastUpdateModel(Context context){
        super(context, TABLE);
    }
    public int add(LastUpdate lastUpdate) {
        ContentValues contentValues=new ContentValues();

        if (lastUpdate==null) {
            Log.wtf(LOG_TAG, "add: The lastUpdate argument is null.");
            return -1;
        }
        contentValues.put(FIELDS[TIMESTAMP], lastUpdate.getTimestamp().getTime());
        contentValues.put(FIELDS[UUID], lastUpdate.getUUID().toString());

        return(int) DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE, null, contentValues);
    }
    public LastUpdate getLastUpdate(){
        String query = "select "
                +"s."+FIELDS[ID]+", "
                +"s."+FIELDS[TIMESTAMP]+", "
                +"s."+FIELDS[UUID]
                +" from " + TABLE + " s"
                +" order by s."+FIELDS[ID]+" desc";

        Cursor c=DatabaseHelper.getInstance(_context).getReadableDatabase().rawQuery(query, null);
        c.moveToFirst();
        if (c.isAfterLast()==false) return null;
        return new LastUpdate(c.getInt(ID), new Date(c.getLong(TIMESTAMP)), java.util.UUID.fromString(c.getString(UUID)));
    }
}
