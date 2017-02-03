package app.com.example.android.geobestiary;

import android.os.AsyncTask;

public class UpdateAnimalTask extends AsyncTask<Animal, Void, Boolean>{
    @Override
    protected Boolean doInBackground(Animal... params) {
        return editAnimal(params[0]);
    }

    private boolean editAnimal(Animal a) {
        return MainActivity.dbh.updateAnimal(a);
    }
}
