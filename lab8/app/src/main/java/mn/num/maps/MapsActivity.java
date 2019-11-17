package  mn.num.maps;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

  public static final int MAP_TYPE_NORMAL = 1;
  public static final int MAP_TYPE_SATELLITE = 2;
  public static final int MAP_TYPE_TERRAIN = 3;
  public static final int MAP_TYPE_HYBRID = 4;

  int i = 0;

  List<Marker> markers = new ArrayList<Marker>();

  private static final String TAG = MapsActivity.class.getSimpleName();
  public GoogleMap mMap;

  private FusedLocationProviderClient mFusedLocationProviderClient;

  private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
  private static final int DEFAULT_ZOOM = 15;
  private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
  private boolean mLocationPermissionGranted;

  private Location mLastKnownLocation;
  private LocationRequest locationRequest;

  static MapsActivity instance;

  public List<LatLng> points = new ArrayList<LatLng>();
  public List<LatLng> tarr = new ArrayList<LatLng>();
  public PolylineOptions lineOptions = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);

    instance = this;

    mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);
  }

  public static MapsActivity getInstance() {
    return instance;
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.current_place_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int i = item.getItemId();
    if (i == R.id.option_normal)
      mMap.setMapType(MAP_TYPE_NORMAL);
    else if (i == R.id.option_satellite)
      mMap.setMapType(MAP_TYPE_SATELLITE);
    else if (i == R.id.option_terrain)
      mMap.setMapType(MAP_TYPE_TERRAIN);
    else if (i == R.id.option_hybrid)
      mMap.setMapType(MAP_TYPE_HYBRID);
    else if (i == R.id.option_distance) {
      measureDistance();
    }
    return true;
  }

  @Override
  public void onMapReady(GoogleMap map) {
    mMap = map;
    mMap.getUiSettings().setZoomControlsEnabled(true);

    getLocationPermission();

    updateLocationUI();
    //getDeviceLocation();

    updateLocation();

    mMap.setMyLocationEnabled(true);

    markerM(mMap);

//    tarr.add(new LatLng(47.921608, 106.88595));
//    tarr.add(new LatLng(47.921609, 106.88596));
//    tarr.add(new LatLng(47.921612, 106.88599));
//
//    lineOptions = new PolylineOptions();
//    lineOptions.addAll(tarr)
//        .width(10)
//        .color(Color.RED);
//
//    mMap.addPolyline(lineOptions);
  }

  private void measureDistance() {
    int n = markers.size();

    if (n >= 2) {
      float[] results = new float[1];

      double Alat = markers.get(n - 2).getPosition().latitude;
      double Alon = markers.get(n - 2).getPosition().longitude;

      double Blat = markers.get(n - 1).getPosition().latitude;
      double Blon = markers.get(n - 1).getPosition().longitude;

      Location.distanceBetween(Alat, Alon, Blat, Blon, results);

      String r = String.valueOf(Math.ceil(results[0]));
      r += "m";
      Toast.makeText(this, r, Toast.LENGTH_LONG).show();
    }
  }

  private void markerM(GoogleMap mMap) {
    mMap.setOnMapLongClickListener((LatLng pos) -> {
      Marker marker = mMap.addMarker(new MarkerOptions().position(pos));
      marker.setDraggable(true);
      markers.add(marker);
      measureDistance();
    });

    mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
      @Override
      public void onMarkerDragStart(Marker marker) {
        markers.remove(marker);
        marker.remove();
        measureDistance();
      }

      @Override
      public void onMarkerDrag(Marker marker) {
        Log.i(TAG, "run");
      }

      @Override
      public void onMarkerDragEnd(Marker marker) {
        Log.i(TAG, "end");
      }
    });
  }

  /*
  private void getDeviceLocation() {
    try {
      if (mLocationPermissionGranted) {
        Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
        locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
						@Override
						public void onComplete(@NonNull Task<Location> task) {
							if (task.isSuccessful()) {
								// Set the map's camera position to the current location of the device.
								mLastKnownLocation = task.getResult();
							} else {
								Log.e(TAG, "Exception: %s", task.getException());
								mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
								mMap.getUiSettings().setMyLocationButtonEnabled(false);
							}
						}
					});
      }
    } catch (SecurityException e)  {
      Log.e("Exception: %s", e.getMessage());
    }
  }
  */

  private void getLocationPermission() {
    if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
      mLocationPermissionGranted = true;
    } else {
      ActivityCompat.requestPermissions(this,
																				new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
																				PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
    mLocationPermissionGranted = false;
    switch (requestCode) {
		case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
			if (grantResults.length > 0
					&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				mLocationPermissionGranted = true;
			}
      break;
    }
    updateLocationUI();
  }

  private void updateLocationUI() {
    if (mMap == null) {
      return;
    }
    try {
      if (mLocationPermissionGranted) {
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
      } else {
        mMap.setMyLocationEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mLastKnownLocation = null;
        getLocationPermission();
      }
    } catch (SecurityException e)  {
      Log.e("Exception: %s", e.getMessage());
    }
  }

  public void updateLocation() {
    locationRequest = new LocationRequest();
    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    locationRequest.setInterval(2000);
    locationRequest.setFastestInterval(1000);
    locationRequest.setSmallestDisplacement(10f);

    mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    getLocationPermission();

    mFusedLocationProviderClient.requestLocationUpdates(locationRequest, getPendingInent());
  }

  private PendingIntent getPendingInent() {
    Intent i = new Intent(this, LocationService.class);
    i.setAction(LocationService.ACTION_PROCESS_UPDATE);
    return PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
  }
}