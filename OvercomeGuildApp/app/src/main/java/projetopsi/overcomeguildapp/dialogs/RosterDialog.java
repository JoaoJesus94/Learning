package projetopsi.overcomeguildapp.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

public class RosterDialog extends DialogFragment implements Listener {

    private TextView txtUsername;
    private TextView txtEmail;
    private TextView txtCreatedat;
    private TextView txtDisplayname;
    private TextView txtRank;
    private TextView txtRole;

    private String id_userProfile;
    private String auth_key;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_fragment_roster_dialog, container, false);

        txtUsername = view.findViewById(R.id.textView_RosterDig_username);
        txtEmail = view.findViewById(R.id.textView_RosterDig_email);
        txtCreatedat = view.findViewById(R.id.textView_RosterDig_createdat);
        txtDisplayname = view.findViewById(R.id.textView_RosterDig_displayname);
        txtRank = view.findViewById(R.id.textView_RosterDig_rank);
        txtRole = view.findViewById(R.id.textView_RosterDig_role);

        Bundle args = getArguments();
        assert args != null;
        id_userProfile = args.getString("id_profile");
        auth_key = args.getString("auth_key");

        Singleton.getInstance(view.getContext()).setListener(this);
        Singleton.getInstance(view.getContext()).apiRequestRosterProfile(view.getContext(), id_userProfile, auth_key);

        return view;
    }

    @Override
    public void onRefreshRosterProfile(RosterModel roster) {
        txtUsername.setText(roster.getUsername());
        txtEmail.setText(roster.getEmail());
        txtCreatedat.setText(roster.getCreated_at());
        txtDisplayname.setText(roster.getDisplayName());
        txtRank.setText(roster.getRank());
        txtRole.setText(roster.getRole());
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

    @Override
    public void onRefreshNewsComments(ArrayList<NewsCommentsModel> newsComments) {

    }
}
