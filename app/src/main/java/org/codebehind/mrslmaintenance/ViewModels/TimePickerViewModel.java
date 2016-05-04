package org.codebehind.mrslmaintenance.ViewModels;

import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by root on 04/05/16.
 */
public class TimePickerViewModel {

    private static final String LOG_TAG="DatePickerViewModel";
    private EditTextViewModel _timeEtVm;
    private Button _selectBtn;
    private int _hour, _minute;
    private Context _context;
    private Boolean _24hr=true; // not sure if I'll allow this to be changed! (it's my ball!!!!)

    public void setTime(int hour, int minute){
        String sHr=hour>9?""+hour:"0"+hour;
        String sMn=minute>9?""+minute:"0"+minute;
        _timeEtVm.setText(sHr+":"+sMn);
    }

    public TimePickerViewModel(Context context,EditTextViewModel timeEtVm, Button selectBtn){
        if(context==null) Log.wtf(LOG_TAG, "ctor: arg violation context can't be null");
        if(timeEtVm==null) Log.wtf(LOG_TAG, "ctor: arg violation timeEtVm can't be null");
        if(selectBtn==null) Log.wtf(LOG_TAG, "ctor: arg violation selectBtn can't be null");

        _context=context;
        _timeEtVm=timeEtVm;
        _selectBtn=selectBtn;

        // I've made these almost global and I quite like them now!
        //setControls();
        setAttributes();
        setEvents();
    }

    private void setAttributes(){
        _timeEtVm.setNonEditable();
    }

    private void setEvents(){
        _selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                _hour = c.get(Calendar.HOUR);
                _minute = c.get(Calendar.MINUTE);

                // show the time dialog and wait for input
                TimePickerDialog timePickerDialog = new TimePickerDialog(_context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        setTime(hourOfDay, minute);
                    }
                }, _hour, _minute, _24hr); // nb the 24hr clock is set here

                //timePickerDialog. getTimePicker().setSpinnersShown(false);
                timePickerDialog.show();
            }
        });
    }
}
