package org.codebehind.mrslmaintenance;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Report;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportNewEquipmentFragment extends Fragment {
    Report _report;
    Equipment _equipment;
    TextView _equipmentNameTextView;

    public ReportNewEquipmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        Intent intent;
        Bundle bundle;

        rootView=inflater.inflate(R.layout.fragment_report_new_equipment, container, false);

        intent = getActivity().getIntent();
        bundle=intent.getExtras();
        _report=(Report)bundle.getSerializable(ReportNewFragment.BUNDLE_REPORT);
        _equipment=(Equipment)bundle.getSerializable(ReportNewFragment.BUNDLE_EQUIPMENT);

        setControls(rootView);
        setText();
        setEvents();

        return rootView;
    }

    private void setControls(View rootView) {
        _equipmentNameTextView=(TextView)rootView.findViewById(R.id.report_new_equipment_name);
    }

    private void setText(){
        _equipmentNameTextView.setText(_equipment.getEquipmentName());
    }

    private void setEvents() {

    }


}
