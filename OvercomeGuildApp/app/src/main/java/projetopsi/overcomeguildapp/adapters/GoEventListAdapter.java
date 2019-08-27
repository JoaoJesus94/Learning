package projetopsi.overcomeguildapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import projetopsi.overcomeguildapp.R;
import projetopsi.overcomeguildapp.models.UserProfileHasEventModel;

public class GoEventListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<UserProfileHasEventModel> goEvents;

    public GoEventListAdapter(Context context, ArrayList<UserProfileHasEventModel> goEvents) {
        this.context = context;
        this.goEvents = goEvents;
    }

    @Override
    public int getCount() {
        return goEvents.size();
    }

    @Override
    public Object getItem(int position) {
        return goEvents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null){
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_go_event_list, null);
        }

        GoEventListAdapter.ViewHolderList viewHolderList = (GoEventListAdapter.ViewHolderList) convertView.getTag();

        if(viewHolderList == null){
            viewHolderList = new GoEventListAdapter.ViewHolderList(convertView);
            convertView.setTag(viewHolderList);
        }

        viewHolderList.update(goEvents.get(position));
        return convertView;
    }

    public void refresh(ArrayList<UserProfileHasEventModel> goEvents){
        this.goEvents = goEvents;
        notifyDataSetChanged();
    }

    private class ViewHolderList{
        private TextView name;
        private TextView attending;
        private TextView role;

        public ViewHolderList(View convertView){
            name = convertView.findViewById(R.id.textViewGoEventDisplayName2);
            attending = convertView.findViewById(R.id.textViewGoEventAttending2);
            role = convertView.findViewById(R.id.textViewGoEventRole2);
        }

        public void update(UserProfileHasEventModel goEvent){
            name.setText(goEvent.getDisplayName());
            attending.setText(goEvent.getAttending());
            role.setText(goEvent.getRole());
        }
    }
}
