package org.codebehind.mrslmaintenance;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Models.SiteDbModel;

import java.security.PublicKey;


/**
 * Created by Gavin on 02/02/2015.
 */
public class SiteNewActivity extends ActionBarActivityBase {

    private Site _site;
    private static final String SAVED_NOTICE="Site saved to database.",
                                LOG_TAG="SiteNewActivity",
                                NEW_SITE="New Site",
                                EDIT_SITE="Edit Site"        ;
    public static final String SITE_BUNDLE="SITE_NEW_ACTIVITY_BUNDLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FragmentTransaction ft;
        SiteNewFragment siteNewFragment;
        Bundle bundle;

        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) return;

        setContentView(R.layout.activity_site_new);

        // check if the bundle has a Site from the SiteActivity and this is to be edited
        bundle=getIntent().getExtras();

        if (bundle==null)
            Log.wtf(LOG_TAG, "onCreate: Violation bundle==null, must contain Site instance, tag=SiteNewActivity.SITE_BUNDLE.");
         else
            _site = (Site) bundle.getSerializable(SiteNewActivity.SITE_BUNDLE);

        if (_site==null)
            Log.wtf(LOG_TAG, "onCreate: Violation bundle must contain Site instance (Site==null), tag=SiteNewActivity.SITE_BUNDLE.");

        // it the _Site is null then this will crash
        if (_site.getId()==-1) setTitle(NEW_SITE);
        else setTitle(EDIT_SITE);

        ft=getSupportFragmentManager().beginTransaction();

        siteNewFragment=SiteNewFragment.newInstance(_site);
        siteNewFragment.setFragmentMode(FragmentMode.NEW);

        ft.add(R.id.activity_site_new_container, siteNewFragment);

        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_new_site, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id;
        SiteDbModel siteDbModel;

        id = item.getItemId();
        siteDbModel=new SiteDbModel(this);

        switch(id){

            case R.id.menu_site_new_save:

                if (_site.getId()==-1)
                    siteDbModel.add(_site);
                else
                    siteDbModel.update(_site);

                Toast.makeText(this, SAVED_NOTICE, Toast.LENGTH_SHORT).show();

                return true;

            default: return true;
        }
    }

}
