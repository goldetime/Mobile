package mn.num.telephony;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class SmsActivity extends AppCompatActivity {

  private Button send = null;
  private EditText number, content;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sms);

    number = (EditText) findViewById(R.id.number);
    content = (EditText) findViewById(R.id.content);

    send = (Button) findViewById(R.id.send);
    send.setOnClickListener((View v) -> {
      SmsManager sm = SmsManager.getDefault();
      Intent s = new Intent(Receiver1.sent_sms);
      Intent r = new Intent(Receiver2.receive_sms); // dald  // new Intent(receive_sms);

      PendingIntent sentSms = PendingIntent.getBroadcast(this, 0, s, 0);
      PendingIntent dSms = PendingIntent.getBroadcast(this, 0, r, 0);
      try {
        String n = number.getText().toString();
        if (!n.startsWith("+976"))
          n = "+976" + n;
        sm.sendTextMessage(n, null, content.getText().toString(), sentSms, dSms);
        Snackbar.make(findViewById(android.R.id.content), "Sended", Snackbar.LENGTH_LONG).show();
      } catch (SecurityException e) {
        Log.i("TAG", "sec", e);
      }
    });
  }
}