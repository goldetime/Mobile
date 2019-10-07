package mn.num.edu.database.db.user.dao;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import mn.num.edu.database.db.user.model.UserEntity;

@Dao
public interface UserDAO {

	@Query("SELECT * FROM user")
	LiveData<List<UserEntity>> getAll();

	@Query("SELECT * FROM user where first_name LIKE  :first AND last_name LIKE :last")
	UserEntity findByUser(String first, String last);

	@Insert
	void insertAll(UserEntity... users);
}
