package org.codebehind.mrslmaintenance.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Entities.ReportEquipParams;
import org.codebehind.mrslmaintenance.Entities.SiteEquipment;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModelBase;
import org.codebehind.mrslmaintenance.Models.Abstract.IReportEquipParamsModel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Gavin on 25/08/2015.
 */
public class ReportDbModel extends DbAbstractModelBase {

    public static final String TABLE="Report";
    public static final String[] FIELDS = new String[]{"_id", "Timestamp", "Deleted", "SiteId", "EngineerName"};
    public static final int ID=0,  TIMESTAMP=1, DELETED=2, SITEID=3, ENGINEER_NAME=4;
    private static final int SITE_NAME=5; // this is internal only and is used for the join to site id
    private static final String LOG_TAG="ReportDbModel";
    private ArrayList<Report> _list;
    private IReportEquipParamsModel _reportEquipParamsModel;

    public ReportDbModel(Context context, IReportEquipParamsModel reportEquipParamsModel) {
        super(context, TABLE);

        if (reportEquipParamsModel==null) Log.wtf(LOG_TAG, "construct: reportEquipParamsModel arg can't be null.");

        _reportEquipParamsModel=reportEquipParamsModel;
        _list=new ArrayList<>();
        getAll();
    }

    public int add(Report report) {
        int reportId;
        ReportEquipParams reportEquipParams;
        ContentValues contentValues;

        if (report==null) {

            Log.d(LOG_TAG, "add: The report argument is null.");
            return -1;
        }

        if (report.getId()>0){

            Log.d(LOG_TAG, "add: The report argument has an Id>0, Id=."+report.getId());
            return -1;
        }

        contentValues= new ContentValues();

        contentValues.put(FIELDS[TIMESTAMP], new Date().getTime());
        contentValues.put(FIELDS[DELETED], 0);
        contentValues.put(FIELDS[SITEID], report.getSiteId());
        contentValues.put(FIELDS[ENGINEER_NAME], report.getEngineerName());

        reportId = (int)DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE, null, contentValues);

        if (reportId<=0){

            Log.d(LOG_TAG, "add: The report wasn't successfully added to the database, " + TABLE + " table.");
            return -1;
        }

        _list.add(report);

        if (report.getSiteEquipmentList()==null || report.getSiteEquipmentList().size()==0){

            Log.d(LOG_TAG, "The report id="+reportId+", had no siteEquipment list.");
            return reportId;
        }

        for(SiteEquipment se : report.getSiteEquipmentList()){

            reportEquipParams=new ReportEquipParams(reportId, se.getEquipmentId(), -1,"");

            for(Parameter p:se.getEquipment().getParameterList()){

                reportEquipParams.setParameterId(p.getId());
                reportEquipParams.setValue(p.getNewValue());
                _reportEquipParamsModel.add(reportEquipParams);
            }
        }


        return reportId;
    }

    public int update(Report report){
        int updateCount, reportId;
        ContentValues contentValues;
        Report reportInList;
        String whereClause;
        ReportEquipParams reportEquipParams;

        if (report==null) {

            Log.d(LOG_TAG, "update: The report argument is null.");
            return 0;
        }

        if (report.getId()==-1) {

            Log.d(LOG_TAG, "update: The report has an id of -1 and is therefore new.");
            return 0;
        }

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

        reportId=report.getId();

        for(SiteEquipment se : report.getSiteEquipmentList()){

            reportEquipParams=new ReportEquipParams(reportId, se.getEquipmentId(), -1,"");

            for(Parameter p:se.getEquipment().getParameterList()){

                reportEquipParams.setParameterId(p.getId());
                reportEquipParams.setValue(p.getNewValue());
                _reportEquipParamsModel.update(reportEquipParams);
            }
        }

        return updateCount;
    }

    public Report getReport(int reportId){

        for(Report report : _list) {

            if (reportId==report.getId()) return report;
        }

        return new Report(-1,"",null,null);
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