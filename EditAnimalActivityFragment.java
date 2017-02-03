package app.com.example.android.geobestiary;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A placeholder fragment containing a simple view.
 */
public class EditAnimalActivityFragment extends Fragment {

    private int editAnimalID = 0;

    public EditAnimalActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Animal animalToEdit = new Animal();
        Intent i = getActivity().getIntent();
        if (i != null) {
            animalToEdit = new Animal(i.getStringExtra("animal").split(";"));
        }

        View rootView = inflater.inflate(R.layout.fragment_edit_animal, container, false);

        EditText et = (EditText) rootView.findViewById(R.id.txtEditAnimalName);
        et.setText(animalToEdit.getName());

        et = (EditText) rootView.findViewById(R.id.txtEditAnimalDescription);
        et.setText(animalToEdit.getDescription());

        et = (EditText) rootView.findViewById(R.id.txtEditAnimalSignificance);
        et.setText(animalToEdit.getSignificance());

        et = (EditText) rootView.findViewById(R.id.txtEditAnimalSize);
        et.setText(animalToEdit.getSize());

        Spinner sp = (Spinner) rootView.findViewById(R.id.spnEditAnimalLimbs);
        String[] spinnersArray = getResources().getStringArray(R.array.LimbItems);
        int spindx = 0;

        for (int it = 0; it < spinnersArray.length; it++) {
            if (spinnersArray[it].equalsIgnoreCase(Integer.toString(animalToEdit.getLimbs()))) {
                spindx = it;
                break;
            }
        }
        sp.setSelection(spindx);

        sp = (Spinner) rootView.findViewById(R.id.spnEditAnimalLocation);
        spinnersArray = getResources().getStringArray(R.array.LocationItems);
        spindx = 0;

        for (int it = 0; it < spinnersArray.length; it++) {
            if (spinnersArray[it].equalsIgnoreCase(animalToEdit.getLocation())) {
                spindx = it;
                break;
            }
        }
        sp.setSelection(spindx);

        editAnimalID = animalToEdit.getId();

        Button b = (Button) rootView.findViewById(R.id.btnEditAnimal);
        b.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                ArrayList<String> emptyList = new ArrayList<String>();

                String editNameText = ((EditText) getView().findViewById(R.id.txtEditAnimalName)).getText().toString();
                String editDescText = ((EditText) getView().findViewById(R.id.txtEditAnimalDescription)).getText().toString();
                String editSigText = ((EditText) getView().findViewById(R.id.txtEditAnimalSignificance)).getText().toString();
                String editSizeText = ((EditText) getView().findViewById(R.id.txtEditAnimalSize)).getText().toString();
                String editLmbsText = ((Spinner) getView().findViewById(R.id.spnEditAnimalLimbs)).getSelectedItem().toString();
                String editLocText = ((Spinner) getView().findViewById(R.id.spnEditAnimalLocation)).getSelectedItem().toString();

                if (editNameText.equals("")) emptyList.add("Name");
                if (editDescText.equals("")) emptyList.add("Description");
                if (editSigText.equals("")) emptyList.add("Significance");
                if (editSizeText.equals("")) emptyList.add("Size");
                if (editLmbsText.equals("Choose limb number:")) emptyList.add("Limbs");
                if (editLocText.equals("Choose preferred location:")) emptyList.add("Location");


                //no blank field was found!
                if (emptyList.isEmpty()) {
                    Animal a = new Animal(editAnimalID, editNameText, editDescText, editSigText, editSizeText, Integer.parseInt(editLmbsText), editLocText);

                    try {
                        boolean animalWasEdited = new UpdateAnimalTask().execute(a).get();

                        if (animalWasEdited) {
                            Intent i = new Intent(getActivity(), MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);

                            Toast.makeText(getActivity(), "The Animal " + editNameText + " was updated successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getActivity(), "Could not update Animal " + editNameText + "...", Toast.LENGTH_SHORT).show();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    String errMessage = "One or more fields were not added or set:\n";

                    for (int i = 0; i < emptyList.size(); i++) {
                        errMessage += emptyList.get(i);
                        if (i != emptyList.size() - 1) errMessage += ", ";
                    }

                    Toast.makeText(getActivity(), errMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }
}
