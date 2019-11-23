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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


public class Telephony extends AppCompatActivity {
	TelephonyManager telephonyManager;
	TextView textOut;

	@RequiresApi(api = Build.VERSION_CODES.M)
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_telephony);

		textOut = (TextView) findViewById(R.id.textOut);

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

		// deviceInfo.txt
	}

	class PhoneListener extends PhoneStateListener {
		String stateString = "N/A";

		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
				case TelephonyManager.CALL_STATE_IDLE:
					stateString = "Idle";
					break;
				case TelephonyManager.CALL_STATE_RINGING:
					stateString = "Ringing";
					break;
			}
			textOut.append(String.format("\nonCallStateChanged: %s", stateString));
		}

		public void onCallForwardingIndicatorChanged(boolean cfi) {
			if (cfi)
				stateString = "true";
			else
				stateString = "false";
			textOut.append(String.format("\nonCallForwardingIndicator: %s", stateString));
		}

		public void onCellInfoChanged(List<CellInfo> cellInfo) {
			textOut.append(String.format("\nonCellInfoChanged: %s", cellInfo));
		}

		public void onCellLocationChanged(CellLocation location) {
			textOut.append(String.format("\nonCellLocationChanged: %s", location.toString()));
		}

		public void onDataActivity(int direction) {
			textOut.append(String.format("\nonDataActivity: %s", String.valueOf(direction)));
		}

		public void onServiceStateChanged (ServiceState state) {
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
			textOut.append(String.format("\nonDataActivity: %s", stateString));
		}
	}
}