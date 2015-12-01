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

import org.codebehind.mrslmaintenance.Adapters.ParameterAdapter;
import org.codebehind.mrslmaintenance.Adapters.ReportEquipmentParamsAdapter;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Entities.ReportEquipmentParameters;
import org.codebehind.mrslmaintenance.Models.EquipmentModel;
import org.codebehind.mrslmaintenance.Models.ParameterDbModel;
import org.codebehind.mrslmaintenance.Models.ReportEquipmentParametersDbModel;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Gavin on 05/01/2015.
 */
public class EquipmentFragment extends Fragment {
    private final static String REPORT = "org.CodeBehind.EQUIPMENT_FRAGMENT_REPORT", EQUIPMENT ="org.CodeBehind.EQUIPMENT_FRAGMENT_EQUIPMENT";
    Report _report;
    Equipment _equipment;
    private static final int REQUEST_PHOTO=1;
    private static final String LOG_TAG = "EquipmentFragment";
    TextView _nameView;
    ImageButton _imageButton;
    ListView _parameterListView;
    ReportEquipmentParametersDbModel _reportEquipmentParametersDbModel;

    public EquipmentFragment() {
        _reportEquipmentParametersDbModel = new ReportEquipmentParametersDbModel(getActivity());
    }
    public static EquipmentFragment newInstance(Report report, Equipment equipment){
        Bundle args = new Bundle();

        args.putSerializable(REPORT, report);
        args.putSerializable(EQUIPMENT, equipment);
        EquipmentFragment em = new EquipmentFragment();
        em.setArguments(args);
        return em;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _report = (Report)getArguments().getSerializable(REPORT);
        _equipment = (Equipment)getArguments().getSerializable(EQUIPMENT);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_equipment, container, false);

        setControls(rootView);
        setAttributes();
        setEvents();

        return rootView;
    }
    private void setControls(View rootView){

        _nameView = (TextView)rootView.findViewById(R.id.equipment_name);
        _imageButton=(ImageButton)rootView.findViewById(R.id.equipment_imagebtn);
        _parameterListView=(ListView)rootView.findViewById(R.id.fragment_equipment_params);
    }

    private void setAttributes(){

        _nameView.setText(_equipment.getEquipmentName());
        _parameterListView.setAdapter(new ReportEquipmentParamsAdapter(_reportEquipmentParametersDbModel.getReportEquipmentParameters(_report.getId(), _equipment.getId()), getActivity()));
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