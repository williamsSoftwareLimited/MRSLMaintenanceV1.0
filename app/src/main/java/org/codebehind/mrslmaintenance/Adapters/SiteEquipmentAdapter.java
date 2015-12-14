package org.codebehind.mrslmaintenance.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Entities.Equipment;
import org.codebehind.mrslmaintenance.Entities.SiteEquipment;
import org.codebehind.mrslmaintenance.R;

import java.util.ArrayList;

/**
 * Created by root on 13/12/15.
 */
public class SiteEquipmentAdapter  extends ArrayAdapter<SiteEquipment> {

    private Activity _activity;

    public SiteEquipmentAdapter(ArrayList<SiteEquipment> equips, Activity activity) {
        super(activity, android.R.layout.simple_list_item_1, equips);

        _activity=activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SiteEquipment siteEquipment;
        TextView tvName;
        ImageView imageBtn;

        if (null == convertView) {

            convertView = _activity.getLayoutInflater()
                    .inflate(R.layout.equipment_list_listitem, null);
        }

        siteEquipment = getItem(position);
        tvName = (TextView)convertView.findViewById((R.id.equipment_list_item_nametextview));
        tvName.setText(siteEquipment.getName());
        imageBtn = (ImageView)convertView.findViewById(R.id.equipment_list_item_imagebutton);
        imageBtn.setImageResource(R.drawable.ic_action_picture);

        return convertView;
    }

}
