package projetopsi.overcomeguildapp.dialogs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import projetopsi.overcomeguildapp.R;
import projetopsi.overcomeguildapp.adapters.GoEventListAdapter;
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


public class GoEventDialog extends DialogFragment implements Listener, View.OnClickListener, AdapterView.OnItemSelectedListener {
    ListView listViewGoEvent;
    TextView going;
    TextView maybe;

    String role;
    String auth_key;
    String id_event;
    String id_profile;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_go_event_dialog, container, false);

        Spinner spinner = view.findViewById(R.id.roles_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(), R.array.roles_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        listViewGoEvent = view.findViewById(R.id.listViewGoEvent);
        going = view.findViewById(R.id.textViewGoEventGoing);
        maybe = view.findViewById(R.id.textViewGoEventMaybe);
        going.setOnClickListener(this);
        maybe.setOnClickListener(this);

        Bundle args = getArguments();
        assert args != null;
        id_event = args.getString("id_event");
        auth_key = args.getString("auth_key");
        id_profile = args.getString("id_profile");

        Singleton.getInstance(view.getContext()).setListener(this);
        Singleton.getInstance(view.getContext()).apiRequestGetUserProfileHasEvents(view.getContext(), auth_key, id_event);
        return view;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        role = (String)parent.getItemAtPosition(pos);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.textViewGoEventGoing){
            UserProfileHasEventModel auxGoEvent = new UserProfileHasEventModel();

            auxGoEvent.setId_event(Integer.parseInt(id_event));
            auxGoEvent.setId_userprofile(Integer.parseInt(id_profile));
            auxGoEvent.setAttending("Going");
            auxGoEvent.setRole(role);

            if(role.equals("Role")){
                Toast.makeText(view.getContext(), "Select a Role", Toast.LENGTH_SHORT).show();
                return;
            }

            Singleton.getInstance(view.getContext()).setListener(this);
            Singleton.getInstance(view.getContext()).apiRequestAddUserProfileHasEvent(view.getContext(), auth_key, auxGoEvent);
        }else if(view.getId() == R.id.textViewGoEventMaybe){
            UserProfileHasEventModel auxGoEvent = new UserProfileHasEventModel();

            auxGoEvent.setId_event(Integer.parseInt(id_event));
            auxGoEvent.setId_userprofile(Integer.parseInt(id_profile));
            auxGoEvent.setAttending("Maybe");
            auxGoEvent.setRole(role);

            if(role.equals("Role")){
                Toast.makeText(view.getContext(), "Select a Role", Toast.LENGTH_SHORT).show();
                return;
            }

            Singleton.getInstance(view.getContext()).setListener(this);
            Singleton.getInstance(view.getContext()).apiRequestAddUserProfileHasEvent(view.getContext(), auth_key, auxGoEvent);
        }
    }

    @Override
    public void onRefreshUserProfileHasEvents(ArrayList<UserProfileHasEventModel> userProfileHasEvents) {
        GoEventListAdapter goEventListAdapter = new GoEventListAdapter(getContext(), userProfileHasEvents);
        listViewGoEvent.setAdapter(goEventListAdapter);
        goEventListAdapter.refresh(userProfileHasEvents);
    }

    @Override
    public void onRefreshSubjects(ArrayList<SubjectModel> subjects) {

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
