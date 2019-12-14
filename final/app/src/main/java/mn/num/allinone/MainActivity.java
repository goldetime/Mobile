package mn.num.allinone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  private Button save, read;
  private Book tmp;
  private List<Book> bookList;
  private FirebaseDatabase database;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    database = FirebaseDatabase.getInstance();

    tmp = new Book();
    bookList = new ArrayList<>();

    save = findViewById(R.id.save);
    save.setOnClickListener((View v) -> {

      DatabaseReference ref = database.getReference("Book");
      //ref.setValue(tmp);

      tmp.setName("Introduction to algorithm");
      tmp.setAuthor("Coreman");

      String id = ref.push().getKey();
      ref.child(id).setValue(tmp);
      Snackbar.make(findViewById(android.R.id.content), "write", Snackbar.LENGTH_LONG).show();
    });

    read = findViewById(R.id.read);
    read.setOnClickListener((View v) -> {
      DatabaseReference ref = database.getReference("Book");

      ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

          int j = 0;
          for (DataSnapshot i : dataSnapshot.getChildren()) {
            bookList.add(i.getValue(Book.class));
            System.out.println(bookList.get(j).getAuthor() + j);
            j++;
          }
          Snackbar.make(findViewById(android.R.id.content), "read", Snackbar.LENGTH_LONG).show();
        }

        @Override
        public void onCancelled(DatabaseError error) {
          Log.w("TAG", "Failed to read value.", error.toException());
        }
      });
    });
  }
}