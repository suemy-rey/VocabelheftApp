package vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList;

public class VocItem
{

    private long id;
    private String vocab;
    private String translation;
    private String vocabLanguageSetting;
    private String translationLanguageSetting;
    private String notes;
    private String category;

    public VocItem(long id)
    {
        this.id = id;
    }

    public VocItem(long id, String vocab, String translation)
    {
        this.id = id;
        this.vocab = vocab;
        this.translation = translation;
    }

    public VocItem(long id, String vocab, String translation,
                   String vocabLanguage, String translationLanguage,
                   String notes, String category)
    {
        this.id = id;
        this.vocab = vocab;
        this.translation = translation;
        this.vocabLanguageSetting = vocabLanguage;
        this.translationLanguageSetting = translationLanguage;
        this.notes = notes;
        this.category = category;
    }

    public long getId()
    {
        return id;
    }

    public void  setVocab(String newVocab)
    {
        vocab = newVocab;
    }

    public String getVocab()
    {
        return vocab;
    }

    public void setVocabTranslation(String newVocabTranslation)
    {
        translation = newVocabTranslation;
    }

    public String getVocabTranslation()
    {
        return translation;
    }

    public void setVocabLanguage(String vocabLanguage)
    {
        vocabLanguageSetting = vocabLanguage;
    }

    public String getVocabLanguageSetting()
    {
        return vocabLanguageSetting;
    }

    public void setVocabTranslationLanguage(String vocabTranslation)
    {
        translationLanguageSetting = vocabTranslation;
    }

    public String getVocabTranslationLanguage()
    {
        return translationLanguageSetting;
    }

    public void setNotes(String newNotes)
    {
        notes = newNotes;
    }

    public String getNotes()
    {
        return notes;
    }

    public void setCategory(String newCategory)
    {
        category = newCategory;
    }

    public String getCategory()
    {
        return category;
    }
}