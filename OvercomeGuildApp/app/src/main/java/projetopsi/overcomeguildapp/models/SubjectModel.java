package projetopsi.overcomeguildapp.models;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubjectModel {
    private int id;
    private String topicName;
    private String description;
    private int userID;

    public SubjectModel(int id, String topicName){
        this.setId(id);
        this.setTopicName(topicName);
    }

    public SubjectModel(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserID(){
        return userID;
    }

    public void setUserID(int userID){
        this.userID = userID;
    }

    public static ArrayList<SubjectModel> parserJsonSubjects(JSONArray response, Context context){

        ArrayList<SubjectModel> tempSubjects = new ArrayList<>();

        try{
            for (int i = 0; i < response.length() ; i++) {
                JSONObject subject = (JSONObject) response.get(i);

                String id = subject.getString("id");
                String topicName = subject.getString("topicName");

                SubjectModel auxSubject = new SubjectModel(Integer.parseInt(id), topicName);

                tempSubjects.add(auxSubject);
            }

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "Error Parser Subjects:: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return tempSubjects;
    }

    public static SubjectModel parserJsonSubject(String response, Context context) {

        SubjectModel auxSubject = null;

        try{
            JSONObject subject = new JSONObject(response);

            String id = subject.getString("id");
            String topicName = subject.getString("topicName");

            auxSubject = new SubjectModel();

            auxSubject.setId(Integer.parseInt(id));
            auxSubject.setTopicName(topicName);

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "Error Parser Subject: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }

        return auxSubject;
    }

    @Override
    public String toString(){
        return topicName;
    }
}
