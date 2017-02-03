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
public class AddAnimalActivityFragment extends Fragment {

    public AddAnimalActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_add_animal, container, false);

        Button b = (Button) rootView.findViewById(R.id.btnAddAnimal);
        b.setOnClickListener(new Button.OnClickListener() {
           public void onClick(View v) {
               ArrayList<String> emptyList = new ArrayList<String>();

               String addNameText = ((EditText) getView().findViewById(R.id.txtAddName)).getText().toString();

               String addDescText = ((EditText) getView().findViewById(R.id.txtAddDescription)).getText().toString();
               String addSigText = ((EditText) getView().findViewById(R.id.txtAddSignificance)).getText().toString();
               String addSizeText = ((EditText) getView().findViewById(R.id.txtAddSize)).getText().toString();
               String addLmbsText = ((Spinner) getView().findViewById(R.id.spnAddLimbs)).getSelectedItem().toString();
               String addLocText = ((Spinner) getView().findViewById(R.id.spnAddLoc)).getSelectedItem().toString();

               if (addNameText.equals("")) emptyList.add("Name");
               if (addDescText.equals("")) emptyList.add("Description");
               if (addSigText.equals("")) emptyList.add("Significance");
               if (addSizeText.equals("")) emptyList.add("Size");
               if (addLmbsText.equals("Choose limb number:")) emptyList.add("Limbs");
               if (addLocText.equals("Choose preferred location:")) emptyList.add("Location");


               //no blank field was found!
               if (emptyList.isEmpty()) {
                   String[] animalArr = new String[] {addNameText, addDescText, addSigText, addSizeText, addLmbsText, addLocText};

                   try {
                       boolean animalWasAdded = new SaveAnimalTask().execute(animalArr).get();

                       if (animalWasAdded) {
                           Intent i = new Intent(getActivity(), MainActivity.class);
                           i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                           startActivity(i);

                           Toast.makeText(getActivity(), "The Animal " + addNameText + " was added successfully", Toast.LENGTH_SHORT).show();
                       }
                       else {
                           Toast.makeText(getActivity(), "Could not add Animal " + addNameText + "...", Toast.LENGTH_SHORT).show();
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
