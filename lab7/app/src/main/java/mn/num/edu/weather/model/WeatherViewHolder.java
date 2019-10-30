package mn.num.edu.weather.model;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import mn.num.edu.weather.R;

public class WeatherViewHolder extends RecyclerView.ViewHolder {

  public TextView itemDate;
  public TextView itemDescription;
  public TextView itemTemperature;
  public View lineView;

  // view = itemView
  public WeatherViewHolder(@NonNull View view) {
    super(view);
    //this.itemDate = (TextView) view.findViewById(R.id.itemDate);
    //this.itemTemperature = (TextView) view.findViewById(R.id.itemTemperature);
    //this.itemDescription = (TextView) view.findViewById(R.id.itemDescription);
    //this.lineView = view.findViewById(R.id.lineView);
  }
}