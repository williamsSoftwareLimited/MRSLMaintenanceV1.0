package org.codebehind.mrslmaintenance;


import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.codebehind.mrslmaintenance.Abstract.ActionBarActivityBase;
import org.codebehind.mrslmaintenance.Abstract.IFragmentLocationCallback;
import org.codebehind.mrslmaintenance.Entities.Site;
import org.codebehind.mrslmaintenance.Models.SiteModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {
    MapFragment mapFragment;
    UUID _siteId;
    Site _site;
    IFragmentLocationCallback _listener;

    public LocationFragment() {}

    // pass the current site on if it's null then direct the map to the current location
    public void setSiteId(UUID siteId){
        _siteId=siteId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;

        _site= SiteModel.getInstance().getSite(_siteId);
        rootView = inflater.inflate(R.layout.fragment_map, container, false);
        if (_site==null){
            Toast.makeText(getActivity(),"The site is is null!", Toast.LENGTH_SHORT);
            return rootView;
        }
        mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMap().setOnMapLongClickListener(this);
        // this calls the onMapReady below - async
        mapFragment.getMapAsync(this);
        return rootView;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LocationManager locationManager;
        Criteria criteria;
        String provider;
        Location location;
        LatLng latLng;

        // todo: this need to be running asynch

        map.setMyLocationEnabled(true);

        // this self explanatory code I got from
        // http://stackoverflow.com/questions/15142089/how-to-display-my-location-on-google-maps-for-android-api-v2
        // Getting LocationManager object from System Service LOCATION_SERVICE
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        criteria = new Criteria();

        // Getting the name of the best provider
        provider = locationManager.getBestProvider(criteria, true);

        latLng=null;
        // check if the site is null, will not cause an exception but will find the current location
        if (_site==null || _site.getAddress()==null) {
            // Getting Current Location
            location = locationManager.getLastKnownLocation(provider);
            latLng = new LatLng(location.getLatitude(), location.getLongitude());
        }
        // check if there's a lat and lng, if not get one from the location services
        // put a message up that this is what is happening
        else if (_site.getLatLng() == null) {
            latLng = getLocationFromAddress(_site.getAddress());
            _site.setLatLng(latLng);
        }
        // the latLng must not be null by here
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
        map.addMarker(new MarkerOptions().position(latLng).title("Start"));

    }

    // adjusted quited drastically from http://stackoverflow.com/questions/3574644/how-can-i-find-the-latitude-and-longitude-from-address
    public LatLng getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(getActivity());
        List<Address> address;
        Address location = new Address(new Locale("English","Ireland"));

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            location = address.get(0);

        } catch (IOException ex) {
        }
        return new LatLng(location.getLatitude(), location.getLongitude());
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            _listener = (IFragmentLocationCallback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement IFragmentCallbackUUID");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        _listener = null;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        // this is to just select to map location should also try and fill in the address
        _listener.onFragmentCallbackLatLong(latLng);
    }
}
