package org.codebehind.mrslmaintenance.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.codebehind.mrslmaintenance.Models.EmailDbModel;
import org.codebehind.mrslmaintenance.Models.EquipmentDbModel;
import org.codebehind.mrslmaintenance.Models.EquipmentParamsDbModel;
import org.codebehind.mrslmaintenance.Models.ImageModel;
import org.codebehind.mrslmaintenance.Models.ParameterDbModel;
import org.codebehind.mrslmaintenance.Models.ParameterTypeDbModel;
import org.codebehind.mrslmaintenance.Models.ReportDbModel;
import org.codebehind.mrslmaintenance.Models.ReportEquipParamsDbModel;
import org.codebehind.mrslmaintenance.Models.SiteDbModel;
import org.codebehind.mrslmaintenance.Models.SiteEquipmentDbModel;

/**
 * Created by Gavin on 18/01/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper instance;
    public static final String DATABASE_NAME="MRSLDatabase";
    public static final int DATABASE_VERSION=86;

    //http://stackoverflow.com/questions/9937713/does-sqlite3-not-support-foreign-key-constraints
    // To enforce the FKs need to add the following to every connection to the Db
    // PRAGMA foreign_keys = ON;

    public DatabaseHelper(Context context){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public static DatabaseHelper getInstance(Context context) {

        if (instance==null)instance=new DatabaseHelper(context);
        return instance;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("Create table " + ImageModel.TABLE + " ("
                +ImageModel.FIELDS[ImageModel.ID]+" integer primary key autoincrement, "
                +ImageModel.FIELDS[ImageModel.IMAGE]+" blob, "
                +ImageModel.FIELDS[ImageModel.TITLE]+" varchar(100) "
                +");");

        db.execSQL("Create table "+ EquipmentDbModel.TABLE+" ("
                +EquipmentDbModel.FIELDS[EquipmentDbModel.ID]+" integer primary key autoincrement, "
                +EquipmentDbModel.FIELDS[EquipmentDbModel.NAME]+" varchar(100), "
                +EquipmentDbModel.FIELDS[EquipmentDbModel.IMAGE_ID]+" integer, "
                +EquipmentDbModel.FIELDS[EquipmentDbModel.TIMESTAMP]+" integer, "
                +EquipmentDbModel.FIELDS[EquipmentDbModel.DELETED]+" boolean, "
                +"foreign key("+EquipmentDbModel.FIELDS[EquipmentDbModel.IMAGE_ID]+") references "+ImageModel.TABLE+"("+ImageModel.FIELDS[ImageModel.ID]+")"
                +");");

        db.execSQL("Create table "+ ReportDbModel.TABLE+" ("
                +ReportDbModel.FIELDS[ReportDbModel.ID]+" integer primary key autoincrement, "
                +ReportDbModel.FIELDS[ReportDbModel.TIMESTAMP]+" integer, "
                +ReportDbModel.FIELDS[ReportDbModel.DELETED]+" boolean, "
                +ReportDbModel.FIELDS[ReportDbModel.SITEID]+" integer, "
                +ReportDbModel.FIELDS[ReportDbModel.ENGINEER_NAME]+" varchar(100), "
                +"foreign key("+ReportDbModel.FIELDS[ReportDbModel.SITEID]+") references "+SiteDbModel.TABLE+"("+SiteDbModel.FIELDS[SiteDbModel.ID]+")"
                +");");

        db.execSQL("Create table "+ SiteDbModel.TABLE+" ("
                +SiteDbModel.FIELDS[SiteDbModel.ID]+" integer primary key autoincrement, "
                +SiteDbModel.FIELDS[SiteDbModel.TIMESTAMP]+" integer, "
                +SiteDbModel.FIELDS[SiteDbModel.DELETED]+" boolean, "
                +SiteDbModel.FIELDS[SiteDbModel.NAME]+" varchar(100), "
                +SiteDbModel.FIELDS[SiteDbModel.ADDRESS]+" varchar(255), "
                +SiteDbModel.FIELDS[SiteDbModel.IMAGE_ID]+" integer,  "
                +SiteDbModel.FIELDS[SiteDbModel.UUID]+" varchar(255) "
                +");");

        // these are two foreign keys to the site and equipment tables -
        db.execSQL("Create table "+ SiteEquipmentDbModel.TABLE+" ("
                +SiteEquipmentDbModel.FIELDS[SiteEquipmentDbModel.ID]+" integer primary key autoincrement,"
                +SiteEquipmentDbModel.FIELDS[SiteEquipmentDbModel.SITE_ID]+ " integer, "
                +SiteEquipmentDbModel.FIELDS[SiteEquipmentDbModel.EQUIPMENT_ID]+ " integer, "
                +SiteEquipmentDbModel.FIELDS[SiteEquipmentDbModel.NAME]+" varchar(100), "
                +SiteEquipmentDbModel.FIELDS[SiteEquipmentDbModel.TIMESTAMP]+ " integer, "
                +SiteEquipmentDbModel.FIELDS[SiteEquipmentDbModel.DELETED]+" boolean, "
                +"foreign key("+SiteEquipmentDbModel.FIELDS[SiteEquipmentDbModel.SITE_ID]+") references "+SiteDbModel.TABLE+"("+SiteDbModel.FIELDS[SiteDbModel.ID]+"),"
                +"foreign key("+SiteEquipmentDbModel.FIELDS[SiteEquipmentDbModel.EQUIPMENT_ID]+") references "+EquipmentDbModel.TABLE+"("+EquipmentDbModel.FIELDS[EquipmentDbModel.ID]+")"
                +");");

        db.execSQL("Create table "+ ParameterTypeDbModel.TABLE+" ("
                +ParameterTypeDbModel.FIELDS[ParameterTypeDbModel.ID]+" integer primary key,"
                +ParameterTypeDbModel.FIELDS[ParameterTypeDbModel.NAME]+ " varchar(100) "
                +");");

        db.execSQL("Create table "+ ParameterDbModel.TABLE+" ("
                +ParameterDbModel.FIELDS[ParameterDbModel.ID]+" integer primary key autoincrement,"
                +ParameterDbModel.FIELDS[ParameterDbModel.NAME]+ " varchar(100), "
                +ParameterDbModel.FIELDS[ParameterDbModel.UNITS]+ " varchar(20), "
                +ParameterDbModel.FIELDS[ParameterDbModel.PARAMETER_TYPE_ID]+ " integer, "
                +ParameterDbModel.FIELDS[ParameterDbModel.TIMESTAMP]+ " integer, "
                +ParameterDbModel.FIELDS[ParameterDbModel.DELETED]+" boolean, "
                +"foreign key("+ParameterDbModel.FIELDS[ParameterDbModel.PARAMETER_TYPE_ID]+") references "+ParameterTypeDbModel.TABLE+"("+ParameterTypeDbModel.FIELDS[ParameterTypeDbModel.ID]+")"
                +");");


        db.execSQL("Create table "+ EquipmentParamsDbModel.TABLE+" ("
                +EquipmentParamsDbModel.FIELDS[EquipmentParamsDbModel.ID]+" integer primary key autoincrement,"
                +EquipmentParamsDbModel.FIELDS[EquipmentParamsDbModel.EQUIPMENT_ID]+ " integer, "
                +EquipmentParamsDbModel.FIELDS[EquipmentParamsDbModel.PARAMETER_ID]+ " integer , "
                +"foreign key("+EquipmentParamsDbModel.FIELDS[EquipmentParamsDbModel.EQUIPMENT_ID]+") references "+EquipmentDbModel.TABLE+"("+EquipmentDbModel.FIELDS[EquipmentDbModel.ID]+"),"
                +"foreign key("+EquipmentParamsDbModel.FIELDS[EquipmentParamsDbModel.PARAMETER_ID]+") references "+ ParameterDbModel.TABLE+"("+ParameterDbModel.FIELDS[ParameterDbModel.ID]+")"
                +");");

        db.execSQL("Create table "+ ReportEquipParamsDbModel.TABLE+" ("
                + ReportEquipParamsDbModel.FIELDS[ReportEquipParamsDbModel.ID]+" integer primary key autoincrement,"
                + ReportEquipParamsDbModel.FIELDS[ReportEquipParamsDbModel.REPORT_ID]+ " integer, "
                + ReportEquipParamsDbModel.FIELDS[ReportEquipParamsDbModel.PARAMETER_ID]+ " integer , "
                + ReportEquipParamsDbModel.FIELDS[ReportEquipParamsDbModel.SITE_EQUIP_ID]+ " integer , "
                + ReportEquipParamsDbModel.FIELDS[ReportEquipParamsDbModel.VALUE]+ " varchar(255) , "
                + ReportEquipParamsDbModel.FIELDS[ReportEquipParamsDbModel.TIMESTAMP]+" integer, "
                + ReportEquipParamsDbModel.FIELDS[ReportEquipParamsDbModel.DELETED]+" boolean "
                +", foreign key("+ ReportEquipParamsDbModel.FIELDS[ReportEquipParamsDbModel.REPORT_ID]+") references "+ReportDbModel.TABLE+"("+ReportDbModel.FIELDS[ReportDbModel.ID]+"),"
                +"foreign key("+ ReportEquipParamsDbModel.FIELDS[ReportEquipParamsDbModel.PARAMETER_ID]+") references "+ ParameterDbModel.TABLE+"("+ParameterDbModel.FIELDS[ParameterDbModel.ID]+"),"
                +"foreign key("+ ReportEquipParamsDbModel.FIELDS[ReportEquipParamsDbModel.SITE_EQUIP_ID]+") references "+ SiteEquipmentDbModel.TABLE+"("+SiteEquipmentDbModel.FIELDS[SiteEquipmentDbModel.ID]+")"
                +");");

        db.execSQL("Create table "+ EmailDbModel.TABLE+" ("
                +EmailDbModel.FIELDS[EmailDbModel.ID]+" integer primary key autoincrement,"
                +EmailDbModel.FIELDS[EmailDbModel.EMAILADDRESS]+ " varchar(100), "
                +EmailDbModel.FIELDS[EmailDbModel.SELECTED]+ " boolean, "
                + EmailDbModel.FIELDS[EmailDbModel.TIMESTAMP]+" integer, "
                + EmailDbModel.FIELDS[EmailDbModel.DELETED]+" boolean "
                +");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists "+ EquipmentParamsDbModel.TABLE);
        db.execSQL("drop table if exists "+ ReportEquipParamsDbModel.TABLE);
        db.execSQL("drop table if exists "+ SiteEquipmentDbModel.TABLE);
        db.execSQL("drop table if exists "+ ImageModel.TABLE);
        db.execSQL("drop table if exists "+ EquipmentDbModel.TABLE);
        db.execSQL("drop table if exists "+ ReportDbModel.TABLE);
        db.execSQL("drop table if exists "+ SiteDbModel.TABLE);
        db.execSQL("drop table if exists "+ ParameterTypeDbModel.TABLE);
        db.execSQL("drop table if exists "+ ParameterDbModel.TABLE);
        db.execSQL("drop table if exists "+ EmailDbModel.TABLE);

        onCreate(db);
    }
}