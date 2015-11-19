package org.codebehind.mrslmaintenance;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Adapters.ParameterAdapter;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Models.ParameterDbModel;
import org.codebehind.mrslmaintenance.ViewModels.TextViewViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportNewEquipmentFragment extends Fragment {
    private Equipment _equipment;
    private TextViewViewModel _equipmentNameTextView;
    private ListView _parametersListView;
    private ParameterDbModel _parameterModel;
    private static final String BUNDLE_EQUIPMENT="org.CodeBehind.REPORT_NEW_EQUIPMENT_FRAGMENT.EQUIPMENT";

    public ReportNewEquipmentFragment() {
        super();
        _parameterModel=new ParameterDbModel(getActivity());
    }

    public static ReportNewEquipmentFragment newInstance(Equipment equipment){
        Bundle bundle;
        ReportNewEquipmentFragment reportNewEquipmentFragment;

        bundle=new Bundle();
        bundle.putSerializable(BUNDLE_EQUIPMENT, equipment);

        reportNewEquipmentFragment=new ReportNewEquipmentFragment();
        reportNewEquipmentFragment.setArguments(bundle);

        return reportNewEquipmentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        Bundle bundle;

        rootView = inflater.inflate(R.layout.fragment_report_new_equipment, container, false);

        bundle=getArguments();
        _equipment=(Equipment)bundle.getSerializable(BUNDLE_EQUIPMENT);
        _equipment.setParameterList(_parameterModel.getParameters(_equipment.getId()));

        setControls(rootView);
        setText();
        setEvents();

        return rootView;
    }

    private void setControls(View rootView) {

        _equipmentNameTextView=new TextViewViewModel((TextView)rootView.findViewById(R.id.report_new_equipment_name));
        _parametersListView=(ListView)rootView.findViewById(R.id.report_new_equipment_params);
    }

    private void setText(){

        _equipmentNameTextView.setText(_equipment.getEquipmentName());
        _parametersListView.setAdapter(new ParameterAdapter(_equipment.getParameterList(), getActivity()));
    }

    private void setEvents() {

    }


}
