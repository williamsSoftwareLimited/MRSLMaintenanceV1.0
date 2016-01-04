package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import org.codebehind.mrslmaintenance.Adapters.SiteEquipmentAdapter;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Entities.SiteEquipment;
import org.codebehind.mrslmaintenance.Models.ReportDbModel;
import org.codebehind.mrslmaintenance.Models.ReportEquipParamsDbModel;
import org.codebehind.mrslmaintenance.Models.SiteEquipmentDbModel;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IEditTextViewModelDelegate;
import org.codebehind.mrslmaintenance.ViewModels.DateEditTextViewModel;
import org.codebehind.mrslmaintenance.ViewModels.EditTextViewModel;

/**
 * Created by Gavin on 30/12/2014.
 */
public class ReportFragment extends Fragment implements IEditTextViewModelDelegate{

    private Report _report;
    private EditTextViewModel siteFieldVm, engineerFieldVm;
    private DateEditTextViewModel _datefieldVm;
    private ListView _siteEquipmentListView;
    private SiteEquipmentAdapter _siteEquipmentAdapter;
    private ReportDbModel _reportModel;
    private SiteEquipmentDbModel _siteEquipmentModel;


    public static final String BUNDLE_REPORT = "REPORT_FRAGMENT_BUNDLE_FLY_REPORT",
                               BUNDLE_SITE_EQUIPMENT = "REPORT_FRAGMENT_BUNDLE_FLY_SITE_EQUIPMENT";

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

        _reportModel=new ReportDbModel(getActivity(), new ReportEquipParamsDbModel(getActivity()));
        _siteEquipmentModel=new SiteEquipmentDbModel(getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int id = (int)getArguments().getSerializable(StaticConstants.EXTRA_REPORT_ID);
        _report = _reportModel.getReport(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report, container, false);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        setControls(getView());
        setAttributes();
        setEvents();
    }

    private void setControls(View rootView){

        siteFieldVm=new EditTextViewModel((EditText)rootView.findViewById(R.id.report_site), this);
        engineerFieldVm=new EditTextViewModel((EditText)rootView.findViewById(R.id.report_engineer), this);
        engineerFieldVm.setNonEditable();
        _siteEquipmentListView=((ListView)rootView.findViewById(R.id.report_equipment_ListView));
        _datefieldVm=new DateEditTextViewModel((EditText)rootView.findViewById(R.id.report_date), this);
    }

    private void setAttributes(){

        siteFieldVm.setText(_report.getSiteName());
        engineerFieldVm.setText(_report.getEngineerName());
        _datefieldVm.setText(_report.getReportDate());

        _report.setSiteEquipmentList(_siteEquipmentModel.getSiteEquipmentListForReport(_report.getId()));

        _siteEquipmentAdapter=new SiteEquipmentAdapter(_report.getSiteEquipmentList(), getActivity());
        _siteEquipmentListView.setAdapter(_siteEquipmentAdapter);
    }

    private void setEvents() {

        _siteEquipmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                Bundle bundle;
                SiteEquipment siteEquipment;

                siteEquipment = (SiteEquipment) parent.getItemAtPosition(position);

                intent = new Intent(getActivity(), RepEquipActivity.class);
                bundle = new Bundle();
                bundle.putSerializable(BUNDLE_REPORT, _report);
                bundle.putSerializable(BUNDLE_SITE_EQUIPMENT, siteEquipment);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

    } // end setEvents method

    @Override
    public void textUpdated(int uniqueId, String text) {

    }
}
