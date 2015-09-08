package vocabelappprojekt.android.mi.ur.de.vocabelheftapp.Category;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.EditVocableActivity;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.R;

/**
 * Created by S�meyye on 18.08.2015.
 */
public class DetailCategoriesActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_category_list);

       ActionBar actionBar = getSupportActionBar();
       actionBar.setDisplayHomeAsUpEnabled(true);




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


    private void addVocable() {
        Intent addNewVoc = new Intent (getApplicationContext(), EditVocableActivity.class);
        startActivity(addNewVoc);

    }


}
