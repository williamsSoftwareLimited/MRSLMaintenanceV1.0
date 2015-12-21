package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.codebehind.mrslmaintenance.Adapters.SiteAdapter;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Models.SiteDbModel;

/**
 * Created by Gavin on 18/01/2015.
 */
public class SiteListFragment extends Fragment {
    private static final String LOG_TAG="SiteListFragment";
    private ListView _siteListView;
    private SiteAdapter _siteAdapter;
    public static final String BUNDLE_SITE="SITELISTFRAGMENT_SITE_BUNDLE";

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
        setAttributes();
        setEvents();
    }

    private void setControls(View rootView){
        _siteListView = (ListView) rootView.findViewById(R.id.site_list_listview);
    }

    private void setAttributes(){
        SiteDbModel siteDbModel;

        siteDbModel=new SiteDbModel(getActivity());
        _siteAdapter=new SiteAdapter(siteDbModel.getList() ,getActivity());
        _siteListView.setAdapter(_siteAdapter);
    }

    private void setEvents(){

        _siteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Site selectedSite;
                Bundle bundle;
                Intent intent;

                selectedSite=_siteAdapter.getItem(position);
                Log.d(LOG_TAG,"The selected siteId is "+selectedSite.getId());

                bundle=new Bundle();
                bundle.putSerializable(BUNDLE_SITE, selectedSite);

                intent=new Intent(getActivity(), SiteActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }

}
