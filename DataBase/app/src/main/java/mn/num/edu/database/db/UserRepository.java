public class UserRepository {
	private UserDAO u;
	private LiveData<List<UserEntity>> allUsers;

	public UserRepository(Application application) {
		AppDatabase db = AppDatabase.getInstance(application);
		u = db.userDao();
		allUsers = u.getAll();
	}

	public void insert(UserEntity user) {

	}

	public void update(UserEntity user) {

	}

	public void delete(UserEntity user) {

	}

	public LiveData<List<UserEntity>> getAllUsers() {
		return allUsers;
	}
}
