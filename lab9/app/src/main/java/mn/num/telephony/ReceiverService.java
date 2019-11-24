package mn.num.telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReceiverService extends BroadcastReceiver {
  String t = null;
  DateFormat df = new SimpleDateFormat("HH:mm:ss");

  @Override
  public void onReceive(Context context, Intent intent) {
    String i = intent.getAction();
    assert i != null;

    if (Intent.ACTION_TIME_CHANGED.equals(i)) {
      Calendar rn = Calendar.getInstance();
      t = "TimeChanged: " +  df.format(rn.getTime()) + "\n";
      MainActivity.getInstance().writeToFileAppend(Telephony.DEVICE_INFO, t, MainActivity.getInstance());
      //Toast.makeText(context, t, Toast.LENGTH_LONG).show();
    }
    if (Intent.ACTION_BOOT_COMPLETED.equals(i)) {
      Calendar rn = Calendar.getInstance();
      t = "BootCompleted:" + df.format(rn.getTime()) + "\n";
      //Toast.makeText(context, t, Toast.LENGTH_LONG).show();
      MainActivity.getInstance().writeToFileAppend(Telephony.DEVICE_INFO, t, MainActivity.getInstance());
    }
    if (Intent.ACTION_POWER_CONNECTED.equals(i)) {
      Calendar rn = Calendar.getInstance();
      t = "PowerConnected: " + df.format(rn.getTime()) + "\n";
      //Toast.makeText(context, t, Toast.LENGTH_LONG).show();
      MainActivity.getInstance().writeToFile(Telephony.DEVICE_INFO, t, MainActivity.getInstance());
    }

    if (Intent.ACTION_POWER_DISCONNECTED.equals(i)) {
      Calendar rn = Calendar.getInstance();
      t = "PowerDisconnected: " + df.format(rn.getTime()) + "\n";
      //Toast.makeText(context, t, Toast.LENGTH_LONG).show();
      MainActivity.getInstance().writeToFileAppend(Telephony.DEVICE_INFO, t, MainActivity.getInstance());
      // todo more
    }

    if (Intent.ACTION_BATTERY_CHANGED.equals(i)) {
      Calendar rn = Calendar.getInstance();
      t = "Battery_Changed: " + df.format(rn.getTime()) + "\n";
      //Toast.makeText(context, t, Toast.LENGTH_LONG).show();
      MainActivity.getInstance().writeToFileAppend(Telephony.DEVICE_INFO, t, MainActivity.getInstance());
    }
  }
}