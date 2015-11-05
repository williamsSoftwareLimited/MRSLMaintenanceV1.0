package org.codebehind.mrslmaintenance.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.SiteEquipment;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModel;
import org.codebehind.mrslmaintenance.StaticConstants;

import java.util.ArrayList;

/**
 * Created by root on 02/11/15.
 * This is all a little pointless but it keeps it in-line with the other Db models and contains the correct statics for creation
 * This is a many to many table from the site to the equipment table
 */
public class SiteEquipmentDbModel {

    protected Context _context;
    protected ArrayList<SiteEquipment> _list;
    public static final String TABLE="SiteEquipment";
    public static final String[] FIELDS = new String[]{"_id", "siteId","equipId" };
    public static final int ID=0, SITEID=1, EQUIPID = 2;

    public SiteEquipmentDbModel(Context context) {
        _context=context;
        _list=new ArrayList<>();
    }

    public int add(SiteEquipment entity) {
        ContentValues cv;
        int dbRetNo;
        if (entity==null)return StaticConstants.BAD_DB;

        cv = new ContentValues();
        cv.put(FIELDS[SITEID], entity.getSiteid());
        cv.put(FIELDS[EQUIPID], entity.getEquipid());

        dbRetNo = (int)DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE,null,cv);
        if (dbRetNo>0) _list.add(entity);

        return dbRetNo;
    }

    public ArrayList<SiteEquipment> getAllList() {
        ArrayList<SiteEquipment> al = new ArrayList<>();
        Cursor c = DatabaseHelper.getInstance(_context).getReadableDatabase().query(TABLE,FIELDS,null,null,null,null,null);
        SiteEquipment se;
        c.moveToFirst();
        while(c.isAfterLast()==false){
            se = new SiteEquipment();
            se.setId(c.getInt(ID));
            se.setSiteid(c.getInt(SITEID));
            se.setEquipid(c.getInt(EQUIPID));
            al.add(se);
            c.moveToNext();
        }
        return al;
    }

    public void close(){
        DatabaseHelper.getInstance(_context).getWritableDatabase().close();
    }
}
