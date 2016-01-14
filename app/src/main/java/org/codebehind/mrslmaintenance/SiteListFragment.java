package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.codebehind.mrslmaintenance.Adapters.SiteAdapter;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Models.SiteDbModel;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IListViewVmDelegate;
import org.codebehind.mrslmaintenance.ViewModels.ListViewViewModel;

/**
 * Created by Gavin on 18/01/2015.
 */
public class SiteListFragment extends Fragment implements IListViewVmDelegate<Site> {
    private static final String LOG_TAG="SiteListFragment";
    private ListViewViewModel<Site> _siteListViewVm;
    public static final String BUNDLE_SITE="SITE_LIST_FRAGMENT_SITE_BUNDLE";

    public SiteListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_site_list, container, false);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        setControls(getView());
        //setAttributes();
        //setEvents();
    }

    private void setControls(View rootView){
        SiteDbModel siteDbModel;
        SiteAdapter siteAdapter;

        siteDbModel=new SiteDbModel(getActivity());
        siteAdapter=new SiteAdapter(siteDbModel.getList() ,getActivity());

        _siteListViewVm=new ListViewViewModel<>((ListView) rootView.findViewById(R.id.site_list_listview), siteAdapter, this);
    }

    private void setAttributes(){}

    private void setEvents(){}

    @Override
    public void onItemClick(Site selectedSite) {
        Bundle bundle;
        Intent intent;

        Log.d(LOG_TAG, "The selected siteId is " + selectedSite.getId());

        bundle = new Bundle();
        bundle.putSerializable(BUNDLE_SITE, selectedSite);

        intent = new Intent(getActivity(), SiteActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }

}
