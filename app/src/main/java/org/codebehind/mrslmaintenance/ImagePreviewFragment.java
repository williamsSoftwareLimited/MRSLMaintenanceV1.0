package org.codebehind.mrslmaintenance;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.codebehind.mrslmaintenance.Abstract.IFragmentImagePreviewCallback;
import org.codebehind.mrslmaintenance.Entities.Image;
import org.codebehind.mrslmaintenance.Models.Abstract.IImageModel;

/**
 * Created by Gavin on 02/03/2015.
 */
public class ImagePreviewFragment  extends Fragment {
    private IImageModel _imageModel;
    private ImageView _imageView;
    private EditText _titleEditText;
    private Button _saveBtn, _cancelBtn, _deleteBtn;
    private IFragmentImagePreviewCallback _listener;
    private Image _image;

    public void setImageModel(IImageModel imageModel){
        _imageModel=imageModel;
    }
    public void setImage(Image i){
        _image = i;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            _listener = (IFragmentImagePreviewCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement IFragmentImagePreviewCallback");
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;

        view = inflater.inflate(R.layout.fragment_image_preview, container, false);
        setControls(view);
        setText();
        setEvents();
        return view;
    }
    @Override
    public void onResume() {
        // this fixes a weird bug that the edittext doesn't update unless called from here!
        super.onResume();
        setText();
    }
    private void setControls(View view){
        _imageView = (ImageView)view.findViewById(R.id.fragment_image_preview_imageview);
        _titleEditText=(EditText)view.findViewById(R.id.fragment_image_preview_title_edittext);
        _saveBtn=(Button)view.findViewById(R.id.imagePreviewSaveBtn);
        _cancelBtn=(Button)view.findViewById(R.id.imagePreviewCancelBtn);
        _deleteBtn=(Button)view.findViewById(R.id.imagePreviewDeleteBtn);
    }
    private void setText(){
        Bitmap bitmap;
        byte[] data;

        data = _image.getImage();
        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        _imageView.setImageBitmap(bitmap);
        _titleEditText.setText(_image.getTitle());

        // if the image has an id then it's not new to display the delete button
        if (_image.getId()>0)_deleteBtn.setVisibility(View.VISIBLE);
    }
    private void setEvents(){
        _saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _image.setTitle(_titleEditText.getText().toString());
                _imageModel.insert(_image);
                _listener.saved();
            }
        });
        _cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _listener.cancelled();
            }
        });
        _deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _imageModel.delete(_image);
                _listener.cancelled();
            }
        });
    }
}
