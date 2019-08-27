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
import projetopsi.overcomeguildapp.models.NewsModel;

public class NewsListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<NewsModel> news;

    public NewsListAdapter(Context context, ArrayList<NewsModel> news){
        this.context = context;
        this.news = news;
    }

    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public Object getItem(int position) {
        return news.get(position);
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
            convertView = inflater.inflate(R.layout.item_news_list, null);
        }

        NewsListAdapter.ViewHolderList viewHolderList = (NewsListAdapter.ViewHolderList) convertView.getTag();

        if(viewHolderList == null){
            viewHolderList = new NewsListAdapter.ViewHolderList(convertView);
            convertView.setTag(viewHolderList);
        }

        viewHolderList.update(news.get(position));
        return convertView;
    }

    public void refresh(ArrayList<NewsModel> news){
        this.news = news;
        notifyDataSetChanged();
    }

    private class ViewHolderList{
        private TextView textViewTitle;
        private TextView textViewContent;

        public ViewHolderList(View convertView){
            textViewTitle = convertView.findViewById(R.id.textViewNewsTitle);
            textViewContent = convertView.findViewById(R.id.textViewNewsContent);
        }

        public void update(NewsModel news){
            textViewTitle.setText(news.getTitle());
            textViewContent.setText(news.getContent());
        }
    }
}
