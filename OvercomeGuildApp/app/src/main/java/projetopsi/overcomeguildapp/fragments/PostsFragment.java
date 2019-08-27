package projetopsi.overcomeguildapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

import projetopsi.overcomeguildapp.dialogs.CreatePostsCommentDialog;
import projetopsi.overcomeguildapp.dialogs.CreatePostDialog;
import projetopsi.overcomeguildapp.dialogs.DeletePostDialog;
import projetopsi.overcomeguildapp.R;
import projetopsi.overcomeguildapp.adapters.PostsListAdapter;
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


public class PostsFragment extends Fragment implements Listener, DeletePostDialog.Communicator, View.OnClickListener, CreatePostDialog.Communicator {
    private ListView listsViewPosts;
    private PostsListAdapter postsListAdapter;
    private String id_topic;
    private String id_profile;
    private String auth_key;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, container, false);

        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        listsViewPosts = view.findViewById(R.id.fForum_listV_posts);

        TextView createPost = view.findViewById(R.id.textViewCreatePost);
        createPost.setOnClickListener(this);

        assert getArguments() != null;
        id_topic = getArguments().getString("id_topic");
        id_profile = getArguments().getString("id_profile");
        auth_key = getArguments().getString("auth_key");

        Singleton.getInstance(view.getContext()).setListener(this);
        Singleton.getInstance(view.getContext()).apiRequestGetPosts(getContext(), auth_key, id_topic);

        listsViewPosts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long idd) {
                PostsModel post = (PostsModel) parent.getItemAtPosition(position);

                String idPost = String.valueOf(post.getId());

                Bundle args = new Bundle();

                args.putString("id_post", String.valueOf(idPost));
                args.putString("auth_key", auth_key);
                args.putString("id_profile", id_profile);

                FragmentManager fm = getFragmentManager();
                CreatePostsCommentDialog createPostsCommentDialog = new CreatePostsCommentDialog();
                createPostsCommentDialog.setArguments(args);
                createPostsCommentDialog.setTargetFragment(PostsFragment.this, 1);
                assert fm != null;
                createPostsCommentDialog.show(fm, "TAG");
            }
        });

        listsViewPosts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                PostsModel post = (PostsModel) parent.getItemAtPosition(position);

                if(Integer.parseInt(id_profile) != post.getUserID()){
                    Toast.makeText(getContext(), "Can't delete", Toast.LENGTH_SHORT).show();
                    return true;
                }

                Bundle args = new Bundle();

                int idPost = post.getId();

                args.putString("id_Post", String.valueOf(idPost));
                args.putString("auth_key", auth_key);
                args.putString("id_profile", id_profile);
                args.putString("id_TopicUser", String.valueOf(post.getUserID()));
                args.putString("postName", post.getTitle());

                FragmentManager fm = getFragmentManager();
                DeletePostDialog deletePostDialog = new DeletePostDialog();
                deletePostDialog.setArguments(args);
                deletePostDialog.setTargetFragment(PostsFragment.this, 1);
                assert fm != null;
                deletePostDialog.show(fm, "TAG");
                return true;
            }
        });

        return view;
    }

    public static PostsFragment newInstance(String auth_key, String id_topic, String id_profile){
        PostsFragment postsFragment = new PostsFragment();
        Bundle args = new Bundle();
        args.putString("id_topic", id_topic);
        args.putString("id_profile", id_profile);
        args.putString("auth_key", auth_key);
        postsFragment.setArguments(args);
        return postsFragment;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.textViewCreatePost){
            Bundle args = new Bundle();

            args.putString("auth_key", auth_key);
            args.putString("id_topic", id_topic);
            args.putString("id_profile", id_profile);

            FragmentManager fm = getFragmentManager();
            CreatePostDialog createPostDialog = new CreatePostDialog();
            createPostDialog.setArguments(args);
            createPostDialog.setTargetFragment(PostsFragment.this, 1);
            assert fm != null;
            createPostDialog.show(fm, "TAG");
        }
    }

    @Override
    public void onRefreshPosts(ArrayList<PostsModel> posts) {
        postsListAdapter = new PostsListAdapter(getContext(), posts);
        listsViewPosts.setAdapter(postsListAdapter);
        postsListAdapter.refresh(posts);
        Singleton.getInstance(getContext()).setListViewHeightBasedOnItems(listsViewPosts);
    }

    @Override
    public void updatePost() {
        Singleton.getInstance(getContext()).setListener(this);
        Singleton.getInstance(getContext()).apiRequestGetPosts(getContext(), auth_key, id_topic);
    }

    @Override
    public void CreatePost() {
        Singleton.getInstance(getContext()).setListener(this);
        Singleton.getInstance(getContext()).apiRequestGetPosts(getContext(), auth_key, id_topic);
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
