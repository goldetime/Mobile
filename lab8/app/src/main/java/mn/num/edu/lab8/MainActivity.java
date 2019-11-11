package mn.num.edu.lab8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

  private Button c;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    c = (Button) findViewById(R.id.button);
    c.setOnClickListener((View v) -> {
      Toast.makeText(this, "lambda", Toast.LENGTH_LONG).show();
    });
  }
}