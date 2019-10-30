package mn.num.edu.weather.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import mn.num.edu.weather.R;
import mn.num.edu.weather.model.Weather;
import mn.num.edu.weather.model.WeatherViewHolder;

public class WeatherRecyclerAdapter extends RecyclerView.Adapter<WeatherViewHolder> {
	private List<Weather> itemList;
	private Context context;

	public WeatherRecyclerAdapter(Context context, List<Weather> itemList) {
		this.itemList = itemList;
		this.context = context;
	}

	@Override
	public WeatherViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_row, null);

		WeatherViewHolder viewHolder = new WeatherViewHolder(view);
		return viewHolder;
	}

	@Override
	public void onBindViewHolder(WeatherViewHolder customViewHolder, int i) {
		Weather weatherItem = itemList.get(i);

		// Description
		customViewHolder.itemDescription.setText(weatherItem.getDescription().substring(0, 1).toUpperCase());

		// Temperature
		float temperature = Float.parseFloat(weatherItem.getTemperature());
		customViewHolder.itemTemperature.setText(new DecimalFormat("0.0").format(temperature) + " " + "°C");


		// Date and time
		TimeZone tz = TimeZone.getDefault();
		String dateFormat = "E dd.MM.yyyy - HH:mm";
		String dateString;
		try {
			SimpleDateFormat resultFormat = new SimpleDateFormat(dateFormat);
			resultFormat.setTimeZone(tz);
			dateString = resultFormat.format(weatherItem.getDate());
		} catch (IllegalArgumentException e) {
			dateString = "DATE FORMAT ERROR";
		}

		customViewHolder.itemDate.setText(dateString);
	}

	@Override
	public int getItemCount() {
		if (itemList != null)
			return itemList.size();
		return 0;
	}
}