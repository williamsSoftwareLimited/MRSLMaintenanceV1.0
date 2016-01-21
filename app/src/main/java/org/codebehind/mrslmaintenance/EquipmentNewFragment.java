package org.codebehind.mrslmaintenance;

import android.app.*;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.codebehind.mrslmaintenance.Abstract.IActAllowDelete;
import org.codebehind.mrslmaintenance.Abstract.IEquipParamDelegate;
import org.codebehind.mrslmaintenance.Adapters.Abstract.AbstractAdapter;
import org.codebehind.mrslmaintenance.Adapters.EquipParamAdapter;
import org.codebehind.mrslmaintenance.Adapters.ParamSpinnerAdapter;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.EquipmentParameters;
import org.codebehind.mrslmaintenance.Entities.Parameter;
import org.codebehind.mrslmaintenance.Entities.ParameterType;
import org.codebehind.mrslmaintenance.Models.EquipmentParamsDbModel;
import org.codebehind.mrslmaintenance.Models.ParameterDbModel;
import org.codebehind.mrslmaintenance.Singletons.ParameterTypesSingleton;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IEditTextViewModelDelegate;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IImageVIewVmDelegate;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IListViewVmDelegate;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.ISpinnerViewModelDelegate;
import org.codebehind.mrslmaintenance.ViewModels.EditTextViewModel;
import org.codebehind.mrslmaintenance.ViewModels.ImageViewVm;
import org.codebehind.mrslmaintenance.ViewModels.ListViewViewModel;
import org.codebehind.mrslmaintenance.ViewModels.SpinnerViewModel;

import java.io.FileNotFoundException;
import java.util.Hashtable;


/**
 * Created by Gavin on 09/02/2015.
 */
public class EquipmentNewFragment extends Fragment implements IEditTextViewModelDelegate, IListViewVmDelegate<Parameter>,ISpinnerViewModelDelegate, IImageVIewVmDelegate {

    public static final String EQUIP_ARG="EQUIP_NEW_FRAG_EQUIP_ARG",
                                MODE_ARG="EQUIP_MODE_FRAG_EQUIP_ARG",
                               LOG_TAG="EquipmentNewFragment";
    private static final String EDIT_EQUIP="Edit Equipment", VIEW_EQUIP="Equipment", NEW_EQUIP="New Equipment";
    private Equipment _equipment;
    private FragmentMode _fragmentMode;
    private EditTextViewModel _nameEditTextVm, _newParamNameEtVm, _newParamUnitsEtVm;
    private SpinnerViewModel<ParameterType> _newParamTypeSpinVm;
    private Button _addParamBtn;
    private LinearLayout _newParamBox;
    private ListViewViewModel<Parameter> _paramListViewVm;
    private ImageViewVm _equipImageVm;

    public Equipment getEquip(){
        return _equipment;
    }

    public EquipmentNewFragment(){}

    public static EquipmentNewFragment newInstance(Equipment equip, FragmentMode fragMode){
        Bundle bundle;
        EquipmentNewFragment equipNewFragment;

        if (equip==null) Log.wtf(LOG_TAG, "newInstance: violation equip==null.");

        bundle = new Bundle();
        bundle.putSerializable(EQUIP_ARG, equip);
        bundle.putSerializable(MODE_ARG, fragMode);

        equipNewFragment=new EquipmentNewFragment();
        equipNewFragment.setArguments(bundle);
        return equipNewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView;

        rootView = inflater.inflate(R.layout.fragment_equipment_new, container, false);

        _equipment=(Equipment)getArguments().getSerializable(EQUIP_ARG);

        setFragmentMode((FragmentMode) getArguments().getSerializable(MODE_ARG));

        if (_equipment==null) Log.wtf(LOG_TAG, "onCreateView: violation _equipment==null. This may cause EquipmentNewFragment to crash.");

        setControls(rootView);
        setAttributes();
        setEvents();

        return rootView;
    }


