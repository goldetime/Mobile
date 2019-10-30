package mn.num.edu.weather.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mn.num.edu.weather.R;

public class RecyclerViewFragment extends Fragment {

  public RecyclerViewFragment() {

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
    return view;
  }
}