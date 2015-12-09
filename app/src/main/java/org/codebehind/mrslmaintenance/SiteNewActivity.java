package org.codebehind.mrslmaintenance;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Models.SiteDbModel;


/**
 * Created by Gavin on 02/02/2015.
 */
public class SiteNewActivity extends ActionBarActivityBase {

    private Site _site;
    private static final String EDIT_SITE="Edit Site",
                                SAVED_NOTICE="Site saved to database.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FragmentTransaction ft;
        SiteNewFragment siteNewFragment;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_new);

        if (savedInstanceState == null) {

            ft= getSupportFragmentManager().beginTransaction();

            initializeSite();

            siteNewFragment=SiteNewFragment.newInstance(_site);
            siteNewFragment.setFragmentMode(FragmentMode.NEW);

            ft.add(R.id.activity_site_new_container, siteNewFragment);

            ft.commit();
        }
    }

    private void initializeSite(){
        Bundle bundle;

        // check if the bundle has a Site from the SiteActivity and this is to be edited
        bundle=getIntent().getExtras();

        if (bundle!=null) _site=(Site)bundle.getSerializable(SiteActivity.BUNDLE_SITE);

        if (_site==null)  _site = new Site("","");
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
