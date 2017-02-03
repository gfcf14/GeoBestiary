package app.com.example.android.geobestiary;

import android.os.AsyncTask;

public class SaveAnimalTask extends AsyncTask<String, Void, Boolean> {
    @Override
    protected Boolean doInBackground(String... params) {
        return saveAnimal(params);
    }

    private boolean saveAnimal(String[] a) {
        return MainActivity.dbh.addAnimal(new Animal(a));
    }
}
