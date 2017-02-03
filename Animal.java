package app.com.example.android.geobestiary;

public class Animal {
    //ID, Name, Description, Significance, Size, Limbs, Location
    public static final String TABLE_NAME = "animals";
    public static final String COL_ANIMAL_ID = "id";
    public static final String COL_ANIMAL_NAME = "name";
    public static final String COL_ANIMAL_DESC = "description";
    public static final String COL_ANIMAL_SIG = "significance";
    public static final String COL_ANIMAL_SIZE = "size";
    public static final String COL_ANIMAL_LMBS = "limbs";
    public static final String COL_ANIMAL_LOC = "location";

    //attributes of an animal
    private int id;
    private String name;
    private String description;
    private String significance;
    private String size;
    private int limbs;
    private String location;

    public Animal() {

    }

    public Animal (int i, String nam, String desc, String sig, String siz, int lmbs, String loc) {
        id = i;
        name = nam;
        description = desc;
        significance = sig;
        size = siz;
        limbs = lmbs;
        location = loc;
    }

    //constructor to specifically add an animal from either fixed values or user defined values
    public Animal (String[] params) {
        if (params.length == 7) {
            id = Integer.parseInt(params[0]);
            name = params[1];
            description = params[2];
            significance = params[3];
            size = params[4];
            limbs = Integer.parseInt(params[5]);
            location = params[6];
        }
        else {
            name = params[0];
            description = params[1];
            significance = params[2];
            size = params[3];
            limbs = Integer.parseInt(params[4]);
            location = params[5];
        }
    }

    public String getAnimalString() {
        return getId() + ";" + getName() + ";" + getDescription() + ";" + getSignificance() + ";" + getSize() + ";" + getLimbs() + ";" + getLocation();
    }

    @Override
    public String toString() {
        return "Name: " + getName() + "\n\nDescription: " + getDescription() + "\n\nSignificance: " + getSignificance() + "\n\nSize: " + getSize() + "\n\nLimbs: " + getLimbs() + "\n\nLocation: " + getLocation();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLimbs() {
        return limbs;
    }

    public void setLimbs(int limbs) {
        this.limbs = limbs;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignificance() {
        return significance;
    }

    public void setSignificance(String significance) {
        this.significance = significance;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
