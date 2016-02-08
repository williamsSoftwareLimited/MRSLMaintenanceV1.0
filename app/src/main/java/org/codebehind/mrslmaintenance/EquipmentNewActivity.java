package org.codebehind.mrslmaintenance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Abstract.IEquipParamDelegate;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.EquipmentParameters;
import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.Models.EquipmentDbModel;
import org.codebehind.mrslmaintenance.Models.EquipmentParamsDbModel;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IViewPagerViewModelDelegate;
import org.codebehind.mrslmaintenance.ViewModels.ViewPagerViewModel;

import java.util.ArrayList;


public class EquipmentNewActivity extends ActionBarActivityBase implements IViewPagerViewModelDelegate, IEquipParamDelegate {

    public static final String BUNDLE_EQUIP="EQUIP_NEW_ACTIVITY_SITE_BUNDLE",
                               LOG_TAG="EquipmentNewActivity";
    private Equipment _equip;
    private ArrayList<Equipment> _equips;
    private ViewPagerViewModel _viewPagerVm;
    private EquipmentDbModel _equipDbModel;
    private FragmentMode _fragMode;
    private MenuItem _editMenuItem, _saveMenuItem, _deleteMenuItem;
    private EquipmentParameters _equipParam2bDeleted;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle;
        Equipment newEquip;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_new);

        if (savedInstanceState != null) return;

        _equipDbModel=new EquipmentDbModel(this);

        bundle=getIntent().getExtras();
        if (bundle==null) Log.wtf(LOG_TAG, "onCreate: the bundle is null.");

        _equip=(Equipment)bundle.getSerializable(EquipmentListFragment.BUNDLE_EQUIP);
        if (_equip==null) Log.wtf(LOG_TAG, "onCreate: the bundle doesn't contain an Equip object.");

        if (_equip.getId()>0){

            _fragMode=FragmentMode.VIEW;

            _equips=_equipDbModel.getList();

        }else{

            _fragMode=FragmentMode.NEW;

            newEquip=new Equipment("",1);
            newEquip.setParameterList(new ArrayList<Parameter>());

            _equips=new ArrayList<>();
            _equips.add(newEquip);

        }

        controlFragMode();
    }

    private void controlFragMode(){
        // this controls the mode that the equipNewFragment is presented by recreating the viewPagerVm.
        // it's only separate because it's called from two separate place and causes the getItem event.

        _viewPagerVm=new ViewPagerViewModel(new ViewPager(this), this, _equips.size());
        setContentView(_viewPagerVm.getViewPager());

        for (int i = 0; i < _equips.size(); i++) {

            if (_equips.get(i).getId() == _equip.getId()) {

                _viewPagerVm.setCurrentItem(i);
                break;
            }
        }

    }

    private void controlMenuItems(){

        if (_editMenuItem==null || _saveMenuItem==null || _deleteMenuItem==null){

            Log.wtf(LOG_TAG, "controlMenuItems: The menu items are null.");
            return;
        }

        if (_fragMode==FragmentMode.VIEW){

            _editMenuItem.setVisible(true);
            _saveMenuItem.setVisible(false);
            _deleteMenuItem.setVisible(false);

            return;
        }

        _editMenuItem.setVisible(false);
        _saveMenuItem.setVisible(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_equipment_new, menu);

        _editMenuItem = menu.findItem(R.id.menu_equipment_edit);
        _saveMenuItem = menu.findItem(R.id.menu_equipment_save);
        _deleteMenuItem = menu.findItem(R.id.menu_equipment_delete);

        controlMenuItems();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id;
        EquipmentParamsDbModel equipParamModel;

        id = item.getItemId();

        switch (id){

            case R.id.menu_equipment_edit:

                _fragMode=FragmentMode.EDIT;

                controlFragMode();
                controlMenuItems();

                return true;

            case R.id.menu_equipment_save:
                // the params are already added

                Log.d(LOG_TAG, "onOptionsItemSelected: menu selected save, equipId="+_equip.getId());

                if (_equip.getId()>0) {

                    for(Equipment e: _equips) // this is horrible but it gets back the referenced equipment
                        if (e.getId()==_equip.getId()) _equip=e;

                    _equipDbModel.update(_equip);

                    finish();

                } else {

                    _equip=_equips.get(0);
                    _equip.setId(_equipDbModel.add(_equip));

                    if (_equip.getId()<0) Log.wtf(LOG_TAG, "onOptionsItemSelected: new equip not saved to Db.");

                    _fragMode=FragmentMode.EDIT;

                    controlFragMode();
                }

                return true;

            case R.id.menu_equipment_delete:

                if (_equipParam2bDeleted==null){

                    Log.wtf(LOG_TAG, "onOptionsItemSelected: violation delete menu: private _equipParam2bDeleted not assigned during callback.");
                }

                Log.d(LOG_TAG, "onOptionsItemSelected: menu selected delete, equipId="+_equipParam2bDeleted.getEquipmentId()+", paramId="+_equipParam2bDeleted.getParameterId());

                equipParamModel=new EquipmentParamsDbModel(this);
                equipParamModel.delete(_equipParam2bDeleted.getEquipmentId(), _equipParam2bDeleted.getParameterId());

                // do or die!!!!!
                // pass back the paramlist as well?
                controlFragMode();
                _deleteMenuItem.setVisible(false);

                return true;
        }

        return true;
    }

    // =====Delegates from ViewPageVm=====

    @Override
    public Fragment getItem(int position) {
        EquipmentNewFragment equipNewFragment;
        Equipment equip;

        equip=_equips.get(position);
        equipNewFragment=EquipmentNewFragment.newInstance(equip, _fragMode);

        return equipNewFragment;
    }

    @Override
    public void onPageSelected(int position) {

        _equip = _equips.get(position);
    }

    //=====================================

    @Override
    public void onCallback(EquipmentParameters equipParam) {
        // This is the callback from the frag when a parameter is selected

        if (_fragMode==FragmentMode.VIEW) return;

        _deleteMenuItem.setVisible(true);
        _equipParam2bDeleted=equipParam;
    }


}
