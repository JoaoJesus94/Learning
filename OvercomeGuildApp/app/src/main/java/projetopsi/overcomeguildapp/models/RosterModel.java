package projetopsi.overcomeguildapp.models;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class RosterModel {
    private String username;
    private String email;
    private String created_at;
    private String displayName;
    private String rank;
    private String role;

    public RosterModel(String username, String email, String created_at, String displayName, String rank, String role){
        this.setUsername(username);
        this.setEmail(email);
        this.setCreated_at(created_at);
        this.setDisplayName(displayName);
        this.setRank(rank);
        this.setRole(role);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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

    public static RosterModel parserJsonRoster(String response, Context context){

        RosterModel auxRoster = null;

        try{
                JSONObject roster = new JSONObject(response);

                String username = roster.getString("username");
                String email = roster.getString("email");
                String created_at = roster.getString("created_at");
                String displayName = roster.getString("displayName");
                String rank = roster.getString("rank");
                String role = roster.getString("role");

                auxRoster = new RosterModel(username, email, created_at, displayName, rank, role);

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "Error Parser Roster:: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return auxRoster;
    }
}
