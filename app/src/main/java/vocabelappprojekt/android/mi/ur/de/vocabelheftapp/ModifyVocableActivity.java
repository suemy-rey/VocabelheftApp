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

import java.util.List;

import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.Category.CategoryDatabase;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList.MyVocableListActivity;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList.VocDatabase;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList.VocItem;

public class ModifyVocableActivity extends AppCompatActivity {

        private VocDatabase voc_database;
        private CategoryDatabase category_database;
        private List<String> names_category;
        private VocItem vocItem;

        private Spinner spinnerFirstLanguage, spinnerSecondLanguage, spinnerCategory;
        private Button button_speichern;
        private EditText input_language_original, input_language_translation, input_notes;
        private String[] language_array;

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.edit_vocable);

            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);

            initDB();
            initUI();
            language_array = getResources().getStringArray(R.array.language_arrays);
            // Log.d("vocId",""+getIntent().getExtras().getInt("voc_id") );
            int voc_id = getIntent().getExtras().getInt("voc_id");
            vocItem = voc_database.getVocItem("" + voc_id);
            input_language_original.setText(vocItem.getName());
            input_language_translation.setText(vocItem.getName_two());
            input_notes.setText(vocItem.getNotes());
            spinnerFirstLanguage.setSelection(getPositionOfLanguageSpinner(vocItem.getSpinnerOfFirstLanguage()), true);
            spinnerSecondLanguage.setSelection(getPositionOfLanguageSpinner(vocItem.getSpinnerOfSecondLanguage()),true);
            spinnerCategory.setSelection(getPositionOfCategorySpinner(vocItem.getCategory()),true);
            updateVocable();
            updateTranslation();
            updateNotes();
            updateSpinner();
        }

    private int getPositionOfCategorySpinner(String category) {
        for(int i = 0 ; i < names_category.size(); i ++) {
            if(category.equals(names_category.get(i))){
                return i;
            }
        }
        return 0;
    }

    private int getPositionOfLanguageSpinner(String language) {
        for (int i = 0; i < language_array.length; i++) {
            if (language.equals(language_array[i])) {
                return i;
            }
        }
        return 0;
    }

    private void updateSpinner() {
       spinnerFirstLanguage.setOnItemSelectedListener(new MyOnItemSelectedListener());
       spinnerSecondLanguage.setOnItemSelectedListener(new MyOnItemSelectedListener());
       spinnerCategory.setOnItemSelectedListener(new MyOnItemSelectedListener());
    }

    private void updateNotes() {
        input_notes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                voc_database.updateNotes("" + vocItem.getId(), input_notes.getText().toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void updateTranslation() {
        input_language_translation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                voc_database.updateTranslation("" + vocItem.getId(), input_language_translation.getText().toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void updateVocable() {
        input_language_original.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("hallo", input_language_original.getText().toString());
                voc_database.updateVocab("" + vocItem.getId(), input_language_original.getText().toString());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
        // spinnerFirstLanguage.setOnItemSelectedListener(new MyOnItemSelectedListener());

        spinnerSecondLanguage = (Spinner) findViewById(R.id.language2);
        ArrayAdapter adapter_translation_spinner = ArrayAdapter.createFromResource(this, R.array.language_arrays, android.R.layout.simple_spinner_dropdown_item);
        adapter_translation_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSecondLanguage.setAdapter(adapter_translation_spinner);

        spinnerCategory = (Spinner) findViewById(R.id.spinner_category);
        names_category = category_database.getAllLabels();
        ArrayAdapter<String> adapter_spinner_category = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, names_category);
        adapter_spinner_category.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter_spinner_category);
    }

    private void initButton()
    {
        button_speichern = (Button) findViewById(R.id.button_save);
        button_speichern.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), MyVocableListActivity.class);
                startActivity(i);
            }

        });
    }

    private void initDB()
    {
        voc_database = new VocDatabase(this);
        voc_database.open();
        category_database = new CategoryDatabase(this);
        category_database.open();
    }

    private class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {
            Log.i("SelectedListener","viewId:"+parent.getId());
            if(parent.getId()==R.id.language1){
                Log.i("SelectedListener","language1:"+id);
              //  ModifyVocableActivity.vocabLanguagePosition=position;
                voc_database.updateOriginalLanguageSpinner(""+ vocItem.getId(),parent.getSelectedItem().toString());
            }else if(parent.getId() == R.id.language2){
                Log.i("SelectedListener","language2:"+id);
               // ModifyVocableActivity.transLanguagePosition=position;
                voc_database.updateTranslationLanguageSpinner(""+ vocItem.getId(),parent.getSelectedItem().toString());
            }else {
                voc_database.updateCategory(""+ vocItem.getId(), parent.getSelectedItem().toString());
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {

        }
    }



}


