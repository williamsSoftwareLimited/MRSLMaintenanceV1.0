package org.codebehind.mrslmaintenance;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Abstract.IFragmentSiteCallback;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Models.SiteModel;

import java.util.UUID;


public class SiteNewFragment extends Fragment implements View.OnClickListener {
    private UUID _siteId;
    private EditText etName, etDescription, etAddress,etSystem,etPlantId;
    private Button btnLocation;

    private IFragmentSiteCallback _listener;

    public SiteNewFragment() { }

    public void setSiteId(UUID siteId){
        _siteId=siteId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Site newSite;
        View view;

        view= inflater.inflate(R.layout.fragment_site_new, container, false);

        // if the site is null then look out!!!!
        newSite= SiteModel.getInstance().getTemplate(_siteId);

        etName = (EditText) view.findViewById(R.id.site_new_name);
        etName.setText(newSite.getName());

        etDescription = (EditText) view.findViewById(R.id.site_new_description);
        etDescription.setText(newSite.getDescription());

        etAddress = (EditText) view.findViewById(R.id.site_new_Address);
        etAddress.setText(newSite.getAddress());

        etSystem = (EditText) view.findViewById(R.id.site_new_system);
        etSystem.setText(newSite.getSystem());

        etPlantId = (EditText) view.findViewById(R.id.site_new_PlantId);
        etPlantId.setText(newSite.getPlantId());

        // the location button
        btnLocation=(Button)view.findViewById(R.id.site_new_Location_btn);
        btnLocation.setOnClickListener(this);
        return view;

    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            _listener = (IFragmentSiteCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement IFragmentCallbackUUID");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        _listener = null;
    }

    @Override
    public void onClick(View v) {
        // this means the location button has been pressed
        InputMethodManager imm;
        // this code hides the keyboard if it's showing
        imm = (InputMethodManager)getActivity().getSystemService(getActivity().getApplicationContext().INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etName.getWindowToken(), 0);

        _listener.onFragmentCallbackLocation(_siteId);
    }
}
