package vocabelappprojekt.android.mi.ur.de.vocabelheftapp.Category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.R;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList.VocDatabase;
import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.VocableList.VocItem;

public class DetailCategoriesAdapter extends ArrayAdapter<VocItem>
{
    private ArrayList<VocItem> vocList;
    private Context context;
    private VocDatabase vocDatabase;

    public DetailCategoriesAdapter(Context context, ArrayList<VocItem> vocItems)
    {
        super(context, R.layout.my_vocable_list_item, vocItems);
        this.context = context;
        this.vocList = vocItems;
        vocDatabase = new VocDatabase(context);
        vocDatabase.open();
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

        TextView original_textView = (TextView) v.findViewById(R.id.textView_original_language);
        TextView translation_textView = (TextView) v.findViewById(R.id.textView_translation_language);
        TextView notes = (TextView) v.findViewById(R.id.textView_of_notes);

        original_textView.setText(vocList.get(position).getName());
        translation_textView.setText(vocList.get(position).getName_two());
        notes.setText(vocList.get(position).getNotes());

        Spinner spinner_language_one = (Spinner) v.findViewById(R.id.spinner_original_language);
        Spinner spinner_language_two = (Spinner) v.findViewById(R.id.spinner_translation_language);

        return v;
    }
}