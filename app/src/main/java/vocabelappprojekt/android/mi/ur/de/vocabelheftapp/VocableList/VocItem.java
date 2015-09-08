package vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList;

public class VocItem
{
    private long id;
    private String name;
    private String name_two;
    private String spinnerOfFirstLanguage;
    private String spinnerOfSecondLanguage;
    private String notes;
    private String category;

    public VocItem(String name)
    {
        this.name = name;
    }

    public VocItem(long id, String name, String name_two, String spinnerOfFirstLanguage, String spinnerOfSecondLanguage, String notes, String category)
    {
        this.id = id;
        this.name = name;
        this.name_two = name_two;
        this.spinnerOfFirstLanguage = spinnerOfFirstLanguage;
        this.spinnerOfSecondLanguage = spinnerOfSecondLanguage;
        this.notes = notes;
        this.category = category;
    }

    public long getId()
    {
        return id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName_two(String name_two)
    {
        this.name_two = name_two;
    }

    public String getName_two()
    {
        return name_two;
    }

    public String getSpinnerOfFirstLanguage()
    {
        return spinnerOfFirstLanguage;
    }

    public String getSpinnerOfSecondLanguage()
    {
        return spinnerOfSecondLanguage;
    }

    public String getNotes()
    {
        return notes;
    }

    public String getCategory()
    {
        return category;
    }
}