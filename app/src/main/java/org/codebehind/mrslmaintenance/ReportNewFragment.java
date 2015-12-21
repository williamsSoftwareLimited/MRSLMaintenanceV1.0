package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import org.codebehind.mrslmaintenance.Adapters.SiteAdapter;
import org.codebehind.mrslmaintenance.Adapters.SiteEquipmentAdapter;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Entities.SiteEquipment;
import org.codebehind.mrslmaintenance.Models.SiteDbModel;
import org.codebehind.mrslmaintenance.Models.SiteEquipmentDbModel;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IEditTextViewModelDelegate;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.ISpinnerViewModelDelegate;
import org.codebehind.mrslmaintenance.ViewModels.EditTextViewModel;
import org.codebehind.mrslmaintenance.ViewModels.SpinnerViewModel;

import java.text.DateFormat;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReportNewFragment extends Fragment implements ISpinnerViewModelDelegate, IEditTextViewModelDelegate {

    public static final String REPORT_BUNDLE="REPORT_NEW_FRAGMENT_REPORT_BUNDLE",
                               SITE_EQUIPMENT_BUNDLE="REPORT_NEW_FRAGMENT_SITE_EQUIPMENT_BUNDLE";
    private static final String REPORT_ARGS = "REPORT_NEW_FRAGMENT_REPORT_ARGS",
                                LOG_TAG="ReportNewFragment";
    private SpinnerViewModel<Site> _siteSpinnerVm;
    private EditTextViewModel _dateEditTextVm, _engineerNameTextViewVm;
    private ListView _equipmentListView;
    private SiteEquipmentAdapter _siteEquipmentAdapter;
    private Report _report;

    public static ReportNewFragment newInstance(Report report){
        Bundle bundle;
        ReportNewFragment reportNewFragment;

        bundle = new Bundle();
        bundle.putSerializable(REPORT_ARGS, report);
        reportNewFragment = new ReportNewFragment();

        reportNewFragment.setArguments(bundle);
        return  reportNewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;

        rootView = inflater.inflate(R.layout.fragment_report_new, container, false);

        _report=(Report)getArguments().getSerializable(REPORT_ARGS);

        setControls(rootView);
        setAttributes();
        setEvents();

        return rootView;
    }

    private void setControls(View rootView){
        SiteDbModel siteDbModel;
        SiteAdapter siteAdapter;

        siteDbModel=new SiteDbModel(getActivity());
        siteAdapter=new SiteAdapter(siteDbModel.getList(), getActivity());

        _siteSpinnerVm=(new SpinnerViewModel((Spinner)rootView.findViewById(R.id.report_new_spinner), siteAdapter, this));
        _dateEditTextVm = new EditTextViewModel((EditText)rootView.findViewById(R.id.report_new_date), this);
        _engineerNameTextViewVm=new EditTextViewModel((EditText)rootView.findViewById(R.id.report_engineer_name), this);
        _equipmentListView=(ListView)rootView.findViewById(R.id.report_new_equipment_ListView);
    }

    private void setAttributes(){

        _engineerNameTextViewVm.setText(_report.getEngineerName());
        _siteSpinnerVm.setId(_report.getSiteId());
        _dateEditTextVm.setText(DateFormat.getDateTimeInstance().format(new Date()));
    }

    private void setEvents(){

        _equipmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                Bundle bundle;
                SiteEquipment siteEquipment;

                siteEquipment = (SiteEquipment) parent.getItemAtPosition(position);

                bundle=new Bundle();
                bundle.putSerializable(SITE_EQUIPMENT_BUNDLE, siteEquipment);


                if (_report.getId()<0) _report.setId(-2);
                bundle.putSerializable(REPORT_BUNDLE, _report);

                intent = new Intent(getActivity(), ReportNewEquipmentActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    // This is the delegate called from the SpinnerViewModel
    @Override
    public void itemSelected(int pos) {
        SiteEquipmentDbModel siteEquipmentDbModel;
        Site site;
        int siteId;

        // invariant 0<=pos<siteEquip.Count
        if (pos<0) Log.wtf(LOG_TAG, "itemSelected: Invariant 0<=pos<siteEquip.Count violation.");

        siteEquipmentDbModel=new SiteEquipmentDbModel(getActivity());

        site = _siteSpinnerVm.getSpinnerAdapter().getItem(pos);
        siteId = site.getId();

        if (_report.getId()==-2){

            _siteSpinnerVm.setEnabled(false);

        } else if (_report.getId()==-1) {

            _siteSpinnerVm.setEnabled(true);

            _report.setSiteEquipmentList(siteEquipmentDbModel.getSiteEquipments(siteId));

            _report.setSiteId(siteId);

        } else {
            // this report already exists switch off the spinner
            _siteSpinnerVm.setEnabled(false);

            if (_report.getSiteEquipmentList()==null)
                _report.setSiteEquipmentList(siteEquipmentDbModel.getSiteEquipmentListForReport(_report.getId(), _report.getSiteId()));

            _engineerNameTextViewVm.setText(_report.getEngineerName());
        }

        _siteEquipmentAdapter = new SiteEquipmentAdapter(_report.getSiteEquipmentList(), getActivity());
        _equipmentListView.setAdapter(_siteEquipmentAdapter);
    }

    @Override
    public void textUpdated(int uniqueId, String text) {

        switch (uniqueId){

            case R.id.report_new_date:
                // do nothing as date can't be changed
                break;

            case R.id.report_engineer_name:
                _report.setEngineerName(text);
                break;
        }
    }
}
