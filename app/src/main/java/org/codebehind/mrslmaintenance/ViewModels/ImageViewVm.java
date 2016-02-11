package org.codebehind.mrslmaintenance.ViewModels;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.codebehind.mrslmaintenance.Services.Abstract.IImageService;
import org.codebehind.mrslmaintenance.Services.ImageService;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IImageVIewVmDelegate;


/**
 * Created by root on 18/01/16.
 */
public class ImageViewVm {
    // may need IImageVIewVmDelegate to handle the click event

    private static final String LOG_TAG="ImageViewVm";
    private ImageView _imageView;
    private IImageVIewVmDelegate _delegate;

    public ImageViewVm(ImageView imageView, IImageVIewVmDelegate delegate){

        if (imageView==null) Log.wtf(LOG_TAG, "construct: violation imageView arg is null.");
        if (delegate==null) Log.wtf(LOG_TAG, "construct: violation parentFrag arg is null.");


        _imageView=imageView;
        _delegate=delegate;

        _imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                IImageVIewVmDelegate delegate;
                // do whatever processing required
                delegate=_delegate;

                // defensive programming
                if (delegate==null) {

                    Log.wtf(LOG_TAG, "onClick: violation private delegate is null.");

                }else {

                    delegate.onImageClick();

                }

            }

        });

    }

    public void setImage(byte [] data){
        IImageService imageService;

        if (data==null) {

            Log.wtf(LOG_TAG, "setImage: violation data arg is null.");
            return;
        }

        imageService=new ImageService();

        _imageView.setImageBitmap(imageService.process(data));

    }

}
