package mn.num.edu.weather.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mn.num.edu.weather.MainActivity;
import mn.num.edu.weather.R;

public class RecyclerViewFragment extends Fragment {

  public RecyclerViewFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);

    Bundle bundle = this.getArguments();

    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    MainActivity mainActivity = (MainActivity) getActivity();
    recyclerView.setAdapter(mainActivity.getAdapter(bundle.getInt("day")));

    return view;
  }
}