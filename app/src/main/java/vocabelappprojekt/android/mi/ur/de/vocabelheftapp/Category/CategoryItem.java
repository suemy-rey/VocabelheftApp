package vocabelappprojekt.android.mi.ur.de.vocabelheftapp.Category;

/**
 * Created by Sümeyye on 23.08.2015.
 */
public class CategoryItem {
    private long id;
    private String name;


    public CategoryItem(long id, String name) {
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
