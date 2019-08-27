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

import projetopsi.overcomeguildapp.R;
import projetopsi.overcomeguildapp.adapters.UserProfileListRosterAdapter;
import projetopsi.overcomeguildapp.dialogs.RosterDialog;
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


public class RosterFragment extends Fragment implements Listener {
    private ListView listViewRosterUserProfile;
    private String auth_key;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_roster, container, false);
        listViewRosterUserProfile = view.findViewById(R.id.fRoster_listV_userProfile);

        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        assert getArguments() != null;
        auth_key = getArguments().getString("auth_key");

        Singleton.getInstance(getContext()).setListener(this);
        Singleton.getInstance(getContext()).apiRequestGetRoster(getContext(), auth_key);

        listViewRosterUserProfile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long idd) {
                UserProfileModel userProfile = (UserProfileModel) parent.getItemAtPosition(position);

                Bundle args = new Bundle();

                int id_userProfile = userProfile.getId();

                args.putString("id_profile", String.valueOf(id_userProfile));
                args.putString("auth_key", auth_key);

                FragmentManager fm = getFragmentManager();
                RosterDialog rosterDialog = new RosterDialog();
                rosterDialog.setArguments(args);
                rosterDialog.setTargetFragment(RosterFragment.this, 1);
                assert fm != null;
                rosterDialog.show(fm, "TAG");
            }
        });

        return view;
    }

    public static RosterFragment newInstance(String auth_key){
        RosterFragment rosterFragment = new RosterFragment();
        Bundle args = new Bundle();
        args.putString("auth_key", auth_key);
        rosterFragment.setArguments(args);
        return rosterFragment;
    }

    @Override
    public void onRefreshRoster(ArrayList<UserProfileModel> userProfileList) {
        UserProfileListRosterAdapter userProfileListRosterAdapter = new UserProfileListRosterAdapter(getContext(), userProfileList);
        listViewRosterUserProfile.setAdapter(userProfileListRosterAdapter);
        userProfileListRosterAdapter.refresh(userProfileList);
        Singleton.getInstance(getContext()).setListViewHeightBasedOnItems(listViewRosterUserProfile);
    }

    @Override
    public void onRefreshUserProfileList(UserProfileModel userprofile) {
    }

    @Override
    public void onRefreshUser(UserModel userModel) {

    }

    @Override
    public void onUpdateUserProfile() {

    }

    @Override
    public void onRefreshCharacterList(ArrayList<CharacterModel> characterList) {

    }

    @Override
    public void onRefreshApply(ApplyModel apply) {

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

    @Override
    public void onRefreshNewsComments(ArrayList<NewsCommentsModel> newsComments) {

    }
}
