package app.com.example.android.geobestiary;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

/**
 * A placeholder fragment containing a simple view.
 */
public class FindAnimaltoEditActivityFragment extends Fragment {

    public FindAnimaltoEditActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_find_animalto_edit, container, false);

        Button b = (Button) rootView.findViewById(R.id.btnFindtoEdit);
        b.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String editAnimalName = ((EditText) getView().findViewById(R.id.txtFindtoEditName)).getText().toString();
                if (editAnimalName.equals("")) {
                    Toast.makeText(getActivity(), "Please enter an animal to edit", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        String animalEditing = new FetchAnimalTask().execute(editAnimalName).get();

                        if (animalEditing.equals("")) {
                            Toast.makeText(getActivity(), "The Animal " + editAnimalName + " doesn't exist in the database...", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent i = new Intent(getActivity(), EditAnimalActivity.class);
                            Bundle b = new Bundle();

                            b.putString("animal", animalEditing);
                            i.putExtras(b);

                            startActivity(i);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return rootView;
    }
}
