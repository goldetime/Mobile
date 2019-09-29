package mn.num.edu.activity.about;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import mn.num.edu.activity.MainActivity;
import mn.num.edu.activity.R;

public class AboutActivity extends AppCompatActivity {

	private Button ok = null;
	private Button cancel = null;
	Intent i = null;
	TextView t;
	private EditText name;
	private EditText age;
	
	@Override
  protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about);

		t = (TextView) findViewById(R.id.about);
		name = (EditText) findViewById(R.id.editName);
		age = (EditText) findViewById(R.id.editAge);

		Bundle abData = getIntent().getExtras();
		String s = abData.getString(Intent.EXTRA_TEXT);

		if (s != null)
			t.setText(s);

		ok = (Button) findViewById(R.id.ok);
		ok.setOnClickListener((View v) -> {
				String newAbout = age.getText().toString() +
					" настай " + name.getText().toString() + " бүтээв.";
				t.setText(newAbout);

				Intent result = getIntent();
				result.putExtra(Intent.EXTRA_TEXT, newAbout);
				if (newAbout.equals("0"))
					setResult(RESULT_CANCELED, result);
				else
					setResult(RESULT_OK, result);
			});

		cancel = (Button) findViewById(R.id.cancel);
		cancel.setOnClickListener((View v) -> {
				i = new Intent(this, MainActivity.class);
				startActivity(i);
			});
	}
}
