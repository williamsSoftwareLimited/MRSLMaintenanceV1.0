package org.codebehind.mrslmaintenance.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.Email;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModelBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by root on 16/02/16.
 */
public class EmailDbModel extends DbAbstractModelBase {

    public static final String TABLE="Email";

    public static final String[] FIELDS =  new String[]{ "_id", "emailAddress", "selected", "timestamp", "deleted" };
    public static final int ID=0, EMAILADDRESS=1, SELECTED=2, TIMESTAMP=3, DELETED=4;

    private static final String LOG_TAG="EmailDbModel";

    public EmailDbModel(Context context) {
        super(context, TABLE);
    }

    public int add(Email email) {
        ContentValues v;

        // email is invariant and must not be null
        if (email==null) return -1;

        v= new ContentValues();
        v.put(FIELDS[EMAILADDRESS], email.getEmailAddress());
        v.put(FIELDS[SELECTED], email.getSelected());
        v.put(FIELDS[TIMESTAMP], new Date().getTime());
        v.put(FIELDS[DELETED], 0);

        return (int) DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE, null, v);
    }

    public ArrayList<Email> getList() {
        ArrayList<Email> emailList;
        Boolean b;
        Email email;

        emailList = new ArrayList<>();

        String query = "select "
                +"e."+FIELDS[ID]+", "
                +"e."+FIELDS[EMAILADDRESS]+", "
                +"e."+FIELDS[SELECTED]+", "
                +"e."+FIELDS[TIMESTAMP]+", "
                +"e."+FIELDS[DELETED]+" "
                +" from " + TABLE + " e";

        Cursor c= DatabaseHelper.getInstance(_context).getReadableDatabase().rawQuery(query, null);
        c.moveToFirst();

        while(c.isAfterLast()==false){

            b=c.getInt(SELECTED)>0;
            email=new Email(c.getInt(ID), c.getString(EMAILADDRESS), b);

            emailList.add(email);
            c.moveToNext();
        }

        Collections.sort(emailList);
        return emailList;
    }

}
