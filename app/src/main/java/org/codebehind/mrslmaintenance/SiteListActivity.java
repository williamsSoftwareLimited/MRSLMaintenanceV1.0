package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Abstract.Fragment2ActivityListener;
import org.codebehind.mrslmaintenance.Abstract.FragmentMenubarListener;
import org.codebehind.mrslmaintenance.Abstract.IFragmentCallbackUUID;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Models.ReportModel;
import org.codebehind.mrslmaintenance.Models.SiteModel;

import java.util.ArrayList;
import java.util.UUID;


public class SiteListActivity extends ActionBarActivityBase implements IFragmentCallbackUUID {
    UUID reportId;
    UUID _siteId;
    Menu _menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FragmentTransaction ft;
        SiteListFragment siteListFragment;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_list);
        if (savedInstanceState != null) return; // if ths has been created before then don't recreate

        reportId=(UUID)getIntent() .getSerializableExtra(StaticConstants.EXTRA_REPORT_ID);

        ft= getSupportFragmentManager().beginTransaction();
        siteListFragment=new SiteListFragment();
        ft.add(R.id.activity_site_list_container, siteListFragment);

        ft.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_site_list, menu);

        _menu=menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        SiteModel siteModel;
        Intent intent;
        ReportModel reportModel;

        siteModel= SiteModel.getInstance();

        switch(id){
            case R.id.menu_view_site_list:
                Toast.makeText(this,"View selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_alter_site_list:
                Toast.makeText(this,"Alter selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_new_site_list:
                //todo: the Alter and view (this should probably be a private method)
                Toast.makeText(this,"New selected", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, SiteNewActivity.class);
                intent.putExtra(StaticConstants.EXTRA_REPORT_ID, reportId);
                intent.putExtra(StaticConstants.EXTRA_SITE_ID, _siteId);
                startActivity(intent);
                return true;
            case R.id.menu_delete_site_list:
                if (_siteId==null){
                    Toast.makeText(this, "The SiteId is null!", Toast.LENGTH_SHORT).show();
                    return true;
                }
                Toast.makeText(this,"Deleting site with id="+_siteId, Toast.LENGTH_SHORT).show();
                siteModel.delete(_siteId);
                // todo: may just refresh the list in the fragment here
                recreate();
                return true;
            case R.id.menu_add_site_list:
                if (reportId==null || _siteId==null){
                    Toast.makeText(this,"Can't add a site as the reportId or siteId is null", Toast.LENGTH_SHORT).show();
                    return true;
                }

                Toast.makeText(this,"Adding site with id "+_siteId, Toast.LENGTH_SHORT).show();
                // change to site and go back to the ReportActivity
                reportModel=ReportModel.getInstance();
                reportModel.setSite(reportId,_siteId );
                intent = new Intent(this, ReportActivity.class);
                intent.putExtra(StaticConstants.EXTRA_REPORT_ID, reportId);
                startActivity(intent);
                return true;
            default: return true;
        }
    }
    @Override
    public void onFragmentCallback(int id) {}
    @Override
    public void onFragmentCallback(UUID id) {
        _siteId=id;
        setMenubarVisibility(true);
    }
    private void setMenubarVisibility(boolean visible) {
        if (_menu == null) {
            Toast.makeText(this, "SiteListActivity.onFragmentCallback : _menu is null.", Toast.LENGTH_SHORT).show();
            return;
        }
        _menu.findItem(R.id.menu_view_site_list).setVisible(visible);
        _menu.findItem(R.id.menu_alter_site_list).setVisible(visible);
        _menu.findItem(R.id.menu_delete_site_list).setVisible(visible);

        // the add doesn't do anything or doesn't appear if this has been accessed from the report menubar
        if (reportId == null) return;
        _menu.findItem(R.id.menu_add_site_list).setVisible(visible);

    }
}
