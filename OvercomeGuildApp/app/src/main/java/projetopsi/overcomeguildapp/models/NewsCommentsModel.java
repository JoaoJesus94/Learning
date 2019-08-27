package projetopsi.overcomeguildapp.models;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsCommentsModel {
    private int id;
    private String content;
    private String displayName;
    private int user_id;

    public NewsCommentsModel(int id, String content,String displayName){
        this.setId(id);
        this.setContent(content);
        this.setDisplayName(displayName);
    }

    public NewsCommentsModel(){

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

    public String getdisplayName(){
        return displayName;
    }

    public void setDisplayName(String displayName){
        this.displayName = displayName;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public static ArrayList<NewsCommentsModel> parserJsonNewsComments(JSONArray response, Context context){

        ArrayList<NewsCommentsModel> tempNewsComments = new ArrayList<>();

        try{
            for (int i = 0; i < response.length() ; i++) {
                JSONObject newComment = (JSONObject) response.get(i);

                String id = newComment.getString("id");
                String content = newComment.getString("content");
                String displayName = newComment.getString("displayName");

                NewsCommentsModel auxNewComment = new NewsCommentsModel(Integer.parseInt(id), content, displayName);

                tempNewsComments.add(auxNewComment);
            }

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "Error Parser NewsComments:: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return tempNewsComments;
    }

    public static NewsCommentsModel parserJsonNewComment(String response, Context context) {

        NewsCommentsModel auxNewComment = null;

        try{
            JSONObject _new = new JSONObject(response);

            String id = _new.getString("id");
            String topicContent = _new.getString("content");

            auxNewComment = new NewsCommentsModel();

            auxNewComment.setId(Integer.parseInt(id));
            auxNewComment.setContent(topicContent);

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "Error Parser NewComment: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }

        return auxNewComment;
    }
}
