package org.codebehind.mrslmaintenance.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.Entities.SiteEquipment;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModelBase;
import org.codebehind.mrslmaintenance.StaticConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

/**
 * Created by root on 02/11/15.
 * This is all a little pointless but it keeps it in-line with the other Db models and contains the correct statics for creation
 * This is a many to many table from the site to the equipment table
 */
public class SiteEquipmentDbModel extends DbAbstractModelBase{

    protected Context _context;
    protected ArrayList<SiteEquipment> _list;
    public static final String TABLE="SiteEquipment";
    public static final String[] FIELDS = new String[]{"_id", "siteId", "equipmentId", "name",  "timestamp", "deleted"};
    public static final int ID=0, SITE_ID=1, EQUIPMENT_ID=2, NAME=3, TIMESTAMP=4, DELETED=5;
    private static final int EQUIPMENT_NAME=DELETED+1, EQUIPMENT_IMAGE_ID=DELETED+2,
                             PARAMETER_ID=DELETED+3, PARAMETER_NAME=DELETED+4,
                             PARAMETER_UNIT=DELETED+5, PARAMETER_TYPE_ID=DELETED+6,
                             VALUE=DELETED+7;

    public SiteEquipmentDbModel(Context context) {
        super(context, TABLE);
        _list=new ArrayList<>();
    }

    public int add(SiteEquipment entity) {
        ContentValues cv;
        int dbRetNo;
        if (entity==null)return StaticConstants.BAD_DB;

        cv = new ContentValues();
        cv.put(FIELDS[SITE_ID], entity.getSiteId());
        cv.put(FIELDS[EQUIPMENT_ID], entity.getEquipmentId());
        cv.put(FIELDS[NAME], entity.getName());

        dbRetNo = (int)DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE,null,cv);
        if (dbRetNo>0) _list.add(entity);

