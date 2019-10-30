package mn.num.edu.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import mn.num.edu.weather.adapters.ViewPagerAdapter;
import mn.num.edu.weather.fragments.RecyclerViewFragment;
import mn.num.edu.weather.model.Weather;

public class MainActivity extends AppCompatActivity {

	private TabLayout tabLayout;
	private ViewPager viewPager;

	private Button b;
	public static TextView t;

	TodayWeatherTask todayWeatherTask;

	private List<Weather> todayList = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tabLayout = (TabLayout) findViewById(R.id.tabs);
		viewPager = (ViewPager) findViewById(R.id.viewPager);

		RecyclerViewFragment recyclerViewFragmentToday = new RecyclerViewFragment();
		RecyclerViewFragment recyclerViewFragmentCities = new RecyclerViewFragment();
		RecyclerViewFragment recyclerViewFragmentLater = new RecyclerViewFragment();

		ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		viewPagerAdapter.addFragment(recyclerViewFragmentToday, "TODAY");
		viewPagerAdapter.addFragment(recyclerViewFragmentCities, "CITIES");
		viewPagerAdapter.addFragment(recyclerViewFragmentLater, "LATER");

		viewPager.setAdapter(viewPagerAdapter);
		tabLayout.setupWithViewPager(viewPager);

		t = (TextView) findViewById(R.id.textView);

		b = (Button) findViewById(R.id.button);
		b.setOnClickListener((View v) -> {
			todayWeatherTask = new TodayWeatherTask();
			todayWeatherTask.execute();
		});
	}

	class TodayWeatherTask extends AsyncTask<Void, Void, String> {
		String data = "";

		@Override
		protected String doInBackground(Void... voids) {
			try {
				URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=Ulaanbaatar&appid=d2d4cc4d7ed3d6940c856916acce29c9&units=metric");

				HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();

				InputStream inputStream = httpConnection.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

				String s = "";
				while (s != null) {
					s = bufferedReader.readLine();
					data += s;
				}
				data = parseTodayJson(data);
			} catch (MalformedURLException e) {
				Log.i("MAIN", "URLException", e);
			} catch (IOException e) {
				Log.i("MAIN", "URLException", e);
			}
			return data;
		}

		@Override
		protected void onPostExecute(String s) {
//			super.onPostExecute(avoid);
			MainActivity.t.setText(s);
		}
	}

	private String parseTodayJson(String result) {
		String sum = "";
		try {
			JSONObject reader = new JSONObject(result);
			String city = reader.getString("name");
			sum += city + "\n";

			JSONObject main = reader.getJSONObject("main");
			String temp = main.getString("temp");

			String description = reader.getJSONArray("weather").getJSONObject(0).getString("description");
			sum += description + "\n";

			sum += temp + "*c\n";
			// final String idString = reader.getJSONArray("weather").getJSONObject(0).getString("id");
		} catch (JSONException e) {
			Log.e("JSONException Data", result);
			e.printStackTrace();
		}
		return sum;
	}
}
