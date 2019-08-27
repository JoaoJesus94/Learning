package projetopsi.overcomeguildapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import projetopsi.overcomeguildapp.models.CharacterModel;
import projetopsi.overcomeguildapp.models.UserProfileModel;

public class DataBase extends SQLiteOpenHelper {

    private static final String DB_NAME = "OvercomeGuildApp";

    private static final String TABLE_USERPROFILE = "userprofile";
    private static final String TABLE_CHARACTER = "character";

    private static final String ID_USERPROFILE = "id";
    private static final String DISPLAYNAME_USERPROFILE = "displayName";
    private static final String RANK_USERPROFILE = "rank";
    private static final String ROLE_USERPROFILE = "role";
    private static final String USERID_USERPROFILE = "user_id";

    private static final String ID_CHARACTER = "id";
    private static final String CHARNAME_CHARACTER = "charName";
    private static final String LEVEL_CHARACTER = "level";
    private static final String CLASS_CHARACTER = "class";
    private static final String RACE_CHARACTER = "race";
    private static final String MAINSPEC_CHARACTER = "mainSpec";
    private static final String OFFSPEC_CHARACTER = "offSpec";
    private static final String USERID_CHARACTER = "User_id";
    private static final String MAIN_CHARACTER = "main";

    private final SQLiteDatabase database;

    public DataBase(Context context){
        super(context, DB_NAME, null, 1);
        this.database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createUserProfileTable = "CREATE TABLE " + TABLE_USERPROFILE + "" +
                                        "("
                                        + ID_USERPROFILE + " INTEGER PRIMARY KEY, "
                                        + DISPLAYNAME_USERPROFILE + " TEXT NOT NULL, "
                                        + RANK_USERPROFILE + " TEXT, "
                                        + ROLE_USERPROFILE + " INTEGER, "
                                        + USERID_USERPROFILE + " LONG NOT NULL "
                                        + ");";

        String createCharacterTable  = "CREATE TABLE " + TABLE_CHARACTER + "" +
                                        "("
                                        + ID_CHARACTER + " INTEGER PRIMARY KEY, "
                                        + CHARNAME_CHARACTER + " TEXT NOT NULL, "
                                        + LEVEL_CHARACTER + " INTEGER NOT NULL, "
                                        + CLASS_CHARACTER + " TEXT NOT NULL, "
                                        + RACE_CHARACTER + " TEXT NOT NULL, "
                                        + MAINSPEC_CHARACTER + " TEXT NOT NULL, "
                                        + OFFSPEC_CHARACTER + " TEXT, "
                                        + USERID_CHARACTER + " LONG NOT NULL, "
                                        + MAIN_CHARACTER + " LONG NOT NULL "
                                        + ");";

        db.execSQL(createUserProfileTable);
        db.execSQL(createCharacterTable);
        System.out.println("-->  DB created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERPROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHARACTER);
        this.onCreate(db);
    }

    // TABLE USERPROFILE ///////////////////////////////////////////////////////////////////////////

    public void addUserProfileDB(UserProfileModel userProfile){
        ContentValues values = getUserProfileValues(userProfile);
        this.database.insert(TABLE_USERPROFILE, null, values);
    }

    public void addUserProfilesDB(ArrayList<UserProfileModel> userProfileArrayList){
        this.deleteAllUserProfiles();
        for(UserProfileModel userProfile : userProfileArrayList){
            addUserProfileDB(userProfile);
        }
    }

    public UserProfileModel getUserProfile(String idUser){
        UserProfileModel auxUserprofile = null;

        Cursor cursor = this.database.query(TABLE_USERPROFILE, new String[]{ID_USERPROFILE, DISPLAYNAME_USERPROFILE, RANK_USERPROFILE, ROLE_USERPROFILE, USERID_USERPROFILE}, USERID_USERPROFILE + " = ?", new String[]{idUser}, null, null, null);

        if(cursor.moveToFirst()){
            do {
                auxUserprofile = new UserProfileModel(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getLong(4));
            }while(cursor.moveToNext());
        }
        return auxUserprofile;
    }

    public ArrayList<UserProfileModel> getAllUserProfiles(){
        ArrayList<UserProfileModel> userprofiles = new ArrayList<>();

        Cursor cursor = this.database.query(TABLE_USERPROFILE, new String[]{ID_USERPROFILE, DISPLAYNAME_USERPROFILE, RANK_USERPROFILE, ROLE_USERPROFILE, USERID_USERPROFILE},
                null, null, null, null, null);

        if(cursor.moveToFirst()){
            do {
                UserProfileModel auxUserprofiles = new UserProfileModel(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3), cursor.getLong(4));
                userprofiles.add(auxUserprofiles);
            }while(cursor.moveToNext());
        }
        return userprofiles;
    }

