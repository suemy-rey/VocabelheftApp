package vocabbook.android.mi.ur.de.vocabbook.MyVocabList;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import vocabbook.android.mi.ur.de.vocabbook.CreateVocabActivity;
import vocabbook.android.mi.ur.de.vocabbook.EditVocabAcitivty;
import vocabbook.android.mi.ur.de.vocabbook.R;

public class MyVocabListActivity extends AppCompatActivity
{
    public final static String VOCAB_EXTRA = "vocabID";

    private ArrayList<VocabItem> vocabItem;
    private VocabAdapter vocabAdapter;
    private VocabDatabase vocabDB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vocab);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initDB();
        initVocabList();
        initUI();
        updateVocabList();
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
        vocabItem = new ArrayList<VocabItem>();
    }

    private void initUI()
    {
        initListView();
        initListAdapter();
    }

    private void initListAdapter()
    {
        ListView list = (ListView) findViewById(R.id.listViewOfMyVoc);
        vocabAdapter = new VocabAdapter(this, vocabItem);
        list.setAdapter(vocabAdapter);
    }

    private void initListView()
    {
        ListView list = (ListView) findViewById(R.id.listViewOfMyVoc);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                VocabItem vocab = vocabItem.get(position);

                Intent editVocab = new Intent(MyVocabListActivity.this, EditVocabAcitivty.class);
                editVocab.putExtra(VOCAB_EXTRA, vocab.getId());
                startActivity(editVocab);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
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
        if (vocabItem.get(position) == null)
        {
            return;
        }
        else
        {
            VocabItem item = vocabItem.get(position);

            vocabDB.deleteVocItem("" + item.getId());
            updateVocabList();
        }
    }

    private void updateVocabList()
    {
        vocabItem.clear();
        vocabItem.addAll(vocabDB.getAllVocItems());
        vocabAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        OnQueryTextListener queryTextListener = new OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextChange(String query)
            {
                searchForVocab(query);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query)
            {
                searchForVocab(query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.action_add:
                createNewVocabItem();
                return true;
            case R.id.action_search:
                searchForVocab();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void searchForVocab()
    {
        vocabItem.clear();
        vocabItem.addAll(vocabDB.getAllVocItems());
        vocabAdapter.notifyDataSetChanged();
    }

    private void searchForVocab(String text)
    {
        vocabItem.clear();
        vocabItem.addAll(vocabDB.getVocItemsByMotherLanguage(text));
        vocabAdapter.notifyDataSetChanged();
    }

    private void createNewVocabItem()
    {
        Intent addNewVoc = new Intent(getApplicationContext(), CreateVocabActivity.class);
        startActivity(addNewVoc);
    }
}