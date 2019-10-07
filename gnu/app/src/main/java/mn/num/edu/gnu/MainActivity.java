package mn.num.edu.gnu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import mn.num.edu.gnu.database.AppDatabase;
import mn.num.edu.gnu.database.user.model.User;

import static mn.num.edu.gnu.database.AppDatabase.DATABASE_NAME;

public class MainActivity extends AppCompatActivity {

  EditText fname, lname;
  Button in, up;
  Intent i;
  User user = new User();

	@Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    user.firstName = "Bat";
    user.lastName = "Bold";

    final AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DATABASE_NAME)
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build();

    db.userDao().insertAll(user);

    fname = (EditText) findViewById(R.id.efname);
    lname = (EditText) findViewById(R.id.elname);


    in = (Button) findViewById(R.id.signin);
    in.setOnClickListener((View v) -> {
				i = new Intent(this, UserInfo.class);
				//i.putExtra("serialize_data", (Serializable) obj);
				startActivity(i);
			});


    up = (Button) findViewById(R.id.signup);

  }
}