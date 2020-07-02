package com.fiek.androidapp;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static String TAG = MapsActivity.class.getName();
    private List<LatLng> latLngArrayList;
    private int node_count=0;
    private Button contraction;
    private Button expansion;
    private Button clear;
    Polyline polyline;
    private LatLng marker1;
    private LatLng marker2;
    private int markercount=0;
    private ClusterManager<MyItem> mClusterManager;
    private float currentZoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        latLngArrayList = new ArrayList<>();
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_3:
                mMap.animateCamera(CameraUpdateFactory.zoomIn());
                Toast.makeText(getApplicationContext(),"Zoom level: "+mMap.getCameraPosition().zoom, Toast.LENGTH_SHORT).show();
                break;
            case KeyEvent.KEYCODE_1:
                mMap.animateCamera(CameraUpdateFactory.zoomOut());
                Toast.makeText(getApplicationContext(),"Zoom level: "+mMap.getCameraPosition().zoom, Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng nagpur = new LatLng(21.1458, 79.0882);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(nagpur));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        currentZoom=10;

        setUpClusterer(mMap);

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
               latLngArrayList.add(point);
                mMap.addMarker(new MarkerOptions().position(point).title(""+(++node_count)));
            }
        });

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                if(cameraPosition.zoom!=currentZoom) {
                    if(cameraPosition.zoom>currentZoom)
                    Toast.makeText(getApplicationContext(), "Zoom level: " + mMap.getCameraPosition().zoom+"\n Expanding ...", Toast.LENGTH_SHORT).show();
                    currentZoom=cameraPosition.zoom;
                }
                else{
                    Toast.makeText(getApplicationContext(), "Zoom level: " + mMap.getCameraPosition().zoom+"\n Compressing ...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return false;
            }
        });
    }
    private void setUpClusterer(GoogleMap mMap) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(42.4635,21.4694), 10));

        mClusterManager = new ClusterManager<MyItem>(this, mMap);

        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

    }
    private void addItems(LatLng point) {
        MyItem offsetItem = new MyItem(point.latitude, point.longitude);
        mClusterManager.addItem(offsetItem);
    }

    public void startClustering(View view) {
        List<MyItem> myItemList = new ArrayList<>();
        for(LatLng p: latLngArrayList)
        {
            myItemList.add(new MyItem(p.latitude,p.longitude));
        }
        mClusterManager.addItems(myItemList);
    }

    public void clearMap(View view) {
        if(mMap!=null)
            mMap.clear();
    }
}
