package org.codebehind.mrslmaintenance;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.codebehind.mrslmaintenance.Abstract.Fragment2ActivityListener;
import org.codebehind.mrslmaintenance.Abstract.FragmentMenubarListener;
import org.codebehind.mrslmaintenance.Abstract.IFragmentCallbackUUID;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.FragmentMenubarHelper;
import org.codebehind.mrslmaintenance.Models.ReportModel;
import org.codebehind.mrslmaintenance.Models.SiteModel;
import org.codebehind.mrslmaintenance.R;

import java.util.ArrayList;
import java.util.MissingFormatArgumentException;
import java.util.UUID;

/**
 * Created by Gavin on 18/01/2015.
 */
public class SiteListFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView _siteListView;
    private ArrayList<Site> _actionList; // this is the list that actions will be carried out on
    private IFragmentCallbackUUID _listener;

    public SiteListFragment() {
        _actionList = new ArrayList<>();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            _listener = (IFragmentCallbackUUID) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement IFragmentCallbackUUID");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;

        rootView = inflater.inflate(R.layout.fragment_site_list, container, false);
        _siteListView = (ListView) rootView.findViewById(R.id.site_list_listview);
        _siteListView.setAdapter(new siteAdapter(SiteModel.getInstance().getList()));
        _siteListView.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // just report back to the activity the SiteUUID that was selected
        UUID siteId;
        View v = _siteListView.getChildAt(position);
        // set the _listView colours back to normal
        for (int i=0; i<_siteListView.getCount();i++)
            _siteListView.getChildAt(i).setBackgroundColor(0);
        v.setBackgroundColor(Color.WHITE);

        siteId = SiteModel.getInstance().getList().get(position).getUUID();
        _listener.onFragmentCallback(siteId);
    }
    class siteAdapter extends ArrayAdapter<Site> {
        public siteAdapter(ArrayList<Site> siteArraylist) {
            super(getActivity(), android.R.layout.simple_list_item_1, siteArraylist);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Site site;
            TextView name, description, address;
            ImageView locationView;
            if (null == convertView) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.site_list_listitem, null);
            }
            site = getItem(position);
            name = (TextView) convertView.findViewById((R.id.site_list_item_nametextview));
            name.setText(site.getName());
            locationView = (ImageView) convertView.findViewById(R.id.site_list_item_imagebutton);
            locationView.setImageResource(R.drawable.ic_action_picture);
            description = (TextView) convertView.findViewById((R.id.site_list_item_descriptiontextview));
            description.setText(site.getDescription());
            address = (TextView) convertView.findViewById((R.id.site_list_item_addresstextview));
            address.setText(site.getAddress());
            return convertView;
        }
    }
}
