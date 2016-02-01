package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Abstract.IImageListFragmentCallback;
import org.codebehind.mrslmaintenance.Models.ImageModel;

public class EquipImageListActivity extends ActionBarActivityBase implements IImageListFragmentCallback {

    public static final String  EQUIP_IMAGE_SELECTED_EXTRA="IMAGE_SELECTED_EXTRA";
    private ImageModel _imageModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FragmentTransaction ft;
        EquipImageListFragment equipImageListFragment;

        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) return;

        setContentView(R.layout.activity_equip_image_list);

        _imageModel = new ImageModel(this);

        equipImageListFragment =new EquipImageListFragment();
        equipImageListFragment.setImageModel(_imageModel);

        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.equip_image_list_container, equipImageListFragment);
        ft.commit();
    }

    @Override
    public void onImageSelected(int imageId) {
        Intent intent;

        intent=new Intent();
        intent.putExtra(EQUIP_IMAGE_SELECTED_EXTRA, imageId);

        setResult(RESULT_OK, intent);
        finish();
    }

}
