package org.codebehind.mrslmaintenance;

import android.content.Context;
import android.graphics.Color;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import org.codebehind.mrslmaintenance.Abstract.FragmentMenubarListener;
import org.codebehind.mrslmaintenance.Models.IRepository;

import java.util.ArrayList;

/**
 * Created by Gavin on 17/01/2015.
 */
public class FragmentMenubarHelper<t> {
    ListView _listview;
    ArrayList<t> _actionList;
    IRepository<t> _modelInstance;
    ArrayList<FragmentMenubarListener> eventListenerList;

    public FragmentMenubarHelper(ListView listview, IRepository<t> modelInstance){
        _listview=listview;
        _actionList=new ArrayList<t>();
        _modelInstance=modelInstance;
        eventListenerList=new ArrayList<FragmentMenubarListener>();
    }
    public void setMenubarforCRUD(final Context context ){

        _listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        _listview.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                // Here you can do something when items are selected/de-selected,
                // such as update the title in the CAB
                t entity = _modelInstance.getList().get(position);
                View v = _listview.getChildAt(position);
                v.setBackgroundColor(Color.WHITE);

                _actionList.add(entity);

                if (_actionList.size()>1) { // there's more than one so remove update icon
                    mode.getMenu().findItem(R.id.menu_alter_general_list).setVisible(false);
                    // note the reason that this (the new icon) is still here is that with my current planning the
                    // template from this item will be used create the new item
                    mode.getMenu().findItem(R.id.menu_new_general_list).setVisible(false);

                    // added late-on as the add was supposed to be multi-select but as it's difficult
                    // to implement then going to only allow single select
                    mode.getMenu().findItem(R.id.menu_add_general_list).setVisible(false);
                }
            }
            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                // Respond to clicks on the actions in the CAB
                switch (item.getItemId()) {
                    case R.id.menu_new_general_list:
                        dispatchEvent(actions.New);
                        break;
                    case R.id.menu_add_general_list:
                        dispatchEvent(actions.Add);
                        break;
                    case R.id.menu_alter_general_list:
                        dispatchEvent(actions.Alter);
                        break;
                    case R.id.menu_delete_general_list:
                        dispatchEvent(actions.Delete);
                        break;
                    default:
                        return false;
                }
                mode.finish(); // Action picked, so close the CAB
                return true;
            }
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Inflate the menu for the CAB
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.menu_general_list, menu);
                return true;
            }
            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // set the _listView colours back to normal
                for (int i=0; i<_listview.getCount();i++)
                    _listview.getChildAt(i).setBackgroundColor(0);

                _actionList.clear();
                // allow the menubar full set of icons to appear
                mode.getMenu().findItem(R.id.menu_alter_general_list).setVisible(true);
                mode.getMenu().findItem(R.id.menu_new_general_list).setVisible(true);
                mode.getMenu().findItem(R.id.menu_add_general_list).setVisible(true);
            }
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // Here you can perform updates to the CAB due to
                // an invalidate() request
                return false;
            }
        });
    }
    public void addEventListener(FragmentMenubarListener listener){
        eventListenerList.add(listener);
    }
    public void removeEventListener(FragmentMenubarListener listener){
        eventListenerList.remove(listener);
    }
    // Broadcast's all the event to whoever wants them
    public void dispatchEvent(actions action){
        for (int i=0; i<eventListenerList.size(); i++){
            eventListenerList.get(i).onMenubarSelected(action, _actionList);
        }
    }
    // think this is a bit of a mistake!
    public enum actions { New, Add, Alter, Delete  }
}

