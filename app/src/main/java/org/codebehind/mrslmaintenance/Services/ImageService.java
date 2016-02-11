package org.codebehind.mrslmaintenance.Services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.codebehind.mrslmaintenance.Services.Abstract.IImageService;

/**
 * Created by root on 10/02/16.
 */
public class ImageService implements IImageService {

    private static final String LOG_TAG="ImageService";


    @Override
    public Bitmap process(byte[] imageData) {
        Bitmap bitmap;
        BitmapFactory.Options options;

        if (imageData==null) {

            Log.wtf(LOG_TAG, "process: violation imageData arg is null.");
            return null;
        }

        options = new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        options.inSampleSize=8;
        options.inJustDecodeBounds=false;

        bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);

        return bitmap;
    }
}
