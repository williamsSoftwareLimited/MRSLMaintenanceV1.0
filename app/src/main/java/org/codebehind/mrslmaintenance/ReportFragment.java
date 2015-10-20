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
import android.widget.Spinner;
import android.widget.Toast;

import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Models.EquipmentModel;
import org.codebehind.mrslmaintenance.Models.ReportModel;

import java.util.UUID;

/**
 * Created by Gavin on 30/12/2014.
 */
public class ReportFragment extends Fragment {
    String TAG = "ReportFragment_EquipmentListview";
    public static final int FROMEQUIPMENTLIST=1,
            FROMSITELIST=2;
    Report _report;
    EditText siteField, engineerField, _datefield;
    ListView equipListView;
    ArrayAdapter<Equipment> equipAdapter;
    DatePicker _datePicker;

    public ReportFragment() {
    }
    public static ReportFragment newInstance(UUID id){
        Bundle args = new Bundle();
        args.putSerializable(StaticConstants.EXTRA_REPORT_ID, id);
        ReportFragment rm = new ReportFragment();
        rm.setArguments(args);
        return rm;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID id = (UUID)getArguments().getSerializable(StaticConstants.EXTRA_REPORT_ID);
        setHasOptionsMenu(true);
        _report = ReportModel.getInstance().getItem(id);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_report, container, false);

        if (_report== null) return rootView;// maybe redirect?

        siteField = (EditText)rootView.findViewById(R.id.report_site);
        siteField.setText(_report.getSiteName());

        engineerField=((EditText)rootView.findViewById(R.id.report_engineer));
        engineerField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _report.setEngineerName(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        engineerField.setText(_report.getEngineerName());
        equipListView=((ListView)rootView.findViewById(R.id.report_equipment_ListView));
        equipAdapter=new ArrayAdapter<Equipment>(getActivity(), android.R.layout.simple_list_item_1, _report.getEquipmentList());
        equipListView.setAdapter(equipAdapter);
        equipListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Equipment e = (Equipment)parent.getItemAtPosition(position);
                Log.d(TAG, "Equipment name = " + e.getEquipmentName());
                Log.d(TAG, "The report number is " + _report.getId());
                Log.d(TAG, "The site name is " + _report.getSiteName());
                Intent i = new Intent(getActivity(), EquipmentActivity.class);
                i.putExtra(EquipmentActivity.EQUIPMENT_ID, e.getId());
                i.putExtra(EquipmentActivity.EQUIPMENT_REPORT_ID, _report.getId());
                startActivity(i);
            }
        });
        _datefield=(EditText)rootView.findViewById(R.id.report_date);
        _datefield.setText(_report.getReportDate().toString());

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
}
