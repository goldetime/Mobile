package mn.num.edu.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

  private static final String DATABASE_NAME = "name.db";
  private static final int DATABASE_VERSION = 1;

  public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase nameDb) {
    nameDb.execSQL("CREATE TABLE " + "User" + " ("
        + "id" + " INTEGER PRIMARY KEY,"
				+ "f_name" + " TEXT,"
				+ "l_name" + " TEXT,"
				+ "age" + " TEXT,"
				+ "sex" + " TEXT,"
        + "phone" + " TEXT,"
				+ "year" + " TEXT,"
				+ "month" + " TEXT,"
        + "day" + " TEXT"
        + ");");
    // nameDb.execSQL("INSERT INTO User ( name) VALUES ('Roger' );");
  }
  public void onUpgrade(SQLiteDatabase arg0, int oldVersion, int newVersion) {

  }
}
