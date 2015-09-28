package vocabbook.android.mi.ur.de.vocabbook.MyVocabTest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import vocabbook.android.mi.ur.de.vocabbook.Log.Log;
import vocabbook.android.mi.ur.de.vocabbook.MyCategories.CategoryDatabase;
import vocabbook.android.mi.ur.de.vocabbook.MyVocabList.VocabDatabase;
import vocabbook.android.mi.ur.de.vocabbook.R;

public class VocabTestSetupActivity extends AppCompatActivity
{
    public final static String CATEGORY_NAME_EXTRA = "category_name";

    private final static String NO_CATEGORIES_FEEDBACK = "Keine Sammlung zu Abfrage verfügbar!";
    private final static String NO_VOCAB_FEEDBACK = "Es gibt keine Vokabeleinträge!";
    private final static String EMPTY_CATEGORY_TOAST = "Die Sammlung ist Leer.\nTest für alle Vokabeln wird gestartet!";
    private final static String TEST_MODE = "Modus: ";
    private final static String VOCAB_TEST = "Vokabeln abfragen";
    private final static String TRANSLATE_TEST = "Übersetzung abfragen";

    private Button testOutButton;
    private Button testModeButton;
    private Spinner categoryChooser;

    private VocabDatabase vocabDB;
    private CategoryDatabase categoryDB;
    private List<String> categoryList;

    private String currentModeText = TEST_MODE + VOCAB_TEST;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setDefaultTestMode();
        initDB();
        initUI();
    }

    @Override
    protected void onDestroy()
    {
        vocabDB.close();
        categoryDB.close();
        super.onDestroy();
    }

    private void initUI()
    {
        setContentView(R.layout.activity_vocab_test_setup);

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
                //Don't start vocab test if no vocab available. Test all vocab if category empty
                if (!vocabDB.getAllVocItems().isEmpty())
                {
                    Intent startTestOut = new Intent(getApplicationContext(), VocabTestActivity.class);
                    String categoryToTest = "";
                    if (categoryChooser.getSelectedItem() != null)
                    {
                        categoryToTest = categoryChooser.getSelectedItem().toString();
                        if (vocabDB.getVocItemsFromCategory(categoryToTest).isEmpty())
                        {
                            Toast emptyCategoryToast = Toast.makeText(getApplicationContext(), EMPTY_CATEGORY_TOAST, Toast.LENGTH_SHORT);
                            emptyCategoryToast.show();
                        }
                    }
                    else
                    {
                        Toast noCategoriesToast = Toast.makeText(getApplicationContext(), NO_CATEGORIES_FEEDBACK, Toast.LENGTH_SHORT);
                        noCategoriesToast.show();
                    }
                    startTestOut.putExtra(CATEGORY_NAME_EXTRA, categoryToTest);
                    startActivity(startTestOut);
                }
                else
                {
                    Toast noVocabToast = Toast.makeText(getApplicationContext(), NO_VOCAB_FEEDBACK, Toast.LENGTH_SHORT);
                    noVocabToast.show();
                }
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
        vocabDB = new VocabDatabase(this);
        vocabDB.open();

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