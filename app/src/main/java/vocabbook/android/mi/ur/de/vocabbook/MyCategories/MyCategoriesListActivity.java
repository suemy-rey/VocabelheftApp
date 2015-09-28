package vocabbook.android.mi.ur.de.vocabbook.MyCategories;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import vocabbook.android.mi.ur.de.vocabbook.R;

public class MyCategoriesListActivity extends AppCompatActivity
{
    public static final String CATEGORY_NAME_EXTRA = "category_name";

    private ArrayList<CategoryItem> categoriesList;
    private CategoryAdapter categoryAdapter;
    private CategoryDatabase categoryDB;

    private ListView categoriesView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_categories);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initDB();
        initCategoryList();
        initUI();
        updateList();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        updateList();
    }

    @Override
    protected void onDestroy()
    {
        categoryDB.close();
        super.onDestroy();
    }

    private void updateList()
    {
        categoriesList.clear();
        categoriesList.addAll(categoryDB.getAllCategoryItems());
        categoryAdapter.notifyDataSetChanged();
    }

    private void initDB()
    {
        categoryDB = new CategoryDatabase(getApplicationContext());
    }

    private void initUI()
    {
        initButton();
        initListAdapter();
        initListView();
    }

    private void initListAdapter()
    {
        categoriesView = (ListView) findViewById(R.id.category_list);
        categoryAdapter = new CategoryAdapter(getApplicationContext(), categoriesList);
        categoriesView.setAdapter(categoryAdapter);
    }

    private void initListView()
    {
        categoriesView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent gotoCategoryContents = new Intent(getApplicationContext(), CategoryContentsActivity.class);
                gotoCategoryContents.putExtra(CATEGORY_NAME_EXTRA, categoriesList.get(position).getName());
                startActivity(gotoCategoryContents);
            }
        });

        categoriesView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                removeCategoryAt(position);
                return true;
            }
        });
    }

    private void removeCategoryAt(int position)
    {
        CategoryItem categoryItem = categoriesList.get(position);
        categoryDB.deleteCategoryItem(String.valueOf(categoryItem.getId()));
        updateList();
    }

    private void initButton()
    {
        Button createNewCategoryButton = (Button) findViewById(R.id.name_category_add_button);
        createNewCategoryButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText categoryNameInput = (EditText) findViewById(R.id.name_category_input);
                String categoryName = categoryNameInput.getText().toString();

                if (!categoryName.equals(""))
                {
                    addNewCategory(categoryName);
                    categoryNameInput.setText("");

                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            }
        });
    }

    private void addNewCategory(String categoryName)
    {
        CategoryItem newCategoryItem = new CategoryItem(categoryName);
        categoriesList.add(newCategoryItem);
        categoryAdapter.notifyDataSetChanged();
        categoryDB.insertCategoryItem(categoryName);
    }

    private void initCategoryList()
    {
        categoriesList = new ArrayList<CategoryItem>();
    }
}