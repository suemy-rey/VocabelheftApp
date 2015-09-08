
package vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.EditVocableActivity;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.R;

public class MyVocableListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private ArrayList<VocItem> vocNames;
    private VocAdapter voc_adapter;
    private VocDatabase voc_database;
    private Spinner original_spinner;
    private Spinner translation_spinner;
    private String[] string_array;


    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_vocable_list);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //string_array = getResources().getStringArray(R.array.language_arrays);

        initDB();
        initVocableList();
        initUI();
        updateList();
        updateSpinner();


        //textView_of_notes_long = (TextView)findViewById(R.id.textView_of_notes_long);
        //toggle_contents(textView_of_notes_long);
    }

    private void updateSpinner()
    {
        original_spinner = (Spinner) findViewById(R.id.spinner_language1);
        //int original_spinner_position = getIntent().getExtras().getInt("firstLanguagePosition");
        int position = getIntent().getIntExtra("firstLanguagePosition", 0);
        Log.d("position", "" + position);
        //original_spinner.setSelection(original_spinner_position, true);

        // translation_spinner = (Spinner)findViewById(R.id.spinner_language2);
        //  int translation_spinner_position = getIntent().getExtras().getInt("secondLanguagePosition");
        //  translation_spinner.setSelection(translation_spinner_position,true);
    }

    /**
     * http://www.javacodegeeks.com/2013/09/android-expandablecollapsible-views.html
     * <p/>
     * onClick handler
     */
    //  public void toggle_contents(View v){
    //    v.setVisibility( v.isShown()
    //          ? View.GONE
    //         : View.VISIBLE );
    //   if(v.isShown()){
    //      Slide.slide_up(this,v);
    //  v.setVisibility(View.GONE);
    //}else{
    //      v.setVisibility(View.VISIBLE);
    //    Slide.slide_down(this, v);
    //     }
    // }
    private void initDB()
    {
        voc_database = new VocDatabase(getApplicationContext());
        voc_database.open();
    }

    private void initVocableList()
    {
        vocNames = new ArrayList<VocItem>();
    }

    private void initUI()
    {
        initListView();
        initListAdapter();
    }


    private void initListAdapter()
    {
        ListView list = (ListView) findViewById(R.id.listViewOfMyVoc);
        voc_adapter = new VocAdapter(getApplicationContext(), vocNames);
        list.setAdapter(voc_adapter);
    }

    private void initListView()
    {
        final ListView list = (ListView) findViewById(R.id.listViewOfMyVoc);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                VocItem vocItem = vocNames.get(position);
                Intent i = new Intent(MyVocableListActivity.this, EditVocableActivity.class);
                i.putExtra("voc_name", vocItem.getName());
                startActivity(i);
            }
        });


        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id)
            {

                return false;
            }
        });
    }

    private void updateList()
    {
        vocNames.clear();
        vocNames.addAll(voc_database.getAllVocItems());
        voc_adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch (id)
        {
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

    private void searchForVocable()
    {

    }

    //des is beim ActionBar
    private void addVocable()
    {
        Intent addNewVoc = new Intent(getApplicationContext(), EditVocableActivity.class);
        startActivity(addNewVoc);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }

    protected void onDestroy()
    {
        voc_database.close();
        super.onDestroy();
    }
}