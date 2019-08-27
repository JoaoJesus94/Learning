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


public class EditCharacterDialog extends DialogFragment implements View.OnClickListener, Listener, AdapterView.OnItemSelectedListener {

    private ArrayAdapter adapterRaces;
    private ArrayAdapter adapterClasses;
    private Spinner spinnerRaces;
    private Spinner spinnerClasses;

    private EditText charName;
    private EditText level;
    private EditText mainSpec;
    private EditText offSpec;

    private String id_character;
    private String id_profile;
    private String auth_key;

    public EditCharacterDialog.Communicator communicator;

    public interface Communicator{
        void updateCharacterList();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
            communicator = (EditCharacterDialog.Communicator) getTargetFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_character_dialog, container, false);

        TextView cancel = view.findViewById(R.id.textView_editCharcater_Cancel);
        TextView edit = view.findViewById(R.id.textView_editCharacter_edit);

        charName = view.findViewById(R.id.editText_editCaracter_charName2h);
        level = view.findViewById(R.id.editText_editCharacter_Level2);
        mainSpec = view.findViewById(R.id.editText_editCharacter_MainSpec2);
        offSpec = view.findViewById(R.id.editText_editCharacter_OffSpec2);
        spinnerRaces = view.findViewById(R.id.spinner_race2);
        spinnerClasses = view.findViewById(R.id.spinner_class2);

        adapterRaces = ArrayAdapter.createFromResource(getContext(), R.array.character_races, android.R.layout.simple_spinner_dropdown_item);
        spinnerRaces.setAdapter(adapterRaces);
        spinnerRaces.setOnItemSelectedListener(this);

        cancel.setOnClickListener(this);
        if(Singleton.getInstance(getContext()).checkInternetState(getContext())){
            edit.setOnClickListener(this);
        }

        Bundle args = getArguments();
        assert args != null;
        id_character = args.getString("id_character");
        String _charName = args.getString("charName");
        String _level = args.getString("level");
        String _mainSpec = args.getString("mainSpec");
        String _offSpec = args.getString("offSpec");


        charName.setText(_charName);
        level.setText(_level);
        mainSpec.setText(_mainSpec);
        offSpec.setText(_offSpec);

        id_profile = args.getString("id_profile");
        auth_key = args.getString("auth_key");

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (spinnerRaces.getSelectedItem().equals("Select Race")) {
            spinnerClasses.setAdapter(null);
        } else if (spinnerRaces.getSelectedItem().equals("Draenei")) {
            adapterClasses = ArrayAdapter.createFromResource(getContext(), R.array.draenei_classes, android.R.layout.simple_spinner_item);
            spinnerClasses.setAdapter(adapterClasses);
        } else if (spinnerRaces.getSelectedItem().equals("Dwarf")) {
            adapterClasses = ArrayAdapter.createFromResource(getContext(), R.array.dwarf_classes, android.R.layout.simple_spinner_item);
            spinnerClasses.setAdapter(adapterClasses);
        } else if (spinnerRaces.getSelectedItem().equals("Gnome")) {
            adapterClasses = ArrayAdapter.createFromResource(getContext(), R.array.gnome_classes, android.R.layout.simple_spinner_item);
            spinnerClasses.setAdapter(adapterClasses);
        } else if (spinnerRaces.getSelectedItem().equals("Human")) {
            adapterClasses = ArrayAdapter.createFromResource(getContext(), R.array.human_classes, android.R.layout.simple_spinner_item);
            spinnerClasses.setAdapter(adapterClasses);
        } else if (spinnerRaces.getSelectedItem().equals("Night Elf")) {
            adapterClasses = ArrayAdapter.createFromResource(getContext(), R.array.night_elf_classes, android.R.layout.simple_spinner_item);
            spinnerClasses.setAdapter(adapterClasses);
        } else if (spinnerRaces.getSelectedItem().equals("Worgen")) {
            adapterClasses = ArrayAdapter.createFromResource(getContext(), R.array.worgen_classes, android.R.layout.simple_spinner_item);
            spinnerClasses.setAdapter(adapterClasses);
        } else if (spinnerRaces.getSelectedItem().equals("Pandaren")) {
            adapterClasses = ArrayAdapter.createFromResource(getContext(), R.array.pandaren_classes, android.R.layout.simple_spinner_item);
            spinnerClasses.setAdapter(adapterClasses);
        } else if (spinnerRaces.getSelectedItem().equals("Dark Iron Dwarf")) {
            adapterClasses = ArrayAdapter.createFromResource(getContext(), R.array.dark_iron_dwarf_classes, android.R.layout.simple_spinner_item);
            spinnerClasses.setAdapter(adapterClasses);
        } else if (spinnerRaces.getSelectedItem().equals("Kul Tiran Human")) {
            adapterClasses = ArrayAdapter.createFromResource(getContext(), R.array.kul_tiran_human_classes, android.R.layout.simple_spinner_item);
            spinnerClasses.setAdapter(adapterClasses);
        } else if (spinnerRaces.getSelectedItem().equals("Lightforged Draenaei")) {
            adapterClasses = ArrayAdapter.createFromResource(getContext(), R.array.lightforged_draenei_classes, android.R.layout.simple_spinner_item);
            spinnerClasses.setAdapter(adapterClasses);
        } else if (spinnerRaces.getSelectedItem().equals("Void Elf")) {
            adapterClasses = ArrayAdapter.createFromResource(getContext(), R.array.void_elf_classes, android.R.layout.simple_spinner_item);
            spinnerClasses.setAdapter(adapterClasses);
        }
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.textView_editCharacter_edit){

            if(TextUtils.isEmpty(charName.getText()) || TextUtils.isEmpty(level.getText()) || TextUtils.isEmpty(spinnerClasses.getSelectedItem().toString())
                    || TextUtils.isEmpty(spinnerRaces.getSelectedItem().toString()) || TextUtils.isEmpty(mainSpec.getText()) || TextUtils.isEmpty(offSpec.getText()))
            {
                Toast.makeText(getContext(), "Empty Fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if(charName.getText().length() > 20 || spinnerClasses.getSelectedItem().toString().length() >20 || spinnerRaces.getSelectedItem().toString().length() > 20 || mainSpec.getText().length() > 20 || offSpec.getText().length() > 20){
                Toast.makeText(getContext(), "Filds are to long", Toast.LENGTH_SHORT).show();
                return;
            }

            if(level.getText().length() > 9){
                Toast.makeText(getContext(), "Level field is to long", Toast.LENGTH_SHORT).show();
                return;
            }

            CharacterModel auxCharacter = new CharacterModel();
            auxCharacter.setCharName(charName.getText().toString());
            auxCharacter.setLevel(Integer.parseInt(level.getText().toString()));
            auxCharacter.set_Class(spinnerClasses.getSelectedItem().toString());
            auxCharacter.setRace(spinnerRaces.getSelectedItem().toString());
            auxCharacter.setMainSpec(mainSpec.getText().toString());
            auxCharacter.setOffSpec(offSpec.getText().toString());
            auxCharacter.setUser_id(Integer.parseInt(id_profile));

            Singleton.getInstance(getContext()).setListener(this);
            Singleton.getInstance(getContext()).apiRequestUpdateCharacter(getContext(), auth_key, auxCharacter, id_character);
            
            getDialog().dismiss();
        }else if(v.getId() == R.id.textView_editCharcater_Cancel){
            getDialog().dismiss();
        }
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
        communicator.updateCharacterList();
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
