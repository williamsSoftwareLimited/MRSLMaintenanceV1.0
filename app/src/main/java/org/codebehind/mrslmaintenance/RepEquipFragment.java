package org.codebehind.mrslmaintenance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Adapters.ReportEquipmentParamsAdapter;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Entities.SiteEquipment;
import org.codebehind.mrslmaintenance.Models.ReportEquipParamsDbModel;
import org.codebehind.mrslmaintenance.ViewModels.TextViewViewModel;

/**
 * Created by Gavin on 05/01/2015.
 */
public class RepEquipFragment extends Fragment {
    private final static String REPORT_ARG = "EQUIPMENT_FRAGMENT_REPORT",
                                SITE_EQUIP_ARG ="EQUIPMENT_FRAGMENT_SITE_EQUIPMENT";
    private Report _report;
    private SiteEquipment _siteEquip;
    private static final int REQUEST_PHOTO=1;
    private static final String LOG_TAG = "RepEquipFragment";
    private TextViewViewModel _nameViewVm, _siteEquipNameTextViewVm;
    private ImageButton _imageButton;
    private ListView _parameterListView;
    private ReportEquipParamsDbModel _reportEquipParamsDbModel;

    public RepEquipFragment() {
        _reportEquipParamsDbModel = new ReportEquipParamsDbModel(getActivity());
    }
    public static RepEquipFragment newInstance(Report report, SiteEquipment siteEquip){
        Bundle args = new Bundle();

        args.putSerializable(REPORT_ARG, report);
        args.putSerializable(SITE_EQUIP_ARG, siteEquip);
        RepEquipFragment em = new RepEquipFragment();
        em.setArguments(args);

        return em;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _report = (Report)getArguments().getSerializable(REPORT_ARG);
        _siteEquip = (SiteEquipment)getArguments().getSerializable(SITE_EQUIP_ARG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rep_equip, container, false);

        setControls(rootView);
        setAttributes();
        setEvents();

        return rootView;
    }

    private void setControls(View rootView){

        _siteEquipNameTextViewVm=new TextViewViewModel((TextView)rootView.findViewById(R.id.equip_site_equip_name));
        _nameViewVm = new TextViewViewModel((TextView)rootView.findViewById(R.id.equipment_name));
        _imageButton=(ImageButton)rootView.findViewById(R.id.equipment_imagebtn);
        _parameterListView=(ListView)rootView.findViewById(R.id.fragment_equipment_params);
    }

    private void setAttributes(){

        _siteEquipNameTextViewVm.setText(_siteEquip.getName());
        _nameViewVm.setText(_siteEquip.getEquipment().getEquipmentName());
        _parameterListView.setAdapter(new ReportEquipmentParamsAdapter(_reportEquipParamsDbModel.getReportEquipmentParameters(_report.getId(), _siteEquip.getId()), getActivity()));
    }

    private void setEvents(){

        _imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), EquipmentCameraActivity.class);
                startActivityForResult(i, REQUEST_PHOTO);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_PHOTO) {
            // create a new Photo object and attach it to the crime
            String filename = data
                    .getStringExtra(EquipmentCameraFragment.EXTRA_PHOTO_FILENAME);
            if (filename != null) {
                Log.i(LOG_TAG, "filename:" + filename);
            }
        }
    }
}