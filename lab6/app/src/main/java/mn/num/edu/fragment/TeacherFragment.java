package mn.num.edu.fragment;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class TeacherFragment extends Fragment {
	
	private TextView info;
  private EditText degree;
	private Button s, c;
	
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_teacher, container, false);
		
		Bundle bundle = getArguments();
		String s_lname = bundle.getString("lname") + ", ";
		String s_fname = bundle.getString("fname") + ", ";
		String s_age = bundle.getString("age") + ", ";
		String s_sex = bundle.getString("sex");

		info = (TextView) rootView.findViewById(R.id.info);
		info.setText(s_lname + s_fname + s_age + s_sex);

    return rootView;
  }
}