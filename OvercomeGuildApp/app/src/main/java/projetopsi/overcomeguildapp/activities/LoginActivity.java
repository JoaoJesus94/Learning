package projetopsi.overcomeguildapp.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
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

public class LoginActivity extends AppCompatActivity implements Listener, View.OnClickListener {

    private TextView username;
    private TextView password;
    private TextView offline;

    private String _usernameShared;
    private String emailShared;
    private String applyShared;
    private int idShared;
    private String displayNameShared;
    private String rankShared;
    private String roleShared;

    private String apply;
    private String user_id;
    private String auth_key;
    private String profile_id;
    private String email;

    public static final String SHARED_PREFERENCES = "shared_preferences";
    public static final String USERNAME = "username";
    public static final String EMAIL = "emailShared";
    public static final String ID = "idShared";
    public static final String APPLY = "applyShared";
    public static final String DISPLAYNAME = "displayNameShared";
    public static final String RANK = "rankShared";
    public static final String ROLE = "roleShared";
    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;

    @SuppressLint({"SetTextI18n", "CommitPrefEdits"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        offline = findViewById(R.id.textViewOffline);

        offline.setOnClickListener(this);
        offline.setVisibility(View.INVISIBLE);

        preferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        preferencesEditor = preferences.edit();

        _usernameShared = preferences.getString(USERNAME, "");
        emailShared = preferences.getString(EMAIL, "");
        idShared = preferences.getInt(ID, 0);
        displayNameShared = preferences.getString(DISPLAYNAME, "");
        rankShared = preferences.getString(RANK, "");
        roleShared = preferences.getString(ROLE, "");

        try{
            applyShared = preferences.getString(APPLY, "");
        }catch (Exception ignored){

        }

        username.setText(_usernameShared);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_IMMERSIVE
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    public void onClickLogin(View view) {

        if(!(Singleton.getInstance(this).checkInternetState(this))){
            offline.setVisibility(View.VISIBLE);
            Toast.makeText(this, "You don't have Internet", Toast.LENGTH_SHORT).show();
            return;
        }

        if((TextUtils.isEmpty(username.getText().toString()) || (TextUtils.isEmpty(password.getText().toString())))){
            Toast.makeText(getApplicationContext(), "Empty Fields", Toast.LENGTH_SHORT).show();
            return;
        }

        preferencesEditor.putString(USERNAME ,username.getText().toString());
        preferencesEditor.apply();

        Singleton.getInstance(getApplicationContext()).setListener(this);
        Singleton.getInstance(this).apiRequestUserLogin(this, username.getText().toString(), password.getText().toString());
    }

    @Override
    public void onRefreshUser(UserModel userModel) {
        user_id = String.valueOf(userModel.getId());
        auth_key = userModel.getAuth_key();
        email = String.valueOf(userModel.getEmail());

        preferencesEditor.putString(EMAIL, email);
        preferencesEditor.putInt(ID, Integer.parseInt(user_id));
        preferencesEditor.apply();

        Singleton.getInstance(this).setListener(this);
        Singleton.getInstance(this).apiRequestGetApply(this, auth_key, user_id);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.textViewOffline){
            if(!(username.getText().toString().equals(_usernameShared))){
                Toast.makeText(this, "Is not the last member loged", Toast.LENGTH_SHORT).show();
                return;
            }

            if(_usernameShared.equals("") || emailShared.equals("")){
                Toast.makeText(this, "Can't go offline, empty data", Toast.LENGTH_SHORT).show();
                return;
            }else if(Singleton.getInstance(this).checkInternetState(this)){
                Toast.makeText(this, "You have internet", Toast.LENGTH_SHORT).show();
                offline.setVisibility(View.INVISIBLE);
                return;
            }

            Intent intent = new Intent(this, MainActivity.class);

            intent.putExtra(MainActivity.ID, String.valueOf(idShared));
            intent.putExtra(MainActivity.USERNAME, _usernameShared);
            intent.putExtra(MainActivity.EMAIL, emailShared);
            intent.putExtra(MainActivity.APPLY, applyShared);
            intent.putExtra(MainActivity.DISPLAYNAME, displayNameShared);
            intent.putExtra(MainActivity.RANK, rankShared);
            intent.putExtra(MainActivity.ROLE, roleShared);
            startActivity(intent);
        }
    }
    @Override
    public void onRefreshApply(ApplyModel apply) {
        if(apply == null){

        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(MainActivity.USERID, user_id);
        intent.putExtra(MainActivity.USERNAME , username.getText().toString());
        intent.putExtra(MainActivity.AUTH_KEY, auth_key);
        intent.putExtra(MainActivity.EMAIL, email);
        intent.putExtra(MainActivity.APPLY, "");
        startActivity(intent);

        }else if(apply.getStatus().equals("REFUSED")){
            Toast.makeText(this, "Apply Refused", Toast.LENGTH_SHORT).show();
        }else if(apply.getStatus().equals("OPEN")){

            this.apply = apply.getStatus();
            preferencesEditor.putString(APPLY, this.apply);
            preferencesEditor.apply();
            Intent intent = new Intent(this, MainActivity.class);

            intent.putExtra(MainActivity.USERID, user_id);
            intent.putExtra(MainActivity.USERNAME , username.getText().toString());
            intent.putExtra(MainActivity.AUTH_KEY, auth_key);
            intent.putExtra(MainActivity.EMAIL, email);
            intent.putExtra(MainActivity.APPLY, apply.getStatus());
            startActivity(intent);
        }else if (apply.getStatus().equals("ACCEPTED")){
            this.apply = apply.getStatus();
            preferencesEditor.putString(APPLY, this.apply);
            preferencesEditor.apply();
            Singleton.getInstance(this).setListener(this);
            Singleton.getInstance(this).apiRequestGetUserProfile(this, auth_key, user_id);
        }
    }

    @Override
    public void onRefreshUserProfileList(UserProfileModel userProfile) {

        profile_id = String.valueOf(userProfile.getId());
        preferencesEditor.putString(DISPLAYNAME, userProfile.getDisplayName());
        preferencesEditor.putString(RANK, userProfile.getRank());
        preferencesEditor.putString(ROLE, userProfile.getRole());
        preferencesEditor.apply();

        Singleton.getInstance(this).setListener(this);
        Singleton.getInstance(this).apiRequestGetCharacters(this, auth_key, profile_id);
    }

    @Override
    public void onRefreshCharacterList(ArrayList<CharacterModel> characterList) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(MainActivity.USERID, user_id);
        intent.putExtra(MainActivity.USERNAME , username.getText().toString());
        intent.putExtra(MainActivity.AUTH_KEY, auth_key);
        intent.putExtra(MainActivity.EMAIL, email);
        intent.putExtra(MainActivity.PROFILE_ID, profile_id);
        intent.putExtra(MainActivity.APPLY, apply);
        startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();

        _usernameShared = preferences.getString(USERNAME, "");
        emailShared = preferences.getString(EMAIL, "");
        idShared = preferences.getInt(ID, 0);
        displayNameShared = preferences.getString(DISPLAYNAME, "");
        rankShared = preferences.getString(RANK, "");
        roleShared = preferences.getString(ROLE, "");

        try{
            applyShared = preferences.getString(APPLY, "");
        }catch (Exception ignored){

        }
    }

    @Override
    public void onUpdateUserProfile() {

    }

    @Override
    public void onRefreshRoster(ArrayList<UserProfileModel> userProfileList) {

    }

    @Override
    public void onRefreshRosterProfile(RosterModel roster) {

    }

    @Override
    public void onRefreshOPTable(ArrayList<OverpointModel> overpoints) {

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
