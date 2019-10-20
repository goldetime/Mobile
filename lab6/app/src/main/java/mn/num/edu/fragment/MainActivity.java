package mn.num.edu.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    InfoFragment fragment = new InfoFragment();
    fragmentTransaction
			.replace(R.id.fragment_container, fragment)
			.commit();
  }

  public void onCheckboxClicked(View view) {

    boolean checked = ((CheckBox) view).isChecked();

    switch(view.getId()) {
      case R.id.c_cprog:
        if (checked)
          Toast.makeText(getApplicationContext(), "c", Toast.LENGTH_LONG).show();
        break;
      case R.id.c_cplusplus:
        if (checked)
          Toast.makeText(getApplicationContext(), "c++", Toast.LENGTH_LONG).show();
        break;
      case R.id.c_java:
        if (checked)
          Toast.makeText(getApplicationContext(), "java", Toast.LENGTH_LONG).show();
        break;
      case R.id.c_linux:
        if (checked)
          Toast.makeText(getApplicationContext(), "linux", Toast.LENGTH_LONG).show();
        break;
      default:
          break;
    }
  }
}