        return dbRetNo;
    }

    public ArrayList<SiteEquipment> getSiteEquipments(int siteId) {
        ArrayList<SiteEquipment> siteEquipmentList;
        ArrayList<Parameter> parameters;
        SiteEquipment siteEquipment;
        Equipment equipment;

        ParameterDbModel parameterDbModel;

        siteEquipmentList = new ArrayList<>();

        parameterDbModel=new ParameterDbModel(_context);
        parameters=new ArrayList<>();

        String query = "select "
                +"se."+FIELDS[ID]+", "
                +"se."+FIELDS[SITE_ID]+", "
                +"se."+FIELDS[EQUIPMENT_ID]+", "
                +"se."+FIELDS[NAME]+", "
                +"se."+FIELDS[TIMESTAMP]+", "
                +"se."+FIELDS[DELETED]+", "
                +"e."+EquipmentDbModel.FIELDS[EquipmentDbModel.NAME]+", "
                +"e."+EquipmentDbModel.FIELDS[EquipmentDbModel.IMAGE_ID]+" "
                +" from " + TABLE + " se"
                +" join " + EquipmentDbModel.TABLE + " e on se."+FIELDS[EQUIPMENT_ID]+" = e."+EquipmentDbModel.FIELDS[EquipmentDbModel.ID]
                +" where se."+FIELDS[SITE_ID]+"="+siteId;

        Cursor c= DatabaseHelper.getInstance(_context).getReadableDatabase().rawQuery(query, null);
        c.moveToFirst();

        while(c.isAfterLast()==false){

            siteEquipment=new SiteEquipment(c.getInt(ID), c.getInt(SITE_ID), c.getInt(EQUIPMENT_ID), c.getString(NAME));
            equipment=new Equipment(c.getInt(EQUIPMENT_ID), c.getString(EQUIPMENT_NAME), c.getInt(EQUIPMENT_IMAGE_ID));

            parameters=parameterDbModel.getNewParameters(equipment.getId()); // this is a bit of a cheat and so much can go wrong!
            equipment.setParameterList(parameters);

            siteEquipment.setEquipment(equipment);

            siteEquipmentList.add(siteEquipment);
            c.moveToNext();
        }

        Collections.sort(siteEquipmentList);
        return siteEquipmentList;
    }

    public ArrayList<SiteEquipment>getSiteEquipmentListForReport(int reportId, int siteId){
        ArrayList<SiteEquipment> siteEquipmentList;
        SiteEquipment siteEquipment;
        Equipment equipment;
        Parameter parameter;
        Hashtable<Integer, SiteEquipment> siteEquipmentHashtable;
        int siteEquipmentId;

        String query = "select "
                +"se."+FIELDS[ID]+", "
                +"se."+FIELDS[SITE_ID]+", "
                +"se."+FIELDS[EQUIPMENT_ID]+", "
                +"se."+FIELDS[NAME]+", "
                +"se."+FIELDS[TIMESTAMP]+", "
                +"se."+FIELDS[DELETED]+", "
                +"e."+EquipmentDbModel.FIELDS[EquipmentDbModel.NAME]+", "
                +"e."+EquipmentDbModel.FIELDS[EquipmentDbModel.IMAGE_ID]+", "
                +"rep."+ ReportEquipParamsDbModel.FIELDS[ReportEquipParamsDbModel.PARAMETER_ID]+", "
                +"p."+ParameterDbModel.FIELDS[ParameterDbModel.NAME]+", "
                +"p."+ParameterDbModel.FIELDS[ParameterDbModel.UNITS]+", "
                +"p."+ParameterDbModel.FIELDS[ParameterDbModel.PARAMETER_TYPE_ID]+", "
                +"rep."+ ReportEquipParamsDbModel.FIELDS[ReportEquipParamsDbModel.VALUE]+" "
                +" from " + TABLE + " se "
                +" join " + EquipmentDbModel.TABLE+" e on se."+FIELDS[EQUIPMENT_ID]+"= e."+EquipmentDbModel.FIELDS[EquipmentDbModel.ID]
                +" join " + ReportEquipParamsDbModel.TABLE + " rep on e."+FIELDS[ID]+" = rep."+ ReportEquipParamsDbModel.FIELDS[ReportEquipParamsDbModel.EQUIPMENT_ID]
                +" join " + ParameterDbModel.TABLE + " p on rep."+ ReportEquipParamsDbModel.FIELDS[ReportEquipParamsDbModel.PARAMETER_ID]+" = p."+ParameterDbModel.FIELDS[ParameterDbModel.ID]
                +" where rep."+ ReportEquipParamsDbModel.FIELDS[ReportEquipParamsDbModel.REPORT_ID]+"="+reportId
                +" and se."+FIELDS[SITE_ID]+"="+siteId;

        Cursor c= DatabaseHelper.getInstance(_context).getReadableDatabase().rawQuery(query, null);
        c.moveToFirst();

        siteEquipmentList=new ArrayList<>();
        siteEquipmentHashtable=new Hashtable<>();

        while(c.isAfterLast()==false){

            siteEquipmentId=c.getInt(ID);

            if (!siteEquipmentHashtable.containsKey(siteEquipmentId)){

                equipment=new Equipment(c.getInt(EQUIPMENT_ID), c.getString(EQUIPMENT_NAME), c.getInt(EQUIPMENT_IMAGE_ID));
                equipment.setParameterList(new ArrayList<Parameter>());

                siteEquipment=new SiteEquipment(c.getInt(ID), c.getInt(SITE_ID), c.getInt(EQUIPMENT_ID), c.getString(NAME));
                siteEquipment.setEquipment(equipment);
                siteEquipmentHashtable.put(siteEquipmentId, siteEquipment);
            }

            parameter=new Parameter(c.getInt(PARAMETER_ID),c.getString(PARAMETER_NAME),c.getString(PARAMETER_UNIT),c.getInt(PARAMETER_TYPE_ID));
            parameter.setNewValue(c.getString(VALUE));
            siteEquipmentHashtable.get(siteEquipmentId).getEquipment().getParameterList().add(parameter);

            c.moveToNext();
        }

        for (SiteEquipment siteEquipment1 : siteEquipmentHashtable.values()) {
            siteEquipmentList.add(siteEquipment1);
        }

        Collections.sort(siteEquipmentList);
        return siteEquipmentList;
    }
}
