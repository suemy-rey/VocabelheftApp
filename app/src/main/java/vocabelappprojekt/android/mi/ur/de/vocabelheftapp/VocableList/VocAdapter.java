package vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.net.ResponseCache;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;

import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.EditVocableActivity;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.R;

public class VocAdapter extends ArrayAdapter<VocItem>
{
    private ArrayList<VocItem> vocList;
    private Context context;

    private VocDatabase vocDatabase;
    private String[] language_array;

    public VocAdapter(Context context, ArrayList<VocItem> vocItems)
    {
        super(context, R.layout.my_vocable_list_item, vocItems);

        this.context = context;
        this.vocList = vocItems;
        language_array = context.getResources().getStringArray(R.array.language_arrays);
        vocDatabase = new VocDatabase(context);
        vocDatabase.open();
    }

    public int positionOfLanguage(String firstLanguage)
    {
        for (int i = 0; i < language_array.length; i++)
        {
            if (firstLanguage.equals(language_array[i]))
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
            v = inflater.inflate(R.layout.my_vocable_list_item, null);
        }

        TextView firstVoc = (TextView) v.findViewById(R.id.textView_language1);
        TextView secondVoc = (TextView) v.findViewById(R.id.textView_language2);
        TextView textView_of_notes_long = (TextView) v.findViewById(R.id.textView_of_notes_long);
        TextView title = (TextView) v.findViewById(R.id.textView_of_notes_title);


        firstVoc.setText(vocList.get(position).getName());
        secondVoc.setText(vocList.get(position).getName_two());
        textView_of_notes_long.setVisibility(View.GONE);
        textView_of_notes_long.setText(vocList.get(position).getNotes());
        title.setText(vocList.get(position).getNotes());

        Spinner spinner_language_one = (Spinner) v.findViewById(R.id.spinner_language1);
        spinner_language_one.setSelection(positionOfLanguage(vocList.get(position).getSpinnerOfFirstLanguage()), true);

        Spinner spinner_language_two = (Spinner) v.findViewById(R.id.spinner_language2);
        spinner_language_two.setSelection(positionOfLanguage(vocList.get(position).getSpinnerOfSecondLanguage()), true);

        return v;
    }
}