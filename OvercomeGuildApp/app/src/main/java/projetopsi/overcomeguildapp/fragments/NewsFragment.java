package projetopsi.overcomeguildapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Objects;

import projetopsi.overcomeguildapp.dialogs.CreateNewsCommentsDialog;
import projetopsi.overcomeguildapp.R;
import projetopsi.overcomeguildapp.adapters.NewsListAdapter;
import projetopsi.overcomeguildapp.listener.Listener;
import projetopsi.overcomeguildapp.models.ApplyModel;
import projetopsi.overcomeguildapp.models.CharacterModel;
import projetopsi.overcomeguildapp.models.EventModel;
import projetopsi.overcomeguildapp.models.NewsCommentsModel;
import projetopsi.overcomeguildapp.models.NewsModel;
import projetopsi.overcomeguildapp.models.OverpointModel;
import projetopsi.overcomeguildapp.models.PostsCommentsModel;
import projetopsi.overcomeguildapp.models.PostsModel;
import projetopsi.overcomeguildapp.models.RosterModel;
import projetopsi.overcomeguildapp.models.SubjectModel;
import projetopsi.overcomeguildapp.models.TopicsModel;
import projetopsi.overcomeguildapp.models.UserModel;
import projetopsi.overcomeguildapp.models.UserProfileHasEventModel;
import projetopsi.overcomeguildapp.models.UserProfileModel;
import projetopsi.overcomeguildapp.singleton.Singleton;


public class NewsFragment extends Fragment implements Listener {
    private ListView listViewNews;
    private String auth_key;
    private String id_profile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_news,container,false);

        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        listViewNews = view.findViewById(R.id.listViewNews);

        assert getArguments() != null;
        auth_key = getArguments().getString("auth_key");
        id_profile = getArguments().getString("profile_id");

        Singleton.getInstance(view.getContext()).setListener(this);
        Singleton.getInstance(view.getContext()).apiRequestGetNews(view.getContext(), auth_key);

        listViewNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long idd) {
                NewsModel news = (NewsModel) parent.getItemAtPosition(position);

                String idNews = String.valueOf(news.getId());

                Bundle args = new Bundle();

                args.putString("id_news", idNews);
                args.putString("auth_key", auth_key);
                args.putString("id_profile", id_profile);

                FragmentManager fm = getFragmentManager();
                CreateNewsCommentsDialog createNewsCommentsDialog = new CreateNewsCommentsDialog();
                createNewsCommentsDialog.setArguments(args);
                createNewsCommentsDialog.setTargetFragment(NewsFragment.this, 1);
                assert fm != null;
                createNewsCommentsDialog.show(fm, "TAG");
            }
        });

        return view;
    }

    public static NewsFragment newInstance(String auth_key, String profile_id){
        NewsFragment newsFragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString("auth_key", auth_key);
        args.putString("profile_id", profile_id);
        newsFragment.setArguments(args);
        return newsFragment;
    }

    @Override
    public void onRefreshNews(ArrayList<NewsModel> news) {
        NewsListAdapter newsListAdapter = new NewsListAdapter(getContext(), news);
        listViewNews.setAdapter(newsListAdapter);
        newsListAdapter.refresh(news);
        Singleton.getInstance(getContext()).setListViewHeightBasedOnItems(listViewNews);
    }

    @Override
    public void onRefreshUser(UserModel userModel) {

    }

    @Override
    public void onUpdateUserProfile() {

    }

    @Override
    public void onRefreshUserProfileList(UserProfileModel userProfile) {

    }

    @Override
    public void onRefreshCharacterList(ArrayList<CharacterModel> characterList) {

    }

    @Override
    public void onRefreshApply(ApplyModel apply) {

    }

    @Override
    public void onRefreshRoster(ArrayList<UserProfileModel> userProfileList) {

    }

    @Override
    public void onRefreshRosterProfile(RosterModel roster) {

    }

    @Override
    public void onRefreshOPTable(ArrayList<OverpointModel> overpoint) {

    }

    @Override
    public void onRefreshEvents(ArrayList<EventModel> events) {

    }

    @Override
    public void onRefreshUserProfileHasEvents(ArrayList<UserProfileHasEventModel> UserProfileHasEvents) {

    }

    @Override
    public void onRefreshSubjects(ArrayList<SubjectModel> subjects) {

    }

    @Override
    public void onRefreshTopics(ArrayList<TopicsModel> topics) {

    }

    @Override
    public void onRefreshPosts(ArrayList<PostsModel> posts) {

    }

    @Override
    public void onRefreshPostsComments(ArrayList<PostsCommentsModel> postsComments) {

    }

    @Override
    public void onRefreshNewsComments(ArrayList<NewsCommentsModel> newsComments) {

    }
}
