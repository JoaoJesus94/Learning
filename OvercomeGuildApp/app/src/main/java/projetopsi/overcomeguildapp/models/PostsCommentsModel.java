package projetopsi.overcomeguildapp.models;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PostsCommentsModel {
    private int id;
    private String content;
    private String displayName;
    private int user_id;

    public PostsCommentsModel(int id, String content, int user_id){
        this.setId(id);
        this.setContent(content);
        this.setUser_id(user_id);
    }

    public PostsCommentsModel(int id, String content, String displayName){
        this.setId(id);
        this.setContent(content);
        this.setDisplayName(displayName);
    }

    public PostsCommentsModel(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getUser_id(){
        return user_id;
    }

    public void setUser_id(int user_id){
        this.user_id = user_id;
    }

    public static ArrayList<PostsCommentsModel> parserJsonPostComments(JSONArray response, Context context){

        ArrayList<PostsCommentsModel> tempPostComment = new ArrayList<>();

        try{
            for (int i = 0; i < response.length() ; i++) {
                JSONObject postComment = (JSONObject) response.get(i);

                String id = postComment.getString("id");
                String content = postComment.getString("content");
                String displayName = postComment.getString("displayName");
                PostsCommentsModel auxPostComment = new PostsCommentsModel(Integer.parseInt(id), content, displayName);

                tempPostComment.add(auxPostComment);
            }

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "Error Parser Characters:: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return tempPostComment;
    }

    public static PostsCommentsModel parserJsonPostComment(String response, Context context) {

        PostsCommentsModel auxPostComment = null;

        try{
            JSONObject postComment = new JSONObject(response);

            String id = postComment.getString("id");
            String content = postComment.getString("content");

            auxPostComment = new PostsCommentsModel();

            auxPostComment.setId(Integer.parseInt(id));
            auxPostComment.setContent(content);

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "Error Parser Character: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }

        return auxPostComment;
    }
}
