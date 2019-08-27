package projetopsi.overcomeguildapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import projetopsi.overcomeguildapp.R;
import projetopsi.overcomeguildapp.models.OverpointModel;


public class OverpointListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<OverpointModel> overpoints;

    public OverpointListAdapter(Context context, ArrayList<OverpointModel> overpoints) {
        this.context = context;
        this.overpoints = overpoints;
    }

    @Override
    public int getCount() {
        return overpoints.size();
    }

    @Override
    public Object getItem(int position) {
        return overpoints.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_point_list, null);
        }

        ViewHolderList viewHolderList = (ViewHolderList) convertView.getTag();

        if (viewHolderList == null) {
            viewHolderList = new ViewHolderList(convertView);
            convertView.setTag(viewHolderList);
        }

        viewHolderList.update(overpoints.get(position));
        return convertView;
    }

    public void refresh(ArrayList<OverpointModel> overpoints) {
        this.overpoints = overpoints;
        notifyDataSetChanged();
    }

    private class ViewHolderList {
        private TextView textViewPlayer;
        private TextView textViewOp;
        private TextView textViewUp;
        private TextView textViewPriority;
        private TextView textViewAttendance;


        public ViewHolderList(View convertView) {
            textViewPlayer = convertView.findViewById(R.id.item_points_player);
            textViewOp = convertView.findViewById(R.id.item_points_op);
            textViewUp = convertView.findViewById(R.id.item_points_up);
            textViewPriority = convertView.findViewById(R.id.item_points_priority);
            textViewAttendance = convertView.findViewById(R.id.item_points_attendance);
        }

        public void update(OverpointModel overpoints) {
            textViewPlayer.setText(String.valueOf(overpoints.getDisplayName()));
            textViewOp.setText(String.valueOf(overpoints.getOp()));
            textViewUp.setText(String.valueOf(overpoints.getUp()));
            textViewPriority.setText(String.valueOf(overpoints.getPriority()));
            textViewAttendance.setText(String.valueOf(overpoints.getAttendance()));
        }
    }
}
