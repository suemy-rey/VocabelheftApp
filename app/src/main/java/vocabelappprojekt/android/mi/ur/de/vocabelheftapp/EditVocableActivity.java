package vocabelappprojekt.android.mi.ur.de.vocabelheftapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.Category.CategoryDatabase;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.Category.DetailCategoriesActivity;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.Category.MyCategoriesActivity;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList.MyVocableListActivity;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList.VocDatabase;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList.VocItem;

public class EditVocableActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{


    private VocDatabase voc_database;
    private CategoryDatabase category_database;
    private List<String> names_category;
    private VocItem vocItem;

    String spinner_category;

    private Spinner spinnerFirstLanguage, spinnerSecondLanguage, spinnerCategory;
    private Button button_speichern;
    private EditText input_language_original, input_language_translation, input_notes;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_vocable);

        initDB();
        initUI();

    }

    private void initUI()
    {
        input_language_original = (EditText) findViewById(R.id.firstVoc);
        input_language_translation = (EditText) findViewById(R.id.secondVoc);
        input_notes = (EditText) findViewById(R.id.textView_of_notes);
        initButton();
        initSpinner();
    }

    private void initSpinner()
    {
        spinnerFirstLanguage = (Spinner) findViewById(R.id.language1);
        ArrayAdapter adapter_original_spinner = ArrayAdapter.createFromResource(this, R.array.language_arrays, android.R.layout.simple_spinner_dropdown_item);
        adapter_original_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFirstLanguage.setAdapter(adapter_original_spinner);

        spinnerSecondLanguage = (Spinner) findViewById(R.id.language2);
        ArrayAdapter adapter_translation_spinner = ArrayAdapter.createFromResource(this, R.array.language_arrays, android.R.layout.simple_spinner_dropdown_item);
        adapter_translation_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSecondLanguage.setAdapter(adapter_translation_spinner);

        spinnerCategory = (Spinner) findViewById(R.id.spinner_category);
        names_category = category_database.getAllLabels();
        ArrayAdapter<String> adapter_spinner_category = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names_category);
        adapter_spinner_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter_spinner_category);

        if(adapter_spinner_category.isEmpty()){
            spinner_category = "";
        }else {
            spinner_category = spinnerCategory.getSelectedItem().toString();
        }

    }

    private void initButton()
    {
        button_speichern = (Button) findViewById(R.id.button_save);
        button_speichern.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String voc_one = input_language_original.getText().toString();
                String voc_two = input_language_translation.getText().toString();
                String note = input_notes.getText().toString();

                String spinner_original = spinnerFirstLanguage.getSelectedItem().toString();
                String spinner_translation = spinnerSecondLanguage.getSelectedItem().toString();


                if (voc_one.equals("") || voc_two.equals("") || spinner_original.equals("Wähle eine Sprache") || spinner_translation.equals("Wähle eine Sprache") )
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Geben Sie zuerst die Vokabeln ein und wählen Sie die Sprachen aus", Toast.LENGTH_LONG);
                    toast.show();
                }
                else
                {
                    input_language_original.setText("");
                    input_language_translation.setText("");
                    input_notes.setText("");


                    addNewVoc(voc_one, voc_two, spinner_original, spinner_translation, note, spinner_category);


                    Intent i = new Intent(getApplicationContext(), MyVocableListActivity.class);
                    Log.d("", "Spinner1: " + spinnerFirstLanguage + " position: " + spinnerFirstLanguage.getSelectedItemPosition());
                    i.putExtra("firstLanguagePosition", spinnerFirstLanguage.getSelectedItemPosition());
                    i.putExtra("secondLanguagePosition", spinnerSecondLanguage.getSelectedItemPosition());
                    startActivity(i);

                }


            }

        });
    }


    private void addNewVoc(String voc_one, String voc_two, String spinner_original, String spinner_translation, String note, String category)
    {

        VocItem newVoc = new VocItem(voc_one, voc_two, spinner_original, spinner_translation, note, category);
        voc_database.insertVocItem(newVoc);


    }
    private void initDB()
    {
        voc_database = new VocDatabase(this);
        voc_database.open();
        category_database = new CategoryDatabase(this);
        category_database.open();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }

}