    private void setFragmentMode(FragmentMode fragmentMode){

        _fragmentMode=fragmentMode;

        if (_nameEditTextVm==null||_newParamNameEtVm==null||_newParamUnitsEtVm==null||_newParamBox==null)return;

        if (_fragmentMode==FragmentMode.VIEW){

            _nameEditTextVm.setEnabled(false);
            _newParamNameEtVm.setEnabled(false);
            _newParamUnitsEtVm.setEnabled(false);
            _newParamBox.setVisibility(View.GONE);

            _paramListViewVm.setSelection(false);

        }else{

            _nameEditTextVm.setEnabled(true);
            _newParamNameEtVm.setEnabled(true);
            _newParamUnitsEtVm.setEnabled(true);

            if (_fragmentMode==FragmentMode.EDIT) {

                _newParamBox.setVisibility(View.VISIBLE);

            }else{
                // this is SAVE mode

                _newParamBox.setVisibility(View.GONE);
            }

            _paramListViewVm.setSelection(true);
        }

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
        _equipImageVm=new ImageViewVm((ImageView)rootView.findViewById(R.id.equipment_new_preview_imageview), this);
    }

    private void setAttributes(){
        Uri targetUri;

        _nameEditTextVm.setText(_equipment.getEquipmentName());

        if (_fragmentMode==FragmentMode.EDIT) getActivity().setTitle(EDIT_EQUIP);

        else if (_fragmentMode==FragmentMode.NEW) getActivity().setTitle(NEW_EQUIP);

        else getActivity().setTitle(VIEW_EQUIP);

        _newParamNameEtVm.setEms(6);
        _newParamBox.setBackgroundResource(R.drawable.add_param_box);

        _addParamBtn.setEnabled(false);

        setFragmentMode(_fragmentMode);

        // this needs to get the image from the database
        //targetUri=Uri.parse(_equipment.getImgPath());
        //_equipImageVm.setImage(targetUri);

    }

    private void setEvents(){

        _addParamBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Parameter param;
                EquipmentParameters equipParam;
                ParameterDbModel paramModel;
                EquipmentParamsDbModel equipParamModel;

                param = new Parameter(_newParamNameEtVm.getText(), _newParamUnitsEtVm.getText(), _newParamTypeSpinVm.getSelectedItem().getId());

                paramModel = new ParameterDbModel(getActivity());
                param.setId(paramModel.add(param));

                if (param.getId() < 1) return;

                equipParam = new EquipmentParameters(_equipment.getId(), param.getId());

                equipParamModel = new EquipmentParamsDbModel(getActivity());
                equipParam.setId(equipParamModel.add(equipParam));

                if (equipParam.getId() < 1) {

                    // rollback
                    //paramModel.delete(param);
                    return;
                }

                _paramListViewVm.add(param);

            }
        });

    }

    @Override
    public void textUpdated(int uniqueId, String text) {

        switch(uniqueId){

            case R.id.equipment_new_name_edittext:

                _equipment.setEquipmentName(_nameEditTextVm.getText());

                break;

            case R.id.equipment_new_param_name_et:

                if (_newParamNameEtVm.getText().equals("")) _addParamBtn.setEnabled(false);
                else _addParamBtn.setEnabled(true);

                break;

            case R.id.equipment_new_param_units_et:

                break;
        }
    }

    @Override
    public void onItemClick(Parameter item) {
        // Param listview callback
        IEquipParamDelegate siteNewAct;
        EquipmentParameters equipParams;

        // callback to activity to show the delete icon
        siteNewAct=(IEquipParamDelegate)getActivity(); // this is an uncomfortable cast

        equipParams=new EquipmentParameters(_equipment.getId(), item.getId());

        siteNewAct.onCallback(equipParams);
    }

    @Override
    public void itemSelected(int pos) {
        // Param Type Spinner callback
    }

    @Override
    public void onImageClick() {
        // this is the equipImageview event called from the vm
        Intent intent;

        intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri targetUri;
        
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK){
            
            targetUri = data.getData();

            Log.d(LOG_TAG, "onActivityResult: the image path is "+targetUri);

            _equipImageVm.setImage(targetUri);

        }

    }

}
