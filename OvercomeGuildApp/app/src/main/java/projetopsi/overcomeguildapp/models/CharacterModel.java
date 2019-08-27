package projetopsi.overcomeguildapp.models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CharacterModel {
    private int id;
    private String charName;
    private int level;
    private String _class;
    private String race;
    private String mainSpec;
    private String offSpec;
    private long user_id;
    private long main;

    public CharacterModel(int id, String charName, int level, String _class, String race, String mainSpec, String offSpec, long user_id, long main) {
        this.setId(id);
        this.setCharName(charName);
        this.setLevel(level);
        this.set_Class(_class);
        this.setRace(race);
        this.setMainSpec(mainSpec);
        this.setOffSpec(offSpec);
        this.setUser_id(user_id);
        this.setMain(main);
    }

    public CharacterModel(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCharName() {
        return charName;
    }

    public void setCharName(String charName) {
        this.charName = charName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String get_Class() {
        return _class;
    }

    public void set_Class(String _class) {
        this._class = _class;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
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

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getMain() {
        return main;
    }

    public void setMain(long main) {
        this.main = main;
    }

    public static ArrayList<CharacterModel> parserJsonCharacters(JSONArray response, Context context){

        ArrayList<CharacterModel> tempCharacterList = new ArrayList<>();

        try{
            for (int i = 0; i < response.length() ; i++) {
                JSONObject character = (JSONObject) response.get(i);

                int id = character.getInt("id");
                String chaName = character.getString("charName");
                int level = character.getInt("level");
                String _class = character.getString("class");
                String race = character.getString("race");
                String mainSpec = character.getString("mainSpec");
                String offSpec = character.getString("offSpec");
                long user_id = character.getLong("User_id");
                long main = character.getLong("main");

                CharacterModel auxCharacterList = new CharacterModel(id, chaName, level, _class, race, mainSpec, offSpec, user_id, main);

                tempCharacterList.add(auxCharacterList);
            }

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "Error Parser Characters:: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return tempCharacterList;
    }

    public static CharacterModel parserJsonCharacter(String response, Context context) {

        CharacterModel auxCharacter = null;

        try{
            JSONObject character = new JSONObject(response);

            int id = character.getInt("id");
            String chaName = character.getString("charName");
            int level = character.getInt("level");
            String _class = character.getString("class");
            String race = character.getString("race");
            String mainSpec = character.getString("mainSpec");
            String offSpec = character.getString("offSpec");
            long user_id = character.getLong("User_id");
            long main = character.getLong("main");

            auxCharacter = new CharacterModel(id, chaName, level, _class, race, mainSpec, offSpec, user_id, main);

        }catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(context, "Error Parser Character: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }

        return auxCharacter;
    }

    @NonNull
    @Override
    public String toString() {
        return "CharName: " + getCharName() + " level: " + getLevel() + " class: " + get_Class() + " race: " + getRace();
    }
}
