package android.geekbrains.a2l1_androidmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private List<Integer> elements  = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;

    ListViewAdapter(Context context) {
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        elements.add(1);
        elements.add(2);
        elements.add(3);
    }

    @Override
    public int getCount() {
        return elements.size();
    }

    @Override
    public Object getItem(int position) {
        return elements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    void addNewElement() {
        elements.add(elements.size() + 1);
        notifyDataSetChanged();
    }

    void editElement() {
        if(elements.size() > 0) {
            elements.set(elements.size() - 1, 100);
            notifyDataSetChanged();
        }
    }

    void deleteElement() {
        if(elements.size() > 0) {
            elements.remove(elements.size() - 1);
            notifyDataSetChanged();
        }
    }

    void clearList() {
        elements.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_view_item, parent, false);
        }
        String text = context.getString(R.string.element_number) + " " +  elements.get(position);
        TextView textView = convertView.findViewById(R.id.element_text);
        textView.setText(text);

        return convertView;
    }
}
