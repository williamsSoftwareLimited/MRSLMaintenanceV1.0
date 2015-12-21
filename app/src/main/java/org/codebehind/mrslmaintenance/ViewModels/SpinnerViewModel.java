package org.codebehind.mrslmaintenance.ViewModels;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import org.codebehind.mrslmaintenance.Adapters.Abstract.ASpinnerAdapter;
import org.codebehind.mrslmaintenance.Entities.Abstract.AEntity;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.ISpinnerViewModelDelegate;

/**
 * Created by root on 21/12/15.
 */
public class SpinnerViewModel<t> {

    protected Spinner _spinner;
    protected ISpinnerViewModelDelegate _spinnerViewModelDelegate; // this is used to call the calling classes delegate method
    protected ASpinnerAdapter<t> _spinnerAdapter;

    public ASpinnerAdapter<t> getSpinnerAdapter(){
        return _spinnerAdapter;
    }

    public void setId(int id){
        int position;
        AEntity aEntity;

        position=0;
        for(t entity : _spinnerAdapter.getList()){

            aEntity =(AEntity)entity; // uncomfortable cast (Ensure entity has inherited from AEntity)

            if (id==aEntity.getId()) break;
            position++;
        }
        if (position>=_spinnerAdapter.getList().size()) position=0; // just in-case it's not found

        _spinner.setSelection(position);
    }

    public SpinnerViewModel(Spinner spinner, ASpinnerAdapter<t> spinnerAdapter, final ISpinnerViewModelDelegate spinnerViewModelDelegate){

        _spinner=spinner;

        _spinnerViewModelDelegate=spinnerViewModelDelegate;

        _spinner.setAdapter(spinnerAdapter);
        _spinnerAdapter=spinnerAdapter;

        //setAttributes();
        setEvents();
    }

    public void setEnabled(Boolean b) {
        _spinner.setEnabled(b);
    }

    private void setAttributes(){

    }

    private void setEvents(){

        _spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                _spinnerViewModelDelegate.itemSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

}
