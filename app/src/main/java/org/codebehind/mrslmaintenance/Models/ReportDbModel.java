package org.codebehind.mrslmaintenance.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModelBase;
import org.codebehind.mrslmaintenance.StaticConstants;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Gavin on 25/08/2015.
 */
public class ReportDbModel extends DbAbstractModelBase {

    public static final String TABLE="Report";
    public static final String[] FIELDS = new String[]{"_id", "Timestamp","Deleted", "SiteId", "EngineerName"};
    public static final int ID=0,  TIMESTAMP=1, DELETED=2, SITEID=3, ENGINEER_NAME=4;
    private static final int SITE_NAME=5; // this is internal only and is used for the join to site id
    ArrayList<Report> _list;

    public ReportDbModel(Context context) {
        super(context, TABLE);
        _list=new ArrayList<>();
        getAll();
    }

    public int add(Report rep) {
        if (rep==null) return StaticConstants.BAD_DB;
        int dbRetVal = -1;

        ContentValues v= new ContentValues();

        v.put(FIELDS[TIMESTAMP], new Date().getTime());
        v.put(FIELDS[DELETED], false);
        v.put(FIELDS[SITEID], rep.getSiteId());
        v.put(FIELDS[ENGINEER_NAME], rep.getEngineerName());

        dbRetVal = (int)DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE, null, v);

        if (dbRetVal>0) _list.add(rep);

        return dbRetVal;
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
            report = new Report(c.getInt(ID), c.getInt(SITEID), c.getString(ENGINEER_NAME), null, new Date(c.getLong(TIMESTAMP) * 1000));
            report.setSiteName(c.getString(SITE_NAME));
            _list.add(report);
            c.moveToNext();
        }
        return _list;
    }
}