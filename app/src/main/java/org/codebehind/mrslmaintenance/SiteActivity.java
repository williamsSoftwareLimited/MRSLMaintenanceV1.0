package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Models.SiteDbModel;

import java.util.ArrayList;


public class SiteActivity extends ActionBarActivityBase {

    public static final String BUNDLE_SITE="org.codebehind.SiteActivity_Site_Bundle";
    private Site _site;
    private ViewPager _viewPager;
    private SiteDbModel _siteDbModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle;
        final ArrayList<Site> sites;
        FragmentManager fragmentManager;

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_site);

        if (savedInstanceState != null) return;

        _siteDbModel=new SiteDbModel(this);
        sites=_siteDbModel.getList();

        _viewPager = new ViewPager(this);
        _viewPager.setId(R.id.viewPagerSiteActivity);
        setContentView(_viewPager);

        bundle=getIntent().getExtras();
        _site=(Site)bundle.getSerializable(SiteListFragment.BUNDLE_SITE);

        fragmentManager=getSupportFragmentManager();

        _viewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public int getCount() {
                return sites.size();
            }

            @Override
            public Fragment getItem(int pos) {
                SiteNewFragment siteNewFragment;
                Site site;

                site=sites.get(pos);
                siteNewFragment=SiteNewFragment.newInstance(site);
                siteNewFragment.setFragmentMode(FragmentMode.VIEW);
                return siteNewFragment;
            }
        });

        _viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {

                _site = sites.get(position);
            }

        });

        for (int i = 0; i < sites.size(); i++) {

            if (sites.get(i).getId() == _site.getId()) {
                _viewPager.setCurrentItem(i);
                break;
            }
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
