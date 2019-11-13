package mn.num.edu.myapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "name.db";
  private static final int DATABASE_VERSION = 1;

  public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  public static final String NAMES_TABLE = "User";
  public static final String ID = "id";
  public static final String NAME = "f_name";
  public static final String L_NAME = "l_name";
  public static final String AGE = "age";
  public static final String SEX = "sex";
  public static final String PHONE = "phone";
  public static final String YEAR = "year";
  public static final String MONTH = "month";
  public static final String DAY = "day";


  @Override
  public void onCreate(SQLiteDatabase nameDb) {
    nameDb.execSQL("CREATE TABLE " + NAMES_TABLE + " ("
        + ID + " INTEGER PRIMARY KEY,"
				+ NAME + " TEXT,"
				+ L_NAME + " TEXT,"
				+ AGE + " TEXT,"
				+ SEX + " TEXT,"
        + PHONE + " TEXT,"
				+ YEAR + " TEXT,"
				+ MONTH + " TEXT,"
        + DAY + " TEXT"
        + ");");
    // nameDb.execSQL("INSERT INTO User ( name) VALUES ('Roger' );");
  }
  public void onUpgrade(SQLiteDatabase arg0, int oldVersion, int newVersion) {

  }
}