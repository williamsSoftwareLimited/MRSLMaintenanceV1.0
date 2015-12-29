package org.codebehind.mrslmaintenance.ViewModels.Abstract;

import android.widget.AdapterView;

/**
 * Created by root on 22/12/15.
 */
public interface IListViewVmDelegate<t> {
    void onItemClick(t item);
}
