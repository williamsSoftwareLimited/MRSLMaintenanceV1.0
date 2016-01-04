package org.codebehind.mrslmaintenance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Models.EquipmentDbModel;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IViewPagerViewModelDelegate;
import org.codebehind.mrslmaintenance.ViewModels.ViewPagerViewModel;

import java.util.ArrayList;


public class EquipmentNewActivity extends ActionBarActivityBase implements IViewPagerViewModelDelegate {

    public static final String BUNDLE_EQUIP="EQUIP_NEW_ACTIVITY_SITE_BUNDLE",
                               LOG_TAG="EquipmentNewActivity";
    private Equipment _equip;
    ArrayList<Equipment> _equips;
    private ViewPagerViewModel _viewPagerVm;
    private EquipmentDbModel _equipDbModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_new);

        if (savedInstanceState != null) return;

        _equipDbModel=new EquipmentDbModel(this);
        _equips=_equipDbModel.getList();

        _viewPagerVm=new ViewPagerViewModel(new ViewPager(this), this, _equips.size());
        setContentView(_viewPagerVm.getViewPager());

        bundle=getIntent().getExtras();
        if (bundle==null) Log.wtf(LOG_TAG, "onCreate: the bundle is null.");

        _equip=(Equipment)bundle.getSerializable(EquipmentListFragment.BUNDLE_EQUIP);

        if (_equip==null) Log.wtf(LOG_TAG, "onCreate: the bundle doesn't contain an Equip object.");


        for (int i = 0; i < _equips.size(); i++) {

            if (_equips.get(i).getId() == _equip.getId()) {
                _viewPagerVm.setCurrentItem(i);
                break;
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_equipment_new, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id;

        id = item.getItemId();

        switch (id){

            case R.id.menu_equipment_edit:
                // check the equipment is valid
                //equipmentNewFragment.addParameter();
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
        equipNewFragment=EquipmentNewFragment.newInstance(equip);
        equipNewFragment.setFragmentMode(FragmentMode.VIEW);

        return equipNewFragment;
    }

    @Override
    public void onPageSelected(int position) {
        _equip = _equips.get(position);
    }

    //=====================================
}
