package vocabelappprojekt.android.mi.ur.de.vocabelheftapp;

/**
 * Created by S�meyye on 18.08.2015.
 */

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

import java.util.ArrayList;

import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList.MyVocableListActivity;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList.VocAdapter;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList.VocDatabase;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList.VocItem;

public class EditVocableActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    private ArrayList<VocItem> vocs = new ArrayList<VocItem>();
    private VocItem vocItem;
    private VocDatabase voc_database;
    private VocAdapter voc_adapter;

    private Spinner spinnerFirstLanguage, spinnerSecondLanguage;
    private Button button_sammlungen, button_speichern;
    private EditText input_language_one, input_language_two, input_notes;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_vocable);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        initDB();
        initUI();


    }

    private void initDB()
    {
        voc_database = new VocDatabase(this);
        voc_database.open();
    }


    private void initUI()
    {

        //  initText();
        initButton();
        // initSpinner();
    }

    private void initSpinner()
    {
        spinnerFirstLanguage = (Spinner) findViewById(R.id.language1);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.language_arrays, android.R.layout.simple_spinner_dropdown_item);
        spinnerFirstLanguage.setAdapter(adapter);
        //  spinnerFirstLanguage.setOnItemSelectedListener(this);

        spinnerSecondLanguage = (Spinner) findViewById(R.id.language2);
        spinnerSecondLanguage.setAdapter(adapter);
        // spinnerSecondLanguage.setOnItemSelectedListener(this);
    }

    private void initButton()
    {
        button_sammlungen = (Button) findViewById(R.id.button_category);
        button_speichern = (Button) findViewById(R.id.button_save);

        button_sammlungen.setClickable(false);

        button_speichern.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                input_language_one = (EditText) findViewById(R.id.firstVoc);
                input_language_two = (EditText) findViewById(R.id.secondVoc);
                input_notes = (EditText) findViewById(R.id.editText_notes);

                String voc_one = input_language_one.getText().toString();
                Log.d("bunela", voc_one);
                String voc_two = input_language_two.getText().toString();
                String note = input_notes.getText().toString();

                if (voc_one.equals("") || voc_two.equals("") || note.equals(""))
                {
                }
                else
                {
                    input_language_one.setText("");
                    input_language_two.setText("");
                    input_notes.setText("");

                    //   String input_one = getIntent().getExtras().getString("voc_name");
                    //  vocItem = voc_database.getVocItem(input_one);
                    //  input_language_one.setText(vocItem.getVocab());

                    //  voc_database.updateTitle(vocItem.getVocab(), voc_one, voc_two);
                    //TODO: selected language missing!!!
                    addNewVoc(voc_one, voc_two, note);

                }

                Intent i = new Intent(getApplicationContext(), MyVocableListActivity.class);
                //  i.putExtra("Vokabel1", input_language_one.getText().toString());
                // i.putExtra("Vokabel2", input_language_two.getText().toString());
                startActivity(i);
            }
        });
    }

    public void addNewVoc(String voc_one, String voc_two, String note)
    {
        //TODO: id!!!
        long id = 0;
        VocItem newVoc = new VocItem(id, voc_one, voc_two, "", "", note, "");
        voc_database.insertVocItem(newVoc);
        // voc_database.updateTitle(String.valueOf(id),voc_one,voc_two);
        updateList();
    }

    private void updateList()
    {
        vocs.clear();
        vocs.addAll(voc_database.getAllVocItems());
        voc_adapter.notifyDataSetChanged();
    }

    private void initText()
    {
        input_language_one = (EditText) findViewById(R.id.firstVoc);
        input_language_two = (EditText) findViewById(R.id.secondVoc);

        long voc_item_id = getIntent().getExtras().getLong("voc_item_id");
        vocItem = voc_database.getVocItem(String.valueOf(voc_item_id));

        input_language_one.setText(vocItem.getVocab());
        input_language_two.setText(vocItem.getVocabTranslation());

        input_language_one.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                String title = input_language_one.getText().toString();
                String title_two = input_language_two.getText().toString();
                voc_database.updateTitle(String.valueOf(vocItem.getId()), title, title_two);
            }

            @Override
            public void afterTextChanged(Editable editable)
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
        });

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
}