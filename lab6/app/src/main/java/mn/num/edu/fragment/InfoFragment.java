package mn.num.edu.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class InfoFragment extends Fragment {
  private EditText lname, fname, age, sex;
  private RadioGroup radioGroup;
  Bundle bundle;
  FragmentManager fragmentManager;
  FragmentTransaction fragmentTransaction;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_info, container, false);

    lname = rootView.findViewById(R.id.e_lname);
    fname = rootView.findViewById(R.id.e_fname);
    age = rootView.findViewById(R.id.e_age);
    sex = rootView.findViewById(R.id.e_sex);

    radioGroup = (RadioGroup) rootView.findViewById(R.id.radio_group);
    radioGroup.clearCheck();

    radioGroup.setOnCheckedChangeListener((RadioGroup group, int checkedId) -> {
      switch(checkedId) {
        case R.id.r_teacher:
          bundle = new Bundle();
          //bundle.putSerializable();
          bundle.putString("lname", lname.getText().toString());
          bundle.putString("fname", fname.getText().toString());
          bundle.putString("age", age.getText().toString());
          bundle.putString("sex", sex.getText().toString());

          fragmentManager = getActivity().getSupportFragmentManager();
          TeacherFragment teacherFragment = new TeacherFragment();
          teacherFragment.setArguments(bundle);
          fragmentTransaction = fragmentManager.beginTransaction();
          fragmentTransaction
              .addToBackStack(null)
              .replace(R.id.fragment_container1, teacherFragment)
              .commit();
          break;
        case R.id.r_student:
          Bundle bundle = new Bundle();
          bundle.putString("lname", lname.getText().toString());
          bundle.putString("fname", fname.getText().toString());
          bundle.putString("age", age.getText().toString());
          bundle.putString("sex", sex.getText().toString());

          FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
          StudentFragment studentFragment = new StudentFragment();
          studentFragment.setArguments(bundle);
          fragmentTransaction = fragmentManager.beginTransaction();
          fragmentTransaction
              .addToBackStack(null)
              .replace(R.id.fragment_container1, studentFragment)
              .commit();
          break;
      }
    });

    return rootView;
  }
}