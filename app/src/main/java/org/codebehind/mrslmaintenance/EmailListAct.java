package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;


/**
 * Created by root on 18/02/16.
 */
public class EmailListAct extends ActionBarActivityBase {

    private Menu _menu;
    private EmailListFrag _frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FragmentTransaction ft;

        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_email_list);

        if (savedInstanceState != null) return; // if this has been created before then don't recreate

        _frag=new EmailListFrag();

        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.act_email_list_container, _frag);
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_email_list, menu);

        _menu=menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id;


        id = item.getItemId();

        switch(id) {

            case R.id.menu_email_list_edit:

                _frag.showBox();
                return true;

            case R.id.menu_email_list_save:

                return true;

            case R.id.menu_email_list_delete:



                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
