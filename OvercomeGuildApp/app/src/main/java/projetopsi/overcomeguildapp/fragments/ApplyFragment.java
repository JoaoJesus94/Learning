package projetopsi.overcomeguildapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

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

public class ApplyFragment extends Fragment implements View.OnClickListener, Listener {

    private String id_Apply;
    private String user_id;
    private String auth_key;

    private EditText name;
    private EditText age;
    private EditText mainSpec;
    private EditText offSpec;
    private EditText _class;
    private EditText race;
    private EditText armory;
    private EditText wowHeroes;
    private EditText logs;
    private EditText uiSreec;
    private EditText experience;
    private EditText availableTime;
    private EditText objective;
    private EditText knownPeople;

    private TextView applyAcepted;

    private Button btnEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apply, container, false);

        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).hide();

        applyAcepted = view.findViewById(R.id.textViewApplyAcepted);

        name = view.findViewById(R.id.editText_applyName);
        age = view.findViewById(R.id.editText_applyAge);
        mainSpec = view.findViewById(R.id.editText_applyMainSpec);
        offSpec = view.findViewById(R.id.editText_applyOffSpec);
        _class = view.findViewById(R.id.editText_applyClass);
        race = view.findViewById(R.id.editText_applyRace);
        armory = view.findViewById(R.id.editText_applyArmory);
        wowHeroes = view.findViewById(R.id.editText_applyWowHeroes);
        logs = view.findViewById(R.id.editText_applyLogs);
        uiSreec = view.findViewById(R.id.editText_applyUiSreen);
        experience = view.findViewById(R.id.editText_applyExperience);
        availableTime = view.findViewById(R.id.editText_applyAvailableTime);
        objective = view.findViewById(R.id.editText_applyObjective);
        knownPeople = view.findViewById(R.id.editText_applyknownPeople);

        btnEdit = view.findViewById(R.id.button_editApply);

        setVisibilityGone();

        btnEdit.setVisibility(View.GONE);

        assert getArguments() != null;
        user_id = getArguments().getString("user_id");
        auth_key = getArguments().getString("auth_key");

        Singleton.getInstance(view.getContext()).setListener(this);
        Singleton.getInstance(view.getContext()).apiRequestGetApply(view.getContext(), auth_key, user_id);

        btnEdit.setOnClickListener(this);

        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_editApply) {

            if (btnEdit.getText().toString().equals("Save")) {
                ApplyModel auxApply = new ApplyModel();

                auxApply.setName(name.getText().toString());
                auxApply.setAge(Integer.parseInt(age.getText().toString()));
                auxApply.setMainSpec(mainSpec.getText().toString());
                auxApply.setOffSpec(offSpec.getText().toString());
                auxApply.set_class(_class.getText().toString());
                auxApply.setRace(race.getText().toString());
                auxApply.setArmory(armory.getText().toString());
                auxApply.setWowHeroes(wowHeroes.getText().toString());
                auxApply.setLogs(logs.getText().toString());
                auxApply.setUiScreen(uiSreec.getText().toString());
                auxApply.setExperience(experience.getText().toString());
                auxApply.setAvailableTime(availableTime.getText().toString());
                auxApply.setObjective(objective.getText().toString());
                auxApply.setKnownPeople(knownPeople.getText().toString());

                if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(age.getText()) || TextUtils.isEmpty(mainSpec.getText())
                        || TextUtils.isEmpty(_class.getText()) || TextUtils.isEmpty(race.getText())) {
                    Toast.makeText(getContext(), "Empty Fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (name.getText().length() > 50 || mainSpec.getText().length() > 20 || offSpec.getText().length() > 20 || _class.getText().length() > 20
                        || race.getText().length() > 20 || armory.getText().length() > 100 || wowHeroes.getText().length() > 100 || logs.getText().length() > 100
                        || uiSreec.getText().length() > 100 || experience.getText().length() > 200 || availableTime.getText().length() > 200
                        || objective.getText().length() > 100 || knownPeople.getText().length() > 50) {
                    Toast.makeText(getContext(), "Filds are to long", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (age.getText().length() > 999) {
                    Toast.makeText(getContext(), "Age Field is to long", Toast.LENGTH_SHORT).show();
                    return;
                }

                this.setEnableFalse();

                Singleton.getInstance(getContext()).setListener(this);
                Singleton.getInstance(getContext()).apiRequestUpdateApply(getContext(), auth_key, auxApply, id_Apply);

                btnEdit.setText("Edit Apply");
            } else if (btnEdit.getText().toString().equals("Create")) {
                ApplyModel auxApply = new ApplyModel();

                auxApply.setName(name.getText().toString());
                auxApply.setAge(Integer.parseInt(age.getText().toString()));
                auxApply.setMainSpec(mainSpec.getText().toString());
                auxApply.setOffSpec(offSpec.getText().toString());
                auxApply.set_class(_class.getText().toString());
                auxApply.setRace(race.getText().toString());
                auxApply.setArmory(armory.getText().toString());
                auxApply.setWowHeroes(wowHeroes.getText().toString());
                auxApply.setLogs(logs.getText().toString());
                auxApply.setUiScreen(uiSreec.getText().toString());
                auxApply.setExperience(experience.getText().toString());
                auxApply.setAvailableTime(availableTime.getText().toString());
                auxApply.setObjective(objective.getText().toString());
                auxApply.setKnownPeople(knownPeople.getText().toString());
                auxApply.setUser_id(Integer.parseInt(user_id));

                if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(age.getText()) || TextUtils.isEmpty(mainSpec.getText())
                        || TextUtils.isEmpty(_class.getText()) || TextUtils.isEmpty(race.getText())) {
                    Toast.makeText(getContext(), "Empty Fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (name.getText().length() > 50 || mainSpec.getText().length() > 20 || offSpec.getText().length() > 20 || _class.getText().length() > 20
                        || race.getText().length() > 20 || armory.getText().length() > 100 || wowHeroes.getText().length() > 100 || logs.getText().length() > 100
                        || uiSreec.getText().length() > 100 || experience.getText().length() > 200 || availableTime.getText().length() > 200
                        || objective.getText().length() > 100 || knownPeople.getText().length() > 50) {
                    Toast.makeText(getContext(), "Filds are to long", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (age.getText().length() > 999) {
                    Toast.makeText(getContext(), "Age Field is to long", Toast.LENGTH_SHORT).show();
                    return;
                }

                this.setEnableFalse();

                Singleton.getInstance(getContext()).setListener(this);
                Singleton.getInstance(getContext()).apiRequestAddApply(getContext(), auth_key, auxApply);

                btnEdit.setText("Edit Apply");
            } else {
                this.setEnableTrue();

                btnEdit.setText("Save");
            }
        }
    }

    public static ApplyFragment newInstance(String user_id, String auth_key) {
        ApplyFragment applyFragment = new ApplyFragment();
        Bundle args = new Bundle();
        args.putString("user_id", user_id);
        args.putString("auth_key", auth_key);
        applyFragment.setArguments(args);
        return applyFragment;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onRefreshApply(ApplyModel apply) {

        if (apply == null) {
            Toast.makeText(getContext(), "Create Apply", Toast.LENGTH_SHORT).show();
            applyAcepted.setText("Create Apply");
            btnEdit.setText("Create");
            this.setVisibilityVisible();
            this.setEnableTrue();
            btnEdit.setVisibility(View.VISIBLE);
            return;
        }

        id_Apply = String.valueOf(apply.getId());

        if (apply.getStatus().equals("ACCEPTED")) {

            applyAcepted.setText("Apply Acepted");

        } else if (apply.getStatus().equals("OPEN")) {
            this.setEnableFalse();
            this.setVisibilityVisible();

            btnEdit.setVisibility(View.VISIBLE);

            this.setText(apply);
        }
    }

    public void setEnableTrue() {
        name.setEnabled(true);
        age.setEnabled(true);
        mainSpec.setEnabled(true);
        offSpec.setEnabled(true);
        _class.setEnabled(true);
        race.setEnabled(true);
        armory.setEnabled(true);
        wowHeroes.setEnabled(true);
        logs.setEnabled(true);
        uiSreec.setEnabled(true);
        experience.setEnabled(true);
        availableTime.setEnabled(true);
        objective.setEnabled(true);
        knownPeople.setEnabled(true);
    }

    public void setEnableFalse() {
        name.setEnabled(false);
        age.setEnabled(false);
        mainSpec.setEnabled(false);
        offSpec.setEnabled(false);
        _class.setEnabled(false);
        race.setEnabled(false);
        armory.setEnabled(false);
        wowHeroes.setEnabled(false);
        logs.setEnabled(false);
        uiSreec.setEnabled(false);
        experience.setEnabled(false);
        availableTime.setEnabled(false);
        objective.setEnabled(false);
        knownPeople.setEnabled(false);
    }

    public void setVisibilityVisible() {
        name.setVisibility(View.VISIBLE);
        age.setVisibility(View.VISIBLE);
        mainSpec.setVisibility(View.VISIBLE);
        offSpec.setVisibility(View.VISIBLE);
        _class.setVisibility(View.VISIBLE);
        race.setVisibility(View.VISIBLE);
        armory.setVisibility(View.VISIBLE);
        wowHeroes.setVisibility(View.VISIBLE);
        logs.setVisibility(View.VISIBLE);
        uiSreec.setVisibility(View.VISIBLE);
        experience.setVisibility(View.VISIBLE);
        availableTime.setVisibility(View.VISIBLE);
        objective.setVisibility(View.VISIBLE);
        knownPeople.setVisibility(View.VISIBLE);
    }

    public void setVisibilityGone() {
        name.setVisibility(View.GONE);
        age.setVisibility(View.GONE);
        mainSpec.setVisibility(View.GONE);
        offSpec.setVisibility(View.GONE);
        _class.setVisibility(View.GONE);
        race.setVisibility(View.GONE);
        armory.setVisibility(View.GONE);
        wowHeroes.setVisibility(View.GONE);
        logs.setVisibility(View.GONE);
        uiSreec.setVisibility(View.GONE);
        experience.setVisibility(View.GONE);
        availableTime.setVisibility(View.GONE);
        objective.setVisibility(View.GONE);
        knownPeople.setVisibility(View.GONE);
    }

    public void setText(ApplyModel apply) {
        name.setText(apply.getName());
        age.setText(String.valueOf(apply.getAge()));
        mainSpec.setText(apply.getMainSpec());
        offSpec.setText(apply.getOffSpec());
        _class.setText(apply.get_class());
        race.setText(apply.getRace());
        armory.setText(apply.getArmory());
        wowHeroes.setText(apply.getWowHeroes());
        logs.setText(apply.getLogs());
        uiSreec.setText(apply.getUiScreen());
        experience.setText(apply.getExperience());
        availableTime.setText(apply.getAvailableTime());
        objective.setText(apply.getObjective());
        knownPeople.setText(apply.getKnownPeople());
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
