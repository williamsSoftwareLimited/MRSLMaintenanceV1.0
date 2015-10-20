package org.codebehind.mrslmaintenance;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Models.EquipmentModel;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Gavin on 05/01/2015.
 */
public class EquipmentFragment extends Fragment {
    Equipment _equipment;
    private static final int REQUEST_PHOTO=1;
    private static final String TAG = "EquipmentFragment";

    public EquipmentFragment() {
    }
    public static EquipmentFragment newInstance(int id){
        Bundle args = new Bundle();
        args.putSerializable(EquipmentActivity.EQUIPMENT_ID, id);
        EquipmentFragment em = new EquipmentFragment();
        em.setArguments(args);
        return em;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id = (int)getArguments().getSerializable(EquipmentActivity.EQUIPMENT_ID);
        _equipment = EquipmentModel.getInstance().getEquipment(id);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView nameView;
        View rootView = inflater.inflate(R.layout.fragment_equipment, container, false);
        ImageButton imgBtn=(ImageButton)rootView.findViewById(R.id.equipment_imagebtn);

        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getActivity(), EquipmentCameraActivity.class);
                startActivityForResult(i, REQUEST_PHOTO);
            }
        });

        nameView = (TextView)rootView.findViewById(R.id.equipment_name);
        nameView.setText(_equipment.getEquipmentName());

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUEST_PHOTO) {
            // create a new Photo object and attach it to the crime
            String filename = data
                    .getStringExtra(EquipmentCameraFragment.EXTRA_PHOTO_FILENAME);
            if (filename != null) {
                Log.i(TAG, "filename:" + filename);
            }
        }
    }
}