    public boolean updateUserProfile(UserProfileModel userProfile){
        ContentValues values = getUserProfileValues(userProfile);
        return(this.database.update(TABLE_USERPROFILE, values, "id=?", new String[]{"" + userProfile.getId()})) > 0;
    }

    public boolean deleteUserProfile(int userProfileID){
        return(this.database.delete(TABLE_USERPROFILE, "id=?", new String[]{"" + userProfileID})) == 1;
    }

    public void deleteAllUserProfiles(){
        this.database.delete(TABLE_USERPROFILE, null, null);
    }

    private ContentValues getUserProfileValues(UserProfileModel userProfile){
        ContentValues values = new ContentValues();
        values.put(ID_USERPROFILE, userProfile.getId());
        values.put(DISPLAYNAME_USERPROFILE, userProfile.getDisplayName());
        values.put(RANK_USERPROFILE, userProfile.getRank());
        values.put(ROLE_USERPROFILE, userProfile.getRole());
        values.put(USERID_USERPROFILE, userProfile.getUser_id());
        return values;
    }


    // TABLE CHARACTER /////////////////////////////////////////////////////////////////////////////

    public void addCharacterDB(CharacterModel character){
        ContentValues values = getCharacterValues(character);
        this.database.insert(TABLE_CHARACTER, null, values);
    }

    public void addCharatersDB(ArrayList<CharacterModel> characterModelArrayList){
        this.deleteAllUserProfiles();
        for(CharacterModel character : characterModelArrayList){
            this.addCharacterDB(character);
        }
    }

    public CharacterModel getCharacter(String idCharacter){
        CharacterModel auxCharacter = new CharacterModel();

        Cursor cursor = this.database.query(TABLE_CHARACTER, null, ID_CHARACTER + "=?", new String[]{idCharacter}, null, null, null);

        if(cursor.moveToFirst()){
            do {
                auxCharacter = new CharacterModel(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                        cursor.getLong(7), cursor.getLong(8));
            }while(cursor.moveToNext());
        }
        return auxCharacter;
    }

    public ArrayList<CharacterModel> getAllCharacters(){
        ArrayList<CharacterModel> characters = new ArrayList<>();

        Cursor cursor = this.database.query(TABLE_CHARACTER, null,
                null, null, null, null, null);

        if(cursor.moveToFirst()){
            do {
                CharacterModel auxCharacters = new CharacterModel(cursor.getInt(0), cursor.getString(1), cursor.getInt(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),
                        cursor.getLong(7), cursor.getLong(8));
                characters.add(auxCharacters);
            }while(cursor.moveToNext());
        }
        return characters;
    }

    public boolean updateCharacter(CharacterModel character){
        ContentValues values = getCharacterValues(character);
        return(this.database.update(TABLE_CHARACTER, values, "id=?", new String[]{"" + character.getId()})) > 0;
    }

    public boolean deleteCharacter(int characterID){
        return(this.database.delete(TABLE_CHARACTER, "id=?", new String[]{"" + characterID})) == 1;
    }

    public void deleteAllCharacters(){
        this.database.delete(TABLE_CHARACTER, null, null);
    }

    private ContentValues getCharacterValues(CharacterModel character){
        ContentValues values = new ContentValues();
        values.put(ID_CHARACTER, character.getId());
        values.put(CHARNAME_CHARACTER, character.getCharName());
        values.put(LEVEL_CHARACTER, character.getLevel());
        values.put(CLASS_CHARACTER, character.get_Class());
        values.put(RACE_CHARACTER, character.getRace());
        values.put(MAINSPEC_CHARACTER, character.getMainSpec());
        values.put(OFFSPEC_CHARACTER, character.getOffSpec());
        values.put(USERID_CHARACTER, character.getUser_id());
        values.put(MAIN_CHARACTER, character.getMain());
        return values;
    }
}
