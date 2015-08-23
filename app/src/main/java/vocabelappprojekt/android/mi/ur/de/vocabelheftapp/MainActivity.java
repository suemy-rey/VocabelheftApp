package vocabelappprojekt.android.mi.ur.de.vocabelheftapp;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;


public class MainActivity extends AppCompatActivity{

    private Button button_one;
    private Button button_two;
    private Button button_three;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);


        initUI();
        
        
    }

    private void initUI() {
        button_one = (Button) findViewById(R.id.button_vokabeln);
        button_two = (Button) findViewById(R.id.button_sammlungen);
        button_three = (Button) findViewById(R.id.button_testen);

        button_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MyVocableList.class);
                startActivity(i);
            }
        });

        button_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MyCategories.class);
                startActivity(i);
            }
        });

        button_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TestClass.class);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
       // MenuItem searchItem = menu.findItem(R.id.action_search);
       // SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
       // return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                return true;
            case R.id.action_add:
                addVocable();
                return true;
            case R.id.action_search:
                searchForVocable();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void searchForVocable() {
    }


    private void addVocable() {
        Intent addNewVoc = new Intent (getApplicationContext(), EditVocable.class);
        startActivity(addNewVoc);

    }
}
