package org.codebehind.mrslmaintenance.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Adapters.Abstract.AbstractAdapter;
import org.codebehind.mrslmaintenance.Entities.Image;
import org.codebehind.mrslmaintenance.Models.ImageModel;
import org.codebehind.mrslmaintenance.R;

import java.util.ArrayList;

/**
 * Created by root on 01/02/16.
 */
public class ImageAdapter extends AbstractAdapter<Image> {

    public ImageAdapter(ArrayList<Image> images, Activity activity) {

        super(activity, android.R.layout.simple_list_item_1, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Image image;
        ImageView imageView;
        TextView titleTextView;
        byte[] data;
        Bitmap bitmap;
        BitmapFactory.Options options;

        if (null == convertView) {

            convertView=_activity.getLayoutInflater().inflate(R.layout.fragment_image_list_item, null);
        }

        image=getItem(position);
        titleTextView = (TextView) convertView.findViewById((R.id.fragment_image_list_item_title));
        titleTextView.setText(image.getTitle());

        data = image.getImage();

        if (data!=null) {

            options = new BitmapFactory.Options();
            options.inJustDecodeBounds=true;
            options.inSampleSize=8;
            options.inJustDecodeBounds=false;
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length,options);

            imageView = (ImageView) convertView.findViewById(R.id.fragment_image_list_item_image);

            imageView.setImageBitmap(bitmap);
        }

        return convertView;
    }

}
