package mn.num.edu.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class UserInfoActivity extends AppCompatActivity implements DatePicker.OnClickListener {

	TextView name;
	EditText age, sex, phone;
	static EditText y, m, d;
	Button edit, change, close;
	User user = new User();
	private static final int REQUEST_CODE_3 = 3;
	String tmp;
	User t;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_info);

    try {
			Intent r = getIntent();
			t = (User) r.getSerializableExtra("serser");

			name = (TextView) findViewById(R.id.fname);
			if (t.getfName() != null)
				name.setText(t.getfName());

			age = (EditText) findViewById(R.id.e_age);
			age.setText(t.getAge());

			sex = (EditText) findViewById(R.id.e_sex);
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
		} catch (Exception e) {
    	Log.i("Tag", "Aldaa", e);
		}

		edit = (Button) findViewById(R.id.edit);
		edit.setOnClickListener((View v) -> {
				Intent replyIntent = new Intent();
				user.setfName(name.getText().toString());
				user.setAge(age.getText().toString());
				user.setSex(sex.getText().toString());
				user.setPhone(phone.getText().toString());

				int yy = Integer.parseInt(y.getText().toString());
				int mm = Integer.parseInt(m.getText().toString());
				int dd = Integer.parseInt(d.getText().toString());
				user.setbDay(yy, mm, dd);

				replyIntent.putExtra("edit_serial", user);
				setResult(RESULT_OK, replyIntent);

				finish();
			});

		change = (Button) findViewById(R.id.change);
		change.setOnClickListener((View v) -> {
				String pass;
				if (tmp != null)
					pass = tmp;
				else
					pass = t.getlName();

			Intent intent = new Intent(this, ChangePasswordActivity.class);
			intent.putExtra("pass", pass);
				startActivityForResult(intent, REQUEST_CODE_3);
			});

		close = (Button) findViewById(R.id.close);
		close.setOnClickListener((View v) -> {
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
//      // Use the current date as the default date in the picker
//      final Calendar c = Calendar.getInstance();
      int year = Integer.parseInt(y.getText().toString());
      int month = Integer.parseInt(m.getText().toString());
      int day = Integer.parseInt(d.getText().toString());

      // Create a new instance of DatePickerDialog and return it
      return new DatePickerDialog(getActivity(), this, year, month - 1, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
      y.setText(String.valueOf(year));
      m.setText(String.valueOf(month + 1));
      d.setText(String.valueOf(day));
    }
  }

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == REQUEST_CODE_3 && resultCode == RESULT_OK) {
			try {
				String u = data.getStringExtra("newpass");
				tmp = u;
				ContentValues updatePass = new ContentValues();
				updatePass.put("l_name", u);
				String id = name.getText().toString();
				
				// db.update("User", updatePass, "f_name = ?", new String[]{id});
				// ^
				// |
				// v
				int r = getContentResolver().update(NameProvider.CONTENT_URI, updatePass, "f_name = ?", new String[] {id});
				Toast.makeText(getApplicationContext(), "Password changed!", Toast.LENGTH_LONG).show();
			} catch(Exception e) {
				Log.i("Tag", "aldaaa", e);
			}
		}
	}
}
