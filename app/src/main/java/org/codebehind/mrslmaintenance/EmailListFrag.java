package org.codebehind.mrslmaintenance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.auth.api.signin.EmailSignInOptions;

import org.codebehind.mrslmaintenance.Abstract.IEmailCallback;
import org.codebehind.mrslmaintenance.Adapters.EmailSelectorAdapter;
import org.codebehind.mrslmaintenance.Entities.Email;
import org.codebehind.mrslmaintenance.Models.EmailDbModel;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IEditTextViewModelDelegate;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IListViewVmDelegate;
import org.codebehind.mrslmaintenance.ViewModels.EditTextViewModel;
import org.codebehind.mrslmaintenance.ViewModels.ListViewViewModel;

import java.util.HashSet;
import java.util.regex.Pattern;

/**
 * Created by root on 18/02/16.
 */
public class EmailListFrag  extends Fragment implements IListViewVmDelegate<Email>, IEmailCallback,IEditTextViewModelDelegate {

    private static final String LOG_TAG="EmailDbModel";

    private ListViewViewModel<Email> _emailLvVm;
    private EditTextViewModel _editBoxVm;
    private ToggleButton _editTb;
    private LinearLayout _linearBox; // this is the editable box for adding emails
    private EmailSelectorAdapter _adapter;
    private EmailDbModel _model;
    private boolean _editToggle;
    private String _regexPat;
    private HashSet<Email> _delHash; // used to control the deletion of the emails

    public EmailListFrag(){

        _regexPat="[A-z0-9._%+-]+@[A-z0-9._%+-]+";
    }

    public void showBox(){

        if (!_editToggle) {

            _linearBox.setVisibility(View.VISIBLE);
            _linearBox.setBackgroundResource(R.drawable.add_param_box);
            _editToggle=true;
        } else {

            _linearBox.setVisibility(View.GONE);
            _editToggle=false;
        }

    }

    public void saveNewEmail(){
        Email e;

        if (!_editToggle) {

            return;
        } else {

            showBox();

            // check is email pattern
            if (Pattern.matches(_regexPat, _editBoxVm.getText())) {

                e = new Email(_editBoxVm.getText(), _editTb.isChecked());
                _model.add(e);
                _emailLvVm.add(e);

                Toast.makeText(getActivity(), "Email save.", Toast.LENGTH_LONG).show();
            }

        }
    }

    public void deleteEmails(){
        Email e;
        CheckBox cb;

        if (_delHash==null){

            Log.wtf(LOG_TAG,"deleteEmails: violation _delHash is null.");
            return;
        }

        while(!_delHash.isEmpty()){

            e=_delHash.iterator().next();
            _model.delete(e.getId());
            _emailLvVm.delete(e);
            _delHash.remove(e);

            setControls(getView());
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
        _editTb=(ToggleButton)view.findViewById(R.id.frag_email_list_item_tb);

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
                    _delHash.add(e);
                else
                    _delHash.remove(e);

                break;

        }

    }

    // this is for the edit box to enter emails
    @Override
    public void textUpdated(int uniqueId, String text) {

    }
}
