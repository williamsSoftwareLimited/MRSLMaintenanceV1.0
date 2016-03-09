package org.codebehind.mrslmaintenance.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.Email;
import org.codebehind.mrslmaintenance.Models.Abstract.DbAbstractModelBase;
import org.codebehind.mrslmaintenance.Models.Abstract.IEmailDbModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by root on 16/02/16.
 */
public class EmailDbModel extends DbAbstractModelBase implements IEmailDbModel {

    public static final String TABLE="Email";
    public static final String[] FIELDS =  new String[]{ "_id", "emailAddress", "selected", "timestamp", "deleted" };
    public static final int ID=0, EMAILADDRESS=1, SELECTED=2, TIMESTAMP=3, DELETED=4;

    private static final String LOG_TAG="EmailDbModel";

    public EmailDbModel(Context context) {
        super(context, TABLE);
    }

    @Override
    public int add(Email email) {
        ContentValues v;

        // email is invariant and must not be null
        if (email==null) return -1;

        v=getContValues(email);

        return (int) DatabaseHelper.getInstance(_context).getWritableDatabase().insert(TABLE, null, v);
    }

    @Override
    public int update(Email e){
        ContentValues v;
        String whereArg;
        int rowCx;

        if (e==null)return -1;

        v=getContValues(e);

        whereArg=FIELDS[ID]+"="+e.getId();

        rowCx= DatabaseHelper.getInstance(_context).getWritableDatabase().update(TABLE, v, whereArg, null);

        if (rowCx>1){

            Log.wtf(LOG_TAG, "update: violation more than 1 record has been altered.");
        }

        return rowCx;

    }

    private ContentValues getContValues(Email e){
        ContentValues v;

        v= new ContentValues();
        v.put(FIELDS[EMAILADDRESS], e.getEmailAddress());
        v.put(FIELDS[SELECTED], e.getSelected());
        v.put(FIELDS[TIMESTAMP], new Date().getTime());
        v.put(FIELDS[DELETED], 0);
        return v;
    }

    @Override
    public int delete(int i){
        ContentValues v;
        String whereArg;
        int rowCx;

        if (i<0)return -1;

        v= new ContentValues();
        v.put(FIELDS[DELETED], 1);

        whereArg=FIELDS[ID]+"="+i;

        rowCx= DatabaseHelper.getInstance(_context).getWritableDatabase().update(TABLE, v, whereArg, null);

        if (rowCx>1){

            Log.wtf(LOG_TAG, "delete: violation more than 1 record has been deleted.");
        }

        return rowCx;

    }

    @Override
    public ArrayList<Email> getList() {

        return getEmails("", new processCmd<ArrayList<Email>>() {

            public ArrayList<Email> getList(Cursor c){
                ArrayList<Email> emailList;
                Boolean b;
                Email email;

                emailList = new ArrayList<>();
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
        });
    }

    @Override
    public String[] getSelectedArray() {

        return getEmails(" and e." + FIELDS[SELECTED] + " = 1 ", new processCmd<String[]>() {

            @Override
            public String[] getList(Cursor c) {
                String []s;
                int i;

                s=new String[c.getCount()];
                c.moveToFirst();
                i=0;

                while(c.isAfterLast()==false){

                    s[i++]=c.getString(EMAILADDRESS);
                    c.moveToNext();
                }

                return s;
            }
        });
    }

    // andArg has to have the complete argument ie " and x=1 "
    // the procCmd interface is implemented below
    private <t> t getEmails(String andArg, processCmd<t> procCmd) {
        Cursor c;

        if (andArg==null) andArg="";

        String query = "select "
                +"e."+FIELDS[ID]+", "
                +"e."+FIELDS[EMAILADDRESS]+", "
                +"e."+FIELDS[SELECTED]+", "
                +"e."+FIELDS[TIMESTAMP]+", "
                +"e."+FIELDS[DELETED]+" "
                +" from " + TABLE + " e"
                +" where e."+FIELDS[DELETED]+"<1 "
                +andArg;

        c= DatabaseHelper.getInstance(_context).getReadableDatabase().rawQuery(query, null);

        return procCmd.getList(c);
    }

    private interface processCmd<t>{

        t getList(Cursor c);

    }

}
