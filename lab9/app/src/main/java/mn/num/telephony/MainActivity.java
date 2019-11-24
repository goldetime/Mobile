package mn.num.telephony;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

  private Button b;
  private TextView d;
  static MainActivity instance;

  private boolean mSmsPermissionGranted;
  private static final int PERMISSIONS_REQUEST_SMS = 1;

  public static MainActivity getInstance() {
    return instance;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    instance = this;
    getPermission();

    d = (TextView) findViewById(R.id.device_info);

    b = (Button) findViewById(R.id.button);
    b.setOnClickListener((View v) -> {
      Intent i = new Intent(this, Telephony.class);
      startActivity(i);
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Intent it;
    int i = item.getItemId();
    if (i == R.id.option_sms) {
      it = new Intent(this, SmsActivity.class);
      startActivity(it);
    } else if (i == R.id.option_device_info) {
      d.append("\n");
      try {
        d.append(readFromFile(Telephony.DEVICE_INFO, this));
      } catch (Exception e) {

      }

    }
    return true;
  }

  public void writeToFileAppend(String fname, String fcontent, Context context) {
    FileOutputStream outputStream;
    try {
      outputStream = openFileOutput(fname, context.MODE_APPEND);
      outputStream.write(fcontent.getBytes());
      outputStream.close();
    } catch (Exception e) {
      Log.i("File", "exception", e);
    }
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