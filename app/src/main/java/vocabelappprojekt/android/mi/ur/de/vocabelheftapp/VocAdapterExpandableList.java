package vocabelappprojekt.android.mi.ur.de.vocabelheftapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sümeyye on 26.08.2015.
 */
public class VocAdapterExpandableList extends BaseExpandableListAdapter {

    private Context context;
    private List<VocItem> voc_list;
    private HashMap<VocItem, List<VocItem>> voc_list_child;

    public VocAdapterExpandableList(Context context, List <VocItem> voc_list, HashMap<VocItem, List<VocItem>> voc_list_child) {
        this.context = context;
        this.voc_list = voc_list;
        this.voc_list_child = voc_list_child;
    }

    @Override
    public int getGroupCount() {
        return this.voc_list.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return this.voc_list_child.get(this.voc_list.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return this.voc_list_child.size();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.voc_list_child.get(this.voc_list.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int position, boolean b, View view, ViewGroup viewGroup) {

        View v = view;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.my_vocable_list_item, null);
        }

        TextView nameOfGenus = (TextView) v.findViewById(R.id.textView_language1);
        TextView nameOfGenusTwo = (TextView) v.findViewById(R.id.textView_language2);


        nameOfGenus.setText(voc_list.get(position).getName());
        nameOfGenusTwo.setText(voc_list.get(position).getName_two());

        Spinner spinner_language_one = (Spinner) v.findViewById(R.id.spinner_language1);
        Spinner spinner_language_two = (Spinner) v.findViewById(R.id.spinner_language2);


        return v;
    }

    @Override
    public View getChildView(int position, int i1, boolean b, View view, ViewGroup viewGroup) {
        View v = view;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.my_vocable_list_item_child, null);
        }

        TextView nameOfNotes = (TextView) v.findViewById(R.id.textView_child_of_expandable_list);



        nameOfNotes.setText(voc_list.get(position).getName());

        CheckBox checkBox_category = (CheckBox) v.findViewById(R.id.checkBox_for_categories);
        checkBox_category.setText("hallo");

        return v;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
