package org.codebehind.mrslmaintenance.Adapters.Abstract;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;

/**
 * Created by root on 21/12/15.
 */
public abstract class AbstractAdapter<t> extends ArrayAdapter<t> implements SpinnerAdapter {

    protected Activity _activity;
    protected ArrayList<t> _list;

    public ArrayList<t> getList(){
        return _list;
    }

    public AbstractAdapter(Activity activity, int layoutId, ArrayList<t> list) {
        super(activity, layoutId, list);

        _list=list;
        _activity=activity;
    }

    @Override
    public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
        return getView(position, cnvtView, prnt);
    }

    @Override
    public int getCount() {
        return _list.size();
    }

    // Force the getView to be overrided by the child
    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);

}
