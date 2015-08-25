package vocabelappprojekt.android.mi.ur.de.vocabelheftapp;

/**
 * Created by Sümeyye on 24.08.2015.
 */
public class VocItem {
    private String name;
    private String original_language_spinner;
    private String translatrion_language_spinner;


    public VocItem(String name, String spinner_one, String spinner_two) {

        this.name = name;
        this.original_language_spinner = spinner_one;
        this.translatrion_language_spinner = spinner_two;
    }

    public String getName() {
        return name;
    }

    public String getOriginal_language_spinner() {
        return original_language_spinner;
    }

    public String getTranslation_language_spinner() {
        return translatrion_language_spinner;
    }

}
