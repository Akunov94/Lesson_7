package kg.geektech.android3.lesson_7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import kg.geektech.android3.lesson_7.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnCameraMoveListener {

    private final static int LOCATION_PERMISSION_CODE = 3;

    private GoogleMap map;
    private ActivityMainBinding ui;
    private final List<LatLng> latLngList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        setupListeners();

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (ContextCompat
                    .checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                map.setMyLocationEnabled(true);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (ActivityCompat
                .checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.
                                checkSelfPermission(
                                        this,
                                        Manifest.permission.ACCESS_COARSE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        }

        /** Animate Camera */
        CameraPosition position = CameraPosition.fromLatLngZoom(new LatLng(42.8788252,74.6210675), 15f);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(position);
        map.animateCamera(cameraUpdate);

        map.setOnMapClickListener(this);
        map.setOnMarkerClickListener(this);
//        map.setOnCameraMoveListener(this);
    }

    private void setupListeners() {
        ui.btnMapHybrid.setOnClickListener(v -> map.setMapType(GoogleMap.MAP_TYPE_HYBRID));
        ui.btnMapNormal.setOnClickListener(v -> map.setMapType(GoogleMap.MAP_TYPE_NORMAL));
        ui.btnMapPolygon.setOnClickListener(v -> {
            PolygonOptions polygonOptions = new PolygonOptions().addAll(latLngList);
            polygonOptions.strokeColor(Color.RED);
            polygonOptions.strokeWidth(3f);
            polygonOptions.fillColor(Color.BLUE);
            map.addPolygon(polygonOptions);
        });
        ui.btnMapPolyline.setOnClickListener(v -> {
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.addAll(latLngList);
            map.addPolyline(polylineOptions);
        });
    }

    @Override
    public void onMapClick(LatLng latLng) {
        latLngList.add(latLng);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title("Marker")
                .draggable(true);
//                .anchor(1, 0.7f)
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.arrow2));
        map.addMarker(markerOptions);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        marker.remove();
        return false;
    }

    @Override
    public void onCameraMove() {
        Toast.makeText(this, "CAMERA MOVED", Toast.LENGTH_SHORT).show();
    }
}