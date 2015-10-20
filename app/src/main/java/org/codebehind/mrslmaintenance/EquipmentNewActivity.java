package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Models.EquipmentModel;

import java.util.UUID;


public class EquipmentNewActivity extends ActionBarActivityBase {
    int _mode,_equipmentId;
    EquipmentNewFragment equipmentNewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FragmentTransaction ft;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_new);
        // should also receive a mode
        _equipmentId=getIntent().getIntExtra(StaticConstants.EXTRA_EQUIPMENT_ID, -1);
        _mode = (int)getIntent().getSerializableExtra(StaticConstants.EXTRA_EQUIPMENT_MODE);

        // to return to the parent activity use
        // setResult(RESULT_OK, data);
        // finish();
        if (savedInstanceState == null) {
            ft = getSupportFragmentManager().beginTransaction();
            equipmentNewFragment = new EquipmentNewFragment();
            equipmentNewFragment.setEquipmentId(_equipmentId);
            equipmentNewFragment.setMode(_mode);
            ft.add(R.id.activity_equipment_new_container, equipmentNewFragment);
            ft.commit();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_equipment_new, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()){
            case R.id.menu_cancel_equipment_new:
                finish();
                return true;
            case R.id.menu_save_equipment_new:
                // check the equipment is valid
                equipmentNewFragment.addParameter();
                return true;
            case R.id.menu_add_param_equipment_new:
                // check the equipment is valid
                equipmentNewFragment.showParameterSpinner();
                return true;
            case R.id.menu_take_equipment_photo:
                // this will either open the camera activity or fragment
                return true;
        }
        return true;
    }
}
