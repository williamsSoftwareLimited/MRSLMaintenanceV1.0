package org.codebehind.mrslmaintenance.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.codebehind.mrslmaintenance.Models.EquipmentDbModel;
import org.codebehind.mrslmaintenance.Models.ImageModel;
import org.codebehind.mrslmaintenance.Models.ReportDbModel;
import org.codebehind.mrslmaintenance.Models.ReportModel;
import org.codebehind.mrslmaintenance.Models.SiteDbModel;

/**
 * Created by Gavin on 18/01/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper instance;
    public static final String DATABASE_NAME="MRSLDatabase";
    public static final int DATABASE_VERSION=15;

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
        db.execSQL("Create table "+ ImageModel.TABLE+"( _id integer primary key autoincrement, image blob, title varchar(100));");
        db.execSQL("Create table "+ EquipmentDbModel.TABLE+" (_id integer primary key autoincrement, name varchar(100), imageId integer );");
        // no NOT NULL for siteId left as sloppy for now
        db.execSQL("Create table "+ ReportDbModel.TABLE+" (_id integer primary key autoincrement, ts integer, del boolean, siteId integer, foreign key(siteId) references site(_id)  );");
        db.execSQL("Create table "+ SiteDbModel.TABLE+" (_id integer primary key autoincrement, ts integer, del boolean, name varchar(50), address varchar(255), imageId integer  );");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+ ImageModel.TABLE);
        db.execSQL("drop table if exists "+ EquipmentDbModel.TABLE);
        db.execSQL("drop table if exists "+ ReportDbModel.TABLE);
        db.execSQL("drop table if exists "+ SiteDbModel.TABLE);
        onCreate(db);
    }
}