package mn.num.edu.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

  private static final int REQUEST_CODE_1 = 1;
	private static final int REQUEST_CODE_2 = 2;
	public static final String PREFS_NAME = "pref";
	SharedPreferences s;
  private EditText eid;
  private EditText fname, lname;
  public DatabaseHelper helper;
  private Button login, sign;
  private Button read, delete;
  User user;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
		
		s = getSharedPreferences(PREFS_NAME, 0);

		helper = (new DatabaseHelper(this));
		SQLiteDatabase db = helper.getWritableDatabase();

    // eid = (EditText) findViewById(R.id.id);
    fname = (EditText) findViewById(R.id.fname);
		String ret = s.getString("key", "");
		if (ret != null)
			fname.setText(ret);
		
    lname = (EditText) findViewById(R.id.lname);

    login = (Button) findViewById(R.id.login);
    login.setOnClickListener((View v) -> {
				SharedPreferences.Editor e = s.edit();
				e.putString("key", fname.getText().toString());
				e.commit();
				
				String id = fname.getText().toString();
				Cursor result = db.rawQuery("select * from User where f_name = ?", new String[]{id});
				if (result.getCount() > 0) {
					result.moveToFirst();
					String a = result.getString(1);
					String b = result.getString(2);
					String age = result.getString(3);
					String sex = result.getString(4);
					String phone = result.getString(5);
					String year = result.getString(6);
					String month = result.getString(7);
					String day = result.getString(8);
          int y = Integer.parseInt(year);
          int m = Integer.parseInt(month);
          int d = Integer.parseInt(day);
					
					if (fname.getText().toString().equals(a) && lname.getText().toString().equals(b)) {
						User t = new User(a, b);
						t.setAge(age);
						t.setSex(sex);
						t.setPhone(phone);
						t.setbDay(y, m, d); 
								
						Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
						intent.putExtra("serser", (Serializable) t);
						startActivityForResult(intent, REQUEST_CODE_2);
          } else
            Toast.makeText(getApplicationContext(), "Нууц үг буруу байна!", Toast.LENGTH_LONG).show();
        } else
					Toast.makeText(getApplicationContext(), "Бүртгэлгүй байна!", Toast.LENGTH_LONG).show();
			});

    sign = (Button) findViewById(R.id.sign);
    sign.setOnClickListener((View v) -> {
        user = new User(fname.getText().toString(), lname.getText().toString());
				
				Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
				intent.putExtra("seri", (Serializable) user);
				startActivityForResult(intent, REQUEST_CODE_1);
			});
		/*
    read = (Button) findViewById(R.id.read);
    read.setOnClickListener((View v) -> {
				String id = getId();
				Cursor result = db.rawQuery("select * from User where id = ?", new String[]{id});
				if (result.getCount() > 0) {
					result.moveToFirst();
					eid.setText(String.valueOf(result.getInt(0)));
					fname.setText(result.getString(1));
					lname.setText(result.getString(2));
				}
			});

    delete = (Button) findViewById(R.id.delete);
    delete.setOnClickListener((View v) -> {
				db.delete("User", "id = ?", new String[]{getId()});
			});
		*/
  }
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    SQLiteDatabase db = helper.getWritableDatabase();

    if (requestCode == REQUEST_CODE_1 && resultCode == RESULT_OK) {
      try {
        User t = (User) data.getSerializableExtra("serial");

				String id = fname.getText().toString();
				Cursor result = db.rawQuery("select * from User where f_name = ?", new String[]{id});
				if (result.getCount() <= 0) {
					// result.moveToFirst();
					// fname.setText(result.getString(1));

					ContentValues newUser = new ContentValues(8);

					newUser.put("f_name", t.getfName());
          newUser.put("l_name", t.getlName());
          newUser.put("age", t.getAge());
          newUser.put("sex", t.getSex());
          newUser.put("phone", t.getPhone());

					Date date = t.getbDay();
          Calendar calendar = new GregorianCalendar();
          calendar.setTime(date);

          int yr = calendar.get(Calendar.YEAR);
          int mh = calendar.get(Calendar.MONTH) + 1;
          int dy = calendar.get(Calendar.DAY_OF_MONTH);
					
          newUser.put("year", yr);
          newUser.put("month", mh);
					newUser.put("day", dy);

					db.insert("User", null, newUser);
					
					Toast.makeText(getApplicationContext(), "registered", Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getApplicationContext(), "Бүртгэлтэй байна!", Toast.LENGTH_LONG).show();
				}
      } catch (Exception e) {
        Log.i("TAG", "aldaa1", e);
      }
    }

		if (requestCode == REQUEST_CODE_2 && resultCode == RESULT_OK) {
      try {
        User t = (User) data.getSerializableExtra("edit_serial");
				
 				ContentValues updateUser = new ContentValues(6);
				updateUser.put("age", t.getAge());
				updateUser.put("sex", t.getSex());
				updateUser.put("phone", t.getPhone());
				
				Date date = t.getbDay();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);

				int yr = calendar.get(Calendar.YEAR);
				int mh = calendar.get(Calendar.MONTH) + 1;
				int dy = calendar.get(Calendar.DAY_OF_MONTH);
					
				updateUser.put("year", yr);
        updateUser.put("month", mh);
        updateUser.put("day", dy);

				String id = t.getfName();
				db.update("User", updateUser, "f_name = ?", new String[]{id});
				Toast.makeText(getApplicationContext(), "edited", Toast.LENGTH_LONG).show();
			} catch (Exception e) {
        Log.i("TAG", "aldaa2", e);
      }
    }
  }
}
