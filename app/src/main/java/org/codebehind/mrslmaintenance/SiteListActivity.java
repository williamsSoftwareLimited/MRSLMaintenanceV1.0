package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Models.ReportModel;
import org.codebehind.mrslmaintenance.Models.SiteModel;


public class SiteListActivity extends ActionBarActivityBase {
    private Menu _menu;

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_site_list, menu);

        _menu=menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        SiteModel siteModel;
        Intent intent;
        ReportModel reportModel;

        siteModel= SiteModel.getInstance();

        switch(id){

            case R.id.menu_site_list_new:

                intent = new Intent(this, ReportActivity.class);
                //intent.putExtra(StaticConstants.EXTRA_REPORT_ID, reportId);
                startActivity(intent);
                return true;
            default: return true;
        }
    }

}
