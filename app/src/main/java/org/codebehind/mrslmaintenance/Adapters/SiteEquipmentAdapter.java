package org.codebehind.mrslmaintenance.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Adapters.Abstract.AbstractAdapter;
import org.codebehind.mrslmaintenance.Entities.SiteEquipment;
import org.codebehind.mrslmaintenance.R;

import java.util.ArrayList;

/**
 * Created by root on 13/12/15.
 */
public class SiteEquipmentAdapter extends AbstractAdapter<SiteEquipment> {


    public SiteEquipmentAdapter(ArrayList<SiteEquipment> equips, Activity activity) {
        super(activity, android.R.layout.simple_list_item_1, equips);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SiteEquipment siteEquipment;
        TextView nameTv;
        TextView typeTv;

        if (null == convertView) {

            convertView = _activity.getLayoutInflater()
                    .inflate(R.layout.site_equipment_list_item, null);
        }

        siteEquipment = getItem(position);

        nameTv = (TextView)convertView.findViewById((R.id.site_equipment_list_item_name_textview));
        nameTv.setText(siteEquipment.getName());

        typeTv = (TextView)convertView.findViewById((R.id.site_equipment_list_item_type_textview));
        typeTv.setText(siteEquipment.getEquipment().getEquipmentName());


        return convertView;
    }

}
