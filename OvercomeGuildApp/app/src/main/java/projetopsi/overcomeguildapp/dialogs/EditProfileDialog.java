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

public class EditProfileDialog extends DialogFragment implements View.OnClickListener, Listener {

    private EditText displayName;
    private EditText username;
    private EditText email;

    private String id;
    private String id_profile;
    private String auth_key;

    public EditProfileDialog.Communicator communicator;

    public interface Communicator{
        void updateUserProfile(String username, String displayName, String email);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
            communicator = (EditProfileDialog.Communicator) getTargetFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup conteiner, Bundle b){
        View view = inflater.inflate(R.layout.fragment_edit_profile_dialog, conteiner, false);
        TextView edit = view.findViewById(R.id.textViewEdit);
        TextView cancel = view.findViewById(R.id.textViewCancel);
        displayName = view.findViewById(R.id.editTextDisplayName);
        username = view.findViewById(R.id.editTextUsername);
        email = view.findViewById(R.id.editTextEmail);

        Bundle args = getArguments();
        assert args != null;
        id = String.valueOf(args.getInt("id"));
        String textUsername = args.getString("username");
        String textDisplayName = args.getString("displayName");
        String textEmail = args.getString("email");
        id_profile = args.getString("id_profile");
        auth_key = args.getString("auth_key");

        edit.setOnClickListener(this);
        cancel.setOnClickListener(this);

        username.setText(textUsername);
        displayName.setText(textDisplayName);
        email.setText(textEmail);

        setCancelable(false);
        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.textViewEdit){

            if(TextUtils.isEmpty(username.getText()) || TextUtils.isEmpty(email.getText())){
                Toast.makeText(getContext(), "Empty Fields", Toast.LENGTH_SHORT).show();
                return;
            }
            if(displayName.getText().length() > 20){
                Toast.makeText(getContext(), "DisplayName is to long", Toast.LENGTH_SHORT).show();
                return;
            }

            UserProfileModel auxUserProfile = new UserProfileModel();
            auxUserProfile.setDisplayName(displayName.getText().toString());

            UserModel auxUser = new UserModel();
            auxUser.setUsername(username.getText().toString());
            auxUser.setEmail(email.getText().toString());

            if(!auxUser.getEmail().contains("@") || !auxUser.getEmail().contains(".") ){
                Toast.makeText(getContext(), "Is not an email", Toast.LENGTH_SHORT).show();
                return;
            }

            Singleton.getInstance(getContext()).setListener(this);
            Singleton.getInstance(getContext()).apiRequestUpdateUser(auxUser, getContext(), auth_key, id);

            Singleton.getInstance(getContext()).setListener(this);
            Singleton.getInstance(getContext()).apiRequestUpdateUserProfile(auxUserProfile, getContext(), auth_key, id_profile);

            getDialog().dismiss();
        }
        if(v.getId() == R.id.textViewCancel){
            getDialog().dismiss();
        }
    }

    @Override
    public void onRefreshUser(UserModel userModel) {

    }

    @Override
    public void onUpdateUserProfile() {
        communicator.updateUserProfile(username.getText().toString(), displayName.getText().toString(), email.getText().toString());
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
