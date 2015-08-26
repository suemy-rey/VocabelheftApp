package vocabelappprojekt.android.mi.ur.de.vocabelheftapp;

/**
 * Created by Sümeyye on 23.08.2015.
 */
public class CategoryItem {

    private String name;
    private long id;

    public CategoryItem(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public long getId(){
        return id;
    }
}
