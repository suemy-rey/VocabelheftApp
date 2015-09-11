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
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList.MyVocableListActivity;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList.VocDatabase;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList.VocItem;

public class EditVocableActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    //private ArrayList<VocItem> vocs = new ArrayList<VocItem>();

    private VocDatabase voc_database;
    private CategoryDatabase category_database;
    private List<String> names_category;
    private VocItem vocItem;

    private Spinner spinnerFirstLanguage, spinnerSecondLanguage, spinnerCategory;
    private Button button_speichern;
    private EditText input_language_original, input_language_translation, input_notes;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_vocable);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        initDB();
        initUI();

       // Log.d("vocId",""+getIntent().getExtras().getInt("voc_id") );
        //int voc_id = getIntent().getExtras().getInt("voc_id");
       // vocItem = voc_database.getVocItem("" + voc_id);
      // input_language_original.setText(vocItem.getName());
      //  updateText();
    }

    private void updateText() {
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
        spinnerCategory.setOnItemSelectedListener(new MyOnItemSelectedListener());
        //spinnerCategory.setSelection(0,true);
    }

    private void initButton()
    {
        button_speichern = (Button) findViewById(R.id.button_save);
        button_speichern.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                String voc_one = input_language_original.getText().toString();
                Log.d("bunela", voc_one);
                String voc_two = input_language_translation.getText().toString();
                String note = input_notes.getText().toString();

                String spinner_original = spinnerFirstLanguage.getSelectedItem().toString();
                //Toast toast=Toast.makeText(getApplicationContext(),""+ spinner_original, Toast.LENGTH_SHORT);
                // toast.show();
                String spinner_translation = spinnerSecondLanguage.getSelectedItem().toString();
                String spinner_category = spinnerCategory.getSelectedItem().toString();

                if (voc_one.equals("") && voc_two.equals("") && note.equals(""))
                {

                }
                else
                {
                    input_language_original.setText("");
                    input_language_translation.setText("");
                    input_notes.setText("");
                }

                addNewVoc(voc_one, voc_two, spinner_original, spinner_translation, note, spinner_category);

                Intent i = new Intent(getApplicationContext(), MyVocableListActivity.class);
                Log.d("", "Spinner1: " + spinnerFirstLanguage + " position: " + spinnerFirstLanguage.getSelectedItemPosition());
                i.putExtra("firstLanguagePosition", spinnerFirstLanguage.getSelectedItemPosition());
                i.putExtra("secondLanguagePosition", spinnerSecondLanguage.getSelectedItemPosition());
                startActivity(i);
            }

            private void addNewVoc(String voc_one, String voc_two, String spinner_original, String spinner_translation, String note, String category)
            {
                //TODO: add add data to DB then get item from DB
                VocItem newVoc = new VocItem(voc_one, voc_two, spinner_original, spinner_translation, note, category);
                Log.d("vocItem", voc_one + "," + voc_two + "," + spinner_original + "," + spinner_translation + "," + note + "," + category);
                Toast toast = Toast.makeText(getApplicationContext(), "Sie haben ein Vokabel in " + category + "gespeichert", Toast.LENGTH_SHORT);
                toast.show();
                voc_database.insertVocItem(newVoc);
//                //TODO: category.addVocabToCategory(int vocabID) if category != empty string
//                if(category != null){
//
//                    // int vocId =
//                   // Log.d("id", ""+vocId);
//                   // detailCategoriesActivity = new DetailCategoriesActivity();
//                  //  detailCategoriesActivity.addVocabToCategoryList((int)vocId);
//
//
//                }
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        //  TextView myText = (TextView) view;
        // Toast.makeText(this,"You Selected"+myText.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }

    private class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
        {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {

        }
    }
}