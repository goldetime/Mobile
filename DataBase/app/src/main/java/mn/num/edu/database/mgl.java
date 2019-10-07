public class MainActivity extends AppCompatActivity {

	private AppDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, DATABASE_NAME)
			.allowMainThreadQueries()
			.build();

		new DatabaseAsync().execute();

	}

	private class DatabaseAsync extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... voids) {

			user.setFirstName("A");
			user.setLastName("B");
			db.userDao().insertAll(user);

			
			db.userDao().insertAll(user);
			return null;
		}
	}
}
