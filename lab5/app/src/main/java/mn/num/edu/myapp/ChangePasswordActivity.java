package mn.num.edu.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {

	EditText old_pass, pass, re_pass;
	Button save, cancel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_change_password);

		Intent r = getIntent();
		String p = r.getStringExtra("pass");

		old_pass = (EditText) findViewById(R.id.old_pass);
		pass = (EditText) findViewById(R.id.e_pass);
		re_pass = (EditText) findViewById(R.id.e_repass);

		save = (Button) findViewById(R.id.save);
		save.setOnClickListener((View v) -> {

		  if (!old_pass.getText().toString().equals(p))
        Toast.makeText(getApplicationContext(), "Хуучин нууц үг буруу байна!", Toast.LENGTH_LONG).show();
			
		  if (!pass.getText().toString().equals(re_pass.getText().toString()))
        Toast.makeText(getApplicationContext(), "Шинэ нууц үг буруу байна!", Toast.LENGTH_LONG).show();
			
      if (old_pass.getText().toString().equals(p) &&
          pass.getText().toString().equals(re_pass.getText().toString())) {
				Intent replyIntent = new Intent();
				replyIntent.putExtra("newpass", pass.getText().toString());
				setResult(RESULT_OK, replyIntent);
				finish();
      }
			});
		cancel = (Button) findViewById(R.id.cancel);
		cancel.setOnClickListener((View v) -> {
				finish();
			});
  }
}
