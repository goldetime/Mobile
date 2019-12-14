package mn.num.allinone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

  private Button save;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    save = findViewById(R.id.save);
    save.setOnClickListener((View v) -> {
      Toast.makeText(this, "Hi", Toast.LENGTH_LONG).show();
      Snackbar.make(this.findViewById(android.R.id.content), "Hello", Snackbar.LENGTH_LONG).show();
    });
  }
}