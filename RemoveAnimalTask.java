package app.com.example.android.geobestiary;

import android.os.AsyncTask;

public class RemoveAnimalTask extends AsyncTask<String, Void, Boolean> {
    @Override
    protected Boolean doInBackground(String... params) {
        return deleteAnimal(params[0]);
    }

    private boolean deleteAnimal(String a) {
        return MainActivity.dbh.deleteAnimal(a);
    }
}
