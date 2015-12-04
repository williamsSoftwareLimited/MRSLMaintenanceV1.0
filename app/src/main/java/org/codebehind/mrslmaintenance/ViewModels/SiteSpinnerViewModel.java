package org.codebehind.mrslmaintenance.ViewModels;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import org.codebehind.mrslmaintenance.Adapters.SiteAdapter;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IEditTextViewModelDelegate;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.ISpinnerViewModelDelegate;

/**
 * Created by root on 26/11/15.
 */
public class SiteSpinnerViewModel {

    private static final String LOG_TAG="SiteSpinnerViewModel";
    private Spinner _spinner;
    private SiteAdapter _spinnerAdapter;
    private ISpinnerViewModelDelegate _spinnerViewModelDelegate; // this is used to call the calling classes delegate method

    public SiteAdapter getSpinnerAdapter(){
        return _spinnerAdapter;
    }

    public void setSiteId(int siteId){
        int sitePosition;

        sitePosition=0;
        for(Site site : _spinnerAdapter.getSiteList()){
            if (siteId==site.getId()) break;
            sitePosition++;
        }
        if (sitePosition>=_spinnerAdapter.getSiteList().size()) sitePosition=0; // just in-case it's not found

        _spinner.setSelection(sitePosition);
    }

    public void setEnabled(Boolean b) {
        _spinner.setEnabled(b);
    }

    public SiteSpinnerViewModel(Spinner spinner, SiteAdapter spinnerAdapter, final ISpinnerViewModelDelegate spinnerViewModelDelegate){

        _spinner=spinner;
        _spinner.setAdapter(spinnerAdapter);
        _spinnerAdapter=spinnerAdapter;

       _spinnerViewModelDelegate=spinnerViewModelDelegate;

        setAttributes();
        setEvents();
    }

    private void setAttributes(){

        //_siteSpinner.setBackgroundResource(R.drawable.edit_text_highlight_box);
    }

    private void setEvents(){
        _spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position >= 0) {
                    _spinnerViewModelDelegate.itemSelected(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

}
