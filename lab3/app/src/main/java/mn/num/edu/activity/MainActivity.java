package mn.num.edu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
  Button b = null;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

		b = findViewById(R.id.button);
		b.setOnClickListener((View v) -> {
        Toast.makeText(getApplicationContext(), "Hello, Lambda!", Toast.LENGTH_LONG).show();
      });
	}
}