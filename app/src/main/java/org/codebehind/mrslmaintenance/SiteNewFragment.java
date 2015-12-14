package org.codebehind.mrslmaintenance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Abstract.IFragmentSiteCallback;
import org.codebehind.mrslmaintenance.Adapters.EquipmentAdapter;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Models.EquipmentDbModel;
import org.codebehind.mrslmaintenance.Models.SiteModel;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IEditTextViewModelDelegate;
import org.codebehind.mrslmaintenance.ViewModels.EditTextViewModel;

import java.util.UUID;


public class SiteNewFragment extends Fragment implements IEditTextViewModelDelegate {

    private static final String SITE_NEW_FRAGMENT_BUNDLE="SiteNewFragment_Bundle",
                                LOG_TAG="SiteNewFragment";
    private EditTextViewModel _nameEditTextVm, _addressEditTextVm;
    private ListView _equipmentListView;
    private Site _site;
    private FragmentMode _fragmentMode;

    public void setFragmentMode(FragmentMode fragmentMode){
        _fragmentMode=fragmentMode;
    }

    public SiteNewFragment() { }

    public static SiteNewFragment newInstance(Site site){
        Bundle bundle;
        SiteNewFragment siteNewFragment;

        bundle = new Bundle();
        bundle.putSerializable(SITE_NEW_FRAGMENT_BUNDLE, site);

        siteNewFragment=new SiteNewFragment();
        siteNewFragment.setArguments(bundle);
        return siteNewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;

        view= inflater.inflate(R.layout.fragment_site_new, container, false);

        _site=(Site)getArguments().getSerializable(SITE_NEW_FRAGMENT_BUNDLE);

        setControls(view);
        setAttributes();
        setEvents();

        return view;
    }
    private void setControls(View rootView){

        _nameEditTextVm=new EditTextViewModel((EditText)rootView.findViewById(R.id.site_new_name), this);
        _addressEditTextVm=new EditTextViewModel((EditText)rootView.findViewById(R.id.site_new_Address), this);
        _equipmentListView=(ListView) rootView.findViewById(R.id.site_new_equipment_listview);
    }

    private void setAttributes(){
        EquipmentDbModel equipmentDbModel;

        equipmentDbModel=new EquipmentDbModel(getActivity());

        if (_fragmentMode==FragmentMode.VIEW) {

            _nameEditTextVm.setNonEditable();
            _addressEditTextVm.setNonEditable();
        }

        _nameEditTextVm.setText(_site.getName());
        _addressEditTextVm.setText(_site.getAddress());

        _equipmentListView.setAdapter(new EquipmentAdapter(equipmentDbModel.getList(_site.getId()), getActivity()));
    }

    private void setEvents() {

    }

    @Override
    public void textUpdated(int uniqueId, String text) {

        switch (uniqueId){

            case R.id.site_new_name:

                _site.setName(text);
                break;

            case R.id.site_new_Address:

                _site.setAddress(text);
                break;
        }
    }
}
