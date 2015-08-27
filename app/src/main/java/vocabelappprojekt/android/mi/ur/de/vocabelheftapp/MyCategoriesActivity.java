package vocabelappprojekt.android.mi.ur.de.vocabelheftapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by S�meyye on 18.08.2015.
 */
public class MyCategoriesActivity extends AppCompatActivity{

    private ArrayList<CategoryItem> categoryNames;
    private CategoryAdapter category_adapter;
    private CategoryDatabase category_database;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_category_list);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initDB();
        initCategoryList();
        initUI();
        updateList();

    }

    private void updateList() {
        categoryNames.clear();
        categoryNames.addAll(category_database.getAllCategoryItems());
        category_adapter.notifyDataSetChanged();
    }

    private void initDB() {
        category_database = new CategoryDatabase(getApplicationContext());

    }


    private void initUI() {
        initButton();
        initListView();
        initListAdapter();
    }

    private void initListAdapter() {
        ListView list = (ListView) findViewById(R.id.category_list);
        category_adapter = new CategoryAdapter(getApplicationContext(),categoryNames, new OnButtonClicklistener() {
            @Override
            public void onButtonClick(int position) {
                Intent detail_activity_intent  = new Intent(getApplicationContext(), DetailCategoriesActivity.class);
                startActivity(detail_activity_intent);
            }
        });
        list.setAdapter(category_adapter);

    }


    private void initListView() {
        ListView listview = (ListView) findViewById(R.id.category_list);

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
               removeItemAtPosition(position);
                return false;
            }
        });
    }

    private void initButton() {
        Button addButton = (Button) findViewById(R.id.name_category_add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = (EditText) findViewById(R.id.name_category_input);
                String names = edit.getText().toString();

                if (!names.equals("")) {
                    addNewNames(names);
                    edit.setText("");
                }
            }
        });


    }

    private void addNewNames(String names) {
        if (categoryNames.equals("")) {
            return;
        } else {
            long id = 0;
            CategoryItem newCategoryItem = new CategoryItem(id, names);
            categoryNames.add(newCategoryItem);
            category_adapter.notifyDataSetChanged();
            category_database.insertCategoryItem(names);
        }

    }
    private void removeItemAtPosition(int position){
        if((categoryNames.get(position) == null)){
            return;
        }else{
            CategoryItem categoryItem = categoryNames.get(position);
            category_database.deleteCategoryItem(categoryItem);
            updateList();
        }
    }

    private void initCategoryList() {
        categoryNames = new ArrayList<CategoryItem>();

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

    protected void onDestroy(){
        category_database.close();
        super.onDestroy();
    }
    protected  void onResume(){
        super.onResume();
        updateList();
    }
}
