package mn.num.maps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class LocationService extends BroadcastReceiver {

  public static final String ACTION_PROCESS_UPDATE = "mn.num.maps.UPDATE_LOCATION";

  @Override
  public void onReceive(Context context, Intent intent) {
    if (intent != null) {
      final String action = intent.getAction();
      if (ACTION_PROCESS_UPDATE.equals(action)) {
        LocationResult restult = LocationResult.extractResult(intent);
        if (restult != null) {
          Location location = restult.getLastLocation();

          String s = String.valueOf(location.getLatitude() + " / " + location.getLongitude());
          LatLng position = new LatLng(location.getLatitude(), location.getLongitude());
          try {
            MapsActivity.getInstance().points.add(position);

            int r = MapsActivity.getInstance().points.size();
            Toast.makeText(context, s + "/" + String.valueOf(r), Toast.LENGTH_LONG).show();

            MapsActivity.getInstance().lineOptions = new PolylineOptions();
            MapsActivity.getInstance().lineOptions.addAll(MapsActivity.getInstance().points).width(10).color(Color.GREEN);
            MapsActivity.getInstance().mMap.addPolyline(MapsActivity.getInstance().lineOptions);

          } catch (Exception ex) {
            Log.i("TAG", "e", ex);
          }
        }
      }
    }
  }
}