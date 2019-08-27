package projetopsi.overcomeguildapp.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class UserModel {

    private int id;
    private String username;
    private String auth_key;
    private String email;
    public UserModel(int id,String auth_key, String email){
        this.setId(id);
        this.setAuth_key(auth_key);
        this.setEmail(email);
    }

    public UserModel(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getAuth_key() {
        return auth_key;
    }

    public void setAuth_key(String auth_key) {
        this.auth_key = auth_key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static UserModel parserJsonUser(String response, Context context) {

        UserModel auxUser = null;

        try{
            JSONObject user = new JSONObject(response);

            int id = user.getInt("id");
            String auth_key = user.getString("auth_key");
            String email = user.getString("email");

            auxUser = new UserModel(id, auth_key, email);

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }

        return auxUser;
    }

    @NonNull
    @Override
    public String toString(){
        return String.valueOf(id) + " " + auth_key + " " + email;
    }
}

