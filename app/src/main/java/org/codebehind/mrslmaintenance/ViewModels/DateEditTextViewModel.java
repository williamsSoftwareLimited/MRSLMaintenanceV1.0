package org.codebehind.mrslmaintenance.ViewModels;

import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by root on 01/12/15.
 */
public class DateEditTextViewModel extends EditTextViewModel {

    public DateEditTextViewModel(TextView textViewType) {
        super(textViewType);
    }

    public void setText(Date date){
        _editText.setText(DateFormat.getDateInstance().format(date));
    }
}
