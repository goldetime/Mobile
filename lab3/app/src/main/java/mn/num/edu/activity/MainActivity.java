package mn.num.edu.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActivityChooserView;

import mn.num.edu.activity.rating.RatingActivity;
import mn.num.edu.activity.settings.SettingsActivity;

import static android.telephony.MbmsDownloadSession.RESULT_CANCELLED;

public class MainActivity extends AppCompatActivity {
  static final int REQUEST = 1;
  static final int REQUEST_T = 2;
  static final int REQUEST_R = 3;
  static final int REQUEST_S = 4;
  Button b = null;
	Button activity2 = null;
  String textMessage = null;
  Intent i = null;
  CheckBox pizza,coffe,burger;
  Button x = null;
  Button y = null;
  Button z = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    b = findViewById(R.id.button1);
    b.setOnClickListener((View v) -> {
				i = new Intent(this, ExampleActivity.class);
        startActivityForResult(i, REQUEST_T);
			});

		activity2 = findViewById(R.id.button2);
		activity2.setOnClickListener((View v) -> {
		 		i = new Intent(this, RatingActivity.class);
				startActivityForResult(i, REQUEST_R);
			});

		x = findViewById(R.id.button3);
		x.setOnClickListener((View v) -> {
      Intent dial = new Intent();
      dial.setAction(android.content.Intent.ACTION_DIAL);
      dial.setData(Uri.parse("tel:1234567"));
      startActivity(dial);
    });

		y = findViewById(R.id.button4);
		y.setOnClickListener((View v) -> {
      String q = "google.com";
      Intent intent = new Intent(Intent.ACTION_WEB_SEARCH );
      intent.putExtra(SearchManager.QUERY, q);
      startActivity(intent);
    });

		z = findViewById(R.id.button5);
		z.setOnClickListener((View v) -> {
      Intent read1=new Intent();
      read1.setAction(android.content.Intent.ACTION_VIEW);
      read1.setData(ContactsContract.Contacts.CONTENT_URI);
      startActivity(read1);
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main_menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    Intent i;
    // Handle item selection
    switch (item.getItemId()) {
		case R.id.settings:
			i = new Intent();

			if (textMessage == null)
				textMessage = "GNU public license!";

			i.setAction("SETT");
			i.putExtra(Intent.EXTRA_TEXT, textMessage);
			i.setType("text/plain");

			//startActivity(i);
			startActivityForResult(i, REQUEST_S);
			return true;
		case R.id.about:
			i = new Intent();

			if (textMessage == null)
				textMessage = "GNU public license!";

			i.setAction(Intent.ACTION_MAIN);
			i.putExtra(Intent.EXTRA_TEXT, textMessage);
			i.setType("text/plain");

			//startActivity(i);
			startActivityForResult(i, REQUEST);
			return true;
		default:
			return super.onOptionsItemSelected(item);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == REQUEST) {
			switch (resultCode) {
      case RESULT_OK:
        Toast.makeText(getApplicationContext(), data.getStringExtra(Intent.EXTRA_TEXT), Toast.LENGTH_LONG).show();
        textMessage = data.getStringExtra(Intent.EXTRA_TEXT);
        break;
      case RESULT_CANCELLED:
        Toast.makeText(getApplicationContext(), "Cancelled!", Toast.LENGTH_LONG).show();
        break;
      default:
        Toast.makeText(getApplicationContext(), "default!", Toast.LENGTH_LONG).show();
			}
		}

    if (requestCode == REQUEST_S) {
      switch (resultCode) {
			case RESULT_OK:
				String s = data.getStringExtra("ch");
				Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
				break;
			case RESULT_CANCELLED:
				Toast.makeText(getApplicationContext(), "Cancelled!", Toast.LENGTH_LONG).show();
				break;
			default:
				Toast.makeText(getApplicationContext(), "default!", Toast.LENGTH_LONG).show();
      }
    }

		if (requestCode == REQUEST_T) {
			switch (resultCode) {
			case RESULT_OK:
				String h = data.getStringExtra("hour");
				String m = data.getStringExtra("min");
				Toast.makeText(getApplicationContext(), h + ":" + m, Toast.LENGTH_LONG).show();
        break;
      case RESULT_CANCELLED:
        Toast.makeText(getApplicationContext(), "Cancelled!", Toast.LENGTH_LONG).show();
        break;
      default:
        Toast.makeText(getApplicationContext(), "default!", Toast.LENGTH_LONG).show();
			}
		}

    if (requestCode == REQUEST_R) {
      switch (resultCode) {
			case RESULT_OK:
				String s = data.getStringExtra("rate");
				Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
				break;
			case RESULT_CANCELLED:
				Toast.makeText(getApplicationContext(), "Cancelled!", Toast.LENGTH_LONG).show();
				break;
			default:
				Toast.makeText(getApplicationContext(), "default!", Toast.LENGTH_LONG).show();
      }
    }
  }
}
