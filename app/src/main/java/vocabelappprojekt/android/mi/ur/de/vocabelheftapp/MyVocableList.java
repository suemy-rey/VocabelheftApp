package vocabelappprojekt.android.mi.ur.de.vocabelheftapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by S�meyye on 18.08.2015.
 */
public class MyVocableList extends AppCompatActivity {

    private ArrayList<VocItem> vocNames;
    private VocAdapter voc_adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_vocable_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initList();
        initUI();



    }

    private void initList() {
            vocNames = new ArrayList<VocItem>();
    }

    private void initUI() {
       // initButton();
        initListView();
        initListAdapter();
    }

    private void initButton() {
        Button save_button = (Button) findViewById(R.id.button_save);
    }

    private void initListAdapter() {
        ListView list = (ListView) findViewById(R.id.listViewOfMyVoc);
        voc_adapter = new VocAdapter(this, vocNames);
        list.setAdapter(voc_adapter);

    }

    private void initListView() {
        ListView list = (ListView) findViewById(R.id.listViewOfMyVoc);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {

                return false;
            }
        });
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
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
