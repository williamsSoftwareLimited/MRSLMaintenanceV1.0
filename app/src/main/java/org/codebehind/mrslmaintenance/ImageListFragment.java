package org.codebehind.mrslmaintenance;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.codebehind.mrslmaintenance.Adapters.ImageAdapter;
import org.codebehind.mrslmaintenance.Entities.Image;
import org.codebehind.mrslmaintenance.Models.ImageModel;
import org.codebehind.mrslmaintenance.ViewModels.Abstract.IListViewVmDelegate;
import org.codebehind.mrslmaintenance.ViewModels.ListViewViewModel;

public class ImageListFragment extends Fragment {

    private ImageModel _imageModel;
    private ListViewViewModel<Image> _imagesListViewVm;

    public ImageListFragment() {}

    public void addImage(Image image){

        _imagesListViewVm.add(image);
    }

    public void removeImage(Image image){

        _imagesListViewVm.delete(image);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view;

        view=inflater.inflate(R.layout.fragment_image_list, container, false);

        _imageModel=new ImageModel(getActivity());

        setControls(view);
        setAttributes();
        //setEvents();

        return view;
    }

    private void setControls(View view){
        ImageAdapter imageAdapter;

        imageAdapter=new ImageAdapter(_imageModel.getList(), getActivity());
        _imagesListViewVm = new ListViewViewModel<> ((ListView) view.findViewById(R.id.image_list_listview),imageAdapter, (IListViewVmDelegate<Image>)getActivity());
    }

    private void setAttributes(){

        _imagesListViewVm.setSelection(true);
    }

    private void setEvents() {}
}
