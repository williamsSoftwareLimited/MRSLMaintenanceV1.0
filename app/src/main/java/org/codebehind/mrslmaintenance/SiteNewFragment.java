package org.codebehind.mrslmaintenance;

import android.os.Bundle;
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

import org.codebehind.mrslmaintenance.Abstract.ISiteActAllowDelete;
import org.codebehind.mrslmaintenance.Adapters.Abstract.AbstractAdapter;
import org.codebehind.mrslmaintenance.Adapters.EquipmentAdapter;
import org.codebehind.mrslmaintenance.Adapters.SiteEquipmentAdapter;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Entities.SiteEquipment;
import org.codebehind.mrslmaintenance.Models.EquipmentDbModel;
import org.codebehind.mrslmaintenance.Models.SiteEquipmentDbModel;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IEditTextViewModelDelegate;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IListViewVmDelegate;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.ISpinnerViewModelDelegate;
import org.codebehind.mrslmaintenance.ViewModels.EditTextViewModel;
import org.codebehind.mrslmaintenance.ViewModels.ListViewViewModel;
import org.codebehind.mrslmaintenance.ViewModels.SpinnerViewModel;


public class SiteNewFragment extends Fragment implements IEditTextViewModelDelegate, ISpinnerViewModelDelegate, IListViewVmDelegate<SiteEquipment> {

    private static final String SITE_NEW_FRAGMENT_BUNDLE="SiteNewFragment_Bundle",
                                LOG_TAG="SiteNewFragment";
    private EditTextViewModel _nameEditTextVm, _addressEditTextVm, _newEquipNameEditTextVm;
    private ListViewViewModel<SiteEquipment> _siteEquipListViewVm;
    private Button _addEquipBtn;
    private LinearLayout _addEquipBox;
    private SpinnerViewModel<Equipment> _equipSpinnerVm;
    private AbstractAdapter<Equipment> _equipSpinAdapter;
    private Site _site;
    private FragmentMode _fragmentMode;
    private SiteEquipmentDbModel _siteEquipModel;


    public void setFragmentMode(FragmentMode fragmentMode){
        _fragmentMode=fragmentMode;
    }

    public void setAddEquipMode(int mode){

        if (_addEquipBox==null) return;

        _addEquipBox.setVisibility(mode);
    }

    public void setSite(Site site){

        if (site==null) return;

        _site=site;
    }

    public SiteNewFragment() { }

    public static SiteNewFragment newInstance(Site site){
        Bundle bundle;
        SiteNewFragment siteNewFragment;

        if (site==null) Log.wtf(LOG_TAG, "newInstance: violation site==null.");

        bundle = new Bundle();
        bundle.putSerializable(SITE_NEW_FRAGMENT_BUNDLE, site);

        siteNewFragment=new SiteNewFragment();
        siteNewFragment.setArguments(bundle);
        return siteNewFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;


        view= inflater.inflate(R.layout.fragment_site_new, container, false);

        _site=(Site)getArguments().getSerializable(SITE_NEW_FRAGMENT_BUNDLE);

        if (_site==null) Log.wtf(LOG_TAG, "onCreateView: violation _site==null. This may cause SiteNewFragment to crash.");

        _siteEquipModel=new SiteEquipmentDbModel(getActivity());

        setControls(view);
        setAttributes();
        setEvents();

        return view;
    }

    public void deleteSelectedEquip(){
        // this is called from the Activity when the delete icon is pressed
        int rowCount;

        Log.d(LOG_TAG, "deleteSelectedEquip: deleting siteEquipId="+_siteEquipListViewVm.getEntity().getId()+".");

        rowCount=_siteEquipModel.delete(_siteEquipListViewVm.getEntity());

        if (rowCount==0) {
            Log.d(LOG_TAG, "deleteSelectedEquip: the siteEquip wasn't deleted, rowCount="+rowCount);
        } else{
            _siteEquipListViewVm.delete(_siteEquipListViewVm.getEntity());
        }
    }

    private void setControls(View rootView){
        EquipmentDbModel equipmentDbModel;
        SiteEquipmentAdapter siteEquipAdapt;

        equipmentDbModel=new EquipmentDbModel(getActivity());

        _nameEditTextVm=new EditTextViewModel((EditText)rootView.findViewById(R.id.site_new_name), this);
        _addressEditTextVm=new EditTextViewModel((EditText)rootView.findViewById(R.id.site_new_Address), this);

        _equipSpinAdapter=new EquipmentAdapter(equipmentDbModel.getList(), getActivity());
        _equipSpinnerVm=new SpinnerViewModel<>((Spinner)rootView.findViewById(R.id.site_new_equipment_spinner), _equipSpinAdapter,this);

        siteEquipAdapt=new SiteEquipmentAdapter(_siteEquipModel.getSiteEquipments(_site.getId()), getActivity());
        _siteEquipListViewVm=new ListViewViewModel<>((ListView) rootView.findViewById(R.id.site_new_equipment_listview), siteEquipAdapt, this);

        _addEquipBtn=(Button)rootView.findViewById(R.id.site_new_add_equipment_button);

        _newEquipNameEditTextVm=new EditTextViewModel((EditText)rootView.findViewById(R.id.site_new_add_equip_name_edittext), this);

        _addEquipBox=(LinearLayout)rootView.findViewById(R.id.site_new_add_equip_box);
    }

    private void setAttributes(){

        if (_fragmentMode==FragmentMode.VIEW) {

            _nameEditTextVm.setNonEditable();
            _addressEditTextVm.setNonEditable();

            setAddEquipMode(View.GONE);
            _siteEquipListViewVm.setSelection(false);

        } else {

            setAddEquipMode(_site.getId()==-1 ? View.GONE : View.VISIBLE);
            _siteEquipListViewVm.setSelection(true);
        }

        _nameEditTextVm.setText(_site.getName());
        _addressEditTextVm.setText(_site.getAddress());

        _addEquipBtn.setEnabled(false);

        _addEquipBox.setBackgroundResource(R.drawable.add_site_equip_box);
    }

    private void setEvents() {

        _addEquipBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Equipment equip;
                SiteEquipment se;

                if (!_newEquipNameEditTextVm.getText().equals("")) {

                    equip = _equipSpinnerVm.getSelectedItem();
                    Log.d(LOG_TAG, "setEvents: EquipBtn Click event, equipId=" + equip.getId());

                    se = new SiteEquipment(_site.getId(), equip.getId(), _newEquipNameEditTextVm.getText());
                    se.setEquipment(equip);

                    _siteEquipModel.add(se);
                    _siteEquipListViewVm.add(se);

                } else {

                    _addEquipBtn.setEnabled(false);
                }
            }
        });

    }

    @Override
    public void textUpdated(int uniqueId, String text) {

        switch (uniqueId){

            case R.id.site_new_name:

                _site.setName(text);
                break;

            case R.id.site_new_Address:

                _site.setAddress(text);
                break;

            case R.id.site_new_add_equip_name_edittext:

                if (_newEquipNameEditTextVm.getText().equals("")) _addEquipBtn.setEnabled(false);
                else _addEquipBtn.setEnabled(true);
                break;
        }
    }

    @Override
    public void itemSelected(int pos) {
        // this is the item selected event from the EquipSpinner
    }

    @Override
    public void onItemClick(SiteEquipment item) {
        // this is the click event from the SiteEquipListView
        ISiteActAllowDelete siteNewAct;

        // callback to activity to show the delete icon
        siteNewAct=(ISiteActAllowDelete)getActivity(); // this is an uncomfortable cast

        siteNewAct.showDeleteIcon(true);
    }
}
