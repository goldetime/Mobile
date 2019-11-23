package mn.num.telephony;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

  private Button b;

  private boolean mSmsPermissionGranted;
  private static final int PERMISSIONS_REQUEST_SMS = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    getPermission();

    b = findViewById(R.id.button);

    b.setOnClickListener((View v) -> {
      Intent i = new Intent(this, Telephony.class);
      startActivity(i);
    });

//    b.setOnClickListener((View v) -> {
//      writeToFile("a.txt", "Real", this);
//      Snackbar.make(findViewById(android.R.id.content), "write to file", Snackbar.LENGTH_LONG).show();
//
//      String r = readFromFile("a.txt", this);
//      Toast.makeText(this, r, Toast.LENGTH_LONG).show();
//    });

//    b.setOnClickListener((View v) -> {
//      writeToFile("a.txt", "Second");
//
//      getPermission();
//
//      SmsManager sm = SmsManager.getDefault();
//
//      Intent s = new Intent(Receiver1.sent_sms);
//      Intent r = new Intent(Receiver2.receive_sms); // dald  // new Intent(receive_sms);
//
//      PendingIntent sentSms = PendingIntent.getBroadcast(MainActivity.this, 0, s, 0);
//      PendingIntent dSms = PendingIntent.getBroadcast(MainActivity.this, 0, r, 0);
//      if (mSmsPermissionGranted) {
//        try {
//          //sm.sendTextMessage("+97695202601", null, "SEND with receiver real", sentSms, dSms);
//        } catch (SecurityException e) {
//          Log.i("TAG", "sec", e);
//        }
//      }
//      Snackbar.make(findViewById(android.R.id.content), "write to file", Snackbar.LENGTH_LONG).show();
//    });
  }

  public void writeToFile(String fname, String fcontent, Context context) {
    FileOutputStream outputStream;
    try {
      outputStream = openFileOutput(fname, context.MODE_PRIVATE);
      outputStream.write(fcontent.getBytes());
      outputStream.close();
    } catch (Exception e) {
      Log.i("File", "exception", e);
    }
  }

  public String readFromFile(String fname, Context context) {
    String contents = null;
    try {
      FileInputStream fis = context.openFileInput(fname);
      InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
      StringBuilder s = new StringBuilder();
      try (BufferedReader reader = new BufferedReader(inputStreamReader)) {
        String line = reader.readLine();
        while (line != null) {
          s.append(line).append('\n');
          line = reader.readLine();
        }
      } catch (IOException e) {
        Log.i("TAG", "io exception", e);
      } finally {
        contents = s.toString();
      }
    } catch (FileNotFoundException e) {
      Log.i("TAG", "File not found!", e);
    }
    return contents;
  }

  private void getPermission() {
    if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
        Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
      mSmsPermissionGranted = true;
    } else {
      ActivityCompat.requestPermissions(this,
          new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS,
              Manifest.permission.ACCESS_COARSE_LOCATION,
              Manifest.permission.READ_PHONE_STATE},
          PERMISSIONS_REQUEST_SMS);
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
    mSmsPermissionGranted = false;
    switch (requestCode) {
      case PERMISSIONS_REQUEST_SMS:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
          mSmsPermissionGranted = true;
        break;
    }
  }
}