package vocabbook.android.mi.ur.de.vocabbook.MyCategories;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import vocabbook.android.mi.ur.de.vocabbook.CreateVocabActivity;
import vocabbook.android.mi.ur.de.vocabbook.EditVocabAcitivty;
import vocabbook.android.mi.ur.de.vocabbook.Log.Log;
import vocabbook.android.mi.ur.de.vocabbook.R;
import vocabbook.android.mi.ur.de.vocabbook.MyVocabList.MyVocabListActivity;
import vocabbook.android.mi.ur.de.vocabbook.MyVocabList.VocabAdapter;
import vocabbook.android.mi.ur.de.vocabbook.MyVocabList.VocabDatabase;
import vocabbook.android.mi.ur.de.vocabbook.MyVocabList.VocabItem;

public class CategoryContentsActivity extends AppCompatActivity
{
    private ArrayList<VocabItem> vocabList;
    private VocabAdapter vocabAdapter;
    private VocabDatabase vocabDB;

    private String categoryName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_contents);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        categoryName = getIntent().getExtras().getString(MyCategoriesListActivity.CATEGORY_NAME_EXTRA);

        initDB();
        initVocabList();
        initUI();
        updateList();
    }

    @Override
    protected void onDestroy()
    {
        vocabDB.close();
        super.onDestroy();
    }

    private void initDB()
    {
        vocabDB = new VocabDatabase(getApplicationContext());
        vocabDB.open();
    }

    private void initVocabList()
    {
        vocabList = new ArrayList<VocabItem>();
    }

    private void initUI()
    {
        initListView();
        initListAdapter();
    }

    private void initListAdapter()
    {
        ListView vocabView = (ListView) findViewById(R.id.list_of_detailed_category_list);
        vocabAdapter = new VocabAdapter(getApplicationContext(), vocabList);
        vocabView.setAdapter(vocabAdapter);
    }

    private void initListView()
    {
        ListView vocabView = (ListView) findViewById(R.id.list_of_detailed_category_list);

        vocabView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                VocabItem vocabItem = vocabList.get(position);
                Intent editVocab = new Intent(CategoryContentsActivity.this, EditVocabAcitivty.class);
                editVocab.putExtra(MyVocabListActivity.VOCAB_EXTRA, vocabItem.getId());
                startActivity(editVocab);
            }
        });

        vocabView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                removeVocabAt(position);
                return true;
            }
        });

    }

    private void removeVocabAt(int position)
    {
        if (vocabList.get(position) == null)
        {
            return;
        }
        else
        {
            VocabItem item = vocabList.get(position);

            vocabDB.deleteVocItem("" + item.getId());
            updateList();
        }
    }

    private void updateList()
    {
        vocabList.clear();
        vocabList.addAll(vocabDB.getVocItemsFromCategory(categoryName));
        vocabAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.action_add:
                createNewVocab();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createNewVocab()
    {
        Intent createVocab = new Intent(getApplicationContext(), CreateVocabActivity.class);
        startActivity(createVocab);
    }
}