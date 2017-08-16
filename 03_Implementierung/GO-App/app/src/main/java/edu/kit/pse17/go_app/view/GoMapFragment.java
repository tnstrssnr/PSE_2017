package edu.kit.pse17.go_app.view;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.kit.pse17.go_app.R;
import edu.kit.pse17.go_app.model.entities.Go;
import edu.kit.pse17.go_app.viewModel.GoViewModel;

/**
 * Created by Vovas on 14.08.2017.
 */

public class GoMapFragment extends Fragment {
    private MapView mapView;
    private GoogleMap map;
    private LatLng destination;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //displayData(go);
        GoViewModel.getInstance().getGo().observeForever(new Observer<Go>() {
            @Override
            public void onChanged(@Nullable Go go) {
                Log.d("Fragement", "Fragment caught livedata change");
                destination = new LatLng(go.getDesLat(),go.getDesLon());
            }
        });
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.go_map_tab, container, false);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView =  (MapView) view.findViewById(R.id.mapView);

        if(mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    Log.d("MAP READY", "MAP IS READY");
                    map = googleMap;
                    if(destination.latitude == 0 && destination.longitude == 0) {
                        destination = new LatLng(49.013382, 8.404402);
                    }
                    map.addMarker(new MarkerOptions().position(destination).title("GO-Destination"));
                    map.moveCamera(CameraUpdateFactory.newLatLng(destination));
                    map.moveCamera(CameraUpdateFactory.zoomTo(17));

                }
            });
        }
    }
}
