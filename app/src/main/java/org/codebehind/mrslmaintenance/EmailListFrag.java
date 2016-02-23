package org.codebehind.mrslmaintenance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.codebehind.mrslmaintenance.Abstract.IEmailCallback;
import org.codebehind.mrslmaintenance.Adapters.EmailSelectorAdapter;
import org.codebehind.mrslmaintenance.Entities.Email;
import org.codebehind.mrslmaintenance.Entities.Report;
import org.codebehind.mrslmaintenance.Models.EmailDbModel;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IListViewVmDelegate;
import org.codebehind.mrslmaintenance.ViewModels.ListViewViewModel;

/**
 * Created by root on 18/02/16.
 */
public class EmailListFrag  extends Fragment implements IListViewVmDelegate<Email>, IEmailCallback {

    private ListViewViewModel<Email> _emailLvVm;
    private EmailSelectorAdapter _adapter;
    EmailDbModel _model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;

        rootView = inflater.inflate(R.layout.frag_email_list, container, false);

        setControls(rootView);
        //setAttributes();
        //setEvents();

        return rootView;
    }

    @Override
    public void onResume (){
        super.onResume();
    }


    private void setControls(View view){

        _model=new EmailDbModel(getActivity());
        _adapter=new EmailSelectorAdapter(_model.getList(), getActivity(), this);
        _emailLvVm=new ListViewViewModel<>((ListView)view.findViewById(R.id.frag_email_list_lv),_adapter,this);

    }

    private void setAttributes(){}

    private void setEvents(){}

    @Override
    public void onItemClick(Email item) {

    }

    // the checkbox callback from the adapter
    @Override
    public void onCallback(Email e) {
        Email email=e;
    }
}
