package projetopsi.overcomeguildapp.models;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserProfileHasEventModel {
    private int id_userprofile;
    private int id_event;
    private String displayName;
    private String attending;
    private String role;

    public UserProfileHasEventModel(int id_userprofile, int id_event, String displayName, String attending, String role){
        this.setId_userprofile(id_userprofile);
        this.setId_event(id_event);
        this.setDisplayName(displayName);
        this.setAttending(attending);
        this.setRole(role);
    }

    public UserProfileHasEventModel(int id_userprofile, int id_event, String attending, String role){
        this.setId_userprofile(id_userprofile);
        this.setId_event(id_event);
        this.setAttending(attending);
        this.setRole(role);
    }

    public UserProfileHasEventModel(){

    }

    public int getId_userprofile() {
        return id_userprofile;
    }

    public void setId_userprofile(int id_userprofile) {
        this.id_userprofile = id_userprofile;
    }

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAttending() {
        return attending;
    }

    public void setAttending(String attending) {
        this.attending = attending;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static ArrayList<UserProfileHasEventModel> parserJsonUserProfileHasEvents(JSONArray response, Context context){

        ArrayList<UserProfileHasEventModel> tempUserProfileHasEventsList = new ArrayList<>();

        try{
            for (int i = 0; i < response.length() ; i++) {
                JSONObject userProfileHasEvent = (JSONObject) response.get(i);

                int id_profile = userProfileHasEvent.getInt("userprofile_id");
                int id_event = userProfileHasEvent.getInt("event_id");
                String displayName = userProfileHasEvent.getString("displayName");
                String attending = userProfileHasEvent.getString("attending");
                String role = userProfileHasEvent.getString("role");

                UserProfileHasEventModel auxUserProfileHasEvent = new UserProfileHasEventModel(id_profile, id_event, displayName, attending, role);

                tempUserProfileHasEventsList.add(auxUserProfileHasEvent);
            }
        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "Error Parser UserProfileHasEvents:: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return tempUserProfileHasEventsList;
    }

    public static UserProfileHasEventModel parserJsonUserProfileHasEvent(String response, Context context) {

        UserProfileHasEventModel auxUserProfileHasEvent = null;

        try{
            JSONObject userProfileHasEvent = new JSONObject(response);

            int id_profile = userProfileHasEvent.getInt("userprofile_id");
            int id_event = userProfileHasEvent.getInt("event_id");
            String attending = userProfileHasEvent.getString("attending");
            String role = userProfileHasEvent.getString("role");

            auxUserProfileHasEvent = new UserProfileHasEventModel(id_profile, id_event, attending, role);

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "UserProfileHasEvents:: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }

        return auxUserProfileHasEvent;
    }
}
