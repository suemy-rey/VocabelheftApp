package vocabelappprojekt.android.mi.ur.de.vocabelheftapp.Category;

public class CategoryItem
{
    private long id;
    private String name;
    private String vocable;


    public CategoryItem(long id, String name)
    {
        this.name = name;
        this.id = id;
        this.vocable = vocable;
    }


    public String getName()
    {
        return name;
    }

    public long getId()
    {
        return id;
    }

    public String getVocable()
    {
        return vocable;
    }
}