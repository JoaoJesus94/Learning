package projetopsi.overcomeguildapp.dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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


public class DeleteTopicDialog extends DialogFragment implements Listener, View.OnClickListener {

    private String id_topic;
    private String auth_key;
    private String id_profile;
    private String id_topicuser;

    public DeleteTopicDialog.Communicator communicator;

    public interface Communicator{
        void updateTopic();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator = (DeleteTopicDialog.Communicator) getTargetFragment();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete_topic_dialog, container, false);

        TextView delete = view.findViewById(R.id.textView_deleteTopic_delete);
        TextView cancel = view.findViewById(R.id.textView_deleteTopic_cancel);
        TextView warning = view.findViewById(R.id.textViewDeleteTopic);

        Bundle args = getArguments();
        assert args != null;
        id_topic = args.getString("id_Topic");
        auth_key = args.getString("auth_key");
        id_profile = args.getString("id_profile");
        id_topicuser = args.getString("id_TopicUser");
        String topicName = args.getString("topicName");

        warning.setText("Are you sure you want to delete?  " + topicName);

        delete.setOnClickListener(this);
        cancel.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.textView_deleteTopic_delete){

            if(Integer.parseInt(id_profile) != Integer.parseInt(id_topicuser)){
                Toast.makeText(getContext(), "Can't delete", Toast.LENGTH_SHORT).show();
                return;
            }

            Singleton.getInstance(getContext()).setListener(this);
            Singleton.getInstance(getContext()).apiRequestDeleteTopic(getContext(), auth_key, id_topic);

            getDialog().dismiss();

        }else if(v.getId() == R.id.textView_deleteTopic_cancel){
            getDialog().dismiss();
        }
    }

    @Override
    public void onRefreshTopics(ArrayList<TopicsModel> topics) {
        communicator.updateTopic();
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
}
