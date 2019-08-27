package projetopsi.overcomeguildapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import projetopsi.overcomeguildapp.dialogs.GoEventDialog;
import projetopsi.overcomeguildapp.R;
import projetopsi.overcomeguildapp.adapters.EventsListAdapter;
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

public class EventsFragment extends Fragment implements Listener {
    ListView evetsList;

    String auth_key;
    String id_profile;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);

        evetsList = view.findViewById(R.id.listViewEvents);

        assert getArguments() != null;
        id_profile = getArguments().getString("id_profile");
        auth_key = getArguments().getString("auth_key");

        Singleton.getInstance(view.getContext()).setListener(this);
        Singleton.getInstance(view.getContext()).apiRequestGetEvents(getContext(), auth_key);

        evetsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long idd) {
                EventModel event = (EventModel) parent.getItemAtPosition(position);

                String idEvent = String.valueOf(event.getId());

                Bundle args = new Bundle();

                args.putString("id_event", String.valueOf(idEvent));
                args.putString("auth_key", auth_key);
                args.putString("id_profile", id_profile);

                FragmentManager fm = getFragmentManager();
                GoEventDialog goEventDialog = new GoEventDialog();
                goEventDialog.setArguments(args);
                goEventDialog.setTargetFragment(EventsFragment.this, 1);
                assert fm != null;
                goEventDialog.show(fm, "TAG");
            }
        });

        return view;
    }

    public static EventsFragment newInstance(String auth_key, String id_profile){
        EventsFragment eventsFragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putString("auth_key", auth_key);
        args.putString("id_profile", id_profile);
        eventsFragment.setArguments(args);
        return eventsFragment;
    }

    @Override
    public void onRefreshEvents(ArrayList<EventModel> events) {
        EventsListAdapter eventsListAdapter = new EventsListAdapter(getContext(), events);
        evetsList.setAdapter(eventsListAdapter);
        eventsListAdapter.refresh(events);
        Singleton.getInstance(getContext()).setListViewHeightBasedOnItems(evetsList);
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
