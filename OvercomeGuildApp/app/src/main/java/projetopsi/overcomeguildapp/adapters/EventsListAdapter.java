package projetopsi.overcomeguildapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import projetopsi.overcomeguildapp.R;
import projetopsi.overcomeguildapp.models.EventModel;

public class EventsListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<EventModel> events;

    public EventsListAdapter(Context context, ArrayList<EventModel> events) {
        this.context = context;
        this.events = events;
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null){
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_events_list, null);
        }

        EventsListAdapter.ViewHolderList viewHolderList = (EventsListAdapter.ViewHolderList) convertView.getTag();

        if(viewHolderList == null){
            viewHolderList = new EventsListAdapter.ViewHolderList(convertView);
            convertView.setTag(viewHolderList);
        }

        viewHolderList.update(events.get(position));
        return convertView;
    }

    public void refresh(ArrayList<EventModel> events){
        this.events = events;
        notifyDataSetChanged();
    }

    private class ViewHolderList{
        private TextView eventName;
        private TextView date;
        private TextView category;
        private TextView description;

        public ViewHolderList(View convertView){
            eventName = convertView.findViewById(R.id.textViewEventName1);
            date = convertView.findViewById(R.id.textViewEventDate2);
            category = convertView.findViewById(R.id.textViewEventCategory2);
            description = convertView.findViewById(R.id.textViewEventDescription2);
        }

        public void update(EventModel event){
            eventName.setText(event.getEventName());
            date.setText(event.getDate());
            category.setText(event.getCategory());
            description.setText(event.getDescription());
        }
    }
}
