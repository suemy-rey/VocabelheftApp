package vocabelappprojekt.android.mi.ur.de.vocabelheftapp;

import android.app.Activity;
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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_category_list);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        initList();
        initUI();

    }


    private void initUI() {
        initButton();
        initListView();
        initListAdapter();
    }

    private void initListAdapter() {
        ListView list = (ListView) findViewById(R.id.sammlungen_list);
        category_adapter = new CategoryAdapter(this, categoryNames);
        list.setAdapter(category_adapter);

    }

    private void initListView() {
        ListView list = (ListView) findViewById(R.id.sammlungen_list);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {

                return false;
            }
        });
    }

    private void initButton() {
        Button addButton = (Button) findViewById(R.id.todo_edit_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit = (EditText) findViewById(R.id.sammlungen_input);
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

            CategoryItem newToDoItem = new CategoryItem(names);
            categoryNames.add(newToDoItem);
            category_adapter.notifyDataSetChanged();
        }

    }

    private void initList() {
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


}
