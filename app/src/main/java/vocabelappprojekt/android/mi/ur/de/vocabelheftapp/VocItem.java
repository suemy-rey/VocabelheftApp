package vocabelappprojekt.android.mi.ur.de.vocabelheftapp;

/**
 * Created by Sümeyye on 24.08.2015.
 */
public class VocItem {
    private String original_name;
    private String translation_name;
    private String original_language_spinner;
    private String translation_language_spinner;
    private String notes;
    private String category;



    public VocItem(String original_name, String translation_name, String original_language_spinner, String translation_language_spinner, String notes, String category) {

        this.original_name = original_name;
        this.translation_name = translation_name;
        this.original_language_spinner = original_language_spinner;
        this.translation_language_spinner = translation_language_spinner;
        this.notes = notes;
        this.category = category;
    }

    public String getOriginal_name() {
        return original_name;
    }

    public  String getTranslation_name(){
        return  translation_name;
    }
    public String getOriginal_language_spinner() {
        return original_language_spinner;
    }

    public String getTranslation_language_spinner() {
        return translation_language_spinner;
    }

    public String getNotes(){
        return notes;
    }
    public String getCategory(){
        return category;
    }
}
