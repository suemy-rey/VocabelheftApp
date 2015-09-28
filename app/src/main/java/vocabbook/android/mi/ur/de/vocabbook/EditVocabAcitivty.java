package vocabbook.android.mi.ur.de.vocabbook;

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

import vocabbook.android.mi.ur.de.vocabbook.Log.Log;
import vocabbook.android.mi.ur.de.vocabbook.MyCategories.CategoryDatabase;
import vocabbook.android.mi.ur.de.vocabbook.MyVocabList.MyVocabListActivity;
import vocabbook.android.mi.ur.de.vocabbook.MyVocabList.VocabDatabase;
import vocabbook.android.mi.ur.de.vocabbook.MyVocabList.VocabItem;

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
    protected void onCreate(Bundle savedInstanceState)
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

    @Override
    protected void onDestroy()
    {
        vocabDB.close();
        categoryDB.close();
        super.onDestroy();
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
                updateSpinner();
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
        vocabDB.updateOriginalLanguageSpinner("" + vocabItem.getId(), wordLanguageSpinner.getSelectedItem().toString());
        vocabDB.updateTranslationLanguageSpinner("" + vocabItem.getId(), translationLanguageSpinner.getSelectedItem().toString());
        if (categorySpinner.getSelectedItem() != null)
        {
            Log.e("updating cat");
            vocabDB.updateCategory("" + vocabItem.getId(), categorySpinner.getSelectedItem().toString());
        }
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
}