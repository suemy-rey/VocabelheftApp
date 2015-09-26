package vocabelappprojekt.android.mi.ur.de.vocabelheftapp.Category;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.EditVocableActivity;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.Log.Log;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.R;

public class MyCategoriesActivity extends AppCompatActivity  {

    public static final String CATEGORY_NAME_EXTRA = "category_name";

    private ArrayList<CategoryItem> categoriesList;
    private CategoryAdapter category_adapter;
    private CategoryDatabase category_database;

    private ListView list;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_category_list);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initDB();
        initCategoryList();
        initUI();
        updateList();
    }

    private void updateList()
    {
        categoriesList.clear();
        categoriesList.addAll(category_database.getAllCategoryItems());
        category_adapter.notifyDataSetChanged();
    }

    private void initDB()
    {
        category_database = new CategoryDatabase(getApplicationContext());
    }

    private void initUI()
    {
        initButton();
        initListAdapter();
        initListView();
    }

    private void initListAdapter()
    {
        list = (ListView) findViewById(R.id.category_list);
        category_adapter = new CategoryAdapter(getApplicationContext(), categoriesList);
        list.setAdapter(category_adapter);
    }

    private void initListView()
    {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent goToDetailCategoryListView = new Intent(getApplicationContext(), DetailCategoriesActivity.class);
                goToDetailCategoryListView.putExtra(CATEGORY_NAME_EXTRA, categoriesList.get(position).getName());
                startActivity(goToDetailCategoryListView);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                removeItemAtPosition(position);
                return true;
            }
        });
    }

    private void removeItemAtPosition(int position)
    {
        CategoryItem categoryItem = categoriesList.get(position);
        category_database.deleteCategoryItem(String.valueOf(categoryItem.getId()));
        updateList();
    }

    private void initButton()
    {
        Button addButton = (Button) findViewById(R.id.name_category_add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = (EditText) findViewById(R.id.name_category_input);
                String name = edit.getText().toString();

                if (!name.equals("")) {
                    addNewCategory(name);
                    edit.setText("");
                }
            }
        });
    }

    private void addNewCategory(String categoryName)
    {
        CategoryItem newCategoryItem = new CategoryItem(categoryName);
        categoriesList.add(newCategoryItem);
        category_adapter.notifyDataSetChanged();
        category_database.insertCategoryItem(categoryName);
    }

    private void initCategoryList()
    {
        categoriesList = new ArrayList<CategoryItem>();
    }

    protected void onDestroy()
    {
        category_database.close();
        super.onDestroy();
    }

    protected void onResume()
    {
        super.onResume();
        updateList();
    }
}