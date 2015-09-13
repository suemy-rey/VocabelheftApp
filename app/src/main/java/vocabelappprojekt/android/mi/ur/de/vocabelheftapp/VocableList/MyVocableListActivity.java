
package vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList;

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
import android.widget.Spinner;

import java.util.ArrayList;

import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.Log.Log;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.EditVocableActivity;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.ModifyVocableActivity;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.R;

public class MyVocableListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    private ArrayList<VocItem> vocItems;
    private VocAdapter voc_adapter;
    private VocDatabase voc_database;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_vocable_list);
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
        vocItems = new ArrayList<VocItem>();
    }

    private void initUI()
    {
        initListView();
        initListAdapter();
    }

    private void initListAdapter()
    {
        ListView list = (ListView) findViewById(R.id.listViewOfMyVoc);
        voc_adapter = new VocAdapter(this, vocItems);
        list.setAdapter(voc_adapter);
    }

    private void initListView()
    {
        ListView list = (ListView) findViewById(R.id.listViewOfMyVoc);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
               // Log.e("clicked");
                VocItem vocItem = vocItems.get(position);
                Intent i = new Intent(MyVocableListActivity.this, ModifyVocableActivity.class);
                //Log.e(""+vocItem.getId());
                i.putExtra("voc_id", vocItem.getId());
                startActivity(i);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id)
            {
               // Log.e("long clicked");
                removeItemAtPosition(position);
                return false;
            }
        });

    }

    private void removeItemAtPosition(int position) {
        if(vocItems.get(position) == null){
            return;
        }else{
            Log.e("id","" + vocItems.get(position).getId());
            VocItem item = vocItems.get(position);

            voc_database.deleteVocItem(""+item.getId());
            updateList();
        }
    }

    private void updateList()
    {
        vocItems.clear();
        vocItems.addAll(voc_database.getAllVocItems());
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