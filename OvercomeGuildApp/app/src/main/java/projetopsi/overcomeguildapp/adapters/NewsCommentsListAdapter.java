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
import projetopsi.overcomeguildapp.models.NewsCommentsModel;

public class NewsCommentsListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<NewsCommentsModel> newsComments;

    public NewsCommentsListAdapter(Context context, ArrayList<NewsCommentsModel> newsComments){
        this.context = context;
        this.newsComments = newsComments;
    }

    @Override
    public int getCount() {
        return newsComments.size();
    }

    @Override
    public Object getItem(int position) {
        return newsComments.get(position);
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
            convertView = inflater.inflate(R.layout.item_posts_news_comment_list, null);
        }

        NewsCommentsListAdapter.ViewHolderList viewHolderList = (NewsCommentsListAdapter.ViewHolderList) convertView.getTag();

        if(viewHolderList == null){
            viewHolderList = new NewsCommentsListAdapter.ViewHolderList(convertView);
            convertView.setTag(viewHolderList);
        }

        viewHolderList.update(newsComments.get(position));
        return convertView;
    }

    public void refresh(ArrayList<NewsCommentsModel> newsComments){
        this.newsComments = newsComments;
        notifyDataSetChanged();
    }

    private class ViewHolderList{
        private TextView textViewContent;
        private TextView textViewDisplayName;

        public ViewHolderList(View convertView){
            textViewContent = convertView.findViewById(R.id.textViewPostsNewsComment);
            textViewDisplayName = convertView.findViewById(R.id.textViewPostsNewsCommentDisplayName);
        }

        public void update(NewsCommentsModel news){
            textViewContent.setText(news.getContent());
            textViewDisplayName.setText(news.getdisplayName());
        }
    }
}
