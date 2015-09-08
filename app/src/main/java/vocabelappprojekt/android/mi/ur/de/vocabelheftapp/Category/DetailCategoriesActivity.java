package vocabelappprojekt.android.mi.ur.de.vocabelheftapp.Category;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.EditVocableActivity;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.R;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList.VocAdapter;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList.VocDatabase;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList.VocItem;

public class DetailCategoriesActivity extends AppCompatActivity
{
    private ArrayList<VocItem> vocNames;
    private DetailCategoriesAdapter detailCategoriesAdapter;
    private VocDatabase voc_database;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_category_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initDB();
        initVocableList();
        initUI();
        updateList();

    }

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
        ListView list = (ListView) findViewById(R.id.list_of_detailed_category_list);
        detailCategoriesAdapter = new DetailCategoriesAdapter(getApplicationContext(), vocNames);
        list.setAdapter(detailCategoriesAdapter);
    }

    private void initListView()
    {
        final ListView list = (ListView) findViewById(R.id.list_of_detailed_category_list);
    }

    private void updateList()
    {
        vocNames.clear();
        vocNames.addAll(voc_database.getAllVocItems());
        detailCategoriesAdapter.notifyDataSetChanged();
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

    private void addVocable()
    {
        Intent addNewVoc = new Intent(getApplicationContext(), EditVocableActivity.class);
        startActivity(addNewVoc);

    }
}