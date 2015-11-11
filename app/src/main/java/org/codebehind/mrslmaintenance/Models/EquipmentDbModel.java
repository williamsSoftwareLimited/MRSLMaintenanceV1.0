package org.codebehind.mrslmaintenance.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModel;
import org.codebehind.mrslmaintenance.StaticConstants;

import java.util.ArrayList;

/**
 * Created by Gavin on 23/03/2015.
 */
public class EquipmentDbModel extends DbAbstractModel<Equipment>  {

    public static final String TABLE="Equipment",
            FILTER_SELECTION_START="name like '%",
            FILTER_SELECTION_END="%'";
    public static final String[] FIELDS =  new String[]{"_id", "Name","ImageId"};//, "ts","del" };
    public static final int ID=0, NAME=1, IMAGE_ID=2,TS=3, DEL=4;;
    protected int _length; // this checks the size of the list and iff it's different

    public EquipmentDbModel(Context context){
        super(context, TABLE);
        _length = -1;
        _list = new ArrayList<Equipment>();
    }

    @Override
    public Equipment getEntity(int id) {
        for(Equipment e: getList()) if (e.getId()==id)return e;
        return null;
    }

    // when this adds an equipment it needs to add the image id as well
    @Override
    public int add(Equipment equipment) {
        ContentValues v;

        // equipment is invariant and must not be null
        if (equipment==null) return StaticConstants.BAD_DB;
        v= new ContentValues();
        v.put(FIELDS[NAME], equipment.getEquipmentName());
        v.put(FIELDS[IMAGE_ID], equipment.getImgId());
        return (int) DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE, null, v);
    }
    @Override
    public ArrayList<Equipment> getList() {
        populateList();
        return _list;
    }

    @Override
    public ArrayList<Equipment> getList(ArrayList<String> params) {
        ArrayList<Equipment> l = new ArrayList<>();

        String query = "select * from " + TABLE + " e"
                +" join " + SiteEquipmentDbModel.TABLE + " se on e."+FIELDS[ID]+" = se."+SiteEquipmentDbModel.FIELDS[SiteEquipmentDbModel.EQUIPID]
                +" where se."+SiteEquipmentDbModel.FIELDS[SiteEquipmentDbModel.SITEID]+"="+params.get(0);

        Cursor c= DatabaseHelper.getInstance(_context).getReadableDatabase().rawQuery(query, null);
        c.moveToFirst();

        while(c.isAfterLast()==false){

            Equipment equipment=new Equipment(c.getInt(ID), c.getString(NAME), c.getInt(IMAGE_ID));

            l.add(equipment);
            c.moveToNext();
        }

        return l;
    }

    @Override
    public ArrayList<Equipment> getFilterList(String filter) {
        filterList(filter);
        return _list;
    }
    @Override
    public void delete(int equipmentId) {
        Equipment e=getEntity(equipmentId);
    }

    @Override
    public void update(Equipment equipment) {

    }

    void populateList(){
        if (_list.size()==_length)return;
        filterList("");
        _length=_list.size();
    }
    void filterList(String filter){
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
