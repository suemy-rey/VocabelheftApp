package vocabelappprojekt.android.mi.ur.de.vocabelheftapp.MyCategories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.R;

public class CategoryAdapter extends ArrayAdapter<CategoryItem>
{
    private ArrayList<CategoryItem> categoryList;
    private Context context;

    public CategoryAdapter(Context context, ArrayList<CategoryItem> categoryItems)
    {
        super(context, R.layout.my_categories_item, categoryItems);

        this.context = context;
        this.categoryList = categoryItems;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View v = convertView;

        if (v == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.my_categories_item, null);
        }

        CategoryItem categoryItem = categoryList.get(position);

        if (categoryItem != null)
        {
            final TextView categoryText = (TextView) v.findViewById(R.id.button_category_list_item);
            categoryText.setText(categoryItem.getName());
            categoryText.setTag(position);
        }

        return v;
    }
}