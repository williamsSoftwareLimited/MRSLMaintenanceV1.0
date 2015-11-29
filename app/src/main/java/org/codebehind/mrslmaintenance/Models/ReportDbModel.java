package org.codebehind.mrslmaintenance.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModelBase;
import org.codebehind.mrslmaintenance.StaticConstants;

import java.nio.channels.FileLockInterruptionException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Gavin on 25/08/2015.
 */
public class ReportDbModel extends DbAbstractModelBase {

    public static final String TABLE="Report";
    public static final String[] FIELDS = new String[]{"_id", "Timestamp", "Deleted", "SiteId", "EngineerName"};
    public static final int ID=0,  TIMESTAMP=1, DELETED=2, SITEID=3, ENGINEER_NAME=4;
    private static final int SITE_NAME=5; // this is internal only and is used for the join to site id
    private ArrayList<Report> _list;
    private static final String LOG_TAG="ReportDbModel";

    public ReportDbModel(Context context) {
        super(context, TABLE);
        _list=new ArrayList<>();
        getAll();
    }

    public int add(Report report) {
        int reportId;
        ContentValues contentValues;

        if (report==null) {
            Log.d(LOG_TAG, "add: The report argument is null.");
            return 0;
        }

        contentValues= new ContentValues();

        contentValues.put(FIELDS[TIMESTAMP], new Date().getTime());
        contentValues.put(FIELDS[DELETED], false);
        contentValues.put(FIELDS[SITEID], report.getSiteId());
        contentValues.put(FIELDS[ENGINEER_NAME], report.getEngineerName());

        reportId = (int)DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE, null, contentValues);

        if (reportId>0) _list.add(report);
        else Log.d(LOG_TAG, "add: The report wasn't successfully added to the database, " + TABLE + " table.");

        return reportId;
    }

    public int update(Report report){
        int updateCount;
        ContentValues contentValues;
        Report reportInList;
        String whereClause;

        if (report==null) {
            Log.d(LOG_TAG, "update: The report argument is null.");
            return 0;
        }
        if (report.getId()==-1) return 0;

        reportInList=getReport(report.getId());

        if (reportInList==null) {
            Log.e(LOG_TAG, "update: There's been a reportId (not equal to -1) but no Report in the list.");
            return 0; // Something seriously bad has happened here
        }

        contentValues= new ContentValues();

        contentValues.put(FIELDS[TIMESTAMP],new Date().getTime());
        contentValues.put(FIELDS[DELETED], report.getDeleted());
        contentValues.put(FIELDS[SITEID], report.getSiteId());
        contentValues.put(FIELDS[ENGINEER_NAME], report.getEngineerName());

        whereClause= FIELDS[ID]+"="+report.getId();
        updateCount = DatabaseHelper.getInstance(_context).getWritableDatabase().update(TABLE, contentValues, whereClause, null);


        if (updateCount>0) {
            _list.remove(reportInList);
            _list.add(report);
        }

        return updateCount;
    }

    public Report getReport(int id){
        Report rep = new Report(-1,"",null,null);
        for(Report r : _list) if (id==r.getId()) rep = r;
        return rep;
    }

    public ArrayList<Report> getAll(){
        String query = "select "
                +"r."+FIELDS[ID]+", "
                +"r."+FIELDS[TIMESTAMP]+", "
                +"r."+FIELDS[DELETED]+", "
                +"r."+FIELDS[SITEID]+", "
                +"r."+FIELDS[ENGINEER_NAME]+", "
                +"s."+SiteDbModel.FIELDS[SiteDbModel.NAME]
                +" from " + TABLE + " r"
                +" join " + SiteDbModel.TABLE + " s on r."+FIELDS[SITEID]+" = s."+SiteDbModel.FIELDS[SiteDbModel.ID ];

        Cursor c= DatabaseHelper.getInstance(_context).getReadableDatabase().rawQuery(query, null);
        _list.clear();
        Report report;
        c.moveToFirst();

        while(c.isAfterLast()==false){
            report = new Report(c.getInt(ID), c.getInt(SITEID), c.getString(ENGINEER_NAME), null, new Date(c.getLong(TIMESTAMP)));
            report.setSiteName(c.getString(SITE_NAME));
            _list.add(report);
            c.moveToNext();
        }
        return _list;
    }
}