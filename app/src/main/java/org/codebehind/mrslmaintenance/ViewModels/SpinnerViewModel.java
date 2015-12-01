package org.codebehind.mrslmaintenance.ViewModels;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import org.codebehind.mrslmaintenance.ViewModels.Abstract.ISpinnerViewModelDelegate;

/**
 * Created by root on 26/11/15.
 */
public class SpinnerViewModel {

    private static final String LOG_TAG="SpinnerViewModel";
    private Spinner _spinner;
    private SpinnerAdapter _spinnerAdapter;
    private ISpinnerViewModelDelegate _spinnerViewModelDelegate; // this is used to call the calling classes delegate method

    public void setSpinnerAdapter(SpinnerAdapter spinnerAdapter){
        _spinner.setAdapter(spinnerAdapter);
        _spinnerAdapter=spinnerAdapter;
    }

    public void setEnabled(Boolean b) {
        _spinner.setEnabled(b);
    }

    public SpinnerAdapter getSpinnerAdapter(){
        return _spinnerAdapter;
    }

    public SpinnerViewModel(Spinner spinner, final ISpinnerViewModelDelegate spinnerViewModelDelegate){

        _spinner=spinner;
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
