package org.codebehind.mrslmaintenance;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.codebehind.mrslmaintenance.Abstract.IEquipmentCameraCallback;
import org.codebehind.mrslmaintenance.Entities.Image;

import java.io.IOException;
import java.util.List;

/**
 * Created by Gavin on 06/01/2015.
 */
@SuppressWarnings("deprecation")
public class EquipmentCameraFragment extends Fragment {
    private android.hardware.Camera _camera;
    final static String TAG="EquipmentCameraFragment";
    public static final String EXTRA_PHOTO_FILENAME = "org.CodeBehind.android.MRSLMaintenance.photo_filename";
    private View _progressCntr;
    private IEquipmentCameraCallback _listener;
    private Image _image;

    public Image getImage(){return _image;}
    public EquipmentCameraFragment() { }

    private android.hardware.Camera.ShutterCallback mShutterCallback = new android.hardware.Camera.ShutterCallback() {
        public void onShutter() {
            // display the progress indicator
            _progressCntr.setVisibility(View.VISIBLE);
        }
    };
    private android.hardware.Camera.PictureCallback mJpegCallBack = new android.hardware.Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, android.hardware.Camera camera) {

            _image = new Image(data,StaticConstants.IMAGE_TITLE);
            // pass control back to the activity
            _listener.onPhotoTakenCallback(-1);
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_equipment_camera, container, false);
        Button picBtn = (Button)rootView.findViewById(R.id.equipment_camera_button);
        SurfaceView picView = (SurfaceView)rootView.findViewById(R.id.equipment_camera_surfaceview);
        SurfaceHolder holder = picView.getHolder();
        _progressCntr=(View)rootView.findViewById(R.id.equipment_camera_progressContainer);

        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        _progressCntr.setVisibility(View.INVISIBLE);

        picBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(_camera!=null){
                    _camera.takePicture(mShutterCallback, null, mJpegCallBack);
                }
            }
        });
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try{
                    if(_camera!=null){
                        _camera.setPreviewDisplay(holder);
                    }
                }catch (IOException ex){
                    Log.e(TAG,"Error setting up the preview display.");
                }
            }
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                android.hardware.Camera.Parameters parameters = _camera.getParameters();
                android.hardware.Camera.Size s = getBestSupportedSize(parameters.getSupportedPreviewSizes(),width,height);

                parameters.setPreviewSize(s.width,s.height);
                s = getBestSupportedSize(parameters.getSupportedPictureSizes(),width,height);
                parameters.setPictureSize(s.width,s.height);
                _camera.setParameters(parameters);
                try{
                    _camera.startPreview();
                }catch (Exception ex){
                    Log.e(TAG,"Could not start preview",ex);
                    _camera.release();
                    _camera=null;
                }
            }
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) { }
        });
        return rootView;
    }
    @Override
    public void onResume(){
        super.onResume();
        _camera= android.hardware.Camera.open(0);
    }
    @Override
    public void onPause(){
        super.onPause();
        if (_camera!=null){
            _camera.release();
            _camera=null;
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            _listener = (IEquipmentCameraCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement IFragmentCallbackUUID");
        }
    }
    /** a simple algorithm to get the largest size available. For a more
     * robust version, see CameraPreview.java in the ApiDemos
     * sample app from Android. */
    private android.hardware.Camera.Size getBestSupportedSize(List<android.hardware.Camera.Size> sizes, int width, int height) {
        android.hardware.Camera.Size bestSize = sizes.get(0);
        int largestArea = bestSize.width * bestSize.height;
        for (android.hardware.Camera.Size s : sizes) {
            int area = s.width * s.height;
            if (area > largestArea) {
                bestSize = s;
                largestArea = area;
            }
        }
        return bestSize;
    }
}
