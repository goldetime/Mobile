package mn.num.edu.gnu.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import mn.num.edu.gnu.database.user.dao.UserDAO;
import mn.num.edu.gnu.database.user.model.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

	public static final String DATABASE_NAME = "ochko";
	
	public abstract UserDAO userDao();
}
