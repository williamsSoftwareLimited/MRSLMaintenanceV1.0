package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.codebehind.mrslmaintenance.Data.LoadData;
import org.codebehind.mrslmaintenance.Database.DatabaseHelper;
import org.codebehind.mrslmaintenance.Entities.Image;
import org.codebehind.mrslmaintenance.Models.ImageModel;

// this controllers job is simply to initialise and pass on to the baton to whoever is the first activity
public class ControllerActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent;
        LoadData loadData;

        super.onCreate(savedInstanceState);

        // initialize any data here
        loadData=new LoadData();
        loadData.load(this);

        intent = new Intent(this, ReportListActivity.class);
        startActivity(intent);
        finish();
    }
}
