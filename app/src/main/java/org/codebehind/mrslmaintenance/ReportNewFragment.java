package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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
import org.codebehind.mrslmaintenance.Singletons.ReportSingleton;
import org.codebehind.mrslmaintenance.Models.SiteDbModel;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.ISpinnerViewModelDelegate;
import org.codebehind.mrslmaintenance.ViewModels.EditTextViewModel;
import org.codebehind.mrslmaintenance.ViewModels.SpinnerViewModel;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReportNewFragment extends Fragment implements ISpinnerViewModelDelegate {

    private SpinnerViewModel _siteSpinnerVm;
    private SiteAdapter _siteSpinnerAdaptor;
    private EditTextViewModel _dateEditTextVm, _engineerNameTextViewVm;
    private ListView _equipmentListView;

    private EquipmentAdapter _equipAdapter;
    private Report _report;
    private ReportSingleton _reportSingleton;

    public String getEngineersName(){
        return _engineerNameTextViewVm.getText();
    }

    public ReportNewFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report_new, container, false);

        setHasOptionsMenu(true); // ensures the fragment knows it has a menu
        setControls(rootView);
        setText();
        setEvents();

        return rootView;
    }

    private void setControls(View rootView){

        _siteSpinnerVm=(new SpinnerViewModel((Spinner)rootView.findViewById(R.id.report_new_spinner), this));
        _dateEditTextVm = new EditTextViewModel((EditText)rootView.findViewById(R.id.report_new_date));
        _engineerNameTextViewVm=new EditTextViewModel((EditText)rootView.findViewById(R.id.report_engineer_name));
        _equipmentListView=(ListView)rootView.findViewById(R.id.report_new_equipment_ListView);
    }

    private void setText(){
        SiteDbModel siteDbModel;

        siteDbModel=new SiteDbModel(getActivity());

        _siteSpinnerAdaptor = new SiteAdapter(siteDbModel.getlist(), getActivity());
        _siteSpinnerVm.setSpinnerAdapter(_siteSpinnerAdaptor);

        _dateEditTextVm.setText(DateFormat.getDateTimeInstance().format(new Date()));

        setReport("1");
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
    private void setReport(String siteId){
        ArrayList<String> params;

        if (siteId==null || siteId=="") return;

        params = new ArrayList<String>();
        params.add(siteId);

        _reportSingleton=ReportSingleton.getInstance();
        _reportSingleton.initializeReport(getActivity(), -1, Integer.parseInt(siteId), _engineerNameTextViewVm.getText(), _dateEditTextVm.getText());
        _report=_reportSingleton.getReport();

        _equipAdapter = new EquipmentAdapter(_report.getEquipmentList(), getActivity());
        _equipmentListView.setAdapter(_equipAdapter);
    }

    // This is the delegate called from the SpinnerViewModel
    @Override
    public void itemSelected(int position) {

        Site s = _siteSpinnerAdaptor.getItem(position);
        setReport("" + s.getId());
    }
}
