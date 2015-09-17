package vocabelappprojekt.android.mi.ur.de.vocabelheftapp;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 1 on 2015/9/10.
 */
public class SearchActivity extends AppCompatActivity
{
    //https://developer.android.com/training/search/setup.html
    @Override
    public void onCreate(Bundle savedInstanceState) {

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {

        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }

}
