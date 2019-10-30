package mn.num.edu.weather.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import mn.num.edu.weather.MainActivity;

public abstract class GenericRequestTask extends AsyncTask<String, String, String> {
  Context context;
  MainActivity activity;

  public GenericRequestTask(Context context, MainActivity activity) {
    this.context = context;
    this.activity = activity;
  }

  @Override
  protected void onPreExecute() {/* nothing */}

  @Override
  protected String doInBackground(String... params) {
    String output = new String();

    String response = "";
    String[] reqParams = new String[]{};
    // reqParams = new String[]{"city", params[1]};
    try {
        URL url = provideURL(reqParams);
        Log.i("URL", url.toString());

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        if (urlConnection.getResponseCode() == 200) {
          InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
          BufferedReader r = new BufferedReader(inputStreamReader);

          StringBuilder stringBuilder = new StringBuilder();
          String line;
          while ((line = r.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append("\n");
          }
          response += stringBuilder.toString();
          close(r);
          urlConnection.disconnect();

          // Background work finished successfully
          Log.i("Task", "done successfully");
          output = "SUCCESS";

        }
//        else if (urlConnection.getResponseCode() == 429) {
//          Log.i("Task", "too many requests");
//          output = "TOO_MANY_REQUESTS";
//        } else {
//          Log.i("Task", "bad response " + urlConnection.getResponseCode());
//          output = "BAD_RESPONSE";
//        }
      } catch (IOException e) {
        Log.e("IOException Data", response, e);
//        output = "IO_EXCEPTION";
      }

    return output;
  }

  @Override
  protected void onPostExecute(String output) {
    handleTaskOutput(output);
  }

  protected final void handleTaskOutput(String output) {
    switch (output) {
      case "SUCCESS": {
        if (output.equals("CITY_NOT_FOUNDparseResult"))
          Snackbar.make(activity.findViewById(android.R.id.content), "city not found", Snackbar.LENGTH_LONG).show();
        break;
      }
    }
  }


  private URL provideURL(String[] reqParams) throws MalformedURLException {

    StringBuilder urlBuilder = new StringBuilder("https://api.openweathermap.org/data/2.5/weather?q=Ulaanbaatar&appid=d2d4cc4d7ed3d6940c856916acce29c9&units=metric");

    // StringBuilder urlBuilder = new StringBuilder("https://api.openweathermap.org/data/2.5/");

    if ("city".equals(reqParams[0])) {
      urlBuilder.append("q=").append(reqParams[1]);
    }
    urlBuilder.append("&appid=").append("599d244ed45b1e570b5633224950ca89");
    urlBuilder.append("&mode=json");

    return new URL(urlBuilder.toString());
  }

  private static void close(Closeable x) {
    try {
      if (x != null) {
        x.close();
      }
    } catch (IOException e) {
      Log.e("IOException Data", "Error occurred while closing stream");
    }
  }

  protected void updateMainUI() {}

  protected abstract String parseResponse(String response);

  protected abstract String getAPIName();
}