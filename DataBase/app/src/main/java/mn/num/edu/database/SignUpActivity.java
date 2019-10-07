package mn.num.edu.database;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import mn.num.edu.database.db.AppDatabase;
import mn.num.edu.database.db.user.model.UserEntity;

import static mn.num.edu.database.db.AppDatabase.DATABASE_NAME;

public class SignUpActivity extends AppCompatActivity {

	AppDatabase db;
  EditText fname, lname;
	Button save;
	UserEntity s = new UserEntity();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sign_up);

    fname = findViewById(R.id.e_fname);
    lname = findViewById(R.id.e_lname);
		save = findViewById(R.id.save);

		db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DATABASE_NAME)
				.allowMainThreadQueries()
				.build();

		s.setFirstName("A");
		s.setLastName("B");
		addUser(db, s);


		save.setOnClickListener((View v) -> {

		});
	}

	private static UserEntity addUser(final AppDatabase db, UserEntity user) {
		db.userDao().insertAll(user);
		return user;
	}
}
