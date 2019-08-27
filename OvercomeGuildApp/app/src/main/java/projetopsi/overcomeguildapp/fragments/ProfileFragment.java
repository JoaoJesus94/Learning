package projetopsi.overcomeguildapp.fragments;

import android.content.Context;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import projetopsi.overcomeguildapp.R;
import projetopsi.overcomeguildapp.adapters.CharacterListAdapter;
import projetopsi.overcomeguildapp.dialogs.AddCharcaterDialog;
import projetopsi.overcomeguildapp.dialogs.DeleteCharacterDialog;
import projetopsi.overcomeguildapp.dialogs.EditCharacterDialog;
import projetopsi.overcomeguildapp.dialogs.EditProfileDialog;
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

public class ProfileFragment extends Fragment implements Listener, View.OnClickListener, EditProfileDialog.Communicator, AddCharcaterDialog.Communicator, DeleteCharacterDialog.Communicator, EditCharacterDialog.Communicator {

    private ListView listViewCharacters;

    private TextView textViewDisplayNameTop;
    private TextView textViewUsername;
    private TextView textViewEmail;
    private TextView textViewDisplayName;
    private TextView textViewRank;
    private TextView textViewRole;

    private int id;
    private String id_profile;
    private String username;
    private String auth_key;
    private String email;
    private String displayName;
    private String displayNameShared;
    private String roleShared;
    private String rankShared;

    public ProfileFragment.Communicator communicator;

    public interface Communicator{
        void setUserCredentials(String username, String email);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator = (ProfileFragment.Communicator) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        try{
            Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();
        }catch (Exception ignored){

        }

        textViewDisplayNameTop = view.findViewById(R.id.fProfile_txtV_mainDisplayName);
        textViewUsername = view.findViewById(R.id.fProfile_txtV_username);
        textViewEmail = view.findViewById(R.id.fProfile_txtV_email);
        textViewDisplayName = view.findViewById(R.id.fProfile_txtV_displayName);
        textViewRank = view.findViewById(R.id.fProfile_txtV_rank);
        textViewRole = view.findViewById(R.id.fProfile_txtV_role);

        Button editProfile = view.findViewById(R.id.fProfile_btn_edit);
        Button addCharacter = view.findViewById(R.id.fProfile_btn_addCharacter);

        editProfile.setOnClickListener(this);
        addCharacter.setOnClickListener(this);

        listViewCharacters = view.findViewById(R.id.fProfile_listV_characters);

        try{
            assert getArguments() != null;
            id = getArguments().getInt("id");
            id_profile = getArguments().getString("id_profile");
            username = getArguments().getString("username");
            auth_key = getArguments().getString("auth_key");
            email = getArguments().getString("email");
            displayNameShared = getArguments().getString("displayName");
            rankShared = getArguments().getString("rank");
            roleShared = getArguments().getString("role");
        }catch (Exception ignored){

        }

        if(!(Singleton.getInstance(getContext()).checkInternetState(getContext()))){
            auth_key = null;
        }

        Singleton.getInstance(getContext()).setListener(this);
        Singleton.getInstance(getContext()).apiRequestGetUserProfile(getContext(), auth_key, String.valueOf(id));

        listViewCharacters.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                CharacterModel character = (CharacterModel) parent.getItemAtPosition(position);

                Bundle args = new Bundle();

                int characterId = character.getId();

                args.putString("id_character", String.valueOf(characterId));
                args.putString("auth_key", auth_key);
                args.putString("displayName", character.getCharName());

