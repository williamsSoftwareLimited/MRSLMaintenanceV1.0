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

    public DbAbstractModel(Context context){
        _context=context;
    }
}