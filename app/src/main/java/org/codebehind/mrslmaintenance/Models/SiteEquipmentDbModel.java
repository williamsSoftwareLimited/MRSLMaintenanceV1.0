package org.codebehind.mrslmaintenance.Models;

import android.content.Context;

import org.codebehind.mrslmaintenance.Entities.SiteEquipment;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModel;

import java.util.ArrayList;

/**
 * Created by root on 02/11/15.
 * This is all a little pointless but it keeps it in-line with the other Db models and contains the correct statics for creation
 * This is a many to many table from the site to the equipment table
 */
public class SiteEquipmentDbModel extends DbAbstractModel<SiteEquipment>{

    public static final String TABLE="SiteEquipment";
    public static final String[] FIELDS = new String[]{"_id", "siteId","equipId" };
    public static final int ID=0, SITEID=1, EQUIPID = 2;

    public SiteEquipmentDbModel(Context context) {
        super(context);
    }

    @Override
    public SiteEquipment getEntity(int id) {
        return null;
    }

    @Override
    public int add(SiteEquipment entity) {
        return 0;
    }

    @Override
    public ArrayList<SiteEquipment> getList() {
        return null;
    }

    @Override
    public ArrayList<SiteEquipment> getList(ArrayList<String> params) {
        return null;
    }

    @Override
    public ArrayList<SiteEquipment> getFilterList(String filter) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void update(SiteEquipment entity) {

    }
}
