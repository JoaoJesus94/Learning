package projetopsi.overcomeguildapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import projetopsi.overcomeguildapp.R;
import projetopsi.overcomeguildapp.models.PostsCommentsModel;

public class PostsCommentsListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<PostsCommentsModel> postsComments;

    public PostsCommentsListAdapter(Context context, ArrayList<PostsCommentsModel> postsComments){
        this.context = context;
        this.postsComments = postsComments;
    }

    @Override
    public int getCount() {
        return postsComments.size();
    }

    @Override
    public Object getItem(int position) {
        return postsComments.get(position);
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
            convertView = inflater.inflate(R.layout.item_posts_news_comment_list, null);
        }

        PostsCommentsListAdapter.ViewHolderList viewHolderList = (PostsCommentsListAdapter.ViewHolderList) convertView.getTag();

        if(viewHolderList == null){
            viewHolderList = new PostsCommentsListAdapter.ViewHolderList(convertView);
            convertView.setTag(viewHolderList);
        }

        viewHolderList.update(postsComments.get(position));
        return convertView;
    }

    public void refresh(ArrayList<PostsCommentsModel> postsComments){
        this.postsComments = postsComments;
        notifyDataSetChanged();
    }

    private class ViewHolderList{
        private TextView textViewContent;
        private TextView textViewDisplayName;

        public ViewHolderList(View convertView){
            textViewContent = convertView.findViewById(R.id.textViewPostsNewsComment);
            textViewDisplayName = convertView.findViewById(R.id.textViewPostsNewsCommentDisplayName);
        }

        public void update(PostsCommentsModel posts){
            textViewContent.setText(posts.getContent());
            textViewDisplayName.setText(posts.getDisplayName());
        }
    }
}
