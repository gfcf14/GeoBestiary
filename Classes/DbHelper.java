package app.com.example.android.geobestiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "geobestiary.db";
    public static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE = "CREATE TABLE " + Animal.TABLE_NAME + " (" +
                Animal.COL_ANIMAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Animal.COL_ANIMAL_NAME + " TEXT NOT NULL, " +
                Animal.COL_ANIMAL_DESC + " TEXT NOT NULL, " +
                Animal.COL_ANIMAL_SIG + " TEXT NOT NULL, " +
                Animal.COL_ANIMAL_SIZE + " TEXT NOT NULL, " +
                Animal.COL_ANIMAL_LMBS + " INTEGER NOT NULL, " +
                Animal.COL_ANIMAL_LOC + " TEXT NOT NULL" +
                ");";

        db.execSQL(SQL_CREATE_TABLE);

        //SAMPLE DATA :)
        addAnimalUponCreate(db, new Animal(new String[] {"Dog", "A member of the Canid species, known for its loyalty and used for a variety of works", "Due to its preference, it is known by humans as man's best friend", "between 6.3 to 34 inches in length", "4", "Ground"}));
        addAnimalUponCreate(db, new Animal(new String[] {"Cat", "A feline, known for always landing on its feet", "This is the most popular animal for internet memes", "between 9.1 to 9.8 inches in length", "4", "Ground"}));
        addAnimalUponCreate(db, new Animal(new String[] {"Horse", "A large animal of the Equidae species, known for its speed and widely domesticated by man", "Horses can be used for heavy work (cold-blooded) or racing (hot-blooded)", "between 4.7 to 6 feet in length", "4", "Ground"}));
        addAnimalUponCreate(db, new Animal(new String[] {"Chicken", "A bird, which despite of having two wings cannot fly. Used as a pet as well as food, either boiled, roasted or fried.", "In culture, chicken can be used to refer to cowardly individuals", "About 10 inches of average height", "2", "Ground"}));
        addAnimalUponCreate(db, new Animal(new String[] {"Shark", "A large, aquatic animal known for its viciousness and strength", "These animals have been used for humans as a personification of terror, specially in movies", "between 11 to 16 feet in length", "0", "Sea"}));
        addAnimalUponCreate(db, new Animal(new String[] {"Snake", "A limbless animal that drags itself on the ground. A member of the reptiles which uses its long body to wrap around its prey", "Snakes have usually been known as the personification of evil and malice, specially in religious context", "between 3.9 inches to 25 feet in length", "0", "Ground"}));
        addAnimalUponCreate(db, new Animal(new String[] {"Bear", "A large, fur-covered animal with an apparent fascination for fish and honey. It may move on four limbs but can stand on two", "Bears are used by humans in the form of stuffed animals which they may give to each other", "between 2 to 5 feet in height", "4", "Ground"}));
        addAnimalUponCreate(db, new Animal(new String[] {"Pig", "A very useful animal which has been used mostly as food, but its fat, bones and fur have also been used for beauty products and tools", "Pigs are an alternative to chicken in regards to food. Some humans like them but others avoid them due to religious views", "between 1.8 to 3.6 feet in length", "4", "Ground"}));
        addAnimalUponCreate(db, new Animal(new String[] {"Spider", "A member of the arachnid species, this animal is known to weave webs to use for hunting or homes", "Though it can be used as a symbol of horror, spiders have become popular through fiction in the form of certain superheroes", "between 1 inch to 5 inches in legspan", "8", "Ground"}));
        addAnimalUponCreate(db, new Animal(new String[] {"Parrot", "A member of the bird species, it is known for being a social animal which may cause mischief in corn fields", "Some species are smart enough to imitate voice. Thus they have been used to repeat expletives, but also as a means of study to assess animal intelligence", "between 3.5 to 40 inches in height", "2", "Air"}));
    }

    public void addAnimalUponCreate(SQLiteDatabase db, Animal a) {
        ContentValues vals = new ContentValues();
        vals.put(Animal.COL_ANIMAL_NAME, a.getName());
        vals.put(Animal.COL_ANIMAL_DESC, a.getDescription());
        vals.put(Animal.COL_ANIMAL_SIG, a.getSignificance());
        vals.put(Animal.COL_ANIMAL_SIZE, a.getSize());
        vals.put(Animal.COL_ANIMAL_LMBS, a.getLimbs());
        vals.put(Animal.COL_ANIMAL_LOC, a.getLocation());

        db.insert(Animal.TABLE_NAME, null, vals);
    }

    public boolean addAnimal(Animal a) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vals = new ContentValues();
        vals.put(Animal.COL_ANIMAL_NAME, a.getName());
        vals.put(Animal.COL_ANIMAL_DESC, a.getDescription());
        vals.put(Animal.COL_ANIMAL_SIG, a.getSignificance());
        vals.put(Animal.COL_ANIMAL_SIZE, a.getSize());
        vals.put(Animal.COL_ANIMAL_LMBS, a.getLimbs());
        vals.put(Animal.COL_ANIMAL_LOC, a.getLocation());

        long result = db.insert(Animal.TABLE_NAME, null, vals);
        db.close();

        if (result != -1) return true;
        return false;
    }

    //will get each animal searched for by name only, in case query returns more than one animal
    public ArrayList<String> getAnimals(String cols, String[] vals) {
        SQLiteDatabase db = this.getReadableDatabase();

        //since we're only getting the names, we only need one string in the selection array
        String[] selection = new String[] {Animal.COL_ANIMAL_NAME};

        Cursor c = db.query(Animal.TABLE_NAME, selection, cols, vals, null, null, null, null);

        ArrayList<String> rows = new ArrayList<String>();

        //no Animals match the criteria
        if (c == null) {
            return null;
        }
        else {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                rows.add(c.getString(0));
                c.moveToNext();
            }

            return rows;
        }
    }

    //will get all animals and order them by name
    public ArrayList<String> getAllAnimals() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] selection = new String[] {Animal.COL_ANIMAL_NAME};

        Cursor c = db.query(Animal.TABLE_NAME, selection, null, null, null, null, Animal.COL_ANIMAL_NAME, null);

        ArrayList<String> rows = new ArrayList<String>();

        //no Animals match the criteria
        if (c == null) {
            return null;
        }
        else {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                rows.add(c.getString(0));
                c.moveToNext();
            }

            return rows;
        }
    }

    //will get an animal based on name only
    public Animal getAnimal(String cols, String[] vals) {
        SQLiteDatabase db = this.getReadableDatabase();

        //this time, we want all the info of an specified Animal, so we need a lenghtier selection
        String[] selection = new String[] {Animal.COL_ANIMAL_ID,
                Animal.COL_ANIMAL_NAME,
                Animal.COL_ANIMAL_DESC,
                Animal.COL_ANIMAL_SIG,
                Animal.COL_ANIMAL_SIZE,
                Animal.COL_ANIMAL_LMBS,
                Animal.COL_ANIMAL_LOC};

        Cursor c = db.query(Animal.TABLE_NAME, selection, cols, vals, null, null, null, null);

        //no Animals match the criteria
        if (c == null) {
            return null;
        }
        else {
            c.moveToFirst();
            try {
                Animal a = new Animal(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getInt(5), c.getString(6));
                return a;
            }catch (CursorIndexOutOfBoundsException e) { //lazy catch! Try to find if there really is no animal to match!
                return null;
            }
        }
    }

    public boolean updateAnimal(Animal a) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues vals = new ContentValues();
        vals.put(Animal.COL_ANIMAL_NAME, a.getName());
        vals.put(Animal.COL_ANIMAL_DESC, a.getDescription());
        vals.put(Animal.COL_ANIMAL_SIG, a.getSignificance());
        vals.put(Animal.COL_ANIMAL_SIZE, a.getSize());
        vals.put(Animal.COL_ANIMAL_LMBS, a.getLimbs());
        vals.put(Animal.COL_ANIMAL_LOC, a.getLocation());

        String selection = Animal.COL_ANIMAL_ID + " = ?";
        String selargs[] = new String[] {Integer.toString(a.getId())};

        int result = db.update(Animal.TABLE_NAME, vals, selection, selargs);

        db.close();

        if (result != 0) return true;
        return false;
    }

    public boolean deleteAnimal(String animalName) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = Animal.COL_ANIMAL_NAME + " = ?";
        String selargs[] = new String[] {animalName};

        long result = db.delete(Animal.TABLE_NAME, selection, selargs);
        db.close();

        //no row was deleted
        if (result == 0) return false;
        return true;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Animal.TABLE_NAME);
        onCreate(db);
    }
}
