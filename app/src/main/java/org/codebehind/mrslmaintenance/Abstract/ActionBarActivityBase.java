package org.codebehind.mrslmaintenance.Abstract;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.inputmethod.InputMethodManager;

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

        // hide the keyboard, http://stackoverflow.com/questions/18977187/how-to-hide-soft-keyboard-when-activity-starts
        if(getCurrentFocus()!=null) {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        // set the orientation to portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
