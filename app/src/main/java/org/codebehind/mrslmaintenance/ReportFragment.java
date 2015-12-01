package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Models.EquipmentDbModel;
import org.codebehind.mrslmaintenance.Models.ReportDbModel;

import java.util.ArrayList;

/**
 * Created by Gavin on 30/12/2014.
 */
public class ReportFragment extends Fragment {

    private Report _report;
    private EditText siteField, engineerField, _datefield;
    private ListView _equipListView;
    private ArrayAdapter<Equipment> _equipmentAdapter;
    private ReportDbModel _reportModel;
    private EquipmentDbModel _equipmentModel;


    public static final String BUNDLE_REPORT = "org.CodeBehind.REPORT_FRAGMENT_BUNDLE_FLY_REPORT",
                               BUNDLE_EQUIPMENT = "org.CodeBehind.REPORT_FRAGMENT_BUNDLE_FLY_EQUIPMENT";

    public ReportFragment() {

        _reportModel=new ReportDbModel(getActivity());
        _equipmentModel=new EquipmentDbModel(getActivity());
    }

    public static ReportFragment newInstance(int id){
        Bundle args;
        ReportFragment reportFragment;

        args = new Bundle();
        args.putSerializable(StaticConstants.EXTRA_REPORT_ID, id);

        reportFragment = new ReportFragment();
        reportFragment.setArguments(args);
        return reportFragment;
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

        siteField = (EditText)rootView.findViewById(R.id.report_site);
        engineerField=((EditText)rootView.findViewById(R.id.report_engineer));
        _equipListView=((ListView)rootView.findViewById(R.id.report_equipment_ListView));
        _datefield=(EditText)rootView.findViewById(R.id.report_date);
    }

    private void setAttributes(){

        siteField.setText(_report.getSiteName());
        engineerField.setText(_report.getEngineerName());
        _datefield.setText(_report.getReportDate().toString());
        ArrayList<String> params = new ArrayList<>();
        params.add("" + _report.getSiteId());

        _report.setEquipmentList(_equipmentModel.getList(params)); // this is a bit strange but it allow the equipment list to bundled in the intent to the EquipmentActivity

        _equipmentAdapter=new ArrayAdapter<Equipment>(getActivity(), android.R.layout.simple_list_item_1, _equipmentModel.getList(params));
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
                bundle.putSerializable(BUNDLE_EQUIPMENT,equipment);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        engineerField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _report.setEngineerName(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    } // end setEvents method
}
