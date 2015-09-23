package vocabelappprojekt.android.mi.ur.de.vocabelheftapp.Category;

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
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.Log.Log;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.ModifyVocableActivity;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.R;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList.VocAdapter;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList.VocDatabase;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList.VocItem;

public class DetailCategoriesActivity extends AppCompatActivity
{
    private ArrayList<Integer> vocabIDList;
    private ArrayList<VocItem> vocabList;
    private VocAdapter categoriesAdapter;
    private VocDatabase voc_database;


    private String categoryName = "";

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_category_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        categoryName = getIntent().getExtras().getString(MyCategoriesActivity.CATEGORY_NAME_EXTRA);

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
        vocabIDList = new ArrayList<Integer>();
        vocabList = new ArrayList<VocItem>();
    }

    private void initUI()
    {
        initListView();
        initListAdapter();
    }

    private void initListAdapter()
    {
        ListView list = (ListView) findViewById(R.id.list_of_detailed_category_list);
        categoriesAdapter = new VocAdapter(getApplicationContext(), vocabList);
        list.setAdapter(categoriesAdapter);
    }

    private void initListView()
    {
        ListView list = (ListView) findViewById(R.id.list_of_detailed_category_list);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                // Log.e("clicked");
                VocItem vocItem = vocabList.get(position);
                Intent i = new Intent(DetailCategoriesActivity.this, ModifyVocableActivity.class);
                //Log.e(""+vocItem.getId());
                i.putExtra("voc_id", vocItem.getId());
                startActivity(i);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                // Log.e("long clicked");
                removeItemAtPosition(position);
                return false;
            }
        });

    }

    private void removeItemAtPosition(int position) {
        if(vocabList.get(position) == null){
            return;
        }else{
            Log.e("id", "" + vocabList.get(position).getId());
            VocItem item = vocabList.get(position);

            voc_database.deleteVocItem(""+item.getId());
            updateList();
        }
    }

    private void updateList()
    {
        vocabList.clear();
        vocabList.addAll(voc_database.getVocItemsFromCategory(categoryName));
        categoriesAdapter.notifyDataSetChanged();
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
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    public void addVocabToCategoryList(int newVocabID)
//    {
//
//        vocabIDList.add(newVocabID);
//        vocabList.add(newVocab);
//    }



    private void addVocable()
    {
        Intent addNewVoc = new Intent(getApplicationContext(), EditVocableActivity.class);
        startActivity(addNewVoc);
    }
}