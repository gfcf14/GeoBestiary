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
public class RemoveAnimalActivityFragment extends Fragment {

    public RemoveAnimalActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_remove_animal, container, false);

        Button b = (Button) rootView.findViewById(R.id.btnRemoveAnimal);
        b.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                String removeAnimalText = ((EditText) getView().findViewById(R.id.txtRemoveAnimal)).getText().toString();
                if (removeAnimalText.equals("")) {
                    Toast.makeText(getActivity(), "Please enter an animal to remove", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        boolean animalWasRemoved = new RemoveAnimalTask().execute(removeAnimalText).get();

                        if (animalWasRemoved) {
                            Intent i = new Intent(getActivity(), MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);

                            Toast.makeText(getActivity(), "The Animal " + removeAnimalText + " was removed successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getActivity(), "Could not remove Animal " + removeAnimalText + "...", Toast.LENGTH_SHORT).show();
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
