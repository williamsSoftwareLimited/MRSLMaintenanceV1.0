package org.codebehind.mrslmaintenance;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;


public class EquipmentListActivity extends ActionBarActivityBase{
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
        int id = item.getItemId();

        switch(id){

            case R.id.menu_delete_equipment_list:

                //EquipmentModel.getInstance().delete(_equipmentId);
                // todo: may just refresh the list in the fragment here
                recreate();
                return true;

            case R.id.menu_add_equipment_list:

                Toast.makeText(this,"Add selected", Toast.LENGTH_SHORT).show();
                return true;

            default: return true;
        }
    }

}
