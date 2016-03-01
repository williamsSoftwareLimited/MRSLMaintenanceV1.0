package org.codebehind.mrslmaintenance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.codebehind.mrslmaintenance.Abstract.IEmailCallback;
import org.codebehind.mrslmaintenance.Adapters.EmailSelectorAdapter;
import org.codebehind.mrslmaintenance.Entities.Email;
import org.codebehind.mrslmaintenance.Models.EmailDbModel;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IEditTextViewModelDelegate;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IListViewVmDelegate;
import org.codebehind.mrslmaintenance.ViewModels.EditTextViewModel;
import org.codebehind.mrslmaintenance.ViewModels.ListViewViewModel;

import java.util.HashSet;

/**
 * Created by root on 18/02/16.
 */
public class EmailListFrag  extends Fragment implements IListViewVmDelegate<Email>, IEmailCallback,IEditTextViewModelDelegate {

    private static final String LOG_TAG="EmailDbModel";

    private ListViewViewModel<Email> _emailLvVm;
    private EditTextViewModel _editBoxVm;
    private LinearLayout _linearBox; // this is the editable box for adding emails
    private EmailSelectorAdapter _adapter;
    private EmailDbModel _model;
    private boolean _editToggle;

    private HashSet<Integer> _delHash; // used to control the deletion of the emails

    public void showBox(){

        if (!_editToggle) {
            _linearBox.setVisibility(View.VISIBLE);
            _editToggle=true;
        }
        else {
            _linearBox.setVisibility(View.GONE);
            _editToggle=false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;

        rootView = inflater.inflate(R.layout.frag_email_list, container, false);

        _delHash=new HashSet<>();
        _editToggle=false;

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

        _model=new EmailDbModel(getActivity());
        _adapter=new EmailSelectorAdapter(_model.getList(), getActivity(), this);
        _emailLvVm=new ListViewViewModel<>((ListView)view.findViewById(R.id.frag_email_list_lv),_adapter,this);

        _linearBox=(LinearLayout)view.findViewById(R.id.frag_email_list_box);
        _editBoxVm=new EditTextViewModel((EditText)view.findViewById(R.id.frag_email_list_item_et), this);

    }

    private void setAttributes(){

        _emailLvVm.setSelection(true);
    }

    private void setEvents(){}

    @Override
    public void onItemClick(Email e) {

    }

    // the checkbox callback from the adapter
    @Override
    public void onCallback(Email e, int n) {
        int rowCount;

        if (e==null) {

            Log.d(LOG_TAG, "onCallback: violation returning from the adapter the Email is null.");
            return;
        }

        switch(n){

            case 1: // this is select

                rowCount=_model.update(e);
                if (rowCount==-1 || rowCount>1) Log.d(LOG_TAG, "onCallback: rowCount violation it's not equal to 1.");
                break;

            case 2: // this is the delete

                if (e.getDeleted())
                    _delHash.add(e.getId());
                else
                    _delHash.remove(e.getId());

                break;

        }

    }

    // this is for the edit box to enter emails
    @Override
    public void textUpdated(int uniqueId, String text) {

    }
}
