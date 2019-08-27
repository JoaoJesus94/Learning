package projetopsi.overcomeguildapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import projetopsi.overcomeguildapp.R;
import projetopsi.overcomeguildapp.models.UserProfileModel;


public class UserProfileListRosterAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<UserProfileModel> userProfiles;

    public UserProfileListRosterAdapter(Context context, ArrayList<UserProfileModel> userProfiles) {
        this.context = context;
        this.userProfiles = userProfiles;
    }

    @Override
    public int getCount() {
        return userProfiles.size();
    }

    @Override
    public Object getItem(int position) {
        return userProfiles.get(position);
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
            convertView = inflater.inflate(R.layout.item_roster_userprofile_list, null);
        }

        ViewHolderList viewHolderList = (ViewHolderList) convertView.getTag();

        if(viewHolderList == null){
            viewHolderList = new ViewHolderList(convertView);
            convertView.setTag(viewHolderList);
        }

        viewHolderList.update(userProfiles.get(position));
        return convertView;
    }

    public void refresh(ArrayList<UserProfileModel> userProfiles){
        this.userProfiles = userProfiles;
        notifyDataSetChanged();
    }

    private class ViewHolderList{
        private TextView textViewPlayer;
        private TextView textViewRank;
        private TextView textViewRole;

        public ViewHolderList(View convertView){
            textViewPlayer = convertView.findViewById(R.id.item_roster_userProfile_player);
            textViewRank = convertView.findViewById(R.id.item_roster_userProfile_rank);
            textViewRole = convertView.findViewById(R.id.item_roster_userProfile_role);
        }

        public void update(UserProfileModel userProfile){
            textViewPlayer.setText(userProfile.getDisplayName());
            textViewRank.setText(userProfile.getRank());
            textViewRole.setText(userProfile.getRole());
        }
    }
}