                FragmentManager fm = getFragmentManager();
                DeleteCharacterDialog deleteCharacterDialog = new DeleteCharacterDialog();
                deleteCharacterDialog.setArguments(args);
                deleteCharacterDialog.setTargetFragment(ProfileFragment.this, 1);
                assert fm != null;
                deleteCharacterDialog.show(fm, "TAG");
                return true;
            }
        });

        listViewCharacters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long idd) {
                CharacterModel character = (CharacterModel) parent.getItemAtPosition(position);

                Bundle args = new Bundle();

                int idCharacter = character.getId();

                args.putString("id_character", String.valueOf(idCharacter));
                args.putString("charName", character.getCharName());
                args.putString("level", String.valueOf(character.getLevel()));
                args.putString("_class", character.get_Class());
                args.putString("race", character.getRace());
                args.putString("mainSpec", character.getMainSpec());
                args.putString("offSpec", character.getOffSpec());

                args.putString("id_profile", String.valueOf(id));
                args.putString("auth_key", auth_key);

                FragmentManager fm = getFragmentManager();
                EditCharacterDialog editCharacterDialog = new EditCharacterDialog();
                editCharacterDialog.setArguments(args);
                editCharacterDialog.setTargetFragment(ProfileFragment.this, 1);
                assert fm != null;
                editCharacterDialog.show(fm, "TAG");
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        if(!(Singleton.getInstance(getContext()).checkInternetState(getContext()))){
            Toast.makeText(getContext(), "Internet offline", Toast.LENGTH_SHORT).show();
            return;
        }else if(auth_key == null){
            Toast.makeText(getContext(), "activity_login again", Toast.LENGTH_SHORT).show();
            return;
        }

        if(v.getId() == R.id.fProfile_btn_edit){
            Bundle args = new Bundle();

            args.putString("username", username);
            args.putString("displayName", displayName);
            args.putString("email", email);
            args.putInt("id", id);
            args.putString("auth_key", auth_key);
            args.putString("id_profile", id_profile);

            FragmentManager fm = getFragmentManager();
            EditProfileDialog editProfile = new EditProfileDialog();
            editProfile.setArguments(args);
            editProfile.setTargetFragment(ProfileFragment.this, 1);
            assert fm != null;
            editProfile.show(fm, "TAG");
        }else if(v.getId() == R.id.fProfile_btn_addCharacter){

            Bundle args = new Bundle();

            args.putString("id_profile", id_profile);
            args.putString("auth_key", auth_key);

            FragmentManager fm = getFragmentManager();
            AddCharcaterDialog addCharcaterDialog = new AddCharcaterDialog();
            addCharcaterDialog.setArguments(args);
            addCharcaterDialog.setTargetFragment(ProfileFragment.this, 1);
            assert fm != null;
            addCharcaterDialog.show(fm, "TAG");
        }
    }

    public static ProfileFragment newInstance(int id, String username, String auth_key, String email, String id_profile){
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        args.putString("username", username);
        args.putString("auth_key", auth_key);
        args.putString("email", email);
        args.putString("id_profile", id_profile);
        profileFragment.setArguments(args);
        return profileFragment;
    }

    public static ProfileFragment newInstance2(int id, String username, String email, String displayName, String rank, String role){
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        args.putString("username", username);
        args.putString("email", email);
        args.putString("displayName", displayName);
        args.putString("rank", rank);
        args.putString("role", role);
        profileFragment.setArguments(args);
        return profileFragment;
    }

    @Override
    public void onRefreshUserProfileList(UserProfileModel userprofile) {

        if(userprofile == null){
            textViewDisplayNameTop.setText(displayNameShared);
            textViewDisplayName.setText(displayNameShared);
            textViewUsername.setText(username);
            textViewEmail.setText(email);
            textViewRank.setText(rankShared);
            textViewRole.setText(roleShared);
        }else{
            displayName = userprofile.getDisplayName();
            String rank = userprofile.getRank();
            String role = userprofile.getRole();

            textViewDisplayNameTop.setText(displayName);
            textViewUsername.setText(username);
            textViewEmail.setText(email);
            textViewDisplayName.setText(displayName);
            textViewRank.setText(rank);
            textViewRole.setText(role);
        }

        Singleton.getInstance(getContext()).setListener(this);
        Singleton.getInstance(getContext()).apiRequestGetCharacters(getContext(), auth_key, id_profile);
    }

    @Override
    public void onRefreshCharacterList(ArrayList<CharacterModel> characterList) {

        CharacterListAdapter characterListAdapter = new CharacterListAdapter(getContext(), characterList);
        listViewCharacters.setAdapter(characterListAdapter);
        characterListAdapter.refresh(characterList);
        Singleton.getInstance(getContext()).setListViewHeightBasedOnItems(listViewCharacters);
    }

    @Override
    public void updateUserProfile(String username, String displayName, String email){
        this.username = username;
        this.displayName = displayName;
        this.email = email;

        textViewDisplayNameTop.setText(displayName);
        textViewDisplayName.setText(displayName);
        textViewUsername.setText(username);
        textViewEmail.setText(email);
    }

    @Override
    public void updateCharacterList() {
        Singleton.getInstance(getContext()).setListener(this);
        Singleton.getInstance(getContext()).apiRequestGetCharacters(getContext(), auth_key, id_profile);
    }

    @Override
    public void updateDeleteCharacter() {
        Singleton.getInstance(getContext()).setListener(this);
        Singleton.getInstance(getContext()).apiRequestGetCharacters(getContext(), auth_key, id_profile);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        communicator.setUserCredentials(username, email);
    }

    @Override
    public void onRefreshUser(UserModel userModel) {

    }

    @Override
    public void onUpdateUserProfile() {
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

    @Override
    public void onRefreshNewsComments(ArrayList<NewsCommentsModel> newsComments) {

    }
}