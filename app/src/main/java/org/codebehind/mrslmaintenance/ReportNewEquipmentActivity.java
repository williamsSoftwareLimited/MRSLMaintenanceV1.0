package org.codebehind.mrslmaintenance;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Singletons.ReportSingleton;

public class ReportNewEquipmentActivity extends ActionBarActivityBase {
    private ViewPager _viewPager;
    private ReportSingleton _reportSingleton;
    private Report _report;
    private Equipment _equipment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FragmentManager fragmentManager;

        super.onCreate(savedInstanceState);

        _viewPager=new ViewPager(this);
        _viewPager.setId(R.id.report_new_equipment_view_pager);
        setContentView(_viewPager);
        if (savedInstanceState != null) return; // if ths has been created before then don't recreate

        _reportSingleton=ReportSingleton.getInstance();
        _report=_reportSingleton.getReport();
        _equipment=_reportSingleton.getEquipment();

        fragmentManager=getSupportFragmentManager();

        _viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Equipment equipment;

                equipment = _report.getEquipmentList().get(position);
                _reportSingleton.setEquipment(equipment);

                return new ReportNewEquipmentFragment();
            }

            @Override
            public int getCount() {
                return _report.getEquipmentList().size();
            }

        });

        for (int i = 0; i < _report.getEquipmentList().size(); i++) {

            if (_report.getEquipmentList().get(i).getId() == _equipment.getId()) {

                _viewPager.setCurrentItem(i);
                break;
            }
        } // end for loop
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_equipment_new_parameters, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id;

        id = item.getItemId();

        switch(id) {

            case R.id.menu_new_equipment_return:

                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
