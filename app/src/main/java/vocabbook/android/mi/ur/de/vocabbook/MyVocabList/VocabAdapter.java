package vocabbook.android.mi.ur.de.vocabbook.MyVocabList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import vocabbook.android.mi.ur.de.vocabbook.R;

public class VocabAdapter extends ArrayAdapter<VocabItem>
{
    private ArrayList<VocabItem> vocabList;
    private Context context;

    private VocabDatabase vocabDB;
    private String[] languageArray;

    public VocabAdapter(Context context, ArrayList<VocabItem> vocabItems)
    {
        super(context, R.layout.my_vocab_item, vocabItems);

        this.context = context;
        this.vocabList = vocabItems;
        languageArray = context.getResources().getStringArray(R.array.language_arrays);
        vocabDB = new VocabDatabase(context);
        vocabDB.open();
    }

    public int positionOfLanguage(String language)
    {
        for (int i = 0; i < languageArray.length; i++)
        {
            if (language.equals(languageArray[i]))
            {
                return i;
            }
        }
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View v = convertView;

        if (v == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.my_vocab_item, null);
        }

        TextView newWord = (TextView) v.findViewById(R.id.textView_language1);
        TextView wordTranslation = (TextView) v.findViewById(R.id.textView_language2);
        newWord.setText(vocabList.get(position).getVocab());
        wordTranslation.setText(vocabList.get(position).getTranslation());


        final TextView expandedNotesView = (TextView) v.findViewById(R.id.textView_of_notes_long);
        final TextView openNotesPrompt = (TextView) v.findViewById(R.id.textView_of_notes_title);

        expandedNotesView.setText(vocabList.get(position).getNotes());


        Spinner wordLanguageSpinner = (Spinner) v.findViewById(R.id.spinner_language1);
        wordLanguageSpinner.setSelection(positionOfLanguage(vocabList.get(position).getVocabLanguage()), true);

        Spinner wordTranslationLanguageSpinner = (Spinner) v.findViewById(R.id.spinner_language2);
        wordTranslationLanguageSpinner.setSelection(positionOfLanguage(vocabList.get(position).getTranslationLanguage()), true);

        openNotesPrompt.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                toggleNotes(expandedNotesView, true);
                toggleNotes(openNotesPrompt, false);
            }
        });
        expandedNotesView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                toggleNotes(openNotesPrompt, false);
                toggleNotes(expandedNotesView, true);
            }
        });
        expandedNotesView.setVisibility(View.INVISIBLE);

        return v;
    }

    public void toggleNotes(View v, boolean isAnimating)
    {
        if (isAnimating)
        {
            if (v.isShown())
            {
                Slide.slideUp(v.getContext(), v);
                v.setVisibility(View.GONE);
            }
            else
            {
                v.setVisibility(View.VISIBLE);
                Slide.slideDown(v.getContext(), v);
            }
        }
        else
        {
            v.setVisibility(v.isShown() ? View.GONE : View.VISIBLE);
        }
    }
}