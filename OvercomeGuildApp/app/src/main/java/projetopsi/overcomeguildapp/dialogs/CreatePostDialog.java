package projetopsi.overcomeguildapp.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import projetopsi.overcomeguildapp.R;
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


public class CreatePostDialog extends DialogFragment implements Listener, View.OnClickListener {

    private String auth_key;
    private String id_topic;
    private String id_profile;

    private EditText title;
    private EditText content;

    public CreatePostDialog.Communicator communicator;

    public interface Communicator{
        void CreatePost();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator = (CreatePostDialog.Communicator) getTargetFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_post_dialog, container, false);

        TextView create = view.findViewById(R.id.textViewPostCreateCreate);
        TextView cancel = view.findViewById(R.id.textViewPostCreateCancel);

        create.setOnClickListener(this);
        cancel.setOnClickListener(this);

        title = view.findViewById(R.id.editTextPostCreateTitle);
        content = view.findViewById(R.id.editTextPostCreateContent);

        Bundle args = getArguments();
        assert args != null;
        auth_key = args.getString("auth_key");
        id_topic = args.getString("id_topic");
        id_profile = args.getString("id_profile");

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.textViewPostCreateCreate){

            if(TextUtils.isEmpty(title.getText()) || TextUtils.isEmpty(content.getText()))
            {
                Toast.makeText(getContext(), "Empty Fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if(title.getText().length() > 30 || content.getText().length() > 100){
                Toast.makeText(getContext(), "Filds are to long", Toast.LENGTH_SHORT).show();
                return;
            }

            PostsModel post = new PostsModel();

            post.setTitle(title.getText().toString());
            post.setContent(content.getText().toString());
            post.setUserID(Integer.parseInt(id_profile));

            Singleton.getInstance(getContext()).setListener(this);
            Singleton.getInstance(getContext()).apiRequestAddPost(getContext(), auth_key, post, id_topic);

            dismiss();
        }else if(view.getId() == R.id.textViewPostCreateCancel){
            dismiss();
        }
    }

    @Override
    public void onRefreshPosts(ArrayList<PostsModel> posts) {
        communicator.CreatePost();
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
    public void onRefreshPostsComments(ArrayList<PostsCommentsModel> postsComments) {

    }

    @Override
    public void onRefreshNews(ArrayList<NewsModel> news) {

    }

    @Override
    public void onRefreshNewsComments(ArrayList<NewsCommentsModel> newsComments) {

    }
}
