package org.codebehind.mrslmaintenance.Abstract;


import android.support.v4.app.Fragment;

/**
 * Created by Gavin on 18/01/2015.
 */
public interface Fragment2ActivityListener {
    public void onFragmentCallback(String nextFragment);// use this piece of shit for testing

    // Fully furnish the fragment with everything it needs before calling this and get the activity
    // to replace the current fragment
    public void onFragmentCallback(Fragment fragment);
}
