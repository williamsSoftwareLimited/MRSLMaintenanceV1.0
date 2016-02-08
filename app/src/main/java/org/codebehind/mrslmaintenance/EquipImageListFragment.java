package org.codebehind.mrslmaintenance;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Abstract.IImageListFragmentCallback;
import org.codebehind.mrslmaintenance.Adapters.ImageAdapter;
import org.codebehind.mrslmaintenance.Entities.Image;
import org.codebehind.mrslmaintenance.Models.ImageModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class EquipImageListFragment extends Fragment {
    private ImageModel _imageModel;
    private ListView _imagesListView;
    private IImageListFragmentCallback _listener;

    public void setImageModel(ImageModel imageModel) {

        _imageModel = imageModel;
    }

    public EquipImageListFragment() {}

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {

            _listener = (IImageListFragmentCallback) activity;

        } catch (ClassCastException e) {

            throw new ClassCastException(activity.toString() + " must implement IImageListFragmentCallback");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.fragment_equip_image_list, container, false);

        setControls(view);
        setAttributes();
        setEvents();

        return view;
    }

    private void setControls(View view){

        _imagesListView = (ListView) view.findViewById(R.id.equip_image_list_listview);
    }

    private void setAttributes(){

        _imagesListView.setAdapter(new ImageAdapter(_imageModel.getList(), getActivity()));
    }

    private void setEvents(){

        _imagesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Image image;

                image = (Image) parent.getItemAtPosition(position);
                _listener.onImageSelected(image.getId());
            }

        });

    }


}
