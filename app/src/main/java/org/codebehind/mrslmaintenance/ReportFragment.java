package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
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
    String TAG = "org.CodeBehind.ReportFragment_EquipmentListview";
    public static final int FROMEQUIPMENTLIST=1,
            FROMSITELIST=2;
    Report _report;
    EditText siteField, engineerField, _datefield;
    ListView _equipListView;
    ArrayAdapter<Equipment> _equipmentAdapter;
    ReportDbModel _reportModel;
    EquipmentDbModel _equipmentModel;
    public static final String BUNDLE_REPORT = "org.CodeBehind.REPORT_FRAGMENT_BUNDLE_FLY_REPORT",
            BUNDLE_EQUIPMENT = "org.CodeBehind.REPORT_FRAGMENT_BUNDLE_FLY_EQUIPMENT";

    public ReportFragment() {
        _reportModel=new ReportDbModel(getActivity());
        _equipmentModel=new EquipmentDbModel(getActivity());
    }
    public static ReportFragment newInstance(int id){
        Bundle args = new Bundle();
        args.putSerializable(StaticConstants.EXTRA_REPORT_ID, id);
        ReportFragment rm = new ReportFragment();
        rm.setArguments(args);
        return rm;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id = (int)getArguments().getSerializable(StaticConstants.EXTRA_REPORT_ID);
        setHasOptionsMenu(true);
        _report = _reportModel.getReport(id);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report, container, false);

        if (_report== null) return rootView;// maybe redirect?

        setControls(rootView);
        setText();
        setEvents();

        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id) {
            case R.id.menu_new_equipment:
                _startActivity(EquipmentListActivity.class, FROMEQUIPMENTLIST, "Add equipment");
                return true;
            case R.id.menu_new_site:
                _startActivity(SiteListActivity.class, FROMSITELIST, "Select site");
                return true;
            default:    return super.onOptionsItemSelected(item);
        }
    }
    boolean _startActivity(Class<?> cl, int requestCode, String toastMsg){
        Intent i = new Intent(getActivity(), cl);

        Toast.makeText(getActivity(),toastMsg, Toast.LENGTH_LONG).show();
        //_report=ReportModel.getInstance().getList().get(mViewPager.getCurrentItem());
        i.putExtra(StaticConstants.EXTRA_REPORT_ID, _report.getId());
        startActivityForResult(i, requestCode);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        switch(requestCode){
            case FROMEQUIPMENTLIST: getActivity().recreate();break;
            case FROMSITELIST:siteField.setText(_report.getSiteName());break;
        }
    }

    private void setControls(View rootView){

        siteField = (EditText)rootView.findViewById(R.id.report_site);
        engineerField=((EditText)rootView.findViewById(R.id.report_engineer));
        _equipListView=((ListView)rootView.findViewById(R.id.report_equipment_ListView));
        _datefield=(EditText)rootView.findViewById(R.id.report_date);
    }

    private void setText(){

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
                Log.d(TAG, "Equipment name = " + equipment.getEquipmentName());
                Log.d(TAG, "The report number is " + _report.getId());
                Log.d(TAG, "The site name is " + _report.getSiteName());

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
