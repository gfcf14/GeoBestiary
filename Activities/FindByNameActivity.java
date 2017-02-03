package app.com.example.android.geobestiary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class FindByNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_by_name);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void displayAnimal(View v) throws ExecutionException, InterruptedException {

        EditText et = (EditText) findViewById(R.id.txtFindByName);
        String animal = et.getText().toString();

        if (animal.equals("")) Toast.makeText(this, "Please enter an animal to search...", Toast.LENGTH_SHORT).show();
        else {
            Intent i = new Intent(this, DisplayAnimalActivity.class);
            Bundle b = new Bundle();

            String animalSpecs = new FetchAnimalTask().execute(animal).get();

            if (animalSpecs.equals("")) {
                Toast.makeText(this, "The animal " + animal + " is not in this database...", Toast.LENGTH_SHORT).show();
            }
            else {
                b.putString("animal", animalSpecs);
                i.putExtras(b);

                startActivity(i);
            }
        }
    }

}
