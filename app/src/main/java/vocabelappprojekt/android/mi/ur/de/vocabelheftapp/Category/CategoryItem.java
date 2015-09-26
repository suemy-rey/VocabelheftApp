package vocabelappprojekt.android.mi.ur.de.vocabelheftapp.Category;

public class CategoryItem
{
    private long id;
    private String categoryName;

    public CategoryItem(String name)
    {
        this.categoryName = name;
    }

    public CategoryItem(long id, String categoryName)
    {
        this.categoryName = categoryName;
        this.id = id;
    }

    public void setName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getName()
    {
        return categoryName;
    }

    public long getId()
    {
        return id;
    }
}