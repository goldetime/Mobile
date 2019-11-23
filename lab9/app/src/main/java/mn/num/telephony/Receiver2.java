package mn.num.telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Receiver2 extends BroadcastReceiver {

  final static String receive_sms = "android.provider.Telephony.SMS_RECEIVED";

  @Override
  public void onReceive(Context context, Intent intent) {

    if (!intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION))
      return;

    switch (getResultCode()) {
      case AppCompatActivity.RESULT_OK:
        Toast.makeText(context, "SMS Delivered", Toast.LENGTH_SHORT).show();
        break;
      case AppCompatActivity.RESULT_CANCELED:
        Toast.makeText(context, "SMS not delivered", Toast.LENGTH_SHORT).show();
        break;
    }
  }
}