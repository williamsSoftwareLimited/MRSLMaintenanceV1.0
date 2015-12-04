package org.codebehind.mrslmaintenance.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModelBase;
import org.codebehind.mrslmaintenance.StaticConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

/**
 * Created by Gavin on 23/03/2015.
 */
public class EquipmentDbModel extends DbAbstractModelBase {

    public static final String TABLE="Equipment",
            FILTER_SELECTION_START="name like '%",
            FILTER_SELECTION_END="%'";
    public static final String[] FIELDS =  new String[]{"_id", "Name", "ImageId", "Timestamp", "Deleted" };
    public static final int ID=0, NAME=1, IMAGE_ID=2, TIMESTAMP=3, DELETED=4;
    private static final int PARAMETER_ID=5, PARAMETER_NAME=6, PARAMETER_UNIT=7, PARAMETER_TYPE_ID=8, VALUE=9 ;
    private int _length; // this checks the size of the list and iff it's different
    private ArrayList<Equipment> _list;

    public EquipmentDbModel(Context context){
        super(context, TABLE);
        _length = -1;
        _list = new ArrayList<>();
    }

    public Equipment getEntity(int id) {
        for(Equipment e: getList()) if (e.getId()==id)return e;
        return null;
    }

    public int add(Equipment equipment) {
        ContentValues v;

        // equipment is invariant and must not be null
        if (equipment==null) return StaticConstants.BAD_DB;
        v= new ContentValues();
        v.put(FIELDS[NAME], equipment.getEquipmentName());
        v.put(FIELDS[IMAGE_ID], equipment.getImgId());
        return (int) DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE, null, v);
    }

    public ArrayList<Equipment> getList() {
        populateList();
        return _list;
    }

    public ArrayList<Equipment> getList(int siteId) {
        ArrayList<Equipment> l = new ArrayList<>();

        String query = "select "
                +"e."+FIELDS[ID]+", "
                +"e."+FIELDS[NAME]+", "
                +"e."+FIELDS[IMAGE_ID]+", "
                +"e."+FIELDS[TIMESTAMP]+", "
                +"e."+FIELDS[DELETED]+" "
                +" from " + TABLE + " e"
                +" join " + SiteEquipmentDbModel.TABLE + " se on e."+FIELDS[ID]+" = se."+SiteEquipmentDbModel.FIELDS[SiteEquipmentDbModel.EQUIPID]
                +" where se."+SiteEquipmentDbModel.FIELDS[SiteEquipmentDbModel.SITEID]+"="+siteId;

        Cursor c= DatabaseHelper.getInstance(_context).getReadableDatabase().rawQuery(query, null);
        c.moveToFirst();

        while(c.isAfterLast()==false){

            Equipment equipment=new Equipment(c.getInt(ID), c.getString(NAME), c.getInt(IMAGE_ID));

            l.add(equipment);
            c.moveToNext();
        }

        Collections.sort(l);
        return l;
    }

    public ArrayList<Equipment> getFilterList(String filter) {
        filterList(filter);
        return _list;
    }

    public ArrayList<Equipment>getEquipmentListForReport(int reportId){
        ArrayList<Equipment> equipmentList;
        Equipment equipment;
        Parameter parameter;
        Hashtable<Integer, Equipment> equipmentHashtable;
        int equipmentId;

        String query = "select "
                +"e."+FIELDS[ID]+", "
                +"e."+FIELDS[NAME]+", "
                +"e."+FIELDS[IMAGE_ID]+", "
                +"e."+FIELDS[TIMESTAMP]+", "
                +"e."+FIELDS[DELETED]+", "
                +"se."+ReportEquipmentParametersDbModel.FIELDS[ReportEquipmentParametersDbModel.PARAMETER_ID]+", "
                +"p."+ParameterDbModel.FIELDS[ParameterDbModel.NAME]+", "
                +"p."+ParameterDbModel.FIELDS[ParameterDbModel.UNITS]+", "
                +"p."+ParameterDbModel.FIELDS[ParameterDbModel.PARAMETER_TYPE_ID]+", "
                +"se."+ReportEquipmentParametersDbModel.FIELDS[ReportEquipmentParametersDbModel.VALUE]+" "
                +" from " + TABLE + " e"
                +" join " + ReportEquipmentParametersDbModel.TABLE + " se on e."+FIELDS[ID]+" = se."+ReportEquipmentParametersDbModel.FIELDS[ReportEquipmentParametersDbModel.EQUIPMENT_ID]
                +" join " + ParameterDbModel.TABLE + " p on se."+ReportEquipmentParametersDbModel.FIELDS[ReportEquipmentParametersDbModel.PARAMETER_ID]+" = p."+ParameterDbModel.FIELDS[ParameterDbModel.ID]
                +" where se."+ReportEquipmentParametersDbModel.FIELDS[ReportEquipmentParametersDbModel.REPORT_ID]+"="+reportId;

        Cursor c= DatabaseHelper.getInstance(_context).getReadableDatabase().rawQuery(query, null);
        c.moveToFirst();

        equipmentList=new ArrayList<>();
        equipmentHashtable=new Hashtable<>();

        while(c.isAfterLast()==false){

            equipmentId=c.getInt(ID);

            if (!equipmentHashtable.containsKey(equipmentId)){
                equipment=new Equipment(equipmentId, c.getString(NAME), c.getInt(IMAGE_ID));
                equipment.setParameterList(new ArrayList<Parameter>());
                equipmentHashtable.put(equipmentId, equipment);
            }
            parameter=new Parameter(c.getInt(PARAMETER_ID),c.getString(PARAMETER_NAME),c.getString(PARAMETER_UNIT),c.getInt(PARAMETER_TYPE_ID));
            parameter.setNewValue(c.getString(VALUE));
            equipmentHashtable.get(equipmentId).getParameterList().add(parameter);

            c.moveToNext();
        }

        for (Equipment equipment1 : equipmentHashtable.values()) equipmentList.add(equipment1);

        Collections.sort(equipmentList);
        return equipmentList;
    }

    private void populateList(){
        if (_list.size()==_length)return;
        filterList("");
        _length=_list.size();
    }

    private void filterList(String filter){
        _list.clear();
        Cursor c= DatabaseHelper.getInstance(_context).getReadableDatabase().query(TABLE, FIELDS,FILTER_SELECTION_START+filter+FILTER_SELECTION_END,null,null,null,null);
        c.moveToFirst();
        while(c.isAfterLast()==false){
            Equipment e=new Equipment();
            e.setId(c.getInt(ID));
            e.setEquipmentName(c.getString(NAME));
            e.setImgId(c.getInt(IMAGE_ID)); // this will nip off and get the image
            _list.add(e);
            c.moveToNext();
        }
    }
}
