package org.codebehind.mrslmaintenance.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.StaticConstants;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Gavin on 25/08/2015.
 */
public class ReportDbModel {

    public static final String TABLE="report";
    private static final String[] FIELDS = new String[]{"_id", "ts","del", "siteId" };
    public static final int ID=0, SITEID=3, TS=1, DEL=2;
    protected Context _context;
    public ReportDbModel(Context context) {
        _context=context;
    }

    public int add(Report rep) {
        if (rep==null) return StaticConstants.BAD_DB;
        ContentValues v= new ContentValues();
        v.put(FIELDS[TS], new Date().getTime());
        v.put(FIELDS[DEL], false);
        v.put(FIELDS[SITEID], rep.getSiteId());
        return (int) DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE, null, v);
    }
    public Cursor getAll(){
        return DatabaseHelper.getInstance(_context).getReadableDatabase().query(TABLE, FIELDS,null,null,null,null,null);
    }
}