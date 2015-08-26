package vocabelappprojekt.android.mi.ur.de.vocabelheftapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by S�meyye on 18.08.2015.
 */
public class MyVocableListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spinnerFirstLanguage, spinnerSecondLanguage;
    private ArrayList<VocItem> vocNames;
    private VocAdapter voc_adapter;
    private VocDatabase voc_database;
    private TextView show_first_voc, show_sec_voc;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_vocable_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initDB();
        initList();
        initUI();
        updateList();




    }

    private void initDB() {
        voc_database = new VocDatabase(getApplicationContext());
        voc_database.open();
    }

    private void initList(){
            vocNames = new ArrayList<VocItem>();
    }

    private void initUI() {
        initListView();
        initListAdapter();
        //initTextView();
       // initSpinner();



    }


    private void initSpinner() {
            spinnerFirstLanguage = (Spinner) findViewById(R.id.spinner_language1);

            ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.language_arrays, android.R.layout.simple_spinner_dropdown_item);
            spinnerFirstLanguage.setAdapter(adapter);
            spinnerFirstLanguage.setOnItemSelectedListener(this);

            spinnerSecondLanguage = (Spinner) findViewById(R.id.spinner_language2);
            spinnerSecondLanguage.setAdapter(adapter);
            spinnerSecondLanguage.setOnItemSelectedListener(this);

    }

    private void initTextView() {
        show_first_voc = (TextView) findViewById(R.id.textView_language1);
        show_sec_voc = (TextView) findViewById(R.id.textView_language2);

        Intent i = getIntent();
        String first_input = i.getStringExtra("Vokabel1");
        String sec_input = i.getStringExtra("Vokabel2");

        show_first_voc.setText(first_input);
        show_sec_voc.setText(sec_input);
    }


    private void initListAdapter() {
        ListView list = (ListView) findViewById(R.id.listViewOfMyVoc);
        voc_adapter = new VocAdapter(this, vocNames);
        list.setAdapter(voc_adapter);

    }

    private void initListView() {
       final ListView list = (ListView) findViewById(R.id.listViewOfMyVoc);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
               // VocItem vocItem = vocNames.get(position);
                Intent i = new Intent (MyVocableListActivity.this, EditVocableActivity.class);
               // i.putExtra("voc_item_id", vocItem.getId());
                startActivity(i);

            }
        });



        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {

                return false;
            }
        });
    }

    private void updateList() {
        vocNames.clear();
        vocNames.addAll(voc_database.getAllVocItems());
        voc_adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_add:
                addVocable();
                return true;
            case R.id.action_search:
                searchForVocable();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void searchForVocable() {
    }

    //des is beim ActionBar
    private void addVocable() {
        Intent addNewVoc = new Intent (getApplicationContext(), EditVocableActivity.class);
        startActivity(addNewVoc);

        VocItem vocItem = new VocItem(0, "", "");
        voc_database.insertVocItem(vocItem);
        updateList();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    protected void onDestroy() {
        voc_database.close();
        super.onDestroy();
    }
}
