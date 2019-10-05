package mn.num.edu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

public class ExampleActivity extends AppCompatActivity {
	static final int REQUEST = 1;
  Button ok = null;
	Button cancel = null;
	int hour = -1;
	int min = -1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_example);

		TimePicker timePicker1;
		timePicker1 = (TimePicker) findViewById(R.id.timePicker1);

		if (hour != -1) {
			timePicker1.setHour(hour);
		}

		ok = (Button) findViewById(R.id.ok);
		ok.setOnClickListener((View v) -> {
				hour = timePicker1.getCurrentHour();
				min = timePicker1.getCurrentMinute();

				Intent r = getIntent();

				r.putExtra("hour", String.valueOf(hour));
				r.putExtra("min", String.valueOf(min));
				if (hour == -1 || min == -1)
				  setResult(RESULT_CANCELED, r);
        else
					setResult(RESULT_OK, r);
        finish();
			});

		cancel = (Button) findViewById(R.id.cancel);
		cancel.setOnClickListener((View v) -> {
			finish();
		});
	}
}