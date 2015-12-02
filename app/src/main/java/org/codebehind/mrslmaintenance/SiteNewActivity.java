package org.codebehind.mrslmaintenance;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.gms.maps.model.LatLng;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Abstract.IFragmentLocationCallback;
import org.codebehind.mrslmaintenance.Abstract.IFragmentSiteCallback;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Models.SiteModel;

import java.util.UUID;

/**
 * Created by Gavin on 02/02/2015.
 */
// Eventhough this the called SiteNewActivity it handles the Location, Alter, New and NewTemplate fragments
public class SiteNewActivity extends ActionBarActivityBase implements IFragmentSiteCallback, IFragmentLocationCallback {
    Menu _menu;
    UUID _reportId;
    UUID _siteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FragmentTransaction ft;
        SiteNewFragment siteNewFragment;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_new);

        _reportId=(UUID)getIntent().getSerializableExtra(StaticConstants.EXTRA_REPORT_ID);
        _siteId=(UUID) getIntent().getSerializableExtra(StaticConstants.EXTRA_SITE_ID);

        if (savedInstanceState == null) {
            ft= getSupportFragmentManager().beginTransaction();
            siteNewFragment=new SiteNewFragment();
            siteNewFragment.setSiteId(_siteId);
            ft.add(R.id.activity_site_new_container, siteNewFragment);
            //ft.setTransition() look into me!

            ft.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_site, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    @Override
    public void onFragmentCallback(Site site) {

    }

    @Override
    public void onFragmentCallbackLocation(UUID siteId) {
        LocationFragment mappyFragment;
        FrameLayout waitLayout;
        FragmentTransaction ft;

        mappyFragment = new LocationFragment();
        mappyFragment.setSiteId(siteId);

        // it takes a little while to display the map so put up the waiting little message
        waitLayout=(FrameLayout)this.findViewById(R.id.site_list_waitFrame);
        waitLayout.setVisibility(FrameLayout.VISIBLE);

        ft= getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);
        ft.replace(R.id.activity_site_new_container, mappyFragment);
        ft.commit();
    }

    @Override
    public void onFragmentCallbackLatLong(LatLng latlng) {
        Site site;

        site = SiteModel.getInstance().getSite(_siteId);
        site.setLatLng(latlng);
        getSupportFragmentManager().popBackStack();
    }
}
