package mn.num.edu.database.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import mn.num.edu.database.db.user.model.UserEntity;
import mn.num.edu.database.db.user.dao.UserDAO;

@Database(entities = {UserEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

	public static final String DATABASE_NAME = "ochko.db";

	private static AppDatabase instance;
	
	public abstract UserDAO userDao();

	public static synchronized AppDatabase getInstance(Context context) {
		if (instance == null) {
			instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
				.fallbackToDestructiveMigration()
				.build();
		}
		return instance;
	}
}
