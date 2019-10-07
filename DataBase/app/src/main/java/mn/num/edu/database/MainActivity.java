package mn.num.edu.database;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.io.Serializable;
import java.util.List;

import mn.num.edu.database.db.AppDatabase;
import mn.num.edu.database.db.user.model.UserEntity;

import static mn.num.edu.database.db.AppDatabase.DATABASE_NAME;

public class MainActivity extends AppCompatActivity {

  EditText fname, lname;
  Button login, signup;
  AppDatabase db;
  Intent i;

	UserEntity user = new UserEntity();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

		db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();

		fname = findViewById(R.id.edit_fname);
		lname = findViewById(R.id.edit_lname);

		login = findViewById(R.id.login);
		login.setOnClickListener((View v) -> {
				UserEntity t = db.userDao().findByUser("A", "B");
				
				i = new Intent(this, UserInfoActivity.class);
				i.putExtra("serialize_data", (Serializable) t);
				startActivity(i);
		 	});
			
		signup = findViewById(R.id.signup);
		signup.setOnClickListener((View v) -> {
				// in = new Intent(this, SignUpActivity.class);
				// startActivity(in);
			});
  }
}
