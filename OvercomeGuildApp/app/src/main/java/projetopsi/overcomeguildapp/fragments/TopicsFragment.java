package projetopsi.overcomeguildapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import projetopsi.overcomeguildapp.dialogs.CreateTopicDialog;
import projetopsi.overcomeguildapp.dialogs.DeleteTopicDialog;
import projetopsi.overcomeguildapp.R;
import projetopsi.overcomeguildapp.adapters.TopicsListAdapter;
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


public class TopicsFragment extends Fragment implements Listener, DeleteTopicDialog.Communicator, CreateTopicDialog.Communicator, View.OnClickListener {

    private ListView listViewTopics;
    private String auth_key;
    private String id_profile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topics, container, false);
        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        listViewTopics = view.findViewById(R.id.fForum_listV_Topics);
        TextView createTopic = view.findViewById(R.id.textViewCreateTopic);

        createTopic.setOnClickListener(this);

        assert getArguments() != null;
        auth_key = getArguments().getString("auth_key");
        id_profile = getArguments().getString("profile_id");

        Singleton.getInstance(view.getContext()).setListener(this);
        Singleton.getInstance(view.getContext()).apiRequestGetTopics(view.getContext(), auth_key);

        listViewTopics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long idd) {
                TopicsModel topic = (TopicsModel) parent.getItemAtPosition(position);

                String idTopic = String.valueOf(topic.getId());

                assert getFragmentManager() != null;
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                PostsFragment postsFragment = PostsFragment.newInstance(auth_key, idTopic, id_profile);
                ft.add(R.id.fragment_container, postsFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        listViewTopics.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TopicsModel topic = (TopicsModel) parent.getItemAtPosition(position);

                if(Integer.parseInt(id_profile) != topic.getUserID()){
                    Toast.makeText(getContext(), "Can't delete", Toast.LENGTH_SHORT).show();
                    return true;
                }

                Bundle args = new Bundle();

                int idTopic = topic.getId();

                args.putString("id_Topic", String.valueOf(idTopic));
                args.putString("auth_key", auth_key);
                args.putString("id_profile", id_profile);
                args.putString("id_TopicUser", String.valueOf(topic.getUserID()));
                args.putString("topicName", topic.getTopicName());

                FragmentManager fm = getFragmentManager();
                DeleteTopicDialog deleteTopicDialog = new DeleteTopicDialog();
                deleteTopicDialog.setArguments(args);
                deleteTopicDialog.setTargetFragment(TopicsFragment.this, 1);
                assert fm != null;
                deleteTopicDialog.show(fm, "TAG");
                return true;
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.textViewCreateTopic){
            Bundle args = new Bundle();

            args.putString("auth_key", auth_key);
            args.putString("profile_id", id_profile);
            FragmentManager fm = getFragmentManager();
            CreateTopicDialog createTopicDialog = new CreateTopicDialog();
            createTopicDialog.setArguments(args);
            createTopicDialog.setTargetFragment(TopicsFragment.this, 1);
            assert fm != null;
            createTopicDialog.show(fm, "TAG");
        }
    }

    public static TopicsFragment newInstance(String auth_key, String profile_id){
        TopicsFragment topicsFragment = new TopicsFragment();
        Bundle args = new Bundle();
        args.putString("auth_key", auth_key);
        args.putString("profile_id", profile_id);
        topicsFragment.setArguments(args);
        return topicsFragment;
    }

    @Override
    public void onRefreshTopics(ArrayList<TopicsModel> topics) {
        TopicsListAdapter topicsListAdapter = new TopicsListAdapter(getContext(), topics);
        listViewTopics.setAdapter(topicsListAdapter);
        topicsListAdapter.refresh(topics);
        Singleton.getInstance(getContext()).setListViewHeightBasedOnItems(listViewTopics);
    }

    @Override
    public void CreateTopic() {
        Singleton.getInstance(getContext()).setListener(this);
        Singleton.getInstance(getContext()).apiRequestGetTopics(getContext(), auth_key);
    }

    @Override
    public void updateTopic() {
        Singleton.getInstance(getContext()).setListener(this);
        Singleton.getInstance(getContext()).apiRequestGetTopics(getContext(), auth_key);
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
    public void onRefreshUserProfileList(UserProfileModel userprofile) {

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
