package vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.R;

public class VocAdapter extends ArrayAdapter<VocItem> {
    private ArrayList<VocItem> vocList;
    private Context context;

    private VocDatabase vocDatabase;
    private String[] language_array;

    public VocAdapter(Context context, ArrayList<VocItem> vocItems) {
        super(context, R.layout.my_vocable_list_item, vocItems);

        this.context = context;
        this.vocList = vocItems;
        language_array = context.getResources().getStringArray(R.array.language_arrays);
        vocDatabase = new VocDatabase(context);
        vocDatabase.open();
    }

    public int positionOfLanguage(String language) {
        for (int i = 0; i < language_array.length; i++) {
            if (language.equals(language_array[i])) {
                return i;
            }
        }
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.my_vocable_list_item, null);
        }

        TextView firstVoc = (TextView) v.findViewById(R.id.textView_language1);
        TextView secondVoc = (TextView) v.findViewById(R.id.textView_language2);
        firstVoc.setText(vocList.get(position).getVocab());
        secondVoc.setText(vocList.get(position).getTranslation());


        final TextView textView_of_notes_long = (TextView) v.findViewById(R.id.textView_of_notes_long);
        final TextView title = (TextView) v.findViewById(R.id.textView_of_notes_title);

        textView_of_notes_long.setText(vocList.get(position).getNotes());


        Spinner spinner_language_one = (Spinner) v.findViewById(R.id.spinner_language1);
        spinner_language_one.setSelection(positionOfLanguage(vocList.get(position).getVocabLanguage()), true);

        Spinner spinner_language_two = (Spinner) v.findViewById(R.id.spinner_language2);
        spinner_language_two.setSelection(positionOfLanguage(vocList.get(position).getTranslationLanguage()), true);


        //Setting brief note is title, taking the first two words
      /**  if (note == null || note.isEmpty() || note.split(" ").length < 3) {
            title.setText(vocList.get(position).getNotes());
        } else {
            String[] noteArray = note.split(" ");
            title.setText(noteArray[0] + " " + noteArray[1] + "...");
        }**/
        //Toggle title and notes
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle_contents(textView_of_notes_long, true);
                toggle_contents(title, false);
            }
        });
        textView_of_notes_long.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle_contents(title, false);
                toggle_contents(textView_of_notes_long, true);
            }
        });
        textView_of_notes_long.setVisibility(View.INVISIBLE);
        return v;
    }

    /**
     * http://www.javacodegeeks.com/2013/09/android-expandablecollapsible-views.html
     * <p/>
     * onClick handler
     */
    public void toggle_contents(View v, boolean isAnimate) {
        if (isAnimate) {
            if (v.isShown()) {
                Slide.slide_up(v.getContext(), v);
                v.setVisibility(View.GONE);
            } else {
                v.setVisibility(View.VISIBLE);
                Slide.slide_down(v.getContext(), v);
            }
        } else {
            v.setVisibility(v.isShown()
                    ? View.GONE
                    : View.VISIBLE);
        }
    }
}