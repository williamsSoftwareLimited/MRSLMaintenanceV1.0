package org.codebehind.mrslmaintenance;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Adapters.ParameterAdapter;
import org.codebehind.mrslmaintenance.Entities.Image;
import org.codebehind.mrslmaintenance.Entities.SiteEquipment;
import org.codebehind.mrslmaintenance.Models.ImageModel;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IImageVIewVmDelegate;
import org.codebehind.mrslmaintenance.ViewModels.ImageViewVm;
import org.codebehind.mrslmaintenance.ViewModels.TextViewViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportNewEquipmentFragment extends Fragment  implements IImageVIewVmDelegate {

    private static final String SITE_EQUIPMENT_ARG="REPORT_NEW_EQUIPMENT_SITE_EQUIPMENT_ARG";
    private SiteEquipment _siteEquipment;
    private TextViewViewModel _equipmentNameTextView, _equipmentIdTextView, _siteEquipTextViewVm;
    private ListView _parametersListView;
    private ImageViewVm _imageViewVm;

    public static ReportNewEquipmentFragment newInstance(SiteEquipment siteEquipment){
        Bundle bundle;
        ReportNewEquipmentFragment reportNewEquipmentFragment;

        bundle = new Bundle();
        bundle.putSerializable(SITE_EQUIPMENT_ARG, siteEquipment);

        reportNewEquipmentFragment = new ReportNewEquipmentFragment();
        reportNewEquipmentFragment.setArguments(bundle);

        return reportNewEquipmentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;

        rootView = inflater.inflate(R.layout.fragment_report_new_equipment, container, false);

        _siteEquipment = (SiteEquipment)getArguments().getSerializable(SITE_EQUIPMENT_ARG);

        setControls(rootView);
        setAttributes();
        setEvents();

        return rootView;
    }

    private void setControls(View rootView) {

        _siteEquipTextViewVm=new TextViewViewModel((TextView)rootView.findViewById(R.id.rep_new_equip_site_equip_name));
        _equipmentIdTextView=new TextViewViewModel((TextView)rootView.findViewById(R.id.report_new_equipment_id));
        _equipmentNameTextView=new TextViewViewModel((TextView)rootView.findViewById(R.id.report_new_equipment_name));
        _parametersListView=(ListView)rootView.findViewById(R.id.report_new_equipment_params);

        _imageViewVm=new ImageViewVm((ImageView)rootView.findViewById(R.id.report_new_equipment_image_view), this);
    }

    private void setAttributes(){
        ImageModel imageModel;
        Image image;

        _siteEquipTextViewVm.setText(_siteEquipment.getName());
        _equipmentIdTextView.setText(""+_siteEquipment.getId());
        _equipmentNameTextView.setText(_siteEquipment.getEquipment().getEquipmentName());
        _parametersListView.setAdapter(new ParameterAdapter(_siteEquipment.getEquipment().getParameterList(), getActivity()));

        if (_siteEquipment.getEquipment().getImgId()>0) {

            imageModel=new ImageModel(getActivity());
            image=imageModel.getImage(_siteEquipment.getEquipment().getImgId());

            if (image!=null) _imageViewVm.setImage(image.getImage());

        }
    }

    private void setEvents() {

    }

    @Override
    public void onImageClick() {

    }

}
