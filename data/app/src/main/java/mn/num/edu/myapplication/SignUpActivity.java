package mn.num.edu.myapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.io.Serializable;
import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity implements DatePicker.OnClickListener {

  static EditText y, m, d;
  Button save, cancel;
  Button date;
  EditText fname, pass, repass;
  EditText age, sex, phone;
  User user = new User();


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);

    Intent r = getIntent();
    User t = (User) r.getSerializableExtra("seri");

		fname = (EditText) findViewById(R.id.e_fname);
		if (t.getfName() != null) 
			fname.setText(t.getfName());

		pass = (EditText) findViewById(R.id.e_pass);
		if (t.getlName() != null) 
			pass.setText(t.getlName());

		repass = findViewById(R.id.e_repass);
		age = findViewById(R.id.e_age);
    sex = findViewById(R.id.e_sex);
    phone = findViewById(R.id.e_phone);

		y = findViewById(R.id.e_year);
    m = findViewById(R.id.e_month);
    d = findViewById(R.id.e_day);

    save = findViewById(R.id.save);
    save.setOnClickListener((View v) -> {
				Intent replyIntent = new Intent();

				if (pass.getText().toString().equals(repass.getText().toString())) {
					user = new User(fname.getText().toString(), pass.getText().toString());
					user.setAge(age.getText().toString());
					user.setSex(sex.getText().toString());
					user.setPhone(phone.getText().toString());

					int yy = Integer.parseInt(y.getText().toString());
          int mm = Integer.parseInt(m.getText().toString());
          int dd = Integer.parseInt(d.getText().toString());
					user.setbDay(yy, mm, dd);

					replyIntent.putExtra("serial", user);
					setResult(RESULT_OK, replyIntent);

          finish();
				} else {
					Toast.makeText(
						getApplicationContext(),
						"Wrong password",
						Toast.LENGTH_LONG).show();
				}
			});

		cancel = findViewById(R.id.cancel);
		cancel.setOnClickListener((View v) -> {
				finish();
			});

  }

  @Override
  public void onClick(View v) {
    DialogFragment newFragment = new DatePickerFragment();
    newFragment.show(getSupportFragmentManager(), "datePicker");
  }

  public static class DatePickerFragment extends DialogFragment
		implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
      // Use the current date as the default date in the picker
      final Calendar c = Calendar.getInstance();
      int year = c.get(Calendar.YEAR);
      int month = c.get(Calendar.MONTH);
      int day = c.get(Calendar.DAY_OF_MONTH);

      // Create a new instance of DatePickerDialog and return it
      return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
      y.setText(String.valueOf(year));
      m.setText(String.valueOf(month));
      d.setText(String.valueOf(day));
    }
  }
}
