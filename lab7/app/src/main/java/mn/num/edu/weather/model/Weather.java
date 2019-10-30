package mn.num.edu.weather.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Weather {
  private String city;
  private String country;
  private double lat;
  private double lon;

  private Date date;
  private String temperature;
  private String description;

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getTemperature() {
    return temperature;
  }

  public void setTemperature(String temperature) {
    this.temperature = temperature;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getLat() {
    return lat;
  }

  public void setLat(double lat) {
    this.lat = lat;
  }

  public double getLon() {
    return lon;
  }

  public void setLon(double lon) {
    this.lon = lon;
  }

	    public Date getDate(){
        return this.date;
    }

    public void setDate(String dateString) {
        try {
            setDate(new Date(Long.parseLong(dateString) * 1000));
        } catch (Exception e) {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            try {
                setDate(inputFormat.parse(dateString));
            } catch (ParseException e2) {
                setDate(new Date());
                e2.printStackTrace();
            }
        }
    }

    public void setDate(Date date) {
        this.date = date;
    }
}