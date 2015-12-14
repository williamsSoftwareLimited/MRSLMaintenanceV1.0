package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Entities.Site;


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
        Bundle bundle;
        Intent intent;
        int id = item.getItemId();

        switch(id){

            case R.id.menu_site_list_new:

                bundle = new Bundle();
                bundle.putSerializable(SiteNewActivity.SITE_BUNDLE, new Site("",""));

                intent = new Intent(this, SiteNewActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);

                return true;

            default: return true;
        }
    }

}
