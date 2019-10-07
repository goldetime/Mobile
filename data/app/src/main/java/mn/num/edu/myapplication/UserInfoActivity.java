package mn.num.edu.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class UserInfoActivity extends AppCompatActivity {

	TextView name;
	EditText age, sex, phone;
	EditText y, m, d;
	Button edit, change, cancel;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_info);

		User t = (User) r.getSerializableExtra("ser");

		name = (TextView) findViewById(R.id.fname);
		if (t.getfName() != null)
			name.setText(t.getfName());

		age = (EditText) findViewById(R.id.e_age);
		age.setText(t.getAge());

		sex = (EditText) findViewById(R.id.e_sexa);
		sex.setText(t.getSex());

		phone = (EditText) findViewById(R.id.e_phone);
		phone.setText(t.getPhone());

		Date date = t.getbDay();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);

		int yr = calendar.get(Calendar.YEAR);
		int mh = calendar.get(Calendar.MONTH) + 1;
		int dy = calendar.get(Calendar.DAY_OF_MONTH);

		y = (EditText) findViewById(R.id.e_year);
		y.setText(String.valueOf(yr));

		m = (EditText) findViewById(R.id.e_month);
		m.setText(String.valueOf(mh));

		d = (EditText) findViewById(R.id.e_day);
		d.setText(String.valueOf(dy));

		edit = (Button) findViewById(R.id.e_edit);
		edit.setOnClickListener((View v) -> {

			});

		change = (Button) findViewById(R.id.e_change);
		change.setOnClickListener((View v) -> {

			});

		close = (Button) findViewById(R.id.e_close);
		close.setOnClickListener((View v) -> {
				finish();
			});
		
  }
}
