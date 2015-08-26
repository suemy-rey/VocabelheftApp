package vocabelappprojekt.android.mi.ur.de.vocabelheftapp;

import android.widget.Spinner;

/**
 * Created by Sümeyye on 24.08.2015.
 */
public class VocItem {
    private String name;
    private String spinner1;
    private String spinner2;


    public VocItem(String name, String spinner_one, String spinner_two) {

        this.name = name;
        this.spinner1 = spinner_one;
        this.spinner2 = spinner_two;
    }

    public String getName() {
        return name;
    }

    public String getSpinner1() {
        return spinner1;
    }

    public String getSpinner2() {
        return spinner2;
    }

}
