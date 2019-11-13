package mn.num.edu.weather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mn.num.edu.weather.adapters.ViewPagerAdapter;
import mn.num.edu.weather.adapters.WeatherRecyclerAdapter;
import mn.num.edu.weather.fragments.RecyclerViewFragment;
import mn.num.edu.weather.model.Weather;

public class MainActivity extends AppCompatActivity {

	private TabLayout tabLayout;
	private ViewPager viewPager;

	TodayWeatherTask todayWeatherTask;
	FindCitiesByNameTask findCitiesByNameTask;
		LongTermLaterWeather longTermLaterWeather;


	private List<Weather> longTermTodayWeather = new ArrayList<>();
	private List<Weather> todayCitiesWeather = new ArrayList<>();
	private List<Weather> longTermWeather = new ArrayList<>();

	private EditText fcity;
	public Button pos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		fcity = (EditText) findViewById(R.id.fcity);
		pos = (Button) findViewById(R.id.pos);
		pos.setOnClickListener((View v) -> {
			findCitiesByNameTask = new FindCitiesByNameTask();
			findCitiesByNameTask.execute(fcity.getText().toString());
		});

		todayWeatherTask = new TodayWeatherTask();
		todayWeatherTask.execute();

    tabLayout = (TabLayout) findViewById(R.id.tabs);
		viewPager = (ViewPager) findViewById(R.id.viewPager);

		ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

		Bundle bundleToday = new Bundle();
		bundleToday.putInt("day", 0);
		RecyclerViewFragment recyclerViewFragmentToday = new RecyclerViewFragment();
		recyclerViewFragmentToday.setArguments(bundleToday);
		viewPagerAdapter.addFragment(recyclerViewFragmentToday, "Today");

		Bundle bundleA = new Bundle();
		bundleA.putInt("day", 1);
		RecyclerViewFragment recyclerViewFragmentC = new RecyclerViewFragment();
		recyclerViewFragmentC.setArguments(bundleA);
		viewPagerAdapter.addFragment(recyclerViewFragmentC, "City");

		Bundle bundle = new Bundle();
		bundle.putInt("day", 2);
		RecyclerViewFragment recyclerViewFragmentLater = new RecyclerViewFragment();
		recyclerViewFragmentLater.setArguments(bundle);
		viewPagerAdapter.addFragment(recyclerViewFragmentLater, "Later");

