package app.com.example.android.geobestiary;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/**
 * A placeholder fragment containing a simple view.
 */
public class AnimalsFoundActivityFragment extends Fragment {

    public AnimalsFoundActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String animalsfound = "";
        Intent i = getActivity().getIntent();

        if (i != null) {
            animalsfound = i.getStringExtra("animals");
        }

        ArrayList<String> animals = new ArrayList<String>(Arrays.asList(animalsfound.split(";")));

        final ArrayAdapter animalAdapter = new ArrayAdapter<String>(getActivity(), R.layout.fragment_animals_found, R.id.tvAnimalsFound, animals);

        View rootView = inflater.inflate(R.layout.fragment_animals_found, container, false);

        ListView lv = (ListView) rootView.findViewById(R.id.lvAnimalsFound);
        lv.setAdapter(animalAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String animalSpecs = new FetchAnimalTask().execute((String) animalAdapter.getItem(position)).get();
                    Intent i = new Intent(getActivity(), DisplayAnimalActivity.class);
                    Bundle b = new Bundle();
                    b.putString("animal", animalSpecs);
                    i.putExtras(b);
                    startActivity(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });

        return rootView;
    }
}
