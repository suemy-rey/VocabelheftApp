package vocabelappprojekt.android.mi.ur.de.vocabelheftapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sümeyye on 24.08.2015.
 */
public class VocAdapter extends ArrayAdapter<VocItem> {


    private ArrayList<VocItem> vocList;
    private Context context;

    public VocAdapter(Context context, ArrayList<VocItem> vocItems) {
        super(context, R.layout.my_vocable_list_item, vocItems);

        this.context = context;
        this.vocList = vocItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.my_vocable_list_item, null);
        }

        TextView firstVoc = (TextView) v.findViewById(R.id.textView_language1);
        TextView secondVoc= (TextView) v.findViewById(R.id.textView_language2);
        TextView notes = (TextView) v.findViewById(R.id.textView_of_notes);

        firstVoc.setText(vocList.get(position).getName());
        secondVoc.setText(vocList.get(position).getName_two());
        notes.setText(vocList.get(position).getNotes());

        Spinner spinner_language_one = (Spinner) v.findViewById(R.id.spinner_language1);
        Spinner spinner_language_two = (Spinner) v.findViewById(R.id.spinner_language2);



        return v;
    }


}