package org.codebehind.mrslmaintenance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import org.codebehind.mrslmaintenance.Adapters.Abstract.AbstractAdapter;
import org.codebehind.mrslmaintenance.Adapters.EquipParamAdapter;
import org.codebehind.mrslmaintenance.Adapters.ParamSpinnerAdapter;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.Entities.ParameterType;
import org.codebehind.mrslmaintenance.Models.ParameterDbModel;
import org.codebehind.mrslmaintenance.Singletons.ParameterTypesSingleton;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IEditTextViewModelDelegate;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IListViewVmDelegate;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.ISpinnerViewModelDelegate;
import org.codebehind.mrslmaintenance.ViewModels.EditTextViewModel;
import org.codebehind.mrslmaintenance.ViewModels.ListViewViewModel;
import org.codebehind.mrslmaintenance.ViewModels.SpinnerViewModel;

import java.util.Hashtable;


/**
 * Created by Gavin on 09/02/2015.
 */
public class EquipmentNewFragment extends Fragment implements IEditTextViewModelDelegate, IListViewVmDelegate<Parameter>,ISpinnerViewModelDelegate {

    public static final String EQUIP_ARG="EQUIP_NEW_FRAG_EQUIP_ARG",
                               LOG_TAG="EquipmentNewFragment";
    private static final String EDIT_EQUIP="Edit Equipment", VIEW_EQUIP="Equipment", NEW_EQUIP="New Equipment";
    private Equipment _equipment;
    private FragmentMode _fragmentMode;
    private EditTextViewModel _nameEditTextVm, _newParamNameEtVm, _newParamUnitsEtVm;
    private SpinnerViewModel<ParameterType> _newParamTypeSpinVm;
    private Button _addParamBtn;
    private LinearLayout _newParamBox;
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
        setAttributes();
        setEvents();

        return rootView;
    }

    private void setControls(View rootView){
        ParameterDbModel paramModel;
        AbstractAdapter<Parameter> equipParamAdapt;
        AbstractAdapter<ParameterType> paramSpinAdapt;

        paramModel=new ParameterDbModel(getActivity());
        equipParamAdapt=new EquipParamAdapter(paramModel.getNewParameters(_equipment.getId()), getActivity());
        paramSpinAdapt=new ParamSpinnerAdapter(ParameterTypesSingleton.getInstance().getParamTypes(), getActivity());

        _nameEditTextVm=new EditTextViewModel((EditText)rootView.findViewById(R.id.equipment_new_name_edittext), this);
        _paramListViewVm=new ListViewViewModel<>((ListView)rootView.findViewById(R.id.equipment_new_param_listview), equipParamAdapt, this);
        _newParamNameEtVm=new EditTextViewModel((EditText)rootView.findViewById(R.id.equipment_new_param_name_et), this);
        _newParamUnitsEtVm=new EditTextViewModel((EditText)rootView.findViewById(R.id.equipment_new_param_units_et), this);
        _newParamTypeSpinVm= new SpinnerViewModel<>((Spinner)rootView.findViewById(R.id.equip_new_param_type_spin), paramSpinAdapt, this);
        _addParamBtn=(Button)rootView.findViewById(R.id.equipment_new_param_add_btn);
        _newParamBox=(LinearLayout)rootView.findViewById(R.id.equip_new_param_box);
    }

    private void setAttributes(){

        _nameEditTextVm.setText(_equipment.getEquipmentName());

        if (_fragmentMode==FragmentMode.EDIT) getActivity().setTitle(EDIT_EQUIP);
        else if (_fragmentMode==FragmentMode.NEW) getActivity().setTitle(NEW_EQUIP);
        else getActivity().setTitle(VIEW_EQUIP);

        _newParamNameEtVm.setEms(10);
        _newParamBox.setBackgroundResource(R.drawable.add_param_box);
    }

    private void setEvents(){

        _addParamBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Parameter param;

                param=new Parameter(_newParamNameEtVm.getText(), _newParamUnitsEtVm.getText(), _newParamTypeSpinVm.getSelectedItem().getId());
                _paramListViewVm.add(param);
            }
        });

    }

    @Override
    public void textUpdated(int uniqueId, String text) {

        switch(uniqueId){

            case R.id.equipment_new_name_edittext:
                break;

        }
    }

    @Override
    public void onItemClick(Parameter item) {
        // Param listview callback
    }


    @Override
    public void itemSelected(int pos) {
    // Param Type Spinner callback
    }
}
