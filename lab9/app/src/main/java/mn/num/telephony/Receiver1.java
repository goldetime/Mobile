package mn.num.telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Receiver1 extends BroadcastReceiver {
  final static String sent_sms = "android.provider.Telephony.SMS_DELIVER";

  @Override
  public void onReceive(Context context, Intent intent) {
    if (!intent.getAction().equals(Telephony.Sms.Intents.SMS_DELIVER_ACTION))
      return;

    switch (getResultCode()) {
      case AppCompatActivity.RESULT_OK:
        Toast.makeText(context, "OK", Toast.LENGTH_LONG).show();
        break;
      case SmsManager.RESULT_ERROR_NO_SERVICE:
        Toast.makeText(context, "No service", Toast.LENGTH_SHORT).show();
        break;
      default:
        Toast.makeText(context, "Not sent", Toast.LENGTH_SHORT).show();
    }
  }
}