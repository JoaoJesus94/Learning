package projetopsi.overcomeguildapp.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EventModel {
    private int id;
    private String eventName;
    private String date;
    private String category;
    private String description;

    public EventModel(int id, String eventName, String date, String category, String description){
        this.setId(id);
        this.setEventName(eventName);
        this.setDate(date);
        this.setCategory(category);
        this.setDescription(description);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static ArrayList<EventModel> parserJsonEvents(JSONArray response, Context context){

        ArrayList<EventModel> tempEventsList = new ArrayList<>();

        try{
            for (int i = 0; i < response.length() ; i++) {
                JSONObject event = (JSONObject) response.get(i);

                int id = event.getInt("id");
                String eventName = event.getString("eventName");
                String date = event.getString("date");
                String category = event.getString("category");
                String description = event.getString("description");

                EventModel auxEvent = new EventModel(id, eventName, date, category, description);

                tempEventsList.add(auxEvent);

            }

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "Error Parser Events:: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return tempEventsList;
    }

    public static EventModel parserJsonEvent(String response, Context context) {

        EventModel auxEvent = null;

        try{
            JSONObject event = new JSONObject(response);

            int id = event.getInt("id");
            String eventName = event.getString("eventName");
            String date = event.getString("date");
            String category = event.getString("category");
            String description = event.getString("description");

            auxEvent = new EventModel(id, eventName, date, category, description);

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "Error Parser Event: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }

        return auxEvent;
    }
}
