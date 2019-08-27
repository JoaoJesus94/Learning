package projetopsi.overcomeguildapp.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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


public class CreateTopicDialog extends DialogFragment implements Listener, View.OnClickListener, AdapterView.OnItemSelectedListener {

    private String auth_key;
    private String id_profile;
    private EditText title;
    private EditText content;
    private Spinner spinner;
    private SubjectModel subject;

    public CreateTopicDialog.Communicator communicator;

    public interface Communicator{
        void CreateTopic();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        communicator = (CreateTopicDialog.Communicator) getTargetFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_create_topic_dialog, container, false);

        TextView create = view.findViewById(R.id.textViewTopicCreateCreate);
        TextView cancel = view.findViewById(R.id.textViewTopicCreateCancel);
        TextView subject = view.findViewById(R.id.textViewTopicCreateSubject);
        spinner = view.findViewById(R.id.spinnerSubject);
        spinner.setOnItemSelectedListener(this);

        create.setOnClickListener(this);
        cancel.setOnClickListener(this);
        subject.setOnClickListener(this);

        title = view.findViewById(R.id.editTextTopicCreateTitle);
        content = view.findViewById(R.id.editTextTopicCreateContent);

        Bundle args = getArguments();
        assert args != null;
        auth_key = args.getString("auth_key");
        id_profile = args.getString("profile_id");

        Singleton.getInstance(view.getContext()).setListener(this);
        Singleton.getInstance(view.getContext()).apiRequestGetSubjects(view.getContext(), auth_key);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.textViewTopicCreateCreate){

            if(TextUtils.isEmpty(title.getText()) || TextUtils.isEmpty(content.getText()))
            {
                Toast.makeText(getContext(), "Empty Fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if(title.getText().length() > 20 || content.getText().length() > 50){
                Toast.makeText(getContext(), "Filds are to long", Toast.LENGTH_SHORT).show();
                return;
            }

            TopicsModel topic = new TopicsModel();

            topic.setTopicName(title.getText().toString());
            topic.setTopicDescription(content.getText().toString());
            topic.setTopic_id(subject.getId());
            topic.setUserID(Integer.parseInt(id_profile));

            Singleton.getInstance(getContext()).setListener(this);
            Singleton.getInstance(getContext()).apiRequestAddTopic(getContext(), auth_key, topic);

            dismiss();
        }else if(view.getId() == R.id.textViewTopicCreateCancel){
            dismiss();
        }else if(view.getId() == R.id.textViewTopicCreateSubject){
            if(TextUtils.isEmpty(title.getText()) || TextUtils.isEmpty(content.getText()))
            {
                Toast.makeText(getContext(), "Empty Fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if(title.getText().length() > 20 || content.getText().length() > 50){
                Toast.makeText(getContext(), "Filds are to long", Toast.LENGTH_SHORT).show();
                return;
            }

            SubjectModel subject = new SubjectModel();

            subject.setTopicName(title.getText().toString());
            subject.setDescription(content.getText().toString());
            subject.setUserID(Integer.parseInt(id_profile));

            Singleton.getInstance(getContext()).setListener(this);
            Singleton.getInstance(getContext()).apiRequestAddSubject(getContext(), auth_key, subject);
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        subject = (SubjectModel)parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onRefreshSubjects(ArrayList<SubjectModel> subjects) {
        ArrayAdapter<SubjectModel> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),  android.R.layout.simple_spinner_dropdown_item, subjects);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onRefreshTopics(ArrayList<TopicsModel> topics) {
        communicator.CreateTopic();
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
