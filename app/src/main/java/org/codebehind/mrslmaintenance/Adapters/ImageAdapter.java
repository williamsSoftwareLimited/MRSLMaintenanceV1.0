package org.codebehind.mrslmaintenance.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.codebehind.mrslmaintenance.Adapters.Abstract.AbstractAdapter;
import org.codebehind.mrslmaintenance.Entities.Image;
import org.codebehind.mrslmaintenance.R;
import org.codebehind.mrslmaintenance.Services.Abstract.IImageService;
import org.codebehind.mrslmaintenance.Services.ImageService;
import org.codebehind.mrslmaintenance.ViewModels.ImageViewVm;

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
        byte[] data;
        IImageService imageService;

        if (null == convertView) {

            convertView=_activity.getLayoutInflater().inflate(R.layout.fragment_image_list_item, null);
        }

        image=getItem(position);

        data = image.getImage();

        if (data!=null) {

            imageService=new ImageService();

            imageView = (ImageView) convertView.findViewById(R.id.fragment_image_list_item_image);

            imageView.setImageBitmap(imageService.process(data));
        }

        return convertView;
    }

}
