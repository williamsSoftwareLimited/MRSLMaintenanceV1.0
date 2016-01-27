package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Abstract.IEquipmentCameraCallback;
import org.codebehind.mrslmaintenance.Abstract.IFragmentImagePreviewCallback;
import org.codebehind.mrslmaintenance.Abstract.IImageListFragmentCallback;
import org.codebehind.mrslmaintenance.Models.Abstract.IImageModel;
import org.codebehind.mrslmaintenance.Models.ImageModel;

public class EquipmentCameraActivity extends ActionBarActivityBase implements IEquipmentCameraCallback,IFragmentImagePreviewCallback,IImageListFragmentCallback {
    private ImageListFragment imageListFragment;
    private EquipmentCameraFragment equipmentCameraFragment;
    private ImagePreviewFragment imagePreviewFragment;
    private ImageModel _imageModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FragmentTransaction ft;

        super.onCreate(savedInstanceState);

        _imageModel = new ImageModel(this);
        imageListFragment = new ImageListFragment();
        imageListFragment.setImageModel(_imageModel);
        imagePreviewFragment = new ImagePreviewFragment();
        imagePreviewFragment.setImageModel(_imageModel);

        setContentView(R.layout.activity_equipment_camera);

        if (savedInstanceState == null) {

            ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.container_equipment_camera, imageListFragment);
            ft.commit();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_equipment_camera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        switch (id){
            case R.id.menu_new_image:
                equipmentCameraFragment = new EquipmentCameraFragment();
                changeFragment(R.id.container_equipment_camera, equipmentCameraFragment);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // this is the callback the photo's been taken so do something
    @Override
    public void onPhotoTakenCallback(int id) {

        imagePreviewFragment.setImage(equipmentCameraFragment.getImage());
        changeFragment(R.id.container_equipment_camera, imagePreviewFragment);
    }


    //=================================================
    // these are the callbacks from the image preview
    @Override
    public void saved() {
        // need to refresh the list
        changeFragment(R.id.container_equipment_camera, imageListFragment);
    }

    @Override
    public void cancelled() {

        changeFragment(R.id.container_equipment_camera, imageListFragment);
    }

    //=================================================


    // this method is from image list and has the id of the selected list
    // head to preview with CRUD options
    @Override
    public void onImageSelected(int id) {

        //Toast.makeText(this, "The image id is " + id, Toast.LENGTH_LONG).show();
        imagePreviewFragment.setImage(_imageModel.getImage(id));
        changeFragment(R.id.container_equipment_camera, imagePreviewFragment);

    }

}
