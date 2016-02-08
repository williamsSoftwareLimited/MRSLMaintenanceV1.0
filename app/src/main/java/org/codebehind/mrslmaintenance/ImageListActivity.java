package org.codebehind.mrslmaintenance;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.codebehind.mrslmaintenance.Entities.Image;
import org.codebehind.mrslmaintenance.Models.EquipmentDbModel;
import org.codebehind.mrslmaintenance.Models.ImageModel;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IListViewVmDelegate;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class ImageListActivity extends ActionBarActivity implements IListViewVmDelegate<Image> {

    private static final String LOG_TAG="ImageListActivity";
    private static final int PICK_IMAGE=1,
                             IMAGE_LIMIT=1000000;
    private ImageListFragment _imageListFragment;
    private Menu _menu;
    private ImageModel _imageModel;
    private Image _imageSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FragmentTransaction ft;


        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) return;

        setContentView(R.layout.activity_image_list);

        _imageModel = new ImageModel(this);

        _imageListFragment =new ImageListFragment();

        ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.image_list_container, _imageListFragment);
        ft.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        _menu=menu;

        getMenuInflater().inflate(R.menu.menu_image_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id;
        Intent intent;

        id = item.getItemId();

        switch (id){

            case R.id.menu_image_add:

                intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);

                return true;

            case R.id.menu_image_del:

                if (_imageSelected!=null) {

                    _imageModel.delete(_imageSelected);
                    _imageListFragment.removeImage(_imageSelected);
                }

                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri targetUri;
        InputStream stream;
        Image image;

        if (resultCode == RESULT_OK){

             if (requestCode == PICK_IMAGE) {

                 // save the image to the image table
                 Log.d(LOG_TAG, "onActivityResult: request to save image in db.");

                 targetUri=data.getData();

                 try {

                     stream=getContentResolver().openInputStream(targetUri);

                     image=new Image(null,"");
                     image.setImage(_imageModel.readBytes(stream));

                     if (image.getImage().length>IMAGE_LIMIT){

                         Toast.makeText(this, "The image limit has been exceeded! Reduce the size to below 1Mb. Use PhotoEditor or something similar.", Toast.LENGTH_LONG).show();
                         Log.wtf(LOG_TAG, "onActivityResult: the image size is larger than 1Mb.");

                         return;
                     }
                     image.setId(_imageModel.insert(image));

                     _imageListFragment.addImage(image);

                 } catch (FileNotFoundException e){}
            }

        }

    }


    // this is from the ListViewVm
    @Override
    public void onItemClick(Image image) {

        // show the menu
        Log.d(LOG_TAG, "onItemClick: received imageId=" + image.getId() + ".");

        _menu.findItem(R.id.menu_image_del).setVisible(true);

        _imageSelected=image;

    }


}
