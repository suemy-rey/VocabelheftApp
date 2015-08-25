package vocabelappprojekt.android.mi.ur.de.vocabelheftapp;

import android.widget.Spinner;

/**
 * Created by Sümeyye on 24.08.2015.
 */
public class VocItem {

    private long id;
    private String name;
    private String name_two;
    private String spinnerOfFirstLanguage;
    private String spinnerOfSecondLanguage;

    public VocItem(long id, String name,String name_two){ //String spinner_one, String spinner_two) {
        this.id = id;
        this.name = name;
        this.name_two = name_two;
       // this.spinner1 = spinner_one;
        //this.spinner2 = spinner_two;
    }

    public long getId() { return id;}

    public String getName() {
        return name;
    }

    public String getName_two() { return name_two; }

    public String getSpinnerOfFirstLanguage() {
        return spinnerOfFirstLanguage;
    }

    public String getSpinnerOfSecondLanguage() {
        return spinnerOfSecondLanguage;
    }

}
