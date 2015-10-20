package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Abstract.Fragment2ActivityListener;
import org.codebehind.mrslmaintenance.Abstract.FragmentMenubarListener;
import org.codebehind.mrslmaintenance.Abstract.IFragmentCallbackUUID;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Models.EquipmentModel;
import org.codebehind.mrslmaintenance.Models.ReportModel;

import java.util.ArrayList;
import java.util.UUID;


public class EquipmentListActivity extends ActionBarActivityBase implements IFragmentCallbackUUID {
    UUID reportId;
    int _equipmentId; // this would be from the callback and used when a menu item is pressed
    Menu _menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FragmentTransaction ft;
        EquipmentListFragment equipmentListFragment;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_list);

        reportId=(UUID)getIntent().getSerializableExtra(StaticConstants.EXTRA_REPORT_ID);
        Debugger.getInstance().debugMessage(this, "The report id is " + reportId);

        if (savedInstanceState == null) {
            ft = getSupportFragmentManager().beginTransaction();
            equipmentListFragment = new EquipmentListFragment();
            ft.add(R.id.equipment_list_container, equipmentListFragment);
            ft.commit();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_equipment_list, menu);
        _menu=menu;
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.menu_view_equipment_list:
                openActivity(StaticConstants.fragmentModes.view);
                return true;
            case R.id.menu_alter_equipment_list:
                openActivity(StaticConstants.fragmentModes.alter);
                return true;
            case R.id.menu_new_equipment_list:
                openActivity(StaticConstants.fragmentModes.newer);
                return true;
            case R.id.menu_delete_equipment_list:
                Debugger.getInstance().debugMessage(this,"Deleting equipment with id="+_equipmentId);
                EquipmentModel.getInstance().delete(_equipmentId);
                // todo: may just refresh the list in the fragment here
                recreate();
                return true;
            case R.id.menu_add_equipment_list:
                Toast.makeText(this,"Add selected", Toast.LENGTH_SHORT).show();
                // this calls back to the ReportActivity
                return true;
            default: return true;
        }
    }
    private void openActivity(StaticConstants.fragmentModes mode){
        Intent intent;
        intent=new Intent(this, EquipmentNewActivity.class);
        intent.putExtra(StaticConstants.EXTRA_EQUIPMENT_ID,_equipmentId);
        intent.putExtra(StaticConstants.EXTRA_EQUIPMENT_MODE,mode.ordinal());
        startActivityForResult(intent, StaticConstants.fragmentModes.newer.ordinal());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==StaticConstants.fragmentModes.newer.ordinal()){
            if (resultCode==RESULT_OK){
                Debugger.getInstance().debugMessage(this,"Received from EquipmentNewActivity data=" + data.getData().toString());
            }
        }
    }
    @Override
    public void onFragmentCallback(int id) {
        _equipmentId=id;
        setMenubarVisibility(true);
    }
    @Override
    public void onFragmentCallback(UUID id) {

    }
    private void setMenubarVisibility(boolean visible){
        if (_menu==null) {
            Debugger.getInstance().debugMessage(this, "EquipmentListActivity.onFragmentCallback : _menu is null.");
            return;
        }
        _menu.findItem(R.id.menu_view_equipment_list).setVisible(visible);
        _menu.findItem(R.id.menu_alter_equipment_list).setVisible(visible);
        _menu.findItem(R.id.menu_delete_equipment_list).setVisible(visible);

        // the add doesn't do anything or doesn't appear if this has been accessed from the report menubar
        _menu.findItem(R.id.menu_add_equipment_list).setVisible(visible);
    }
}
