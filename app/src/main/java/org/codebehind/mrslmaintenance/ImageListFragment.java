package org.codebehind.mrslmaintenance;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.codebehind.mrslmaintenance.Adapters.ImageAdapter;
import org.codebehind.mrslmaintenance.Models.ImageModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class ImageListFragment extends Fragment {

    private ImageModel _imageModel;
    private ListView _imagesListView;

    public ImageListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View view;

        view=inflater.inflate(R.layout.fragment_image_list, container, false);

        _imageModel=new ImageModel(getActivity());

        setControls(view);
        setAttributes();
        setEvents();

        return view;
    }

    private void setControls(View view){

        _imagesListView = (ListView) view.findViewById(R.id.image_list_listview);
    }

    private void setAttributes(){

        _imagesListView.setAdapter(new ImageAdapter(_imageModel.getList(), getActivity()));
    }

    private void setEvents() {

    }
}
