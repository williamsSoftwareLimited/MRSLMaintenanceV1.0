package org.codebehind.mrslmaintenance;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.codebehind.mrslmaintenance.Abstract.Fragment2ActivityListener;
import org.codebehind.mrslmaintenance.Abstract.FragmentMenubarListener;
import org.codebehind.mrslmaintenance.Abstract.IFragmentCallbackUUID;
import org.codebehind.mrslmaintenance.Adapters.EquipmentAdapter;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Models.EquipmentModel;
import org.codebehind.mrslmaintenance.Models.ReportModel;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Gavin on 27/01/2015.
 */
public class EquipmentListFragment extends Fragment implements AdapterView.OnItemClickListener{
    ListView _equipListview;
    IFragmentCallbackUUID _fragmentCallback;

    public EquipmentListFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            _fragmentCallback = (IFragmentCallbackUUID) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement IFragmentCallbackUUID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        ArrayAdapter<Equipment> equipAdapter;

        rootView = inflater.inflate(R.layout.fragment_equipment_list, container, false);
        equipAdapter = new EquipmentAdapter(EquipmentModel.getInstance().getList(), getActivity());

        _equipListview = (ListView)rootView.findViewById(R.id.equipment_list_listview);
        _equipListview.setAdapter(equipAdapter);
        _equipListview.setOnItemClickListener(this);

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int equipmentId;
        View v = _equipListview.getChildAt(position);
        for (int i=0; i<_equipListview.getCount();i++)
            _equipListview.getChildAt(i).setBackgroundColor(0);
        v.setBackgroundColor(Color.WHITE);

        equipmentId = EquipmentModel.getInstance().getList().get(position).getId();
        _fragmentCallback.onFragmentCallback(equipmentId);
    }

}