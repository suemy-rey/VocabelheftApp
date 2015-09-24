package vocabelappprojekt.android.mi.ur.de.vocabelheftapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.Category.MyCategoriesActivity;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.Testen.TestActivity;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList.MyVocableListActivity;

public class MainActivity extends AppCompatActivity
{
    private Button button_vocable;
    private Button button_categories;
    private Button button_test;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);


        initUI();
    }

    private void initUI()
    {
        button_vocable = (Button) findViewById(R.id.button_vokabeln);
        button_categories = (Button) findViewById(R.id.button_sammlungen);
        button_test = (Button) findViewById(R.id.button_testen);

        button_vocable.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), MyVocableListActivity.class);
                startActivity(i);
            }
        });

        button_categories.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), MyCategoriesActivity.class);
                startActivity(i);
            }
        });

        button_test.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(), TestActivity.class);
                startActivity(i);
            }
        });
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id)
        {

            case R.id.action_add:
                addVocable();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addVocable()
    {
        Intent addNewVoc = new Intent(getApplicationContext(), EditVocableActivity.class);
        startActivity(addNewVoc);
    }
}