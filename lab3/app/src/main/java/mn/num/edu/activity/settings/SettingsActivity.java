package mn.num.edu.activity.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import mn.num.edu.activity.R;

public class SettingsActivity extends AppCompatActivity {
  Intent i;

  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    Button ok = findViewById(R.id.ok);
    ok.setOnClickListener((View v) -> {
      String s = null;
      i = getIntent();
      final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
      if (checkBox.isChecked())
        s = checkBox.getText().toString();
      Intent result = getIntent();
      result.putExtra("ch", s);
      if (s.equals("0"))
        setResult(RESULT_CANCELED, result);
      else
        setResult(RESULT_OK, result);
      finish();
    });

    Button c = findViewById(R.id.cancel);
    c.setOnClickListener((View v) -> {
      finish();
    });
	}
}
