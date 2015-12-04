package org.codebehind.mrslmaintenance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.codebehind.mrslmaintenance.ViewModels.TextViewViewModel;

/**
 * Created by root on 03/12/15.
 */
public class SiteFragment extends Fragment {

    private TextViewViewModel _siteNameTextViewVm, _descriptionTextViewVm, _addressTextViewVm;

    public SiteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_site, container, false);

        return rootView;
    }
}
