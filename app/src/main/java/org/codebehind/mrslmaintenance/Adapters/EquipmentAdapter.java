package org.codebehind.mrslmaintenance.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Adapters.Abstract.AbstractAdapter;
import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.R;

import java.util.ArrayList;

/**
 * Created by root on 25/10/15.
 */
public class EquipmentAdapter extends AbstractAdapter<Equipment> {

    public EquipmentAdapter(ArrayList<Equipment> equips, Activity activity) {
        super(activity, android.R.layout.simple_list_item_1, equips);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Equipment equip;
        TextView nameTv;
        //ImageView imageBtn;

        if (null == convertView) {

            convertView = _activity.getLayoutInflater().inflate(R.layout.equipment_list_listitem, null);
        }

        equip = getItem(position);
        nameTv = (TextView)convertView.findViewById((R.id.equipment_list_item_name_textview));
        nameTv.setText(equip.getEquipmentName());


        //imageBtn = (ImageView)convertView.findViewById(R.id.equipment_list_item_imagebutton);
        //imageBtn.setImageResource(R.drawable.ic_action_picture);

        return convertView;
    }

}
