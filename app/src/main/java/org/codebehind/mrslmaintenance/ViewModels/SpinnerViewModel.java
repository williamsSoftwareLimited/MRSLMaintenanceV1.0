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

    public void setSpinnerAdapter(SpinnerAdapter spinnerAdapter){
        _spinner.setAdapter(spinnerAdapter);
        _spinnerAdapter=spinnerAdapter;
    }

    public SpinnerAdapter getSpinnerAdapter(){
        return _spinnerAdapter;
    }

    public SpinnerViewModel(Spinner spinner, final ISpinnerViewModelDelegate spinnerViewModelDelegate){

        _spinner=spinner;
        //_siteSpinner.setBackgroundResource(R.drawable.edit_text_highlight_box);

        _spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // this changes the equipment list on the fly
                if (position >= 0) {
                   spinnerViewModelDelegate.itemSelected(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


}
