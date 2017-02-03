package app.com.example.android.geobestiary;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A placeholder fragment containing a simple view.
 */
public class DisplayAnimalActivityFragment extends Fragment {

    private final String LOG_TAG = FetchAnimalTask.class.getSimpleName();

    public DisplayAnimalActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String dispAnimal = "";
        Intent i = getActivity().getIntent();

        if (i != null) {
            dispAnimal = new Animal(i.getStringExtra("animal").split(";")).toString();
        }

        View rootView  = inflater.inflate(R.layout.fragment_display_animal, container, false);

        TextView tv  = (TextView) rootView.findViewById(R.id.animalInfo);
        tv.setText(dispAnimal);

        //Log.v(LOG_TAG, "oc***" + AnimalInfo + "***");

        return rootView;
    }
}
