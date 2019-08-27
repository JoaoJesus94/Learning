package projetopsi.overcomeguildapp.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TopicsModel {
    private int id;
    private String displayName;
    private String topicName;
    private String topicDescription;
    private int userID;
    private int Topic_id;

    public TopicsModel(int id, String displayName, String topicName, String topicDescription, int userID){
        this.setId(id);
        this.setDisplayName(displayName);
        this.setTopicName(topicName);
        this.setTopicDescription(topicDescription);
        this.setUserID(userID);
    }

    public TopicsModel(){};

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

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicDescription() {
        return topicDescription;
    }

    public void setTopicDescription(String topicDescription) {
        this.topicDescription = topicDescription;
    }

    public int getTopic_id() {
        return Topic_id;
    }

    public void setTopic_id(int Topic_id) {
        this.Topic_id = Topic_id;
    }

    public int getUserID(){
        return userID;
    }

    public void setUserID(int userID){
        this.userID = userID;
    }

    public static ArrayList<TopicsModel> parserJsonTopics(JSONArray response, Context context){

        ArrayList<TopicsModel> tempTopics = new ArrayList<>();

        try{
            for (int i = 0; i < response.length() ; i++) {
                JSONObject topic = (JSONObject) response.get(i);

                String id = topic.getString("id");
                String displayName = topic.getString("displayName");
                String topicName = topic.getString("topicName");
                String topicDescription = topic.getString("topicDescription");
                String userID = topic.getString("User_id");

                TopicsModel auxTopic = new TopicsModel(Integer.parseInt(id), displayName, topicName, topicDescription, Integer.parseInt(userID));

                tempTopics.add(auxTopic);
            }

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "Error Parser Topics:: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return tempTopics;
    }

    public static TopicsModel parserJsonTopic(String response, Context context) {

        TopicsModel auxTopic = null;

        try{
            JSONObject topic = new JSONObject(response);

            String id = topic.getString("id");
            String topicName = topic.getString("topicName");
            String topicDescription = topic.getString("topicDescription");

            auxTopic = new TopicsModel();

            auxTopic.setId(Integer.parseInt(id));
            auxTopic.setTopicName(topicName);
            auxTopic.setTopicDescription(topicDescription);

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "Error Parser Topic: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }

        return auxTopic;
    }
}
