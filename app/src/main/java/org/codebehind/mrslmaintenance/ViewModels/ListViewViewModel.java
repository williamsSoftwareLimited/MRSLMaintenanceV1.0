package org.codebehind.mrslmaintenance.ViewModels;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.codebehind.mrslmaintenance.Adapters.Abstract.AbstractAdapter;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IListViewVmDelegate;

/**
 * Created by root on 22/12/15.
 * Consuming class need to implement IListViewVmDelegate<t>, to get the item selection event.
 */
public class ListViewViewModel<t> {

    private static final String LOG_TAG="ListViewViewModel";
    private ListView _listview;
    private AbstractAdapter<t> _adapter;
    private IListViewVmDelegate<t> _delegate;
    private boolean _allowSelection;
    private t _tPersist;

    public void setSelection(boolean b){
        _allowSelection=b;
    }

    public t getEntity(){
        return _tPersist;
    }

    public ListViewViewModel(ListView listview, AbstractAdapter adapter, IListViewVmDelegate<t> delegate){

        if(listview==null) Log.wtf(LOG_TAG, "construct: arg violation listview can't be null");
        if(adapter==null) Log.wtf(LOG_TAG, "construct: arg violation adapter can't be null");
        if(delegate==null) Log.wtf(LOG_TAG, "construct: arg violation delegate can't be null");

        _listview=listview;
        _listview.setAdapter(adapter);

        _adapter=adapter;

        _delegate=delegate;
        setSelection(false);

        setEvents();
    }

    public void add(t entity){

        _adapter.add(entity);
    }

    public void delete(t entity){

        _adapter.remove(entity);
    }

    private void setEvents(){

        _listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                t entity;

                entity = (t) parent.getItemAtPosition(position);

                selection(view, entity);

                _delegate.onItemClick(entity);
            }
        });
    }

    private void selection(View view, t entity){

        if (!_allowSelection) return;

        for (int i=0; i<_listview.getCount();i++)
            _listview.getChildAt(i).setBackgroundColor(0);

        view.setBackgroundColor(Color.CYAN);

        _tPersist=entity;
    }

}
