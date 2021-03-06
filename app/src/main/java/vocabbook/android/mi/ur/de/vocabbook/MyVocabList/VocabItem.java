package vocabbook.android.mi.ur.de.vocabbook.MyVocabList;

public class VocabItem
{
    private int id;
    private String vocab;
    private String translation;
    private String vocabLanguage;
    private String translationLanguage;
    private String notes;
    private String category;

    public VocabItem(String vocab)
    {
        this.vocab = vocab;
    }

    public VocabItem(String vocab, String translation, String vocabLanguage, String translationLanguage, String notes, String category)
    {
        this.vocab = vocab;
        this.translation = translation;
        this.vocabLanguage = vocabLanguage;
        this.translationLanguage = translationLanguage;
        this.notes = notes;
        this.category = category;
    }

    public VocabItem(int id, String vocab, String translation, String vocabLanguage, String translationLanguage, String notes, String category)
    {
        this.id = id;
        this.vocab = vocab;
        this.translation = translation;
        this.vocabLanguage = vocabLanguage;
        this.translationLanguage = translationLanguage;
        this.notes = notes;
        this.category = category;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

    public void setVocab(String vocab)
    {
        this.vocab = vocab;
    }

    public String getVocab()
    {
        return vocab;
    }

    public void setTranslation(String translation)
    {
        this.translation = translation;
    }

    public String getTranslation()
    {
        return translation;
    }

    public String getVocabLanguage()
    {
        return vocabLanguage;
    }

    public String getTranslationLanguage()
    {
        return translationLanguage;
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