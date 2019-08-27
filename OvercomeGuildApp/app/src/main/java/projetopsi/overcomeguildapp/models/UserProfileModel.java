package projetopsi.overcomeguildapp.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserProfileModel {
    private int id;
    private String displayName;
    private String rank;
    private String role;
    private long user_id;

    public UserProfileModel(int id, String displayName, String rank, String role, long user_id){
        this.setId(id);
        this.setDisplayName(displayName);
        this.setRank(rank);
        this.setRole(role);
        this.setUser_id(user_id);
    }

    public UserProfileModel(String displayName, String rank, String role){
        this.setDisplayName(displayName);
        this.setRank(rank);
        this.setRole(role);
    }

    public UserProfileModel(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getUser_id(){
        return user_id;
    }

    public void setUser_id(long user_id){
        this.user_id = user_id;
    }

    public static ArrayList<UserProfileModel> parserJsonUserProfiles(JSONArray response, Context context){

        ArrayList<UserProfileModel> tempUserProfileList = new ArrayList<>();

        try{
            for (int i = 0; i < response.length() ; i++) {
                JSONObject userprofile = (JSONObject) response.get(i);

                int id = userprofile.getInt("id");
                String displayName = userprofile.getString("displayName");
                String rank = userprofile.getString("rank");
                String role = userprofile.getString("role");
                long user_id = userprofile.getLong("user_id");

                UserProfileModel auxUserprofile = new UserProfileModel(id, displayName, rank, role, user_id);

                tempUserProfileList.add(auxUserprofile);

            }

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "Error Parser UserProfiles:: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return tempUserProfileList;
    }

    public static UserProfileModel parserJsonUserProfile(String response, Context context) {

        UserProfileModel auxUserprofile = null;

        try{
            JSONObject userprofile = new JSONObject(response);

            int id = userprofile.getInt("id");
            String displayName = userprofile.getString("displayName");
            String rank = userprofile.getString("rank");
            String role = userprofile.getString("role");
            long user_id = userprofile.getLong("user_id");

            auxUserprofile = new UserProfileModel(id, displayName, rank, role, user_id);

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "Error Parser UserProfile: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }

        return auxUserprofile;
    }

    @NonNull
    @Override
    public String toString(){
        return String.valueOf(id) + " " + displayName + " " + rank + " " + role + " " + user_id;
    }
}
