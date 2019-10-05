package mn.num.edu.activity.rating;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import mn.num.edu.activity.R;

public class RatingActivity extends AppCompatActivity {
  Button ok = null;
  Button cancel = null;
  Intent i = null;
  RatingBar ratingBar = null;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rating);

    ok = findViewById(R.id.ok);
    ok.setOnClickListener((View v) -> {
      i = getIntent();
      ratingBar = (RatingBar) findViewById(R.id.ratingBar);
      i.putExtra("rate", String.valueOf(ratingBar.getRating()));
      setResult(RESULT_OK, i);
      finish();
    });

    cancel = findViewById(R.id.cancel);
    cancel.setOnClickListener((View v) -> {
      finish();
    });
  }
}