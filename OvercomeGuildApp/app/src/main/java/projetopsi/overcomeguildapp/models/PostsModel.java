package projetopsi.overcomeguildapp.models;


import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PostsModel {
    private int id;
    private String displayName;
    private String title;
    private String content;
    private int userID;

    public PostsModel(int id, String displayName, String title, String content, int userID){
        this.setId(id);
        this.setDisplayName(displayName);
        this.setTitle(title);
        this.setContent(content);
        this.setUserID(userID);
    }

    public PostsModel(){

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUserID(){
        return userID;
    }

    public void setUserID(int userID){
        this.userID = userID;
    }

    public static ArrayList<PostsModel> parserJsonPosts(JSONArray response, Context context){

        ArrayList<PostsModel> tempPosts = new ArrayList<>();

        try{
            for (int i = 0; i < response.length() ; i++) {
                JSONObject topic = (JSONObject) response.get(i);

                String id = topic.getString("id");
                String displayName = topic.getString("displayName");
                String topicName = topic.getString("title");
                String topicDescription = topic.getString("content");
                String User_id = topic.getString("User_id");
                PostsModel auxPost = new PostsModel(Integer.parseInt(id), displayName, topicName, topicDescription, Integer.parseInt(User_id));

                tempPosts.add(auxPost);
            }

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "Error Parser Characters:: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return tempPosts;
    }

    public static PostsModel parserJsonPost(String response, Context context) {

        PostsModel auxPost = null;

        try{
            JSONObject topic = new JSONObject(response);

            String id = topic.getString("id");
            String topicTitle = topic.getString("title");
            String topicContent = topic.getString("content");

            auxPost = new PostsModel();

            auxPost.setId(Integer.parseInt(id));
            auxPost.setTitle(topicTitle);
            auxPost.setContent(topicContent);

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "Error Parser Character: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }

        return auxPost;
    }
}

