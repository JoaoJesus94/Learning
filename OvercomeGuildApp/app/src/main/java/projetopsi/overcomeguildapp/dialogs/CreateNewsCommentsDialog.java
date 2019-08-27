package projetopsi.overcomeguildapp.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import projetopsi.overcomeguildapp.R;
import projetopsi.overcomeguildapp.adapters.NewsCommentsListAdapter;
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

public class CreateNewsCommentsDialog extends DialogFragment implements Listener, View.OnClickListener {

    private ListView newsCommentsList;
    private String auth_key;
    private String id_profile;
    private String id_news;
    private EditText comment;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_news_comments_dialog, container, false);

        comment = view.findViewById(R.id.editTextNewsCommet);
        newsCommentsList = view.findViewById(R.id.listViewNewsComments);
        Button create = view.findViewById(R.id.btnNewsComment);
        create.setOnClickListener(this);


        Bundle args = getArguments();

        assert args != null;
        id_profile = args.getString("id_profile");
        auth_key = args.getString("auth_key");
        id_news = args.getString("id_news");


        Singleton.getInstance(view.getContext()).setListener(this);
        Singleton.getInstance(view.getContext()).apiRequestGetNewsComments(view.getContext(), auth_key, id_news);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnNewsComment){
            if(TextUtils.isEmpty(comment.getText()))
            {
                Toast.makeText(getContext(), "Empty Field", Toast.LENGTH_SHORT).show();
                return;
            }

            if(comment.getText().length() > 100){
                Toast.makeText(getContext(), "Fild is to long", Toast.LENGTH_SHORT).show();
                return;
            }

            NewsCommentsModel newsComments = new NewsCommentsModel();

            newsComments.setContent(comment.getText().toString());
            newsComments.setUser_id(Integer.parseInt(id_profile));

            Singleton.getInstance(getContext()).setListener(this);
            Singleton.getInstance(getContext()).apiRequestAddNewComment(getContext(), auth_key, newsComments, id_news);
        }
    }

    @Override
    public void onRefreshNewsComments(ArrayList<NewsCommentsModel> newsComments) {
        NewsCommentsListAdapter newsCommentsAdapter = new NewsCommentsListAdapter(getContext(), newsComments);
        newsCommentsList.setAdapter(newsCommentsAdapter);
        newsCommentsAdapter.refresh(newsComments);
        newsCommentsList.setSelection(newsCommentsAdapter.getCount() - 1);
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
    public void onRefreshNews(ArrayList<NewsModel> news) {

    }
}
