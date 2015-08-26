package vocabelappprojekt.android.mi.ur.de.vocabelheftapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sümeyye on 24.08.2015.
 */
public class VocAdapter extends ArrayAdapter<VocItem> {


        private ArrayList<VocItem> vocList;
        private Context context;

        public VocAdapter(Context context, ArrayList < VocItem > vocItems) {
        super(context, R.layout.my_vocable_list_item, vocItems);

        this.context = context;
        this.vocList = vocItems;
    }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.my_vocable_list_item,null);
        }

        TextView nameOfGenus_original = (TextView)v.findViewById(R.id.textView_language_original);
        TextView nameOfGenus_translation = (TextView) v.findViewById(R.id.textView_language_translation);

        nameOfGenus_original.setText(vocList.get(position).getOriginal_name());
        nameOfGenus_translation.setText(vocList.get(position).getTranslation_name());


        return v;
    }


    }
