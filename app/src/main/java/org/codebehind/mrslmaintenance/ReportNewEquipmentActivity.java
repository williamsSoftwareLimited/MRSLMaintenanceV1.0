package org.codebehind.mrslmaintenance;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;

public class ReportNewEquipmentActivity extends ActionBarActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_new_equipment);
    }
}
