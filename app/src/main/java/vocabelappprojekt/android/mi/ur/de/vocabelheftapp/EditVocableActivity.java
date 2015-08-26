package vocabelappprojekt.android.mi.ur.de.vocabelheftapp;

/**
 * Created by S�meyye on 18.08.2015.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class EditVocableActivity extends AppCompatActivity{

    private Spinner spinner_original_language, spinner_translation_language;
    private Button button_sammlungen, button_speichern;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_vocable);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        initUI();
       // initDB();
    }

    private void initUI() {
        button_sammlungen = (Button) findViewById(R.id.button_category);
        button_speichern = (Button) findViewById(R.id.button_save);

        button_sammlungen.setClickable(false);

        button_speichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MyVocableListActivity.class);
                startActivity(i);
            }
        });
    }


}
