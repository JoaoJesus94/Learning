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
import projetopsi.overcomeguildapp.adapters.PostsCommentsListAdapter;
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


public class CreatePostsCommentDialog extends DialogFragment implements Listener, View.OnClickListener {

    private EditText comment;
    private String auth_key;
    private String post_id;
    private String id_profile;
    private ListView listViewPostsComments;
    private PostsCommentsListAdapter postsCommentsListAdapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_posts_comment_dialog, container, false);

        listViewPostsComments = view.findViewById(R.id.listViewPostComment);
        comment = view.findViewById(R.id.editTextPostsCommet);
        Button create = view.findViewById(R.id.btnCreateComment);
        create.setOnClickListener(this);

        Bundle args = getArguments();
        assert args != null;
        auth_key = args.getString("auth_key");
        post_id = args.getString("id_post");
        id_profile = args.getString("id_profile");

        Singleton.getInstance(view.getContext()).setListener(this);
        Singleton.getInstance(view.getContext()).apiRequestGetPostsComments(view.getContext(), auth_key, post_id);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnCreateComment){

            if(TextUtils.isEmpty(comment.getText()))
            {
                Toast.makeText(getContext(), "Empty Field", Toast.LENGTH_SHORT).show();
                return;
            }

            if(comment.getText().length() > 100){
                Toast.makeText(getContext(), "Fild is to long", Toast.LENGTH_SHORT).show();
                return;
            }

            PostsCommentsModel postsComment = new PostsCommentsModel();

            postsComment.setContent(comment.getText().toString());
            postsComment.setUser_id(Integer.parseInt(id_profile));

            Singleton.getInstance(getContext()).setListener(this);
            Singleton.getInstance(getContext()).apiRequestAddPostComment(getContext(), auth_key, postsComment, post_id);
        }
    }

    @Override
    public void onRefreshPostsComments(ArrayList<PostsCommentsModel> postsComments) {
        postsCommentsListAdapter = new PostsCommentsListAdapter(getContext(), postsComments);
        listViewPostsComments.setAdapter(postsCommentsListAdapter);
        postsCommentsListAdapter.refresh(postsComments);
        listViewPostsComments.setSelection(postsCommentsListAdapter.getCount() - 1);
    }

    @Override
    public void onRefreshNews(ArrayList<NewsModel> news) {

    }

    @Override
    public void onRefreshNewsComments(ArrayList<NewsCommentsModel> newsComments) {

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
}
