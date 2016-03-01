package org.codebehind.mrslmaintenance.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.codebehind.mrslmaintenance.Abstract.IEmailCallback;
import org.codebehind.mrslmaintenance.Adapters.Abstract.AbstractAdapter;
import org.codebehind.mrslmaintenance.Entities.Email;
import org.codebehind.mrslmaintenance.R;
import org.codebehind.mrslmaintenance.Services.Abstract.IEmailService;

import java.util.ArrayList;

/**
 * Created by root on 18/02/16.
 */
public class EmailSelectorAdapter extends AbstractAdapter<Email> {

    private static final String LOG_TAG="EmailSelectorAdapter";
    private IEmailCallback _callback;

    public EmailSelectorAdapter(ArrayList<Email> reports, Activity activity, IEmailCallback callback){

        super(activity, android.R.layout.simple_list_item_1, reports);

        if (callback==null) Log.wtf(LOG_TAG, "construct: violation callback arg is null.");

        _callback=callback;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Email e;
        TextView tvEmail;
        CheckBox cbDel;
        ToggleButton tbSel;

        // if we weren't given a view, inflate one
        if (null == convertView)
            convertView = _activity.getLayoutInflater().inflate(R.layout.ada_email_item, null);

        e = getItem(position);

        tvEmail=(TextView)convertView.findViewById(R.id.ada_email_item_tv);
        tvEmail.setText(e.getEmailAddress());

        tbSel=(ToggleButton)convertView.findViewById(R.id.ada_email_item_tb);
        cbDel=(CheckBox)convertView.findViewById(R.id.ada_email_del_cb);

        tbSel.setChecked(e.getSelected());

        tbSel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ToggleButton tb;

                tb = (ToggleButton) v;

                e.setSelected(tb.isChecked());
                _callback.onCallback(e, 1);
            }
        });

        cbDel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CheckBox cb;

                cb=(CheckBox)v;

                e.setDeleted(cb.isChecked());
                _callback.onCallback(e, 2);
            }
        });

        return convertView;
    }

}
