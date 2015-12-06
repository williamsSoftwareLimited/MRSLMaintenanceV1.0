package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Entities.Site;


public class SiteActivity extends ActionBarActivityBase {

    public static final String BUNDLE_SITE="org.codebehind.SiteActivity_Site_Bundle";
    private Site _site;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FragmentTransaction fragmentTransaction;
        Bundle bundle;
        SiteNewFragment siteNewFragment;

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_site);

        if (savedInstanceState == null) {

            fragmentTransaction=getSupportFragmentManager().beginTransaction();

            siteNewFragment=new SiteNewFragment();
            bundle=getIntent().getExtras();
            _site=(Site)bundle.getSerializable(SiteListFragment.BUNDLE_SITE);
            siteNewFragment.setSite(_site);
            siteNewFragment.setFragmentMode(FragmentMode.VIEW);

            fragmentTransaction.add(R.id.activity_site_container, siteNewFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_site, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id;
        Bundle bundle;
        Intent intent;

        id = item.getItemId();

        if (id == R.id.menu_site_edit) {

            bundle = new Bundle();
            bundle.putSerializable(BUNDLE_SITE, _site);

            intent = new Intent(this, SiteNewActivity.class);
            intent.putExtras(bundle);

            startActivity(intent);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
