package org.codebehind.mrslmaintenance;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Abstract.IFragmentSiteCallback;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Models.SiteModel;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IEditTextViewModelDelegate;
import org.codebehind.mrslmaintenance.ViewModels.EditTextViewModel;

import java.util.UUID;


public class SiteNewFragment extends Fragment implements IEditTextViewModelDelegate {

    private EditTextViewModel _nameEditTextVm, _addressEditTextVm;
    private Site _site;
    private FragmentMode _fragmentMode; // 1 for edit and anything

    public void setSite(Site site){
        _site=site;
    }

    public void setFragmentMode(FragmentMode fragmentMode){
        _fragmentMode=fragmentMode;
    }

    public SiteNewFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;

        view= inflater.inflate(R.layout.fragment_site_new, container, false);

        setControls(view);
        setAttributes();
        setEvents();

        return view;
    }
    private void setControls(View rootView){

        _nameEditTextVm=new EditTextViewModel((EditText)rootView.findViewById(R.id.site_new_name), this);
        _addressEditTextVm=new EditTextViewModel((EditText)rootView.findViewById(R.id.site_new_Address), this);
    }

    private void setAttributes(){

        if (_fragmentMode==FragmentMode.VIEW) {

            _nameEditTextVm.setNonEditable();
            _addressEditTextVm.setNonEditable();
        }

        _nameEditTextVm.setText(_site.getName());
        _addressEditTextVm.setText(_site.getAddress());
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
