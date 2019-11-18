package mn.num.telephony;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

  private Button b;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    b = findViewById(R.id.button);
    b.setOnClickListener((View v) -> {
				Snackbar.make(findViewById(android.R.id.content), "Hello", Snackbar.LENGTH_LONG).show();
			});
  }
}