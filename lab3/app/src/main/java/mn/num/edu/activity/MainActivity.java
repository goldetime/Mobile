package mn.num.edu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import mn.num.edu.activity.about.AboutActivity;
import mn.num.edu.activity.settings.SettingsActivity;

import static android.telephony.MbmsDownloadSession.RESULT_CANCELLED;

public class MainActivity extends AppCompatActivity {
  Button b = null;
	String textMessage = null;
	static final int REQUEST = 1;
	
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    b = findViewById(R.id.button);
    b.setOnClickListener((View v) -> {
				Intent i = new Intent(this, ExampleActivity.class);
				startActivity(i);
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
      i = new Intent(this, SettingsActivity.class);
			i.setAction(Intent.ACTION_SEND);
			startActivity(i);
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
    if (requestCode != REQUEST) 
      Toast.makeText(getApplicationContext(), "Aldaa!", Toast.LENGTH_LONG).show();
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
}
