package app.com.example.android.geobestiary;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

public class FetchAnimalTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {

        if (params.length == 1) { //for when an specified animal is queried
            String animal = params[0];
            if (animal.equals("all")) return getAllAnimals();
            else return getAnimal(animal);
        }
        else {
            return getAnimals(params);
        }
    }

    private String getAnimal(String animal) {

        Animal a = MainActivity.dbh.getAnimal("Name=?", new String[]{animal});

        if (a == null) return "";
        else return a.getAnimalString();

        /*try {
                URL url = new URL(getString(R.string.get_single_animal) + animal);

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoInput(true);

                InputStream is = new BufferedInputStream(urlConnection.getInputStream());
                String s = getStringFromInputStream(is);

                return s;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return "None";*/
    }

    private String getAnimals(String... params) {
        ArrayList<String> animals = MainActivity.dbh.getAnimals(params[0], params[1].split(";"));

        if (animals.isEmpty()) return "";

        String allAnimals = "";
        for (int i = 0; i < animals.size(); i++) {
            allAnimals += animals.get(i);
            if (i != animals.size() - 1) allAnimals += ";";
        }

        return allAnimals;
    }

    private String getAllAnimals() {
        ArrayList<String> animals = MainActivity.dbh.getAllAnimals();

        if (animals.isEmpty()) return "";

        String allAnimals = "";
        for (int i = 0; i < animals.size(); i++) {
            allAnimals += animals.get(i);
            if (i != animals.size() - 1) allAnimals += ";";
        }

        return allAnimals;
    }

//        private String getStringFromInputStream (InputStream is) throws IOException {
//            BufferedReader br = new BufferedReader(new InputStreamReader(is));
//            StringBuilder out = new StringBuilder();
//            String line;
//
//            while ((line = br.readLine()) != null) {
//                out.append(line);
//            }
//
//            br.close();
//            return out.toString();
//        }

}
