package org.codebehind.mrslmaintenance.ViewModels;

import android.widget.TextView;

import org.codebehind.mrslmaintenance.ViewModels.Abstract.IEditTextViewModelDelegate;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by root on 01/12/15.
 */
public class DateEditTextViewModel extends EditTextViewModel {

    public DateEditTextViewModel(TextView textViewType, final IEditTextViewModelDelegate editTextViewModelDelegate) {
        super(textViewType, editTextViewModelDelegate);
    }

    public void setText(Date date){
        _editText.setText(DateFormat.getDateInstance().format(date));
    }
}
