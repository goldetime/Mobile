package  mn.num.maps;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * An activity that displays a map showing the place at the device's current location.
 */
public class MapsActivity extends AppCompatActivity
    implements OnMapReadyCallback {

  public static final int MAP_TYPE_NORMAL = 1;
  public static final int MAP_TYPE_SATELLITE = 2;
  public static final int MAP_TYPE_TERRAIN = 3;
  public static final int MAP_TYPE_HYBRID = 4;

  private static final String TAG = MapsActivity.class.getSimpleName();
  private GoogleMap mMap;
  private CameraPosition mCameraPosition;

  private FusedLocationProviderClient mFusedLocationProviderClient;

  private final LatLng mDefaultLocation = new LatLng(-33.8523341, 151.2106085);
  private static final int DEFAULT_ZOOM = 15;
  private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
  private boolean mLocationPermissionGranted;

  // The geographical location where the device is currently located. That is, the last-known
  // location retrieved by the Fused Location Provider.
  private Location mLastKnownLocation;

  // Keys for storing activity state.
  private static final String KEY_CAMERA_POSITION = "camera_position";
  private static final String KEY_LOCATION = "location";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Retrieve location and camera position from saved instance state.
    if (savedInstanceState != null) {
      mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
      mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
    }

    // Retrieve the content view that renders the map.
    setContentView(R.layout.activity_maps);

    // Construct a FusedLocationProviderClient.
    mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

    // Build the map.
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);
  }

  /**
   * Saves the state of the map when the activity is paused.
   */
  @Override
  protected void onSaveInstanceState(Bundle outState) {
    if (mMap != null) {
      outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
      outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
      super.onSaveInstanceState(outState);
    }
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
    return true;
  }

  @Override
  public void onMapReady(GoogleMap map) {
    mMap = map;
//    mMap.setMapType(MAP_TYPE_SATELLITE);
    mMap.getUiSettings().setZoomControlsEnabled(true);
    mMap.getUiSettings().setCompassEnabled(true);

    // Prompt the user for permission.
    getLocationPermission();

    // Turn on the My Location layer and the related control on the map.
    // updateLocationUI();

    // Get the current location of the device and set the position of the map.
    getDeviceLocation();
  }

  /**
   * Gets the current location of the device, and positions the map's camera.
   */
  private void getDeviceLocation() {
    /*
     * Get the best and most recent location of the device, which may be null in rare
     * cases when a location is not available.
     */
    try {
      if (mLocationPermissionGranted) {
        Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
        locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
          @Override
          public void onComplete(@NonNull Task<Location> task) {
            if (task.isSuccessful()) {
              // Set the map's camera position to the current location of the device.
              mLastKnownLocation = task.getResult();
              mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                  new LatLng(mLastKnownLocation.getLatitude(),
                      mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
            } else {
              Log.d(TAG, "Current location is null. Using defaults.");
              Log.e(TAG, "Exception: %s", task.getException());
              mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, 30));
              mMap.getUiSettings().setMyLocationButtonEnabled(false);
            }
          }
        });
      }
    } catch (SecurityException e)  {
      Log.e("Exception: %s", e.getMessage());
    }
  }

  /**
   * Prompts the user for permission to use the device location.
   */
  private void getLocationPermission() {
    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
    if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
        android.Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED) {
      mLocationPermissionGranted = true;
    } else {
      ActivityCompat.requestPermissions(this,
          new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
          PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
    }
  }

  /**
   * Handles the result of the request for location permissions.
   */
  @Override
  public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
    mLocationPermissionGranted = false;
    switch (requestCode) {
      case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          mLocationPermissionGranted = true;
        }
      }
    }
    updateLocationUI();
  }

  /**
   * Updates the map's UI settings based on whether the user has granted location permission.
   */
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
}