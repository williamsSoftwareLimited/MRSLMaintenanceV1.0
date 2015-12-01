package org.codebehind.mrslmaintenance.ViewModels;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.Singletons.ReportSingleton;

import java.util.ArrayList;

/**
 * Created by root on 28/11/15.
 */
public class ParameterEditTextViewModel extends EditTextViewModel {

    private static final int MIN_TEXT_LENGTH=2; //the is ems
    private Parameter _parameter;

    public void setParameter(Parameter parameter){
        _parameter=parameter;
    }

    public ParameterEditTextViewModel(TextView textViewType) {
        super(textViewType);
        setAttributes();
        setEvent();
    }

    private void setAttributes(){
        _editText.setMinEms(MIN_TEXT_LENGTH);
    }

    private void setEvent(){

        _editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;

                if (actionId == EditorInfo.IME_ACTION_NEXT) {

                    _parameter.setNewValue(v.getText().toString());
                    handled = false;
                }
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    _parameter.setNewValue(v.getText().toString());
                    handled = false;
                }
                return handled;
            }
        });

    }

}
