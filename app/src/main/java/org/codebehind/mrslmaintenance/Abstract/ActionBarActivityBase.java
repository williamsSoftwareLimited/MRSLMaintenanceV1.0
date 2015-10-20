package org.codebehind.mrslmaintenance.Abstract;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import org.codebehind.mrslmaintenance.R;

/**
 * Created by Gavin on 10/01/2015.
 */
public abstract class ActionBarActivityBase  extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }
    // this method may never be used - second attempt in EquipmentCameraActivity
    protected void changeFragment(int containerId, Fragment newFragment){
        FragmentTransaction ft;
        ft = getSupportFragmentManager().beginTransaction();
        //ft.addToBackStack(null);
        ft.replace(containerId, newFragment);
        ft.commit();
    }
}
