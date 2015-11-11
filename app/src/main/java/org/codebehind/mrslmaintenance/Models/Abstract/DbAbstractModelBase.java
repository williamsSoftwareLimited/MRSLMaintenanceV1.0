package org.codebehind.mrslmaintenance.Models.Abstract;

import android.content.Context;
import android.database.Cursor;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;

/**
 * Created by root on 11/11/15.
 */
public abstract class DbAbstractModelBase {

    private String _tableName;
    protected Context _context;

    public DbAbstractModelBase(Context context, String tableName){
        _context=context;
        _tableName=tableName;
    }

    public void close() {
        DatabaseHelper.getInstance(_context).getWritableDatabase().close();
    }

    public int getCount(){
        String query = "select count(*)"
                +" from " + _tableName;

        Cursor cursor = DatabaseHelper.getInstance(_context).getReadableDatabase().rawQuery(query, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }
}
