package esadrcanfer.us.alumno.autotesting;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class NotesAdapter extends BaseAdapter {
    HashMap<Integer, String> mIdMap = new HashMap<Integer, String>();
    private Context context;
    HashMap<Integer, String> itemsMap = new HashMap<Integer, String>();
    HashMap<Integer, String> radsMap = new HashMap<Integer, String>();
    HashMap<Integer, Boolean> selected1Map = new HashMap<Integer, Boolean>();
    HashMap<Integer, Boolean> selected2Map = new HashMap<Integer, Boolean>();
    HashMap<Integer, Boolean> selected3Map = new HashMap<Integer, Boolean>();
    HashMap<Integer, Boolean> switchMap = new HashMap<>();




    public NotesAdapter(Context context, List<String> objects, List<String> items, List<String> rads, List<Boolean> selectedOption1, List<Boolean> selectedOption2, List<Boolean> selectedOption3, List<Boolean> selectedSwitches) {
        this.context = context;
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(i, objects.get(i));
            itemsMap.put(i, items.get(i));
            radsMap.put(i, rads.get(i));
            selected1Map.put(i, selectedOption1.get(i));
            selected2Map.put(i, selectedOption2.get(i));
            selected3Map.put(i, selectedOption3.get(i));
            switchMap.put(i, selectedSwitches.get(i));
        }
    }

    @Override
    public int getCount() {
        return mIdMap.size();
    }

    @Override
    public Object getItem(int position) {
        return mIdMap.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.details_layout, null);
        }

        TextView noteText= (TextView)view.findViewById(R.id.noteText);
        noteText.setText(mIdMap.get(position));

        Button editButton= (Button)view.findViewById(R.id.editButton);

        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailsActivity.class);
                intent.putExtra("noteid", position);
                intent.putExtra("data", itemsMap.get(position));
                intent.putExtra("radioChosen", radsMap.get(position));
                intent.putExtra("boxChosen1", selected1Map.get(position));
                intent.putExtra("boxChosen2", selected2Map.get(position));
                intent.putExtra("boxChosen3", selected3Map.get(position));
                intent.putExtra("switchChosen", switchMap.get(position));
                context.startActivity(intent);
            }
        });

        return view;
    }
}
