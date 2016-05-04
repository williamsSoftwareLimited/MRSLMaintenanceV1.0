package org.codebehind.mrslmaintenance;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import org.codebehind.mrslmaintenance.Entities.LastUpdate;
import org.codebehind.mrslmaintenance.Models.LastUpdateModel;
import org.codebehind.mrslmaintenance.Models.SiteDbModel;
import org.codebehind.mrslmaintenance.Services.JsonService;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IEditTextViewModelDelegate;
import org.codebehind.mrslmaintenance.ViewModels.DatePickerViewModel;
import org.codebehind.mrslmaintenance.ViewModels.EditTextViewModel;
import org.codebehind.mrslmaintenance.ViewModels.TimePickerViewModel;

import java.util.Calendar;
import java.util.Date;

public class RestTestActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IEditTextViewModelDelegate {

    private DatePickerViewModel _datePickerVm;
    private TimePickerViewModel _timePickerVm;
    private LastUpdateModel _lastUpdateModel; // this is injected and used here but I don't change the state!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Activity cont;
        FloatingActionButton fab;
        Toolbar toolbar;
        DrawerLayout drawer;
        ActionBarDrawerToggle toggle;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_test);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        cont =this;
        _lastUpdateModel=new LastUpdateModel(this);

        setControls(null);
        setAttributes();
        setEvents();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonService js;

                Snackbar.make(view, "This is awesome", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                js = new JsonService(new SiteDbModel(cont), _lastUpdateModel);
                js.execute();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.act_rest_test_drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setControls(View view){
        TextView tv=(TextView)findViewById(R.id.act_rest_test_update_tv);

        _datePickerVm=new DatePickerViewModel(this,
                new EditTextViewModel((EditText)findViewById(R.id.act_rest_test_update_date), this),
                (Button)findViewById(R.id.act_rest_test_update_btn));

        _timePickerVm=new TimePickerViewModel(this,
                new EditTextViewModel((EditText)findViewById(R.id.act_rest_test_update_time), this),
                (Button)findViewById(R.id.act_rest_test_time_btn),
                new TimePicker(this));
    }

    private void setAttributes(){
        LastUpdate lastUpdate=_lastUpdateModel.getLastUpdate();
        Calendar cal = Calendar.getInstance();

        cal.setTime(lastUpdate.getTimestamp());

        _datePickerVm.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH)); // MONTH goes from 0 to 11 bizarre!
        _timePickerVm.setTime(cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE));
    }

    private void setEvents(){}

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.act_rest_test_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.rest_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.act_rest_test_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // this for the DateEditText
    @Override
    public void textUpdated(int uniqueId, String text) {

    }
}
