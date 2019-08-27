package projetopsi.overcomeguildapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import projetopsi.overcomeguildapp.R;
import projetopsi.overcomeguildapp.models.PostsModel;

public class PostsListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<PostsModel> posts;

    public PostsListAdapter(Context context, ArrayList<PostsModel> posts){
        this.context = context;
        this.posts = posts;
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
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
            convertView = inflater.inflate(R.layout.item_post_list, null);
        }

        PostsListAdapter.ViewHolderList viewHolderList = (PostsListAdapter.ViewHolderList) convertView.getTag();

        if(viewHolderList == null){
            viewHolderList = new PostsListAdapter.ViewHolderList(convertView);
            convertView.setTag(viewHolderList);
        }

        viewHolderList.update(posts.get(position));
        return convertView;
    }

    public void refresh(ArrayList<PostsModel> posts){
        this.posts = posts;
        notifyDataSetChanged();
    }

    private class ViewHolderList{
        private TextView textViewTitle;
        private TextView textViewContent;
        private TextView textViewAuthor;

        public ViewHolderList(View convertView){
            textViewTitle = convertView.findViewById(R.id.item_posts_txtV_title);
            textViewAuthor = convertView.findViewById(R.id.item_posts_txtV_author);
            textViewContent = convertView.findViewById(R.id.item_posts_txtV_content);
        }

        public void update(PostsModel posts){
            textViewTitle.setText(posts.getTitle());
            textViewAuthor.setText(posts.getDisplayName());
            textViewContent.setText(posts.getContent());
        }
    }
}
