package org.codebehind.mrslmaintenance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import org.codebehind.mrslmaintenance.Adapters.Abstract.AbstractAdapter;
import org.codebehind.mrslmaintenance.Adapters.EquipParamAdapter;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.Models.ParameterDbModel;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IEditTextViewModelDelegate;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IListViewVmDelegate;
import org.codebehind.mrslmaintenance.ViewModels.EditTextViewModel;
import org.codebehind.mrslmaintenance.ViewModels.ListViewViewModel;


/**
 * Created by Gavin on 09/02/2015.
 */
public class EquipmentNewFragment extends Fragment implements IEditTextViewModelDelegate, IListViewVmDelegate<Parameter> {

    public static final String EQUIP_ARG="EQUIP_NEW_FRAG_EQUIP_ARG",
                               LOG_TAG="EquipmentNewFragment";
    private static final String EDIT_EQUIP="Edit Equipment", VIEW_EQUIP="Equipment", NEW_EQUIP="New Equipment";
    private Equipment _equipment;
    private FragmentMode _fragmentMode;
    private EditTextViewModel _nameEditTextVm;
    private ListViewViewModel<Parameter> _paramListViewVm;

    public void setFragmentMode(FragmentMode fragmentMode){
        _fragmentMode=fragmentMode;
    }

    public EquipmentNewFragment(){}

    public static EquipmentNewFragment newInstance(Equipment equip){
        Bundle bundle;
        EquipmentNewFragment equipNewFragment;

        if (equip==null) Log.wtf(LOG_TAG, "newInstance: violation site==null.");

        bundle = new Bundle();
        bundle.putSerializable(EQUIP_ARG, equip);

        equipNewFragment=new EquipmentNewFragment();
        equipNewFragment.setArguments(bundle);
        return equipNewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView;

        rootView = inflater.inflate(R.layout.fragment_equipment_new, container, false);

        _equipment=(Equipment)getArguments().getSerializable(EQUIP_ARG);

        if (_equipment==null) Log.wtf(LOG_TAG, "onCreateView: violation _equipment==null. This may cause EquipmentNewFragment to crash.");

        setControls(rootView);
        setText();
        //setEvents();

        return rootView;
    }

    private void setControls(View rootView){
        ParameterDbModel paramModel;
        AbstractAdapter<Parameter> equipParamAdapt;

        paramModel=new ParameterDbModel(getActivity());
        equipParamAdapt=new EquipParamAdapter(paramModel.getNewParameters(_equipment.getId()), getActivity());

        _nameEditTextVm= new EditTextViewModel((EditText)rootView.findViewById(R.id.equipment_new_name_edittext), this);
        _paramListViewVm=new ListViewViewModel<>((ListView)rootView.findViewById(R.id.equipment_new_param_listview), equipParamAdapt, this);

    }

    private void setText(){

        _nameEditTextVm.setText(_equipment.getEquipmentName());

        if (_fragmentMode==FragmentMode.EDIT) getActivity().setTitle(EDIT_EQUIP);
        else if (_fragmentMode==FragmentMode.NEW) getActivity().setTitle(NEW_EQUIP);
        else getActivity().setTitle(VIEW_EQUIP);
    }

    private void setEvents(){}

    @Override
    public void textUpdated(int uniqueId, String text) {

        switch(uniqueId){

            case R.id.equipment_new_name_edittext:
                break;

        }
    }

    @Override
    public void onItemClick(Parameter item) {

    }
}
