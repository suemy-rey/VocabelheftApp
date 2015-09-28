package vocabelappprojekt.android.mi.ur.de.vocabelheftapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.MyCategories.CategoryDatabase;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.MyVocabList.MyVocabListActivity;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.MyVocabList.VocabDatabase;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.MyVocabList.VocabItem;

public class EditVocabAcitivty extends AppCompatActivity
{
    private VocabDatabase vocabDB;
    private CategoryDatabase categoryDB;
    private List<String> categoriesList;
    private VocabItem vocabItem;

    private Spinner wordLanguageSpinner;
    private Spinner translationLanguageSpinner;
    private Spinner categorySpinner;

    private Button saveVocabButton;
    private EditText wordInput;
    private EditText translationInput;
    private EditText notesInput;

    private String[] languageArray;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocab);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initDB();
        initUI();

        languageArray = getResources().getStringArray(R.array.language_arrays);

        setupVocabDisplay();
        updateVocab();
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
        ArrayAdapter wordLanguageAdapter = ArrayAdapter.createFromResource(this, R.array.language_arrays, android.R.layout.simple_spinner_dropdown_item);
        wordLanguageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        wordLanguageSpinner.setAdapter(wordLanguageAdapter);
        //SPINNER TRANSLATION LANGUAGE
        translationLanguageSpinner = (Spinner) findViewById(R.id.language2);
        ArrayAdapter translationLanguageAdapter = ArrayAdapter.createFromResource(this, R.array.language_arrays, android.R.layout.simple_spinner_dropdown_item);
        translationLanguageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        translationLanguageSpinner.setAdapter(translationLanguageAdapter);
        //SPINNER CATEGORIES
        categorySpinner = (Spinner) findViewById(R.id.spinner_category);
        categoriesList = categoryDB.getAllLabels();
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesList);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoriesAdapter);
    }

    private void initButton()
    {
        saveVocabButton = (Button) findViewById(R.id.button_save);
        saveVocabButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent returnToVocabList = new Intent(getApplicationContext(), MyVocabListActivity.class);
                startActivity(returnToVocabList);
            }
        });
    }

    private void initDB()
    {
        vocabDB = new VocabDatabase(this);
        vocabDB.open();

        categoryDB = new CategoryDatabase(this);
        categoryDB.open();
    }

    private void setupVocabDisplay()
    {
        int vocabID = getIntent().getExtras().getInt(MyVocabListActivity.VOCAB_EXTRA);
        vocabItem = vocabDB.getVocItem("" + vocabID);

        wordInput.setText(vocabItem.getVocab());
        translationInput.setText(vocabItem.getTranslation());
        notesInput.setText(vocabItem.getNotes());

        wordLanguageSpinner.setSelection(getLanguagePosition(vocabItem.getVocabLanguage()), true);
        translationLanguageSpinner.setSelection(getLanguagePosition(vocabItem.getTranslationLanguage()), true);
        categorySpinner.setSelection(getCategoryPosition(vocabItem.getCategory()), true);
    }

    private void updateVocab()
    {
        updateWord();
        updateTranslation();
        updateNotes();
        updateSpinner();
    }

    private int getLanguagePosition(String language)
    {
        for (int i = 0; i < languageArray.length; i++)
        {
            if (language.equals(languageArray[i]))
            {
                return i;
            }
        }
        return 0;
    }

    private int getCategoryPosition(String category)
    {
        for (int i = 0; i < categoriesList.size(); i++)
        {
            if (category.equals(categoriesList.get(i)))
            {
                return i;
            }
        }
        return 0;
    }

    private void updateSpinner()
    {
        wordLanguageSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        translationLanguageSpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
        categorySpinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
    }

    private void updateNotes()
    {
        notesInput.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                vocabDB.updateNotes("" + vocabItem.getId(), notesInput.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

    private void updateTranslation()
    {
        translationInput.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                vocabDB.updateTranslation("" + vocabItem.getId(), translationInput.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

    private void updateWord()
    {
        wordInput.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                vocabDB.updateVocab("" + vocabItem.getId(), wordInput.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

    private class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            if (parent.getId() == R.id.language1)
            {
                vocabDB.updateOriginalLanguageSpinner("" + vocabItem.getId(), parent.getSelectedItem().toString());
            }
            else if (parent.getId() == R.id.language2)
            {
                vocabDB.updateTranslationLanguageSpinner("" + vocabItem.getId(), parent.getSelectedItem().toString());
            }
            else
            {
                vocabDB.updateCategory("" + vocabItem.getId(), parent.getSelectedItem().toString());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {

        }
    }
}