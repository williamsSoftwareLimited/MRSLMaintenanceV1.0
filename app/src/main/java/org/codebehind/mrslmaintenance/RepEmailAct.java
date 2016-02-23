package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Services.Abstract.IEmailService;
import org.codebehind.mrslmaintenance.Services.EmailService;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IListViewVmDelegate;

/**
 * Created by root on 11/02/16.
 */
public class RepEmailAct  extends ActionBarActivityBase implements IListViewVmDelegate<Report> {

    private static final String LOG_TAG="RepEmailAct";
    private Menu _menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FragmentTransaction ft;

        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_rep_email);

        if (savedInstanceState != null) return; // if this has been created before then don't recreate

        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.act_rep_email_container, new RepEmailFrag()); //loads ReportListFragment (below) into the tags activity_report_list_container in activity_report_list
        ft.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_email_report, menu);

        _menu=menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id;
        IEmailService emailService;
        Intent intent;


        id = item.getItemId();

        switch(id) {

            case R.id.menu_email_list:

                intent=new Intent(this, EmailListAct.class);

                startActivity(intent);

                return true;

            case R.id.menu_send_report:

                emailService=new EmailService(this);
                emailService.sendEmail();

                finish();


                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(Report item) {
        MenuItem menuItem;

        if (_menu==null){

            Log.wtf(LOG_TAG, "OnItemClick: violation the menu is null.");
            return;
        }

        menuItem=_menu.findItem(R.id.menu_send_report);
        menuItem.setVisible(true);

    }
}

