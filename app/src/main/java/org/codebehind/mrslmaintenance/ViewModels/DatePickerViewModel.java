package org.codebehind.mrslmaintenance.ViewModels;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by root on 05/04/16.
 */
public class DatePickerViewModel {

    private static final String LOG_TAG="DatePickerViewModel";
    private EditTextViewModel _dateEtVm;
    private Button _selectBtn;
    private int mYear, mMonth, mDay;
    private Context _context;


    public void setDate(int year, int monthOfYear, int dayOfMonth){
        _dateEtVm.setText(
                (dayOfMonth<10?"0":"")
                        + dayOfMonth
                        + "-"
                        + (monthOfYear<10?"0":"") // put the zero before
                        + monthOfYear
                        + "-"
                        + year);
    }

    public DatePickerViewModel(Context context,EditTextViewModel dateEtVm, Button selectBtn){
        if(context==null) Log.wtf(LOG_TAG, "ctor: arg violation context can't be null");
        if(dateEtVm==null) Log.wtf(LOG_TAG, "ctor: arg violation dateEtVm can't be null");
        if(selectBtn==null) Log.wtf(LOG_TAG, "ctor: arg violation selectBtn can't be null");

        _context=context;
        _dateEtVm=dateEtVm;
        _selectBtn=selectBtn;

        // I've made these almost global and I quite like them now!
        //setControls();
        setAttributes();
        setEvents();
    }

    private void setAttributes(){
        _dateEtVm.setNonEditable();
    }

    private void setEvents(){
        _selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                // show the date dialog and wait for input
                DatePickerDialog datePickerDialog = new DatePickerDialog(_context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // the date been picked so fill in the data edittext
                                setDate(year,monthOfYear+1,dayOfMonth);// monthOfYear seems like its goes from {0,1,...
                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.getDatePicker().setSpinnersShown(false);
                datePickerDialog.show();
            }
        });
    }
}
