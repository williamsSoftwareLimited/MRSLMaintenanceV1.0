package org.codebehind.mrslmaintenance.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Image;
import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModelBase;
import org.codebehind.mrslmaintenance.StaticConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;

/**
 * Created by Gavin on 23/03/2015.
 */
public class EquipmentDbModel extends DbAbstractModelBase {

    public static final String TABLE="Equipment",
            FILTER_SELECTION_START="name like '%",
            FILTER_SELECTION_END="%'";

    public static final String[] FIELDS =  new String[]{"_id", "name", "imageId", "timestamp", "deleted" };
    public static final int ID=0, NAME=1, IMAGE_ID=2, TIMESTAMP=3, DELETED=4;

    private static final String LOG_TAG="EquipmentDbModel";
    private int _length; // this checks the size of the list and iff it's different
    private ArrayList<Equipment> _list;

    public EquipmentDbModel(Context context){
        super(context, TABLE);

        _length = -1;
        _list = new ArrayList<>();
    }

    public Equipment getEquip(int id) {

        for(Equipment e: getList())
            if (e.getId()==id)return e;

        return null;
    }

    public int add(Equipment equipment) {
        ContentValues v;

        // equipment is invariant and must not be null
        if (equipment==null) return -1;

        v= new ContentValues();
        v.put(FIELDS[NAME], equipment.getEquipmentName());
        v.put(FIELDS[IMAGE_ID], equipment.getImgId());

        return (int) DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE, null, v);
    }

    public int update(Equipment equip){

        return updateWithDelete(equip, false);
    }

    public int delete(Equipment equip){
        int rowCount;

        rowCount=updateWithDelete(equip, true);

        if (rowCount<1) {

            Log.d(LOG_TAG, "delete: Delete unsuccessful check update message above.");
            return -1;
        } else return equip.getId();

    }

    public ArrayList<Equipment> getList() {
        ArrayList<Equipment> equipmentList;

        equipmentList = new ArrayList<>();

        String query = "select "
                +"e."+FIELDS[ID]+", "
                +"e."+FIELDS[NAME]+", "
                +"e."+FIELDS[IMAGE_ID]+", "
                +"e."+FIELDS[TIMESTAMP]+", "
                +"e."+FIELDS[DELETED]+" "
                +" from " + TABLE + " e";

        Cursor c= DatabaseHelper.getInstance(_context).getReadableDatabase().rawQuery(query, null);
        c.moveToFirst();

        while(c.isAfterLast()==false){

            Equipment equipment=new Equipment(c.getInt(ID), c.getString(NAME), c.getInt(IMAGE_ID));

            equipmentList.add(equipment);
            c.moveToNext();
        }

        Collections.sort(equipmentList);
        return equipmentList;
    }

    public ArrayList<Equipment> getList(int siteId) {
        ArrayList<Equipment> equipmentList;

        equipmentList = new ArrayList<>();

        String query = "select "
                +"e."+FIELDS[ID]+", "
                +"e."+FIELDS[NAME]+", "
                +"e."+FIELDS[IMAGE_ID]+", "
                +"e."+FIELDS[TIMESTAMP]+", "
                +"e."+FIELDS[DELETED]+" "
                +" from " + TABLE + " e"
                +" join " + SiteEquipmentDbModel.TABLE + " se on e."+FIELDS[ID]+" = se."+SiteEquipmentDbModel.FIELDS[SiteEquipmentDbModel.EQUIPMENT_ID]
                +" where se."+SiteEquipmentDbModel.FIELDS[SiteEquipmentDbModel.SITE_ID]+"="+siteId;

        Cursor c= DatabaseHelper.getInstance(_context).getReadableDatabase().rawQuery(query, null);
        c.moveToFirst();

        while(c.isAfterLast()==false){

            Equipment equipment=new Equipment(c.getInt(ID), c.getString(NAME), c.getInt(IMAGE_ID));

            equipmentList.add(equipment);
            c.moveToNext();
        }

        Collections.sort(equipmentList);
        return equipmentList;
    }

    public ArrayList<Equipment> getFilterList(String filter) {

        filterList(filter);
        return _list;
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

    private int updateWithDelete(Equipment equip, boolean deleted){
        int rowCount; // invariance: 0 or 1
        ContentValues contentValues;
        Equipment equipInList;
        String whereClause;

        contentValues=new ContentValues();

        if (equip==null) {

            Log.wtf(LOG_TAG, "update: The equip argument is null.");
            return 0;
        }

        if (equip.getId()==-1) {

            Log.wtf(LOG_TAG, "update: The equip has an id of -1 and is therefore new.");
            return 0;
        }

        equipInList=getEquip(equip.getId());

        if (equipInList==null) {

            Log.e(LOG_TAG, "update: There's been a equipId (not equal to -1) but no equip in the list.");
            return 0; // Something seriously bad has happened here
        }

        contentValues.put(FIELDS[NAME], equip.getEquipmentName());
        contentValues.put(FIELDS[IMAGE_ID], equip.getImgId());
        contentValues.put(FIELDS[TIMESTAMP], new Date().getTime());
        contentValues.put(FIELDS[DELETED], deleted?1:0);

        whereClause=FIELDS[ID]+"="+equip.getId();

        rowCount=DatabaseHelper.getInstance(_context).getWritableDatabase().update(TABLE, contentValues, whereClause, null);

        if (rowCount>0) {
            _list.remove(equipInList);
            _list.add(equip);
        }

        return rowCount;
    }

}
