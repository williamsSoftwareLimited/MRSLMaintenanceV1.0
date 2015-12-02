package org.codebehind.mrslmaintenance.Abstract;

import org.codebehind.mrslmaintenance.FragmentMenubarHelper;

import java.util.ArrayList;
import java.util.EventListener;

/**
 * Created by Gavin on 18/01/2015.
 */
public interface FragmentMenubarListener<t> extends EventListener {
    public void onMenubarSelected(FragmentMenubarHelper.actions action, ArrayList<t> arrayList);
}
