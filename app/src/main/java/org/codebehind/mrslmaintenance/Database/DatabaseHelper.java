package org.codebehind.mrslmaintenance.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.codebehind.mrslmaintenance.Models.EquipmentDbModel;
import org.codebehind.mrslmaintenance.Models.ImageModel;
import org.codebehind.mrslmaintenance.Models.ReportDbModel;
import org.codebehind.mrslmaintenance.Models.SiteDbModel;
import org.codebehind.mrslmaintenance.Models.SiteEquipmentDbModel;

/**
 * Created by Gavin on 18/01/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper instance;
    public static final String DATABASE_NAME="MRSLDatabase";
    public static final int DATABASE_VERSION=23;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static DatabaseHelper getInstance(Context context) {
        if (instance==null)instance=new DatabaseHelper(context);
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // keep the ordering of the tables to _id, ts then del as this will help with an abstraction (later)
        db.execSQL("Create table " + ImageModel.TABLE + "( _id integer primary key autoincrement, image blob, title varchar(100));");
        db.execSQL("Create table "+ EquipmentDbModel.TABLE+" (_id integer primary key autoincrement, name varchar(100), imageId integer );");

        // no NOT NULL for siteId left as sloppy for now
        db.execSQL("Create table "+ ReportDbModel.TABLE+" ("+ReportDbModel.FIELDS[ReportDbModel.ID]+" integer primary key autoincrement, "
                +ReportDbModel.FIELDS[ReportDbModel.TS]+" integer, "
                +ReportDbModel.FIELDS[ReportDbModel.DEL]+" boolean, "
                +ReportDbModel.FIELDS[ReportDbModel.SITEID]+" integer, "
                    +"foreign key("+ReportDbModel.FIELDS[ReportDbModel.SITEID]+") references "+SiteDbModel.TABLE+"("+SiteDbModel.FIELDS[SiteDbModel.ID]+")"
                +");");

        db.execSQL("Create table "+ SiteDbModel.TABLE+" ("+SiteDbModel.FIELDS[SiteDbModel.ID]+" integer primary key autoincrement, "
                +SiteDbModel.FIELDS[SiteDbModel.TS]+" integer, "
                +SiteDbModel.FIELDS[SiteDbModel.DEL]+" boolean, "
                +SiteDbModel.FIELDS[SiteDbModel.NAME]+" varchar(50), "
                +SiteDbModel.FIELDS[SiteDbModel.ADDRESS]+" varchar(255), "
                +SiteDbModel.FIELDS[SiteDbModel.IMAGE_ID]+" integer  );");

        // these are two foreign keys to the site and equpment tables -
        // todo: Enforce the FK restraints SiteId and EquipId
        db.execSQL("Create table "+ SiteEquipmentDbModel.TABLE+" ("+SiteEquipmentDbModel.FIELDS[SiteEquipmentDbModel.ID]+" integer primary key autoincrement,"
                +SiteEquipmentDbModel.FIELDS[SiteEquipmentDbModel.SITEID]+ " integer, "
                    //+"foreign key("+SiteEquipmentDbModel.FIELDS[SiteEquipmentDbModel.SITEID]+") references "+SiteDbModel.TABLE+"("+SiteDbModel.FIELDS[SiteDbModel.ID]+"),"
                +SiteEquipmentDbModel.FIELDS[SiteEquipmentDbModel.EQUIPID]+ " integer , "
                    +"foreign key("+SiteEquipmentDbModel.FIELDS[SiteEquipmentDbModel.SITEID]+") references "+SiteDbModel.TABLE+"("+SiteDbModel.FIELDS[SiteDbModel.ID]+"),"
                    +"foreign key("+SiteEquipmentDbModel.FIELDS[SiteEquipmentDbModel.EQUIPID]+") references "+EquipmentDbModel.TABLE+"("+EquipmentDbModel.FIELDS[EquipmentDbModel.ID]+")"
                +");");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ SiteEquipmentDbModel.TABLE);
        db.execSQL("drop table if exists "+ ImageModel.TABLE);
        db.execSQL("drop table if exists "+ EquipmentDbModel.TABLE);
        db.execSQL("drop table if exists "+ ReportDbModel.TABLE);
        db.execSQL("drop table if exists "+ SiteDbModel.TABLE);
        onCreate(db);
    }
}