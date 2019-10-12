package mn.num.edu.myapplication;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import java.sql.SQLException;

public class NameProvider extends ContentProvider {
  public static final String AUTHORITY = "mn.num.edu.NameProvider";
  public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/names");

  // MIME types
  public static final String CONTENT_TYPE = "vnd.num.cursor.dir/vnd.num.name";
  public static final String CONTENT_ITEM_TYPE = "vnd.num.cursor.item/vnd.num.name";

  private DatabaseHelper nameHelper;

  private static final int NAMES = 1;
  private static final int NAMES_ID = 2;

  private static final UriMatcher nameUriMatcher;
  static {
    nameUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    nameUriMatcher.addURI(AUTHORITY, "names", NAMES);
    nameUriMatcher.addURI(AUTHORITY, "names/#", NAMES_ID);
  }

  public boolean onCreate() {
    nameHelper = (new DatabaseHelper(getContext()));
    return true;
  }

  public String getType(Uri uri) {
    int match = nameUriMatcher.match(uri);
    switch (match) {
      case NAMES:
        return CONTENT_TYPE;
      case NAMES_ID:
        return CONTENT_ITEM_TYPE;
      default:
        throw new IllegalArgumentException("Unknown URI " + uri);
    }
  }

  public Uri insert(Uri uri, ContentValues values) {
    Uri noteUri = null;
    if (nameUriMatcher.match(uri) != NAMES) {
      throw new IllegalArgumentException("Unknown URI " + uri);
    }
    SQLiteDatabase db = nameHelper.getWritableDatabase();

    long rowId = db.insert(DatabaseHelper.NAMES_TABLE, null, values);
    if (rowId > 0) {
      noteUri = ContentUris.withAppendedId(CONTENT_URI, rowId);
      getContext().getContentResolver().notifyChange(noteUri, null);
    }
    try {
      throw new SQLException("Failed to insert row into " + uri);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return noteUri;
  }


  public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
    String sqlSelection = null;
    switch (nameUriMatcher.match(uri)) {
      case NAMES:
        sqlSelection = selection;
        break;
      case NAMES_ID:
        sqlSelection = DatabaseHelper.NAMES_TABLE + "." + DatabaseHelper.ID
            + " = " + uri.getPathSegments().get(1);
        break;
      default:
        throw new IllegalArgumentException("Unknown URI " + uri);
    }
    SQLiteDatabase db = nameHelper.getWritableDatabase();
    Cursor result = db.query(DatabaseHelper.NAMES_TABLE, projection, sqlSelection, selectionArgs, null, null, sortOrder);
    result.setNotificationUri(getContext().getContentResolver(), uri);
    return result;
  }

  public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
    SQLiteDatabase db = nameHelper.getWritableDatabase();
    int count;
    switch (nameUriMatcher.match(uri)) {
      case NAMES:
        count = db.update(DatabaseHelper.NAMES_TABLE, values, where, whereArgs);
        break;
      case NAMES_ID:
        String nameId = uri.getPathSegments().get(1);
        count = db.update(DatabaseHelper.NAMES_TABLE, values,
            DatabaseHelper.ID
                + " = "
                + nameId
                + (!TextUtils.isEmpty(where) ? " AND (" + where
                + ')' : ""), whereArgs);
        break;
      default:
        throw new IllegalArgumentException("Unknown URI " + uri);
    }
    getContext().getContentResolver().notifyChange(uri, null);
    return count;
  }

  public int delete(Uri uri, String where, String[] whereArgs) {
    /*
    SQLiteDatabase db = nameHelper.getWritableDatabase();
    int count;
    switch (nameUriMatcher.match(uri)) {
      case NAMES:
        count = db.delete(DatabaseHelper.NAMES_TABLE, where, whereArgs);
        break;
      case NAMES_ID:
        String nameId = uri.getPathSegments().get(1);
        count = db.delete(DatabaseHelper.NAMES_TABLE,
            DatabaseHelper.ID
                + " = "
                + nameId
                + (!TextUtils.isEmpty(where) ? " AND (" + where
                + ')' : ""), whereArgs);
        break;
      default:
        throw new IllegalArgumentException("Unknown URI " + uri);
    }
    getContext().getContentResolver().notifyChange(uri, null);
    return count;
     */
    return 0;
  }
}