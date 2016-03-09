package org.codebehind.mrslmaintenance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.codebehind.mrslmaintenance.Adapters.ReportAdapter;
import org.codebehind.mrslmaintenance.Entities.Email;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Entities.ReportEquipParams;
import org.codebehind.mrslmaintenance.Models.Abstract.IReportEquipParamsModel;
import org.codebehind.mrslmaintenance.Models.ReportDbModel;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IListViewVmDelegate;
import org.codebehind.mrslmaintenance.ViewModels.ListViewViewModel;

import java.util.ArrayList;

/**
 * Created by root on 11/02/16.
 */
public class RepEmailFrag  extends Fragment implements IReportEquipParamsModel{

    private ListViewViewModel<Report> _reportsLvVm;
    private ReportAdapter _reportAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;

        rootView = inflater.inflate(R.layout.frag_rep_email, container, false);

        setControls(rootView);
        setAttributes();
        //setEvents();

        return rootView;
    }

    @Override
    public void onResume (){
        super.onResume();
    }

    private void setControls(View view){
        ReportDbModel model;

        model=new ReportDbModel(getActivity(), this);

        _reportAdapter=new ReportAdapter(model.getAll(), getActivity());
        _reportsLvVm=new ListViewViewModel<>((ListView)view.findViewById(R.id.frag_rep_email_lv), _reportAdapter, (IListViewVmDelegate<Report>) getActivity());
    }

    private void setAttributes(){

        _reportsLvVm.setSelection(true);
    }

    private void setEvents(){}


    // ----- these are from the ReportDbModel -----
    @Override
    public int add(ReportEquipParams reportEquipParams) {
        return 0;
    }

    @Override
    public int update(ReportEquipParams reportEquipParams) {
        return 0;
    }
    // ---------------------------------------------


}
