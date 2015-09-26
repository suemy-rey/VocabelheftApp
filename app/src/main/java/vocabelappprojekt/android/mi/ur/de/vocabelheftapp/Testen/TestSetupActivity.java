package vocabelappprojekt.android.mi.ur.de.vocabelheftapp.Testen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.Category.CategoryDatabase;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.Log.Log;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.R;

public class TestSetupActivity extends AppCompatActivity
{
    public final static String CATEGORY_NAME_EXTRA = "category_name";
    private final static String TEST_MODE = "Modus: ";
    private final static String VOCAB_TEST = "Vokabeln abfragen";
    private final static String TRANSLATE_TEST = "Übersetzung abfragen";

    private Button testOutButton;
    private Button testModeButton;
    private Spinner categoryChooser;

    private CategoryDatabase categoryDB;
    private List<String> categoryList;

    private String currentModeText = TEST_MODE + VOCAB_TEST;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setDefaultTestMode();
        initDB();
        initUI();
    }

    protected void onDestroy()
    {
        categoryDB.close();
        super.onDestroy();
    }

    private void initUI()
    {
        setContentView(R.layout.test_setup_layout);

        initButton();
        initSpinner();
    }

    private void initButton()
    {
        testOutButton = (Button) findViewById(R.id.start_test_button);
        testOutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent startTestOut = new Intent(getApplicationContext(), TestActivity.class);
                startTestOut.putExtra(CATEGORY_NAME_EXTRA, categoryChooser.getSelectedItem().toString());
                startActivity(startTestOut);
            }
        });

        testModeButton = (Button) findViewById(R.id.set_test_mode_button);
        testModeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switchTestMode();
            }
        });
    }

    private void initSpinner()
    {
        categoryChooser = (Spinner) findViewById(R.id.category_to_test_spinner);
        categoryList = categoryDB.getAllLabels();
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryList);
        categoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryChooser.setAdapter(categoriesAdapter);
    }

    private void initDB()
    {
        categoryDB = new CategoryDatabase(this);
        categoryDB.open();
    }

    private void setDefaultTestMode()
    {
        if (!VocabTestMode.isVocabTest())
        {
            VocabTestMode.changeMode(VocabTestMode.testMode.VOCAB_TEST);
        }
    }

    private void switchTestMode()
    {
        switch (VocabTestMode.currentMode)
        {
            case VOCAB_TEST:
                VocabTestMode.changeMode(VocabTestMode.testMode.TRANSLATE_TEST);
                currentModeText = TEST_MODE + TRANSLATE_TEST;
                break;
            case TRANSLATE_TEST:
                VocabTestMode.changeMode(VocabTestMode.testMode.VOCAB_TEST);
                currentModeText = TEST_MODE + VOCAB_TEST;
                break;
            default:
                break;
        }

        testModeButton.setText(currentModeText);
    }
}
