package vocabelappprojekt.android.mi.ur.de.vocabelheftapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sümeyye on 23.08.2015.
 */
public class CategoryAdapter extends ArrayAdapter<CategoryItem>  {



        private ArrayList<CategoryItem> categoryList;
        private Context context;

        public CategoryAdapter(Context context, ArrayList < CategoryItem > categoryItems) {
            super(context, R.layout.my_category_list_item, categoryItems);

            this.context = context;
            this.categoryList = categoryItems;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if(v == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.my_category_list_item,null);
            }

            Button nameOfGenus = (Button)v.findViewById(R.id.button_category_list_item);


            nameOfGenus.setText(categoryList.get(position).getName());



            return v;
        }


}
