package projetopsi.overcomeguildapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import projetopsi.overcomeguildapp.R;
import projetopsi.overcomeguildapp.fragments.ApplyFragment;
import projetopsi.overcomeguildapp.fragments.CalendarFragment;
import projetopsi.overcomeguildapp.fragments.TopicsFragment;
import projetopsi.overcomeguildapp.fragments.HomeFragment;
import projetopsi.overcomeguildapp.fragments.NewsFragment;
import projetopsi.overcomeguildapp.fragments.OPTableFragment;
import projetopsi.overcomeguildapp.fragments.ProfileFragment;
import projetopsi.overcomeguildapp.fragments.RosterFragment;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Listener, ProfileFragment.Communicator {

    public static final String USERID = "user_id";
    public static final String USERNAME = "username";
    public static final String AUTH_KEY = "auth_key";
    public static final String EMAIL = "email";
    public static final String PROFILE_ID = "profile_id";
    public static final String ID = "id";
    public static final String APPLY = "apply";
    public static final String DISPLAYNAME = "displayName";
    public static final String RANK = "rank";
    public static final String ROLE = "role";

    private String user_id;
    private String username;
    private String auth_key;
    private String email;
    private String profile_id;
    private String apply;
    private String displayName;
    private String rank;
    private String role;

    NavigationView navigationView;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(!(Singleton.getInstance(this).checkInternetState(this))){

            Intent intent = getIntent();

            user_id = intent.getStringExtra(ID);
            username = intent.getStringExtra(USERNAME);
            email = intent.getStringExtra(EMAIL);
            apply = intent.getStringExtra(APPLY);
            displayName = intent.getStringExtra(DISPLAYNAME);
            rank = intent.getStringExtra(RANK);
            role = intent.getStringExtra(ROLE);

            FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
            ProfileFragment profileFragment = ProfileFragment.newInstance2(Integer.parseInt(user_id),username, email, displayName, rank, role);
            ft1.add(R.id.fragment_container, profileFragment);
            ft1.commit();
        }else{
            Intent intent = getIntent();

            user_id = intent.getStringExtra(USERID);
            username = intent.getStringExtra(USERNAME);
            auth_key = intent.getStringExtra(AUTH_KEY);
            email = intent.getStringExtra(EMAIL);
            profile_id = intent.getStringExtra(PROFILE_ID);
            apply = intent.getStringExtra(APPLY);

            if(apply.equals("")){
                FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                ApplyFragment applyFragment = ApplyFragment.newInstance(user_id, auth_key);
                ft2.add(R.id.fragment_container, applyFragment);
                //ft2.addToBackStack(null);
                ft2.commit();
            }else if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_home);
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                FragmentTransaction ft0 = getSupportFragmentManager().beginTransaction();
                ft0.add(R.id.fragment_container, new HomeFragment());
                ft0.addToBackStack(null);
                ft0.commit();
                break;
            case R.id.nav_profile:
                if(apply.equals("OPEN") || apply.equals("")){
                    Toast.makeText(this, "Apply not Acepted yet", Toast.LENGTH_SHORT).show();
                    return false;
                }else if (auth_key == null){
                    Intent intent = getIntent();

                    user_id = intent.getStringExtra(ID);
                    username = intent.getStringExtra(USERNAME);
                    email = intent.getStringExtra(EMAIL);
                    apply = intent.getStringExtra(APPLY);
                    displayName = intent.getStringExtra(DISPLAYNAME);
                    rank = intent.getStringExtra(RANK);
                    role = intent.getStringExtra(ROLE);

                    FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                    ProfileFragment profileFragment = ProfileFragment.newInstance2(Integer.parseInt(user_id),username, email, displayName, rank, role);
                    ft1.add(R.id.fragment_container, profileFragment);
                    ft1.commit();
                    break;
                }

                this.removeFragment();

                FragmentTransaction ft1 = getSupportFragmentManager().beginTransaction();
                ProfileFragment profileFragment = ProfileFragment.newInstance(Integer.parseInt(user_id),username, auth_key, email, profile_id);
                ft1.add(R.id.fragment_container, profileFragment);
                ft1.addToBackStack(null);
                ft1.commit();
                break;
            case R.id.nav_apply:
                if(!(Singleton.getInstance(this).checkInternetState(this))){
                    Toast.makeText(this, "Internet OFF", Toast.LENGTH_SHORT).show();
                    return false;
                }else if (auth_key == null){
                    Toast.makeText(this, "Try to activity_login", Toast.LENGTH_SHORT).show();
                    return false;
                }

                this.removeFragment();

                FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                ApplyFragment applyFragment = ApplyFragment.newInstance(user_id, auth_key);
                ft2.add(R.id.fragment_container, applyFragment);
                ft2.addToBackStack(null);
                ft2.commit();
                break;
            case R.id.nav_opTable:
                if(!(Singleton.getInstance(this).checkInternetState(this))){
                    Toast.makeText(this, "Internet OFF", Toast.LENGTH_SHORT).show();
                    return false;
                }else if(apply.equals("OPEN") || apply.equals("")){
                    Toast.makeText(this, "Apply not Acepted yet", Toast.LENGTH_SHORT).show();
                    return false;
                }else if (auth_key == null){
                    Toast.makeText(this, "Try to activity_login", Toast.LENGTH_SHORT).show();
                    return false;
                }

                this.removeFragment();

                FragmentTransaction ft3 = getSupportFragmentManager().beginTransaction();
                OPTableFragment opTableFragment = OPTableFragment.newInstance(auth_key);
                ft3.add(R.id.fragment_container, opTableFragment);
                ft3.addToBackStack(null);
                ft3.commit();
                break;
            case R.id.nav_roster:
                if(!(Singleton.getInstance(this).checkInternetState(this))){
                    Toast.makeText(this, "Internet OFF", Toast.LENGTH_SHORT).show();
                    return false;
                }else if(apply.equals("OPEN") || apply.equals("")){
                    Toast.makeText(this, "Apply not Acepted yet", Toast.LENGTH_SHORT).show();
                    return false;
                }else if (auth_key == null){
                    Toast.makeText(this, "Try to activity_login", Toast.LENGTH_SHORT).show();
                    return false;
                }

                this.removeFragment();

                FragmentTransaction ft4 = getSupportFragmentManager().beginTransaction();
                RosterFragment rosterFragment = RosterFragment.newInstance(auth_key);
                ft4.add(R.id.fragment_container, rosterFragment);
                ft4.addToBackStack(null);
                ft4.commit();
                break;
            case R.id.nav_calendar:
                if(!(Singleton.getInstance(this).checkInternetState(this))){
                    Toast.makeText(this, "Internet OFF", Toast.LENGTH_SHORT).show();
                    return false;
                }else if(apply.equals("OPEN") || apply.equals("")){
                    Toast.makeText(this, "Apply not Acepted yet", Toast.LENGTH_SHORT).show();
                    return false;
                }else if (auth_key == null){
                    Toast.makeText(this, "Try to activity_login", Toast.LENGTH_SHORT).show();
                    return false;
                }

                this.removeFragment();

                FragmentTransaction ft5 = getSupportFragmentManager().beginTransaction();
                CalendarFragment calendarFragment = CalendarFragment.newInstance(auth_key, profile_id);
                ft5.add(R.id.fragment_container, calendarFragment);
                ft5.addToBackStack(null);
                ft5.commit();
                break;
            case R.id.nav_forum:
                if(!(Singleton.getInstance(this).checkInternetState(this))){
                    Toast.makeText(this, "Internet OFF", Toast.LENGTH_SHORT).show();
                    return false;
                }else if(apply.equals("OPEN") || apply.equals("")){
                    Toast.makeText(this, "Apply not Acepted yet", Toast.LENGTH_SHORT).show();
                    return false;
                }else if (auth_key == null){
                    Toast.makeText(this, "Try to activity_login", Toast.LENGTH_SHORT).show();
                    return false;
                }

                this.removeFragment();

                FragmentTransaction ft6 = getSupportFragmentManager().beginTransaction();
                TopicsFragment topicsFragment = TopicsFragment.newInstance(auth_key, profile_id);
                ft6.add(R.id.fragment_container, topicsFragment);
                ft6.addToBackStack(null);
                ft6.commit();
                break;
            case R.id.nav_news:
                if(!(Singleton.getInstance(this).checkInternetState(this))){
                    Toast.makeText(this, "Internet OFF", Toast.LENGTH_SHORT).show();
                    return false;
                }else if(apply.equals("OPEN") || apply.equals("")){
                    Toast.makeText(this, "Apply not Acepted yet", Toast.LENGTH_SHORT).show();
                    return false;
                }else if (auth_key == null){
                    Toast.makeText(this, "Try to activity_login", Toast.LENGTH_SHORT).show();
                    return false;
                }

                this.removeFragment();

                FragmentTransaction ft7 = getSupportFragmentManager().beginTransaction();
                NewsFragment newsFragment = NewsFragment.newInstance(auth_key, profile_id);
                ft7.add(R.id.fragment_container, newsFragment);
                ft7.addToBackStack(null);
                ft7.commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void setUserCredentials(String username, String email) {
        this.username = username;
        this.email = email;
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }

    public void removeFragment(){
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if(fragment != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.remove(fragment);
            ft.commit();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
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
