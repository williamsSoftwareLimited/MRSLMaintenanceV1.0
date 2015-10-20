package org.codebehind.mrslmaintenance.Models.Abstract;

import android.content.Context;

import org.codebehind.mrslmaintenance.Entities.Equipment;

import java.util.ArrayList;

/**
 * Created by Gavin on 23/08/2015.
 */
public abstract class DbAbstractModel<t> {
    protected Context _context;
    protected ArrayList<t> _list;
    protected int _length; // this checks the size of the list and iff it's different

    public DbAbstractModel(Context context){
        _context=context;
        _list = new ArrayList<t>();
        _length = -1;
    }
    protected abstract void populateList();
    protected abstract void filterList(String filter);
    public ArrayList<t> getList() {
        populateList();
        return _list;
    }
    public ArrayList<t> getFilterList(String filter) {
        filterList(filter);
        return _list;
    }
}