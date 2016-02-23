package org.codebehind.mrslmaintenance;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;


/**
 * Created by root on 18/02/16.
 */
public class EmailListAct extends ActionBarActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FragmentTransaction ft;

        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_email_list);

        if (savedInstanceState != null) return; // if this has been created before then don't recreate

        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.act_email_list_container, new EmailListFrag());
        ft.commit();
    }

}
