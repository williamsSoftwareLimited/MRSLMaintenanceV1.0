package org.codebehind.mrslmaintenance;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;


public class ImageListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FragmentTransaction ft;
        ImageListFragment imageListFragment;

        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) return;

        setContentView(R.layout.activity_image_list);

        //_imageModel = new ImageModel(this);

        imageListFragment =new ImageListFragment();
        //equipImageListFragment.setImageModel(_imageModel);

        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.image_list_container, imageListFragment);
        ft.commit();

    }

}
