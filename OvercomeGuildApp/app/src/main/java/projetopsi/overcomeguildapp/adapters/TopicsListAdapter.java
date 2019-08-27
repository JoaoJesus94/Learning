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
import projetopsi.overcomeguildapp.models.TopicsModel;


public class TopicsListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<TopicsModel> topics;

    public TopicsListAdapter(Context context, ArrayList<TopicsModel> topics) {
        this.context = context;
        this.topics = topics;
    }

    @Override
    public int getCount() {
        return topics.size();
    }

    @Override
    public Object getItem(int position) {
        return topics.get(position);
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
            convertView = inflater.inflate(R.layout.item_topic_posts_list, null);
        }

        ViewHolderList viewHolderList = (ViewHolderList) convertView.getTag();

        if(viewHolderList == null){
            viewHolderList = new ViewHolderList(convertView);
            convertView.setTag(viewHolderList);
        }

        viewHolderList.update(topics.get(position));
        return convertView;
    }

    public void refresh(ArrayList<TopicsModel> topics){
        this.topics = topics;
        notifyDataSetChanged();
    }

    private class ViewHolderList{
        private TextView textViewTitle;
        private TextView textViewContent;
        private TextView textViewAuthor;

        public ViewHolderList(View convertView){
            textViewTitle = convertView.findViewById(R.id.item_fForum_txtV_title);
            textViewAuthor = convertView.findViewById(R.id.item_fForum_txtV_author);
            textViewContent = convertView.findViewById(R.id.item_fForum_txtV_content);
        }

        public void update(TopicsModel topics){
            textViewTitle.setText(topics.getTopicName());
            textViewAuthor.setText(topics.getDisplayName());
            textViewContent.setText(topics.getTopicDescription());
        }
    }
}
