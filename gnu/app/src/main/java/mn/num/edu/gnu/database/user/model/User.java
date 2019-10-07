package mn.num.edu.gnu.database.user.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


//@Entity(tableName = "user")
@Entity
public class User {//implements Serializable {
	@PrimaryKey
	public int uid;

	@ColumnInfo(name = "first_name")
	public String firstName;

	@ColumnInfo(name = "last_name")
	public String lastName;
}