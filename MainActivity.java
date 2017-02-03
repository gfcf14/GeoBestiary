package app.com.example.android.geobestiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public static DbHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbh = new DbHelper(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void findAll(View v) throws ExecutionException, InterruptedException {
        String allAnimals = new FetchAnimalTask().execute("all").get();

        if (allAnimals.equals("")) {
            Toast.makeText(this, "No Animals match the specified criteria...", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent i = new Intent(this, AnimalsFoundActivity.class);
            Bundle b = new Bundle();
            b.putString("animals", allAnimals);
            i.putExtras(b);

            startActivity(i);
        }
    }

    public void findAnimal(View v) {
        Intent i = new Intent(this, FindAnimalActivity.class);
        startActivity(i);
    }

    public void addAnimal(View v) {
        Intent i = new Intent(this, AddAnimalActivity.class);
        startActivity(i);
    }

    public void editAnimal(View v) {
        Intent i = new Intent(this, FindAnimaltoEditActivity.class);
        startActivity(i);
    }

    public void removeAnimal(View v) {
        Intent i = new Intent(this, RemoveAnimalActivity.class);
        startActivity(i);
    }
}
