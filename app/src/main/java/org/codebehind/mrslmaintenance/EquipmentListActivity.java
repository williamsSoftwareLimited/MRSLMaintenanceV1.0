package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Entities.Equipment;


public class EquipmentListActivity extends ActionBarActivityBase{

    private static final String LOG_TAG="EquipmentListActivity";
    private Menu _menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FragmentTransaction ft;
        EquipmentListFragment equipmentListFragment;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_list);

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
        int id;
        Bundle bundle;
        Intent intent;
        Equipment equip;

        id = item.getItemId();

        switch(id){

            case R.id.menu_delete_equipment_list:

                //EquipmentModel.getInstance().delete(_equipmentId);
                // todo: may just refresh the list in the fragment here
                recreate();
                return true;

            case R.id.menu_new_equipment_list:

                Log.d(LOG_TAG, "onOptionsItemSelected: Add new equip from menu selected.");

                equip=new Equipment("",1);

                bundle=new Bundle();
                bundle.putSerializable(EquipmentListFragment.BUNDLE_EQUIP, equip);

                intent=new Intent(this, EquipmentNewActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
                return true;

            default: return true;
        }
    }

}
