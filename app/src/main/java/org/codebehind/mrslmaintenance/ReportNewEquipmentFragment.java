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
import org.codebehind.mrslmaintenance.Singletons.ReportSingleton;
import org.codebehind.mrslmaintenance.ViewModels.TextViewViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportNewEquipmentFragment extends Fragment {
    private Equipment _equipment;
    private TextViewViewModel _equipmentNameTextView, _equipmentIdTextView;
    private ListView _parametersListView;

    public ReportNewEquipmentFragment() {
        super();
        _equipment= ReportSingleton.getInstance().getEquipment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;

        rootView = inflater.inflate(R.layout.fragment_report_new_equipment, container, false);

        setControls(rootView);
        setText();
        setEvents();

        return rootView;
    }

    private void setControls(View rootView) {

        _equipmentIdTextView=new TextViewViewModel((TextView)rootView.findViewById(R.id.report_new_equipment_id));
        _equipmentNameTextView=new TextViewViewModel((TextView)rootView.findViewById(R.id.report_new_equipment_name));
        _parametersListView=(ListView)rootView.findViewById(R.id.report_new_equipment_params);
    }

    private void setText(){

        _equipmentIdTextView.setText(""+_equipment.getId());
        _equipmentNameTextView.setText(_equipment.getEquipmentName());
        _parametersListView.setAdapter(new ParameterAdapter(_equipment.getParameterList(), getActivity()));
    }

    private void setEvents() {

    }
}
