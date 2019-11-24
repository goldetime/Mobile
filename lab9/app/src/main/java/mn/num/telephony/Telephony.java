package mn.num.telephony;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class Telephony extends AppCompatActivity {
	TelephonyManager telephonyManager;
//	TextView textOut;
	private TextView r = null;
	private Button read = null;
	private Button sim = null;
	public StringBuilder textOut = new StringBuilder();
	public final static String PHONE_STATE = "phonestate.txt";
	public final static String SIM_INFO = "SIMInfo.txt";
	public final static String DEVICE_INFO = "deviceinfo.txt";

	@RequiresApi(api = Build.VERSION_CODES.M)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_telephony);

//		textOut = (TextView) findViewById(R.id.textOut);
		r = (TextView) findViewById(R.id.textOut);

		read = (Button) findViewById(R.id.read);
		read.setOnClickListener((View v) -> {
			r.setText(readFromFile(PHONE_STATE, Telephony.this));
		});

		sim = (Button) findViewById(R.id.read_sim);
		sim.setOnClickListener((View v) -> {
			r.setText(readFromFile(SIM_INFO, Telephony.this));
		});

		// Get the telephony manager
		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

		// Register the listener with the telephony manager
		telephonyManager.listen(new PhoneListener(),
				PhoneStateListener.LISTEN_CALL_STATE
						| PhoneStateListener.LISTEN_CALL_FORWARDING_INDICATOR
						| PhoneStateListener.LISTEN_CELL_INFO // Requires API 17
						| PhoneStateListener.LISTEN_CELL_LOCATION
						| PhoneStateListener.LISTEN_DATA_ACTIVITY
						| PhoneStateListener.LISTEN_SERVICE_STATE);

		int simState = telephonyManager.getSimState();

		String s = null;
		switch (simState) {
			case TelephonyManager.SIM_STATE_ABSENT:
				s = "absent";
				break;
			case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
				s = "network locked";
				break;
			case TelephonyManager.SIM_STATE_PIN_REQUIRED:
				s = "pin required";
				break;
			case TelephonyManager.SIM_STATE_PUK_REQUIRED:
				s = "puk required";
				break;
			case TelephonyManager.SIM_STATE_READY:
				// writeToFile("SIMInfo.txt", "content", Telephony.this);
				s = "ready";
				if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
					//   public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
					return;
				}
				String phoneNumber = telephonyManager.getLine1Number();
				textOut.append(String.format("\nPhone Number: %s", phoneNumber));

				String simCountry = telephonyManager.getSimCountryIso();
				textOut.append(String.format("\nSim Country: %s", simCountry));

				String opCode = telephonyManager.getSimOperator();
				textOut.append(String.format("\nOperator Code: %s", opCode));

				String opName = telephonyManager.getSimOperatorName();
				textOut.append(String.format("\nOperator Name: %s", opName));

				String simSerial = telephonyManager.getSimSerialNumber();
				textOut.append(String.format("\nSim Serial: %s", simSerial));
				break;
			case TelephonyManager.SIM_STATE_UNKNOWN:
				s = "unknown";
				break;
		}
		textOut.append(String.format("\nSim State: %s", s));
		writeToFile(SIM_INFO, textOut.toString(), Telephony.this);
	}

	class PhoneListener extends PhoneStateListener {
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		String stateString = "N/A";

		public void onCallStateChanged(int state, String incomingNumber) {
			Calendar rn = Calendar.getInstance();
			switch (state) {
				case TelephonyManager.CALL_STATE_IDLE:
					stateString = "Idle at: " + df.format(rn.getTime());
					break;
				case TelephonyManager.CALL_STATE_RINGING:
					stateString = "Ringing at: " + df.format(rn.getTime());
					break;
			}
			String t = String.format("\nonCallStateChanged: %s", stateString);
			writeToFile(PHONE_STATE, t, Telephony.this);
		}

		public void onCallForwardingIndicatorChanged(boolean cfi) {
			Calendar rn = Calendar.getInstance();
			if (cfi)
				stateString = "true  at: " + df.format(rn.getTime());
			else
				stateString = "false  at: " + df.format(rn.getTime());
			String t = String.format("\nonCallForwardingIndicator: %s", stateString);
			writeToFile(PHONE_STATE, t, Telephony.this);
		}

		public void onCellInfoChanged(List<CellInfo> cellInfo) {
			Calendar rn = Calendar.getInstance();
			String t = String.format("\nonCellInfoChanged: %s", cellInfo + " at: "
					+ df.format(rn.getTime()));
			writeToFile(PHONE_STATE, t, Telephony.this);
		}

		public void onCellLocationChanged(CellLocation location) {
			Calendar rn = Calendar.getInstance();
//			textOut.append(String.format("\nonCellLocationChanged: %s", location.toString() + " at: "
//					+ df.format(rn.getTime())));
			String t = String.format("\nonCellLocationChanged: %s", location.toString() + " at: "
					+ df.format(rn.getTime()));
			writeToFile(PHONE_STATE, t, Telephony.this);
		}

		public void onDataActivity(int direction) {
			Calendar rn = Calendar.getInstance();
			String t = String.format("\nonDataActivity: %s", String.valueOf(direction) + " at: "
					+ df.format(rn.getTime()));
			writeToFile(PHONE_STATE, t, Telephony.this);
		}

		public void onServiceStateChanged (ServiceState state) {
			Calendar rn = Calendar.getInstance();
			stateString = "N/A";
			switch (state.getState()) {
				case ServiceState.STATE_EMERGENCY_ONLY:
					stateString = "EMERGENCY ONLY!";
					break;
				case ServiceState.STATE_IN_SERVICE:
					stateString = "IN SERVICE!";
					break;
				case ServiceState.STATE_OUT_OF_SERVICE:
					stateString = "OUT OF SERVICE!";
					break;
				case ServiceState.STATE_POWER_OFF:
					stateString = "POWER OFF!";
					break;
			}
			String t = String.format("\nonDataActivity: %s", stateString + " at: "
					+ df.format(rn.getTime()));
			writeToFile(PHONE_STATE, t, Telephony.this);
		}

		public void writeToFile(String fname, String fcontent, Context context) {
			FileOutputStream outputStream;
			try {
				outputStream = openFileOutput(fname, context.MODE_APPEND);
				outputStream.write(fcontent.getBytes());
				outputStream.close();
			} catch (Exception e) {
				Log.i("File", "exception", e);
			}
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
}