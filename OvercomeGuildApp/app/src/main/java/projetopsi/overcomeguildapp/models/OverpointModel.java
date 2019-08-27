package projetopsi.overcomeguildapp.models;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OverpointModel {
    private String displayName;
    private String op;
    private String up;
    private String priority;
    private String attendance;

    public OverpointModel(String displayName, String op, String up, String priority, String attendance) {
        this.setDisplayName(displayName);
        this.setOp(op);
        this.setUp(up);
        this.setPriority(priority);
        this.setAttendance(attendance);
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getUp() {
        return up;
    }

    public void setUp(String up) {
        this.up = up;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public static ArrayList<OverpointModel> parserJsonOverpoints(JSONArray response, Context context){

        ArrayList<OverpointModel> tempOverpoint = new ArrayList<>();

        try{
            for (int i = 0; i < response.length() ; i++) {
                JSONObject overpoint = (JSONObject) response.get(i);

                String displayName = overpoint.getString("displayName");
                String op = overpoint.getString("op");
                String up = overpoint.getString("up");
                String priority = overpoint.getString("priority");
                String attendance = overpoint.getString("attendance");
                OverpointModel auxOverpoint = new OverpointModel(displayName, op, up, priority, attendance);

                tempOverpoint.add(auxOverpoint);
            }

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "Error Parser Characters:: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return tempOverpoint;
    }
}
