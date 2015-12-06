package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;


public class SiteListActivity extends ActionBarActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FragmentTransaction fragmentTransaction;
        SiteListFragment siteListFragment;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_list);
        if (savedInstanceState != null) return; // if ths has been created before then don't recreate

        fragmentTransaction=getSupportFragmentManager().beginTransaction();
        siteListFragment=new SiteListFragment();
        fragmentTransaction.add(R.id.activity_site_list_container, siteListFragment);

        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_site_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        switch(id){

            case R.id.menu_site_list_new:

                intent = new Intent(this, SiteNewActivity.class);
                startActivity(intent);

                return true;

            default: return true;
        }
    }

}
