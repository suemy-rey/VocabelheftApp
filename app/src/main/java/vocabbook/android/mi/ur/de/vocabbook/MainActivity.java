package vocabbook.android.mi.ur.de.vocabbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import vocabbook.android.mi.ur.de.vocabbook.MyCategories.MyCategoriesListActivity;
import vocabbook.android.mi.ur.de.vocabbook.MyVocabTest.VocabTestSetupActivity;
import vocabbook.android.mi.ur.de.vocabbook.MyVocabList.MyVocabListActivity;

public class MainActivity extends AppCompatActivity
{
    private Button gotoVocabButton;
    private Button gotoCategoriesButton;
    private Button gotoVocabTestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        initButtons();
    }

    private void initButtons()
    {
        gotoVocabButton = (Button) findViewById(R.id.button_vokabeln);
        gotoCategoriesButton = (Button) findViewById(R.id.button_sammlungen);
        gotoVocabTestButton = (Button) findViewById(R.id.button_testen);

        gotoVocabButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent gotoVocabList = new Intent(getApplicationContext(), MyVocabListActivity.class);
                startActivity(gotoVocabList);
            }
        });

        gotoCategoriesButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent gotoCategoriesList = new Intent(getApplicationContext(), MyCategoriesListActivity.class);
                startActivity(gotoCategoriesList);
            }
        });

        gotoVocabTestButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent gotoVocabTest = new Intent(getApplicationContext(), VocabTestSetupActivity.class);
                startActivity(gotoVocabTest);
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

        switch (id)
        {
            case R.id.action_add:
                createNewVocab();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createNewVocab()
    {
        Intent addNewVoc = new Intent(getApplicationContext(), CreateVocabActivity.class);
        startActivity(addNewVoc);
    }
}