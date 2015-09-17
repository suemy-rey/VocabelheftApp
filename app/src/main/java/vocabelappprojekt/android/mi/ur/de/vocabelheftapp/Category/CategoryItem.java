package vocabelappprojekt.android.mi.ur.de.vocabelheftapp.Category;

public class CategoryItem
{
    private long id;
    private String name;

    public CategoryItem(String name)
    {
        this.name = name;
    }

    public CategoryItem(long id, String name)
    {
        this.name = name;
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public long getId()
    {
        return id;
    }
}