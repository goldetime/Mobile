package mn.num.edu.database;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import mn.num.edu.database.db.user.model.UserEntity;

public class UserInfoActivity extends AppCompatActivity {

  UserEntity user;
  TextView name, age, sex, phone;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_info);

		user = (UserEntity) getIntent().getSerializableExtra("serialize_data");

    name = findViewById(R.id.t_name);
    age = findViewById(R.id.t_age);
    sex = findViewById(R.id.t_sex);
    phone = findViewById(R.id.t_phone);

		name.setText(user.getFirstName());
		age.setText(String.valueOf(user.getAge()));
		sex.setText(user.getSex());
    phone.setText(user.getPhone());
	}
}
