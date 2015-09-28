package vocabbook.android.mi.ur.de.vocabbook.MyVocabTest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import vocabbook.android.mi.ur.de.vocabbook.R;
import vocabbook.android.mi.ur.de.vocabbook.MyVocabList.VocabDatabase;
import vocabbook.android.mi.ur.de.vocabbook.MyVocabList.VocabItem;

public class VocabTestActivity extends AppCompatActivity
{
    private final static String CORRECT_FEEDBACK = " ist Richtig!";
    private final static String WRONG_FEEDBACK = " is Falsch!";
    private final static String SCORE_TEXT = "Punkte: ";
    private final static String ERRORS_TEXT = "Fehler: ";
    private final static String LANGUAGE_TEXT = "Antworte in folgender Sprache:\n";

    private final static Random r = new Random();

    private TextView scoreView;
    private TextView errorsView;
    private TextView vocabView;
    private TextView languageView;
    private EditText answerInput;
    private Button enterButton;

    private VocabDatabase vocabDB;
    private ArrayList<VocabItem> vocabList;

    private VocabItem previousVocab;
    private VocabItem currentVocab;
    private Toast shortToast;

    private String categoryName;
    private String enteredAnswer;

    private int score;
    private int errors;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocab_test);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        categoryName = getIntent().getExtras().getString(VocabTestSetupActivity.CATEGORY_NAME_EXTRA);

        initUI();
        initDB();
        initVocabs();

        startVocabTest();
    }

    protected void onDestroy()
    {
        vocabDB.close();
        super.onDestroy();
    }

    private void initUI()
    {
        scoreView = (TextView) findViewById(R.id.test_score_view);
        errorsView = (TextView) findViewById(R.id.test_errors_view);

        vocabView = (TextView) findViewById(R.id.vocab_view);
        languageView = (TextView) findViewById(R.id.requested_language_view);

        answerInput = (EditText) findViewById(R.id.answer_input);
        enterButton = (Button) findViewById(R.id.enter_answer_button);

        enterButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                enteredAnswer = answerInput.getText().toString().toLowerCase();

                if (VocabTestMode.isVocabTest())
                {
                    String currentVocabTestAnswer = currentVocab.getTranslation().toLowerCase();
                    checkAnswerForVocabTest(currentVocabTestAnswer);
                }
                else if (VocabTestMode.isTranslateTest())
                {
                    String currentTranslationTestAnswer = currentVocab.getVocab().toLowerCase();
                    checkAnswerForTranslationTest(currentTranslationTestAnswer);
                }

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        });
    }

    private void initDB()
    {
        vocabDB = new VocabDatabase(getApplicationContext());
        vocabDB.open();
    }

    private void initVocabs()
    {
        vocabList = new ArrayList<VocabItem>();

        vocabList.addAll(vocabDB.getVocItemsFromCategory(categoryName));
    }

    private void checkAnswerForVocabTest(String currentVocabAnswer)
    {
        if (currentVocabAnswer.equals(enteredAnswer))
        {
            correctAnswer();
        }
        else
        {
            wrongAnswer();
        }
    }

    private void checkAnswerForTranslationTest(String currentTranslationAnswer)
    {
        if (currentTranslationAnswer.equals(enteredAnswer))
        {
            correctAnswer();
        }
        else
        {
            wrongAnswer();
        }
    }

    private void startVocabTest()
    {
        setNewVocabToTest();
    }

    private void setNewVocabToTest()
    {
        String currentVocabName = "";
        String languageToTranslateTo = "";

        if (currentVocab != null)
        {
            previousVocab = currentVocab;
        }

        int randomVocabIndex = r.nextInt(vocabList.size());
        currentVocab = vocabList.get(randomVocabIndex);

        if (vocabList.size() > 1)
        {
            while (currentVocab.equals(previousVocab))
            {
                currentVocab = vocabList.get(r.nextInt(vocabList.size()));
            }
        }

        if (VocabTestMode.isVocabTest())
        {
            currentVocabName = currentVocab.getVocab();
            languageToTranslateTo = currentVocab.getTranslationLanguage();
        }
        else if (VocabTestMode.isTranslateTest())
        {
            currentVocabName = currentVocab.getTranslation();
            languageToTranslateTo = currentVocab.getVocabLanguage();
        }


        vocabView.setText(currentVocabName);
        languageView.setText(LANGUAGE_TEXT + languageToTranslateTo);

        answerInput.setText("");
    }

    private void correctAnswer()
    {
        displayToastMessage(enteredAnswer + CORRECT_FEEDBACK);
        addToScore();
        setNewVocabToTest();
    }

    private void wrongAnswer()
    {
        displayToastMessage(enteredAnswer + WRONG_FEEDBACK);
        addToErrors();
        setNewVocabToTest();
    }

    private void addToScore()
    {
        score++;
        scoreView.setText(SCORE_TEXT + score);
    }

    private void addToErrors()
    {
        score--;
        errors++;

        scoreView.setText(SCORE_TEXT + score);
        errorsView.setText(ERRORS_TEXT + errors);
    }

    private void displayToastMessage(String message)
    {
        if (shortToast != null)
        {
            shortToast.cancel();
        }

        shortToast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        shortToast.show();
    }
}