package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Abstract.IActAllowDelete;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Models.SiteDbModel;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IViewPagerViewModelDelegate;
import org.codebehind.mrslmaintenance.ViewModels.ViewPagerViewModel;

import java.util.ArrayList;


public class SiteActivity extends ActionBarActivityBase implements IViewPagerViewModelDelegate, IActAllowDelete {

    public static final String BUNDLE_SITE="SITE_ACTIVITY_SITE_BUNDLE";
    private Site _site;
    ArrayList<Site> _sites;
    private ViewPagerViewModel _viewPagerVm;
    private SiteDbModel _siteDbModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle;

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_site);

        if (savedInstanceState != null) return;

        _siteDbModel=new SiteDbModel(this);
        _sites=_siteDbModel.getList();

        _viewPagerVm = new ViewPagerViewModel(new ViewPager(this), this, _sites.size());
        setContentView(_viewPagerVm.getViewPager());

        bundle=getIntent().getExtras();
        _site=(Site)bundle.getSerializable(SiteListFragment.BUNDLE_SITE);


        for (int i = 0; i < _sites.size(); i++) {

            if (_sites.get(i).getId() == _site.getId()) {
                _viewPagerVm.setCurrentItem(i);
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
            bundle.putSerializable(SiteNewActivity.SITE_BUNDLE, _site);

            intent = new Intent(this, SiteNewActivity.class);
            intent.putExtras(bundle);

            startActivity(intent);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // =====Delegates from ViewPageVm=====
    @Override
    public Fragment getItem(int position) {
        SiteNewFragment siteNewFragment;
        Site site;

        site=_sites.get(position);
        siteNewFragment=SiteNewFragment.newInstance(site);
        siteNewFragment.setFragmentMode(FragmentMode.VIEW);
        return siteNewFragment;
    }

    @Override
    public void onPageSelected(int position) {
        _site = _sites.get(position);
    }

    //=====================================

    @Override
    public void showDeleteIcon(boolean b) {
        // this is the delegate from SiteNewFragment
    }

}
