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
import android.widget.Spinner;

import org.codebehind.mrslmaintenance.Adapters.EquipmentAdapter;
import org.codebehind.mrslmaintenance.Adapters.SiteAdapter;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Models.SiteDbModel;
import org.codebehind.mrslmaintenance.Singletons.ReportSingleton;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.ISpinnerViewModelDelegate;
import org.codebehind.mrslmaintenance.ViewModels.EditTextViewModel;
import org.codebehind.mrslmaintenance.ViewModels.SiteSpinnerViewModel;

import java.text.DateFormat;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReportNewFragment extends Fragment implements ISpinnerViewModelDelegate {

    private SiteSpinnerViewModel _siteSpinnerVm;
    private EditTextViewModel _dateEditTextVm, _engineerNameTextViewVm;
    private ListView _equipmentListView;
    private EquipmentAdapter _equipmentAdapter;
    private Report _report;
    private ReportSingleton _reportSingleton;

    public String getEngineersName(){
        return _engineerNameTextViewVm.getText();
    }

    public void setSiteSpinnerVmEnabled(boolean b){
        _siteSpinnerVm.setEnabled(b);
    }

    public ReportNewFragment() {
        _reportSingleton=ReportSingleton.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report_new, container, false);

        setHasOptionsMenu(true); // ensures the fragment knows it has a menu
        setControls(rootView);
        setAttributes();
        setEvents();

        return rootView;
    }

    private void setControls(View rootView){
        SiteDbModel siteDbModel;
        SiteAdapter siteAdapter;

        siteDbModel=new SiteDbModel(getActivity());
        siteAdapter=new SiteAdapter(siteDbModel.getlist(), getActivity());

        _siteSpinnerVm=(new SiteSpinnerViewModel((Spinner)rootView.findViewById(R.id.report_new_spinner), siteAdapter, this));
        _dateEditTextVm = new EditTextViewModel((EditText)rootView.findViewById(R.id.report_new_date));
        _engineerNameTextViewVm=new EditTextViewModel((EditText)rootView.findViewById(R.id.report_engineer_name));
        _equipmentListView=(ListView)rootView.findViewById(R.id.report_new_equipment_ListView);
    }

    private void setAttributes(){

        setReport(1);

        _siteSpinnerVm.setSiteId(_report.getSiteId());
        _dateEditTextVm.setText(DateFormat.getDateTimeInstance().format(new Date()));
    }

    private void setEvents(){

        _equipmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                Equipment equipment;

                equipment = (Equipment) parent.getItemAtPosition(position);
                _reportSingleton.setEquipment(equipment);

                intent = new Intent(getActivity(), ReportNewEquipmentActivity.class);
                startActivity(intent);
            }
        });
    }

    // The siteId needs to be an integer the check will be here but nothing will happen if it isn't
    private void setReport(int siteId){

        if (_reportSingleton.getReport()==null) {

            _report = _reportSingleton.initializeReport(getActivity(), -1, siteId, _engineerNameTextViewVm.getText(), _dateEditTextVm.getText());
            setSiteSpinnerVmEnabled(true);

        }else if (_reportSingleton.getReport().getId()<0){

            _reportSingleton.setSiteId(getActivity(), siteId);

        } else {
            // this report already exists switch of the spinner
            setSiteSpinnerVmEnabled(false);

            _report=_reportSingleton.initializeEquipmentList(getActivity());
            _engineerNameTextViewVm.setText(_report.getEngineerName());
        }

        _equipmentAdapter = new EquipmentAdapter(_report.getEquipmentList(), getActivity());
        _equipmentListView.setAdapter(_equipmentAdapter);
    }

    // This is the delegate called from the SiteSpinnerViewModel
    @Override
    public void itemSelected(int position) {

        Site site = _siteSpinnerVm.getSpinnerAdapter().getItem(position);
        setReport(site.getId());
    }
}
