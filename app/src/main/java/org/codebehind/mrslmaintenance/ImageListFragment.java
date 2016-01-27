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
import org.codebehind.mrslmaintenance.Models.Abstract.IImageModel;
import org.codebehind.mrslmaintenance.Models.ImageModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageListFragment extends Fragment {
    private ImageModel _imageModel;
    private ListView imagesListView;
    private IImageListFragmentCallback _listener;

    public void setImageModel(ImageModel imageModel) {
        _imageModel = imageModel;
    }

    public ImageListFragment() {}

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

        view = inflater.inflate(R.layout.fragment_image_list, container, false);

        imagesListView = (ListView) view.findViewById(R.id.fragment_image_list_listview);
        imagesListView.setAdapter(new imageAdapter(_imageModel.getAll()));
        imagesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int imageId;
                Cursor cursor;

                cursor = (Cursor) parent.getItemAtPosition(position);
                imageId = cursor.getInt(ImageModel.ID);
                _listener.onImageSelected(imageId);
            }
        });
        return view;
    }

    class imageAdapter extends CursorAdapter {
        private ImageView imageView;
        private TextView titleTextView;

        public imageAdapter(Cursor imageCursor) {
            super(getActivity(), imageCursor);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater;
            View v;

            inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.fragment_image_list_item, parent, false);
            return v;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor){
            byte[] data;
            Bitmap bitmap;
            BitmapFactory.Options options;

            titleTextView = (TextView) view.findViewById((R.id.fragment_image_list_item_title));
            titleTextView.setText(cursor.getString(ImageModel.TITLE));
            data = cursor.getBlob(ImageModel.IMAGE);

            if (data!=null) {

                options = new BitmapFactory.Options();
                options.inJustDecodeBounds=true;
                options.inSampleSize=8;
                options.inJustDecodeBounds=false;
                bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,options);
                imageView = (ImageView) view.findViewById(R.id.fragment_image_list_item_image);
                imageView.setImageBitmap(bitmap);
            }

        }

    }

}
