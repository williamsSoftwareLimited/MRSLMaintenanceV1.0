package org.codebehind.mrslmaintenance.Services.Abstract;

import android.graphics.Bitmap;

/**
 * Created by root on 10/02/16.
 */
public interface IImageService {

    Bitmap process(byte[] imageData);

}
