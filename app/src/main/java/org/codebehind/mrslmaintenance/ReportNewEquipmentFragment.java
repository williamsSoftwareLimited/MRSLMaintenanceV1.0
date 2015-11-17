package org.codebehind.mrslmaintenance;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Adapters.ParameterAdapter;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Models.ParameterDbModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportNewEquipmentFragment extends Fragment {
    Report _report;
    Equipment _equipment;
    TextView _equipmentNameTextView;
    ListView _parametersListView;
    ParameterDbModel _parameterModel;

    public ReportNewEquipmentFragment() {
        _parameterModel=new ParameterDbModel(getActivity());
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

        _equipment.setParameterList(_parameterModel.getParameters(_equipment.getId()));

        setControls(rootView);
        setText();
        setEvents();

        return rootView;
    }

    private void setControls(View rootView) {

        _equipmentNameTextView=(TextView)rootView.findViewById(R.id.report_new_equipment_name);
        _parametersListView=(ListView)rootView.findViewById(R.id.report_new_equipment_params);
    }

    private void setText(){

        _equipmentNameTextView.setText(_equipment.getEquipmentName());
        _parametersListView.setAdapter(new ParameterAdapter(_equipment.getParameterList(), getActivity()));
    }

    private void setEvents() {

    }


}