		viewPager.setAdapter(viewPagerAdapter);
		tabLayout.setupWithViewPager(viewPager);

	}

	public WeatherRecyclerAdapter getAdapter(int id) {
		WeatherRecyclerAdapter weatherRecyclerAdapter;
		if (id == 0) {
			weatherRecyclerAdapter = new WeatherRecyclerAdapter(this, longTermTodayWeather);
		} else if (id == 1) {
			weatherRecyclerAdapter = new WeatherRecyclerAdapter(this, todayCitiesWeather);
		} else if (id == 2) {
			weatherRecyclerAdapter = new WeatherRecyclerAdapter(this, longTermWeather);
		} else {
			weatherRecyclerAdapter = new WeatherRecyclerAdapter(this, todayCitiesWeather);
		}
		return weatherRecyclerAdapter;
	}

	class TodayWeatherTask extends AsyncTask<Void, Void, String> {
		String data = "";

		@Override
		protected String doInBackground(Void... voids) {
			try {
        URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?q=Ulaanbaatar&appid=d2d4cc4d7ed3d6940c856916acce29c9&units=metric");

				HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();

				InputStream inputStream = httpConnection.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

				String s = "";
				while (s != null) {
					s = bufferedReader.readLine();
					data += s;
				}

				parseTodayJson(data);

			} catch (MalformedURLException e) {
				Log.i("MAIN", "URLException", e);
			} catch (IOException e) {
				Log.i("MAIN", "URLException", e);
			}
			return data;
		}

		@Override
		protected void onPostExecute(String s) {
			System.out.println("ok");
		}
	}

	class FindCitiesByNameTask extends AsyncTask<String, Void, String> {
		String data = "";

		@Override
		protected String doInBackground(String... str) {
			try {
				String f = str[0];
				URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + f + "&appid=d2d4cc4d7ed3d6940c856916acce29c9&units=metric");

				HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
				InputStream inputStream = httpConnection.getInputStream();
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

				String s = "";
				while (s != null) {
					s = bufferedReader.readLine();
					data += s;
				}

				parse(data);

			} catch (MalformedURLException e) {
				Log.i("MAIN", "URLException", e);
			} catch (IOException e) {
				Log.i("MAIN", "URLException", e);
			}
			return data;
		}

		@Override
		protected void onPostExecute(String s) {
			System.out.println("ok2");
		}
	}

  class LongTermLaterWeather extends AsyncTask<Void, Void, String> {
    String data = "";

    @Override
    protected String doInBackground(Void... str) {
      try {

        URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?q=Ulaanbaatar&appid=d2d4cc4d7ed3d6940c856916acce29c9&units=metric&mode=xml");

        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        InputStream is = httpConnection.getInputStream();

        XmlPullParserFactory parserFactory;
        try {
          parserFactory = XmlPullParserFactory.newInstance();
          XmlPullParser parser = parserFactory.newPullParser();

          parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
          parser.setInput(is, null);

          int eventType = parser.getEventType();
          while(eventType != XmlPullParser.END_DOCUMENT) {
            Weather weather = null;
            Calendar cal = null;

            String eltName = null;
            switch (eventType) {
              case XmlPullParser.START_TAG:
                eltName = parser.getName();
                if ("time".equals(eltName)) {
                  weather = new Weather();
                  weather.setDate(parser.getAttributeName(0));
                  final String dateMsString = parser.getAttributeName(0) + "000";
                  cal = Calendar.getInstance();
                  cal.setTimeInMillis(Long.parseLong(dateMsString));
                } else if ("temperature".equals(eltName)) {
                  weather.setTemperature(parser.nextText());
                } else if ("clouds value".equals(eltName)) {
                  weather.setDescription(parser.nextText());
                } else if ("name".equals(eltName)) {
                  weather.setCity(parser.nextText());
                }


                Calendar today = Calendar.getInstance();
                today.set(Calendar.HOUR_OF_DAY, 0);
                today.set(Calendar.MINUTE, 0);
                today.set(Calendar.SECOND, 0);
                today.set(Calendar.MILLISECOND, 0);

                Calendar tomorrow = (Calendar) today.clone();
                tomorrow.add(Calendar.DAY_OF_YEAR, 1);

                if (cal.before(tomorrow)) {
                  //longTermWeather.add(weather);
                } else {
                  longTermWeather.add(weather);
                }
                break;
            }
            eventType = parser.next();
          }
        } catch (XmlPullParserException e) {
          Log.i("Main", "xml", e);
        }

      } catch (MalformedURLException e) {
        Log.i("MAIN", "URLException", e);
      } catch (IOException e) {
        Log.i("MAIN", "URLException", e);
      }
      return data;
    }

    @Override
    protected void onPostExecute(String s) {
      System.out.println("ok2");
    }
  }

	private void parseXML(String result) {


  }

	private void parse(String result) {
		try {
			JSONObject reader = new JSONObject(result);
			JSONObject main = reader.getJSONObject("main");
			String temp = main.getString("temp");
			String description = reader.getJSONArray("weather").getJSONObject(0).getString("description");
			String city = reader.getString("name");

			Weather w = new Weather();
			w.setDate(reader.getString("dt"));
			w.setDescription(description);
			w.setTemperature(temp);
			w.setCity(city);

			todayCitiesWeather.add(w);


		} catch (JSONException e) {
			Log.i("TAG", "JSON", e);
		}
	}

	private void parseTodayJson(String result) {
		try {
			JSONObject reader = new JSONObject(result);
			JSONArray list = reader.getJSONArray("list");

			for (int i = 0; i < list.length(); i++) {
				Weather weather = new Weather();

				JSONObject listItem = list.getJSONObject(i);
				JSONObject main = listItem.getJSONObject("main");

				weather.setDate(listItem.getString("dt"));
				weather.setTemperature(main.getString("temp"));
				weather.setDescription(listItem.optJSONArray("weather").getJSONObject(0).getString("description"));
				weather.setCity(reader.getJSONObject("city").getString("name"));

				final String dateMsString = listItem.getString("dt") + "000";
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(Long.parseLong(dateMsString));

				Calendar today = Calendar.getInstance();
				today.set(Calendar.HOUR_OF_DAY, 0);
				today.set(Calendar.MINUTE, 0);
				today.set(Calendar.SECOND, 0);
				today.set(Calendar.MILLISECOND, 0);

				Calendar tomorrow = (Calendar) today.clone();
				tomorrow.add(Calendar.DAY_OF_YEAR, 1);

				if (cal.before(tomorrow)) {
					longTermTodayWeather.add(weather);
				} else {
					longTermWeather.add(weather);
				}
			}

		} catch (JSONException e) {
			Log.e("JSONException Data", result);
			e.printStackTrace();
		}
	}
}