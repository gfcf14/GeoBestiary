package app.com.example.android.geobestiary;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A placeholder fragment containing a simple view.
 */
public class FindByAttributesActivityFragment extends Fragment {

    public FindByAttributesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_find_by_attributes, container, false);

        Button b = (Button) rootView.findViewById(R.id.btnFindByAttributes);
        b.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                ArrayList<String> attList = new ArrayList<String>();

                String findLmbsText = ((Spinner) getView().findViewById(R.id.spnFindByLimbs)).getSelectedItem().toString();
                String findLocText = ((Spinner) getView().findViewById(R.id.spnFindByLocation)).getSelectedItem().toString();

                if (!findLmbsText.equals("Choose limb number:")) attList.add(findLmbsText);
                if (!findLocText.equals("Choose preferred location:")) attList.add(findLocText);

                //at least one field was set
                if (!attList.isEmpty()) {
                    String cols = "";
                    ArrayList<String> validCols = new ArrayList<String>();
                    ArrayList<String> validVals = new ArrayList<String>();

                    if (!findLmbsText.equals("Choose limb number:"))  {
                        validCols.add(Animal.COL_ANIMAL_LMBS + "=?");
                        validVals.add(findLmbsText);
                    }
                    if (!findLocText.equals("Choose preferred location:")) {
                        validCols.add(Animal.COL_ANIMAL_LOC + "=?");
                        validVals.add(findLocText);
                    }

                    for (int i = 0; i < validCols.size(); i++) {
                        if (i > 0) cols += " AND ";
                        cols += validCols.get(i);
                    }

                    String vals = "";
                    for (int i = 0; i < validVals.size(); i++) {
                        vals += validVals.get(i);
                        if (i != validVals.size() - 1) vals += ";";
                    }

                    try {
                        String animalsFound = new FetchAnimalTask().execute(cols, vals).get();

                        if (animalsFound.equals("")) {
                            Toast.makeText(getActivity(), "No Animals match the specified criteria...", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent i = new Intent(getActivity(), AnimalsFoundActivity.class);
                            Bundle b = new Bundle();
                            b.putString("animals", animalsFound);
                            i.putExtras(b);

                            startActivity(i);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                else Toast.makeText(getActivity(), "At least one or more fields need to be set", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}
