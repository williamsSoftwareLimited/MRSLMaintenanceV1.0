package org.codebehind.mrslmaintenance.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

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
    public static final String[] FIELDS = new String[]{"_id", "ts","del", "siteId", "engineerName"};
    public static final int ID=0,  TS=1, DEL=2, SITEID=3, ENGINEER_NAME=4;
    private static final int SITE_NAME=5; // this is internal only and is used for the join to site id
    protected Context _context;
    ArrayList<Report> _list;

    public ReportDbModel(Context context) {
        _context=context;
        _list=new ArrayList<>();
        getAll();
    }

    public int add(Report rep) {
        if (rep==null) return StaticConstants.BAD_DB;
        int dbRetVal = -1;

        ContentValues v= new ContentValues();

        v.put(FIELDS[TS], new Date().getTime());
        v.put(FIELDS[DEL], false);
        v.put(FIELDS[SITEID], rep.getSiteId());
        v.put(FIELDS[ENGINEER_NAME], rep.getEngineerName());

        dbRetVal = (int)DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE, null, v);

        if (dbRetVal>0) _list.add(rep);

        return dbRetVal;
    }

    public Report getReport(int id){
        Report rep = new Report();
        rep.setId(-1);
        for(Report r : _list) if (id==r.getId()) rep = r;
        return rep;
    }

    public ArrayList<Report> getAll(){
        String query = "select "
                +"r."+FIELDS[ID]+", "
                +"r."+FIELDS[TS]+", "
                +"r."+FIELDS[DEL]+", "
                +"r."+FIELDS[SITEID]+", "
                +"r."+FIELDS[ENGINEER_NAME]+", "
                +"s."+SiteDbModel.FIELDS[SiteDbModel.NAME]
                +" from " + TABLE + " r"
                +" join " + SiteDbModel.TABLE + " s on r."+FIELDS[SITEID]+" = s."+SiteDbModel.FIELDS[SiteDbModel.ID ];

        Cursor c= DatabaseHelper.getInstance(_context).getReadableDatabase().rawQuery(query, null);

        Report report;
        c.moveToFirst();

        while(c.isAfterLast()==false){
            report = new Report();
            report.setId(c.getInt(ID));
            report.setReportDate(new Date(c.getLong(TS) * 1000));
            report.setSiteId(c.getInt(SITEID));
            report.setEngineerName(c.getString(ENGINEER_NAME));
            report.setSiteName(c.getString(SITE_NAME));
            _list.add(report);
            c.moveToNext();
        }
        return _list;
    }
    public void close() {
        DatabaseHelper.getInstance(_context).getWritableDatabase().close();
    }
}