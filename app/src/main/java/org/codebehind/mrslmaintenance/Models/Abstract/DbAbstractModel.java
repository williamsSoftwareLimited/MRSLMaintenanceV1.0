package org.codebehind.mrslmaintenance.Models.Abstract;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Gavin on 23/08/2015.
 */
public abstract class DbAbstractModel<t> {
    protected Context _context;
    protected ArrayList<t> _list;

    public DbAbstractModel(Context context){
        _context=context;
    }

    public abstract t getEntity(int id);

    public abstract int add(t entity);

    public abstract ArrayList<t> getList();

    public abstract ArrayList<t> getList(ArrayList<String> params); // this can be the where params

    public abstract ArrayList<t> getFilterList(String filter);

    public abstract void delete(int id);

    public abstract void update(t entity);
}