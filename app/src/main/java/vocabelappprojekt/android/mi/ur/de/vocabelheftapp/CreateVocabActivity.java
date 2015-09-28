package vocabelappprojekt.android.mi.ur.de.vocabelheftapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.MyCategories.CategoryDatabase;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.MyVocabList.MyVocabListActivity;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.MyVocabList.VocabDatabase;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.MyVocabList.VocabItem;

public class CreateVocabActivity extends AppCompatActivity
{
    private final static String USER_PROMPT = "Geben Sie zuerst die Vokabeln ein";

    private VocabDatabase vocabDB;
    private CategoryDatabase categoryDB;
    private List<String> categoriesList;

    private String categoriesText;

    private Spinner wordLanguageSpinner;
    private Spinner translationLanguageSpinner;
    private Spinner categorySpinner;

    private EditText wordInput;
    private EditText translationInput;
    private EditText notesInput;

    private Button saveVocabButton;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocab);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initDB();
        initUI();
    }

    private void initUI()
    {
        initInputFields();
        initButton();
        initSpinner();
    }

    private void initInputFields()
    {
        wordInput = (EditText) findViewById(R.id.firstVoc);
        translationInput = (EditText) findViewById(R.id.secondVoc);
        notesInput = (EditText) findViewById(R.id.textView_of_notes);
    }

    private void initSpinner()
    {
        //SPINNER WORD LANGUAGE
        wordLanguageSpinner = (Spinner) findViewById(R.id.language1);
        ArrayAdapter wordLanguageDropdownAdapter = ArrayAdapter.createFromResource(this, R.array.language_arrays, android.R.layout.simple_spinner_dropdown_item);
        wordLanguageDropdownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wordLanguageSpinner.setAdapter(wordLanguageDropdownAdapter);
        //SPINNER TRANSLATION LANGUAGE
        translationLanguageSpinner = (Spinner) findViewById(R.id.language2);
        ArrayAdapter translationLanguageDropdownLanguage = ArrayAdapter.createFromResource(this, R.array.language_arrays, android.R.layout.simple_spinner_dropdown_item);
        translationLanguageDropdownLanguage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        translationLanguageSpinner.setAdapter(translationLanguageDropdownLanguage);
        //SPINNER CATEGORIES
        categorySpinner = (Spinner) findViewById(R.id.spinner_category);
        categoriesList = categoryDB.getAllLabels();
        ArrayAdapter<String> categoryDropdownAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesList);
        categoryDropdownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryDropdownAdapter);

        if (categoryDropdownAdapter.isEmpty())
        {
            categoriesText = "";
        }
        else
        {
            categoriesText = categorySpinner.getSelectedItem().toString();
        }
    }

    private void initButton()
    {
        saveVocabButton = (Button) findViewById(R.id.button_save);
        saveVocabButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String word = wordInput.getText().toString();
                String translation = translationInput.getText().toString();
                String notes = notesInput.getText().toString();

                String wordLanguage = wordLanguageSpinner.getSelectedItem().toString();
                String translationLanguage = translationLanguageSpinner.getSelectedItem().toString();

                if (word.equals("") || translation.equals(""))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), USER_PROMPT, Toast.LENGTH_LONG);
                    toast.show();
                }
                else
                {
                    createNewVocab(word, translation, wordLanguage, translationLanguage, notes, categoriesText);

                    wordInput.setText("");
                    translationInput.setText("");
                    notesInput.setText("");

                    Intent returnToVocabList = new Intent(getApplicationContext(), MyVocabListActivity.class);
                    startActivity(returnToVocabList);
                }
            }
        });
    }

    private void createNewVocab(String word, String translation, String wordLanguage, String translationLanguage, String notes, String category)
    {
        VocabItem newVocab = new VocabItem(word, translation, wordLanguage, translationLanguage, notes, category);
        vocabDB.insertVocItem(newVocab);
    }

    private void initDB()
    {
        vocabDB = new VocabDatabase(this);
        vocabDB.open();

        categoryDB = new CategoryDatabase(this);
        categoryDB.open();
    }
}