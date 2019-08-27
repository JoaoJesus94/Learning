package projetopsi.overcomeguildapp.models;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsModel {
    private int id;
    private String title;
    private String content;
    private int User_id;

    public NewsModel(int id, String title, String content, int user_id){
        this.setId(id);
        this.setTitle(title);
        this.setContent(content);
        this.setUser_id(user_id);
    }

    public NewsModel(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getUser_id() {
        return User_id;
    }

    public void setUser_id(int user_id) {
        User_id = user_id;
    }

    public static ArrayList<NewsModel> parserJsonNews(JSONArray response, Context context){

        ArrayList<NewsModel> tempNews = new ArrayList<>();

        try{
            for (int i = 0; i < response.length() ; i++) {
                JSONObject _new = (JSONObject) response.get(i);

                String id = _new.getString("id");
                String title = _new.getString("tittle");
                String content = _new.getString("content");
                String user_id = _new.getString("User_id");

                NewsModel auxNew = new NewsModel(Integer.parseInt(id), title, content, Integer.parseInt(user_id));

                tempNews.add(auxNew);
            }

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "Error Parser News:: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return tempNews;
    }

    public static NewsModel parserJsonNew(String response, Context context) {

        NewsModel auxNew = null;

        try{
            JSONObject _new = new JSONObject(response);

            String id = _new.getString("id");
            String topicTitle = _new.getString("title");
            String topicContent = _new.getString("content");

            auxNew = new NewsModel();

            auxNew.setId(Integer.parseInt(id));
            auxNew.setTitle(topicTitle);
            auxNew.setContent(topicContent);

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "Error Parser New: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }

        return auxNew;
    }
}
