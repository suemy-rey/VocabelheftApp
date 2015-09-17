package vocabelappprojekt.android.mi.ur.de.vocabelheftapp.Category;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

import vocabelappprojekt.android.mi.ur.de.vocabelheftapp.R;

public class CategoryAdapter extends ArrayAdapter<CategoryItem>
{
    private ArrayList<CategoryItem> categoryList;
    private Context context;
    private OnButtonClicklistener onButtonClicklistener = null;

    public CategoryAdapter(Context context, ArrayList<CategoryItem> categoryItems, OnButtonClicklistener onButtonClicklistener)
    {
        super(context, R.layout.my_category_list_item, categoryItems);

        this.context = context;
        this.categoryList = categoryItems;
        this.onButtonClicklistener = onButtonClicklistener;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View v = convertView;

        if (v == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.my_category_list_item, null);
        }

        CategoryItem categoryItem = categoryList.get(position);
        if (categoryItem != null)
        {
            Button nameOfGenus = (Button) v.findViewById(R.id.button_category_list_item);
            nameOfGenus.setText(categoryItem.getName());
            nameOfGenus.setTag(position);
            nameOfGenus.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Log.d("hallo", "" + position);
                    if (onButtonClicklistener != null)
                    {
                        onButtonClicklistener.onButtonClick(position);
                    }
                }

            });
        }

        return v;
    }
}