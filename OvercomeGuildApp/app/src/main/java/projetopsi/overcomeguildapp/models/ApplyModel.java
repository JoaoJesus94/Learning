package projetopsi.overcomeguildapp.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ApplyModel {

    private int id;
    private String status;
    private String name;
    private int age;
    private String mainSpec;
    private String offSpec;
    private String _class;
    private String race;
    private String armory;
    private String wowHeroes;
    private String logs;
    private String uiScreen;
    private String experience;
    private String availableTime;
    private String objective;
    private String knownPeople;
    private int user_id;

    public ApplyModel (int id, String status, String name, int age, String mainSpec, String offSpec,
                  String _class, String race, String armory, String wowHeroes, String logs, String uiScreen,
                  String experience, String availableTime, String objective, String knownPeople, int user_id){
        this.setId(id);
        this.setStatus(status);
        this.setName(name);
        this.setAge(age);
        this.setMainSpec(mainSpec);
        this.setOffSpec(offSpec);
        this.set_class(_class);
        this.setRace(race);
        this.setArmory(armory);
        this.setWowHeroes(wowHeroes);
        this.setLogs(logs);
        this.setUiScreen(uiScreen);
        this.setExperience(experience);
        this.setAvailableTime(availableTime);
        this.setObjective(objective);
        this.setKnownPeople(knownPeople);
        this.setUser_id(user_id);
    }

    public ApplyModel(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMainSpec() {
        return mainSpec;
    }

    public void setMainSpec(String mainSpec) {
        this.mainSpec = mainSpec;
    }

    public String getOffSpec() {
        return offSpec;
    }

    public void setOffSpec(String offSpec) {
        this.offSpec = offSpec;
    }

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getArmory() {
        return armory;
    }

    public void setArmory(String armory) {
        this.armory = armory;
    }

    public String getWowHeroes() {
        return wowHeroes;
    }

    public void setWowHeroes(String wowHeroes) {
        this.wowHeroes = wowHeroes;
    }

    public String getLogs() {
        return logs;
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }

    public String getUiScreen() {
        return uiScreen;
    }

    public void setUiScreen(String uiScreen) {
        this.uiScreen = uiScreen;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(String availableTime) {
        this.availableTime = availableTime;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getKnownPeople() {
        return knownPeople;
    }

    public void setKnownPeople(String knownPeople) {
        this.knownPeople = knownPeople;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public static ArrayList<ApplyModel> parserJsonApplys(JSONArray response, Context context){

        ArrayList<ApplyModel> tempApplyList = new ArrayList<>();

        try{
            for (int i = 0; i < response.length() ; i++) {
                JSONObject apply = (JSONObject) response.get(i);

                int id = apply.getInt("id");
                String status = apply.getString("status");
                String name = apply.getString("name");
                int age = apply.getInt("age");
                String mainSpec = apply.getString("mainSpec");
                String offSpec = apply.getString("offSpec");
                String _class = apply.getString("class");
                String race = apply.getString("race");
                String armory = apply.getString("armory");
                String wowHeroes = apply.getString("wowHeroes");
                String logs = apply.getString("logs");
                String uiScreen = apply.getString("uiScreen");
                String experience = apply.getString("experience");
                String availableTime = apply.getString("availableTime");
                String objective = apply.getString("objective");
                String knownPeople = apply.getString("knownPeople");
                int user_id = apply.getInt("user_id");

                ApplyModel auxApply = new ApplyModel(id, status, name, age, mainSpec, offSpec, _class, race,
                        armory, wowHeroes, logs, uiScreen, experience, availableTime, objective, knownPeople, user_id);

                tempApplyList.add(auxApply);

            }

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return tempApplyList;
    }

    public static ApplyModel parserJsonApply(String response, Context context) {

        ApplyModel auxApply = null;

        try{
            JSONObject apply = new JSONObject(response);

            int id = apply.getInt("id");
            String status = apply.getString("status");
            String name = apply.getString("name");
            int age = apply.getInt("age");
            String mainSpec = apply.getString("mainSpec");
            String offSpec = apply.getString("offSpec");
            String _class = apply.getString("class");
            String race = apply.getString("race");
            String armory = apply.getString("armory");
            String wowHeroes = apply.getString("wowHeroes");
            String logs = apply.getString("logs");
            String uiScreen = apply.getString("uiScreen");
            String experience = apply.getString("experience");
            String availableTime = apply.getString("availableTime");
            String objective = apply.getString("objective");
            String knownPeople = apply.getString("knownPeople");
            int user_id = apply.getInt("user_id");

            auxApply = new ApplyModel(id, status, name, age, mainSpec, offSpec, _class, race,
                    armory, wowHeroes, logs, uiScreen, experience, availableTime, objective, knownPeople, user_id);

        }catch (JSONException ignored){
        }

        return auxApply;
    }

}