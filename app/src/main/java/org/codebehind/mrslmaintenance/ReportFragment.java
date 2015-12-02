package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Models.EquipmentDbModel;
import org.codebehind.mrslmaintenance.Models.ReportDbModel;
import org.codebehind.mrslmaintenance.ViewModels.DateEditTextViewModel;
import org.codebehind.mrslmaintenance.ViewModels.EditTextViewModel;

/**
 * Created by Gavin on 30/12/2014.
 */
public class ReportFragment extends Fragment {

    private Report _report;
    private EditTextViewModel siteFieldVm, engineerFieldVm;
    private DateEditTextViewModel _datefieldVm;
    private ListView _equipListView;
    private ArrayAdapter<Equipment> _equipmentAdapter;
    private ReportDbModel _reportModel;
    private EquipmentDbModel _equipmentModel;


    public static final String BUNDLE_REPORT = "org.CodeBehind.REPORT_FRAGMENT_BUNDLE_FLY_REPORT",
                               BUNDLE_EQUIPMENT = "org.CodeBehind.REPORT_FRAGMENT_BUNDLE_FLY_EQUIPMENT";

    public static ReportFragment newInstance(int id){
        Bundle args;
        ReportFragment reportFragment;

        args = new Bundle();
        args.putSerializable(StaticConstants.EXTRA_REPORT_ID, id);

        reportFragment = new ReportFragment();
        reportFragment.setArguments(args);
        return reportFragment;
    }

    public ReportFragment() {

        _reportModel=new ReportDbModel(getActivity());
        _equipmentModel=new EquipmentDbModel(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int id = (int)getArguments().getSerializable(StaticConstants.EXTRA_REPORT_ID);
        setHasOptionsMenu(true);
        _report = _reportModel.getReport(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report, container, false);

        if (_report== null) return rootView;// maybe redirect?

        setControls(rootView);
        setAttributes();
        setEvents();

        return rootView;
    }

    private void setControls(View rootView){

        siteFieldVm=new EditTextViewModel((EditText)rootView.findViewById(R.id.report_site));
        engineerFieldVm=new EditTextViewModel((EditText)rootView.findViewById(R.id.report_engineer));
        engineerFieldVm.setEditable(false);
        _equipListView=((ListView)rootView.findViewById(R.id.report_equipment_ListView));
        _datefieldVm=new DateEditTextViewModel((EditText)rootView.findViewById(R.id.report_date));
    }

    private void setAttributes(){

        siteFieldVm.setText(_report.getSiteName());
        engineerFieldVm.setText(_report.getEngineerName());
        _datefieldVm.setText(_report.getReportDate());

        _report.setEquipmentList(_equipmentModel.getList(_report.getSiteId())); // this is a bit strange but it allow the equipment list to bundled in the intent to the EquipmentActivity

        _equipmentAdapter=new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, _equipmentModel.getList(_report.getSiteId()));
        _equipListView.setAdapter(_equipmentAdapter);
    }

    private void setEvents() {

        _equipListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                Bundle bundle;
                Equipment equipment = (Equipment) parent.getItemAtPosition(position);

                intent = new Intent(getActivity(), EquipmentActivity.class);
                bundle = new Bundle();
                bundle.putSerializable(BUNDLE_REPORT, _report);
                bundle.putSerializable(BUNDLE_EQUIPMENT, equipment);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

    } // end setEvents method
}
