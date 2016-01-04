package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.codebehind.mrslmaintenance.Adapters.Abstract.AbstractAdapter;
import org.codebehind.mrslmaintenance.Adapters.EquipmentAdapter;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Models.EquipmentDbModel;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IListViewVmDelegate;
import org.codebehind.mrslmaintenance.ViewModels.ListViewViewModel;

/**
 * Created by Gavin on 27/01/2015.
 */
public class EquipmentListFragment extends Fragment implements IListViewVmDelegate<Equipment> {
    private ListViewViewModel<Equipment> _equipListviewVm;
    private static final String LOG_TAG="EquipmentListFragment";
    public static final String BUNDLE_EQUIP="EQUIP_LIST_FRAGMENT_SITE_BUNDLE";

    public EquipmentListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;


        rootView = inflater.inflate(R.layout.fragment_equipment_list, container, false);

        setControls(rootView);
        //setAttributes();
        //setEvents();

        return rootView;
    }

    private  void setControls(View rootView){
        AbstractAdapter<Equipment> equipAdapter;
        EquipmentDbModel equipMod;

        equipMod=new EquipmentDbModel(getActivity());

        equipAdapter = new EquipmentAdapter(equipMod.getList(), getActivity());
        _equipListviewVm=new ListViewViewModel<>((ListView)rootView.findViewById(R.id.equipment_list_listview), equipAdapter, this);
    }

    private void setAttributes(){}

    private void setEvents() {}

    @Override
    public void onItemClick(Equipment selectedEquip) {
        Bundle bundle;
        Intent intent;

        Log.d(LOG_TAG, "The selected siteId is " + selectedEquip.getId());

        bundle = new Bundle();
        bundle.putSerializable(BUNDLE_EQUIP, selectedEquip);

        intent = new Intent(getActivity(), EquipmentNewActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}