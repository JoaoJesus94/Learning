package projetopsi.overcomeguildapp.singleton;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import projetopsi.overcomeguildapp.database.DataBase;
import projetopsi.overcomeguildapp.listener.Listener;
import projetopsi.overcomeguildapp.models.ApplyModel;
import projetopsi.overcomeguildapp.models.CharacterModel;
import projetopsi.overcomeguildapp.models.EventModel;
import projetopsi.overcomeguildapp.models.NewsCommentsModel;
import projetopsi.overcomeguildapp.models.NewsModel;
import projetopsi.overcomeguildapp.models.OverpointModel;
import projetopsi.overcomeguildapp.models.PostsCommentsModel;
import projetopsi.overcomeguildapp.models.PostsModel;
import projetopsi.overcomeguildapp.models.RosterModel;
import projetopsi.overcomeguildapp.models.SubjectModel;
import projetopsi.overcomeguildapp.models.TopicsModel;
import projetopsi.overcomeguildapp.models.UserModel;
import projetopsi.overcomeguildapp.models.UserProfileHasEventModel;
import projetopsi.overcomeguildapp.models.UserProfileModel;

public class Singleton {

    private static Singleton INSTANCE = null;
    private ArrayList<UserProfileModel> userProfiles;
    private ArrayList<CharacterModel> characters;
    private ArrayList<OverpointModel> overpoints;
    public ArrayList<EventModel> events;
    public ArrayList<UserProfileHasEventModel> userProfileHasEvents;
    private ArrayList<SubjectModel> subjects;
    private ArrayList<TopicsModel> topics;
    private ArrayList<PostsModel> posts;
    private ArrayList<PostsCommentsModel> postsComments;
    private ArrayList<NewsModel> news;
    private ArrayList<NewsCommentsModel> newsComments;
    private static RequestQueue volleyQueue = null;
    private DataBase dataBase;
    private UserModel userModel;
    private ApplyModel applyModel;
    private RosterModel rosterModel;
    private UserProfileModel userProfileModel;
    private UserProfileHasEventModel userProfileHasEventModel;
    
    private static final String IP = "192.168.1.69";

    private Listener listener = null;

    public static synchronized Singleton getInstance(Context context)
    {
        if (INSTANCE == null){
            INSTANCE = new Singleton(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return INSTANCE;
    }

    private Singleton(Context context)
    {
        userProfiles = new ArrayList<>();
        characters = new ArrayList<>();
        overpoints = new ArrayList<>();
        events = new ArrayList<>();
        userProfileHasEvents = new ArrayList<>();
        subjects = new ArrayList<>();
        topics = new ArrayList<>();
        postsComments = new ArrayList<>();
        news = new ArrayList<>();
        newsComments = new ArrayList<>();
        dataBase = new DataBase(context);
    }

    public void setListener(Listener listener)
    {
        this.listener = listener;
    }

    public void apiRequestUserLogin(final Context context, final String username, final String password)
    {
        if(checkInternetState(context))
        {
            String url = "http://" + IP + ":8080/v1/user/login";

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {

                            switch (response)
                            {
                                case "-1":
                                    Toast.makeText(context, "Invalid Username", Toast.LENGTH_SHORT).show();
                                    break;
                                case "-2":
                                    Toast.makeText(context, "Invalid Password", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    userModel = UserModel.parserJsonUser(response, context);
                                    if(listener != null){
                                        listener.onRefreshUser(userModel);
                                    }
                                    break;
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Toast.makeText(context, "Responce ERROR" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    return headers;
                }

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);

                    return params;
                }
            };
            volleyQueue.add(postRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestUpdateUser(final UserModel user, final Context context, final String auth_key, final String id)
    {
        if (checkInternetState(context))
        {

            String url = "http://" + IP + ":8080/v1/user/" + id;

            StringRequest request = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        if (listener != null)
                        {
                            listener.onUpdateUserProfile();
                        }
                    }
                }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    System.out.println("--> Erro Add: " + error.getMessage());
                }
            })
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }

                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", user.getUsername());
                    params.put("email", user.getEmail());
                    return params;
                }
            };
            volleyQueue.add(request);
        } else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestGetUserProfile(final Context context, final String auth_key, final String id)
    {
        if(checkInternetState(context))
        {

            String url = "http://" + IP + ":8080/v1/userprofile/profile/" + id;

            StringRequest request = new StringRequest(
                Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        userProfileModel = UserProfileModel.parserJsonUserProfile(response, context);
                        dataBase.deleteAllUserProfiles();
                        dataBase.addUserProfileDB(userProfileModel);
                        if(listener != null)
                        {
                            listener.onRefreshUserProfileList(userProfileModel);
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(context, "Response ERROR", Toast.LENGTH_SHORT).show();
                    }
                })
            {
                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
            };
            volleyQueue.add(request);
        }else
        {
            if(listener != null)
            {
                userProfileModel = dataBase.getUserProfile(id);
                System.out.println(userProfileModel);
                listener.onRefreshUserProfileList(userProfileModel);
            }
        }
    }

    public void apiRequestUpdateUserProfile(final UserProfileModel userProfile, final Context context, final String auth_key, final String id_profile)
    {
        if(checkInternetState(context))
        {

            String url = "http://" + IP + ":8080/v1/userprofile/" + id_profile;

            StringRequest request = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>()
                {
                @Override
                public void onResponse(String response)
                {
                    userProfileModel = UserProfileModel.parserJsonUserProfile(response, context);
                    dataBase.updateUserProfile(userProfileModel);
                    if (listener != null){
                        listener.onUpdateUserProfile();
                    }
                }
            },
                new Response.ErrorListener()
                {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    System.out.println("--> Erro Edit: " + error.getMessage());
                }
                })
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
                @Override
                protected Map<String, String> getParams()
                {
                    Map <String, String> params = new HashMap<>();
                    params.put("displayName", userProfile.getDisplayName());
                    return params;
                }
            };
            volleyQueue.add(request);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestGetCharacters(final Context context, final String auth_key, final String id_profile)
    {
        if(checkInternetState(context))
        {

            String url = "http://" + IP + ":8080/v1/character/char/" + id_profile;

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>()
                    {
                        @Override
                        public void onResponse(JSONArray response)
                        {
                            characters = CharacterModel.parserJsonCharacters(response, context);
                            dataBase.deleteAllCharacters();
                            dataBase.addCharatersDB(characters);
                            if(listener != null)
                            {
                                listener.onRefreshCharacterList(characters);
                                getInstance(context).setListener(null);
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {

                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
            };
            volleyQueue.add(jsonArrayRequest);
        }else
        {
            characters = dataBase.getAllCharacters();
            if(listener != null)
            {
                listener.onRefreshCharacterList(characters);
            }
        }
    }

    public void apiRequestAddCharacter(final Context context, final String auth_key, final CharacterModel character)
    {
        if(checkInternetState(context))
        {
            String url = "http://" + IP + ":8080/v1/character";

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            CharacterModel auxCharacter = CharacterModel.parserJsonCharacter(response, context);
                            characters.add(auxCharacter);
                            dataBase.addCharacterDB(auxCharacter);
                            if(listener != null){
                                listener.onRefreshCharacterList(characters);
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            System.out.println("--> Erro ADD: " + error.getMessage());
                            Toast.makeText(context, "This name already exists", Toast.LENGTH_SHORT).show();
                        }
                    }
            )
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
                @Override
                protected Map<String, String> getParams()
                {
                    Map <String, String> params = new HashMap<>();
                    params.put("charName", character.getCharName());
                    params.put("level", String.valueOf(character.getLevel()));
                    params.put("class", character.get_Class());
                    params.put("race", character.getRace());
                    params.put("mainSpec", character.getMainSpec());
                    params.put("offSpec", character.getOffSpec());
                    params.put("User_id", String.valueOf(character.getUser_id()));
                    params.put("main", "1");
                    return params;
                }
            };
            volleyQueue.add(postRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestUpdateCharacter(final Context context, final String auth_key, final CharacterModel character, final String id_character)
    {
        if(checkInternetState(context))
        {

            String url = "http://" + IP + ":8080/v1/character/" + id_character;

            StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response)
                        {
                            CharacterModel auxCharacter = CharacterModel.parserJsonCharacter(response, context);

                            int index = -1;
                            for(CharacterModel charr : characters){
                                if(charr.getId() == auxCharacter.getId()){
                                    index = characters.indexOf(charr);
                                    break;
                                }
                            }
                            characters.set(index, character);
                            dataBase.updateCharacter(auxCharacter);
                            if(listener != null){
                                listener.onRefreshCharacterList(characters);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {

                        }
                    }
            )
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
                @Override
                protected Map<String, String> getParams()
                {
                    Map <String, String> params = new HashMap<>();
                    params.put("charName", character.getCharName());
                    params.put("level", String.valueOf(character.getLevel()));
                    params.put("class", character.get_Class());
                    params.put("race", character.getRace());
                    params.put("mainSpec", character.getMainSpec());
                    params.put("offSpec", character.getOffSpec());
                    return params;
                }
            };
            volleyQueue.add(putRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestDeleteCharacter(final Context context, final String auth_key, final String id_character)
    {
        if(checkInternetState(context))
        {
            String url = "http://" + IP + ":8080/v1/character/" + id_character;

            StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response)
                        {
                            characters.remove(getCharacterByID(Integer.parseInt(id_character)));
                            dataBase.deleteCharacter(Integer.parseInt(id_character));
                            if(listener != null){
                                listener.onRefreshCharacterList(characters);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {

                        }
                    }
            )
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
            };
            volleyQueue.add(deleteRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestGetApply(final Context context, final String auth_key, final String userId)
    {
        if(checkInternetState(context))
        {
            String url = "http://" + IP + ":8080/v1/apply/userapply/" + userId;

            StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            applyModel = ApplyModel.parserJsonApply(response, context);
                            if(listener != null){
                                listener.onRefreshApply(applyModel);
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Toast.makeText(context, "Response ERROR" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
            };
            volleyQueue.add(getRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestAddApply(final Context context, final String auth_key, final ApplyModel apply)
    {
        if(checkInternetState(context))
        {
            String url = "http://" + IP + ":8080/v1/apply";

            StringRequest addRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            applyModel = ApplyModel.parserJsonApply(response, context);
                            if(listener != null){
                                listener.onRefreshApply(applyModel);
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Toast.makeText(context, "Response ERROR" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", apply.getName());
                    params.put("status", "OPEN");
                    params.put("age", String.valueOf(apply.getAge()));
                    params.put("mainSpec", apply.getMainSpec());
                    params.put("offSpec", apply.getOffSpec());
                    params.put("class", apply.get_class());
                    params.put("race", apply.getRace());
                    params.put("armory", apply.getArmory());
                    params.put("wowHeroes", apply.getWowHeroes());
                    params.put("logs", apply.getLogs());
                    params.put("uiScreen", apply.getUiScreen());
                    params.put("experience", apply.getExperience());
                    params.put("availableTime", apply.getAvailableTime());
                    params.put("objective", apply.getObjective());
                    params.put("knownPeople", apply.getKnownPeople());
                    params.put("user_id", String.valueOf(apply.getUser_id()));
                    return params;
                }
            };
            volleyQueue.add(addRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestUpdateApply(final Context context, final String auth_key, final ApplyModel apply, final String id_apply)
    {
        if(checkInternetState(context))
        {
            String url = "http://" + IP + ":8080/v1/apply/" + id_apply;

            StringRequest getRequest = new StringRequest(Request.Method.PUT, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            applyModel = ApplyModel.parserJsonApply(response, context);
                            if(listener != null){
                                listener.onRefreshApply(applyModel);
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Toast.makeText(context, "Response ERROR" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", apply.getName());
                    params.put("age", String.valueOf(apply.getAge()));
                    params.put("mainSpec", apply.getMainSpec());
                    params.put("offSpec", apply.getOffSpec());
                    params.put("class", apply.get_class());
                    params.put("race", apply.getRace());
                    params.put("armory", apply.getArmory());
                    params.put("wowHeroes", apply.getWowHeroes());
                    params.put("logs", apply.getLogs());
                    params.put("uiScreen", apply.getUiScreen());
                    params.put("experience", apply.getExperience());
                    params.put("availableTime", apply.getAvailableTime());
                    params.put("objective", apply.getObjective());
                    params.put("knownPeople", apply.getKnownPeople());
                    return params;
                }
            };
            volleyQueue.add(getRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestGetRoster(final Context context, final String auth_key)
    {
        if(checkInternetState(context))
        {

            String url = "http://" + IP + ":8080/v1/userprofile";

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>()
                    {
                        @Override
                        public void onResponse(JSONArray response)
                        {
                            userProfiles = UserProfileModel.parserJsonUserProfiles(response, context);
                            if(listener != null)
                            {
                                listener.onRefreshRoster(userProfiles);
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {

                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
            };
            volleyQueue.add(jsonArrayRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestGetOPTable(final Context context, final String auth_key)
    {
        if(checkInternetState(context))
        {

            String url = "http://" + IP + ":8080/v1/overpoint/overpoint";

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>()
                    {
                        @Override
                        public void onResponse(JSONArray response)
                        {
                            overpoints = OverpointModel.parserJsonOverpoints(response, context);
                            if(listener != null)
                            {
                                listener.onRefreshOPTable(overpoints);
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Toast.makeText(context, "Response ERROR" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
            };
            volleyQueue.add(jsonArrayRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestRosterProfile(final Context context, final String user_id, final String auth_key)
    {
        if(checkInternetState(context))
        {
            String url = "http://" + IP + ":8080/v1/user/roster/" + user_id;

            StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            rosterModel = RosterModel.parserJsonRoster(response, context);
                            if(listener != null){
                                listener.onRefreshRosterProfile(rosterModel);
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Toast.makeText(context, "Response ERROR" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
            };
            volleyQueue.add(getRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestGetEvents(final Context context, final String auth_key)
    {
        if(checkInternetState(context))
        {

            String url = "http://" + IP + ":8080/v1/event";

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>()
                    {
                        @Override
                        public void onResponse(JSONArray response)
                        {
                            if(listener != null)
                            {
                                events = EventModel.parserJsonEvents(response, context);
                                listener.onRefreshEvents(events);
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {

                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
            };
            volleyQueue.add(jsonArrayRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestGetUserProfileHasEvents(final Context context, final String auth_key, final String id_event)
    {
        if(checkInternetState(context))
        {

            String url = "http://" + IP + ":8080/v1/userprofilehasevent/events/" + id_event;

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>()
                    {
                        @Override
                        public void onResponse(JSONArray response)
                        {
                            if(listener != null)
                            {
                                userProfileHasEvents = UserProfileHasEventModel.parserJsonUserProfileHasEvents(response, context);
                                listener.onRefreshUserProfileHasEvents(userProfileHasEvents);
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {

                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
            };
            volleyQueue.add(jsonArrayRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestAddUserProfileHasEvent(final Context context, final String auth_key, final UserProfileHasEventModel userProfileHasEvent)
    {
        if(checkInternetState(context))
        {
            String url = "http://" + IP + ":8080/v1/userprofilehasevent";

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            userProfileHasEventModel = UserProfileHasEventModel.parserJsonUserProfileHasEvent(response, context);

                            UserProfileHasEventModel auxGoEvent = new UserProfileHasEventModel();
                            auxGoEvent.setId_event(userProfileHasEventModel.getId_event());
                            auxGoEvent.setId_userprofile(userProfileHasEventModel.getId_userprofile());
                            auxGoEvent.setDisplayName(userProfileModel.getDisplayName());
                            auxGoEvent.setAttending(userProfileHasEventModel.getAttending());
                            auxGoEvent.setRole(userProfileHasEventModel.getRole());

                            userProfileHasEvents.add(auxGoEvent);
                            if(listener != null){
                                listener.onRefreshUserProfileHasEvents(userProfileHasEvents);
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            System.out.println("--> Erro ADD: " + error.getMessage());
                        }
                    }
            )
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
                @Override
                protected Map<String, String> getParams()
                {
                    Map <String, String> params = new HashMap<>();
                    params.put("event_id", String.valueOf(userProfileHasEvent.getId_event()));
                    params.put("userprofile_id", String.valueOf(userProfileHasEvent.getId_userprofile()));
                    params.put("attending", userProfileHasEvent.getAttending());
                    params.put("role", userProfileHasEvent.getRole());
                    return params;
                }
            };
            volleyQueue.add(postRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestGetSubjects(final Context context, final String auth_key)
    {
        if(checkInternetState(context))
        {

            String url = "http://" + IP + ":8080/v1/topic/subject";

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>()
                    {
                        @Override
                        public void onResponse(JSONArray response)
                        {
                            if(listener != null)
                            {
                                subjects = SubjectModel.parserJsonSubjects(response, context);
                                listener.onRefreshSubjects(subjects);
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {

                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
            };
            volleyQueue.add(jsonArrayRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestAddSubject(final Context context, final String auth_key, final SubjectModel subject)
    {
        if(checkInternetState(context))
        {
            String url = "http://" + IP + ":8080/v1/topic";

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            Toast.makeText(context, "New Subject Created", Toast.LENGTH_SHORT).show();
                            SubjectModel auxSubject = SubjectModel.parserJsonSubject(response, context);
                            subjects.add(auxSubject);
                            if(listener != null){
                                listener.onRefreshSubjects(subjects);
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            System.out.println("--> Erro ADD: " + error.getMessage());
                        }
                    }
            )
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
                @Override
                protected Map<String, String> getParams()
                {
                    Map <String, String> params = new HashMap<>();
                    params.put("topicName", subject.getTopicName());
                    params.put("User_id", String.valueOf(subject.getUserID()));
                    return params;
                }
            };
            volleyQueue.add(postRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestGetTopics(final Context context, final String auth_key)
    {
        if(checkInternetState(context))
        {

            String url = "http://" + IP + ":8080/v1/topic/topic";

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>()
                    {
                        @Override
                        public void onResponse(JSONArray response)
                        {
                            if(listener != null)
                            {
                                topics = TopicsModel.parserJsonTopics(response, context);
                                listener.onRefreshTopics(topics);
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {

                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
            };
            volleyQueue.add(jsonArrayRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestAddTopic(final Context context, final String auth_key, final TopicsModel topic)
    {
        if(checkInternetState(context))
        {
            String url = "http://" + IP + ":8080/v1/topic";

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            TopicsModel auxTopic = TopicsModel.parserJsonTopic(response, context);
                            topics.add(auxTopic);
                            if(listener != null){
                                listener.onRefreshTopics(topics);
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            System.out.println("--> Erro ADD: " + error.getMessage());
                        }
                    }
            )
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
                @Override
                protected Map<String, String> getParams()
                {
                    Map <String, String> params = new HashMap<>();
                    params.put("topicName", topic.getTopicName());
                    params.put("topicDescription", topic.getTopicDescription());
                    params.put("Topic_id", String.valueOf(topic.getTopic_id()));
                    params.put("User_id", String.valueOf(topic.getUserID()));
                    return params;
                }
            };
            volleyQueue.add(postRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestDeleteTopic(final Context context, final String auth_key, final String id_topic)
    {
        if(checkInternetState(context))
        {
            String url = "http://" + IP + ":8080/v1/topic/del/" + id_topic;

            final StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response)
                        {
                            if(listener != null)
                            {
                                topics.remove(getTopicByID(Integer.parseInt(id_topic)));
                                listener.onRefreshTopics(topics);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Toast.makeText(context, "Topic with Posts", Toast.LENGTH_SHORT).show();
                        }
                    }
            )
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
            };
            volleyQueue.add(deleteRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestGetPosts(final Context context, final String auth_key, final String topic_id)
    {
        if(checkInternetState(context))
        {

            String url = "http://" + IP + ":8080/v1/posts/posts/" + topic_id;

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>()
                    {
                        @Override
                        public void onResponse(JSONArray response)
                        {
                            if(listener != null)
                            {
                                posts = PostsModel.parserJsonPosts(response, context);
                                listener.onRefreshPosts(posts);
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {

                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
            };
            volleyQueue.add(jsonArrayRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestAddPost(final Context context, final String auth_key, final PostsModel post, final String id_topic)
    {
        if(checkInternetState(context))
        {
            String url = "http://" + IP + ":8080/v1/posts";

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            PostsModel auxPost = PostsModel.parserJsonPost(response, context);
                            posts.add(auxPost);
                            if(listener != null){
                                listener.onRefreshPosts(posts);
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            System.out.println("--> Erro ADD: " + error.getMessage());
                        }
                    }
            )
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
                @Override
                protected Map<String, String> getParams()
                {
                    Map <String, String> params = new HashMap<>();
                    Date date = new Date();
                    DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
                    params.put("title", post.getTitle());
                    params.put("content", post.getContent());
                    params.put("date", dateFormat.format(date));
                    params.put("Topic_id", id_topic);
                    params.put("User_id", String.valueOf(post.getUserID()));
                    return params;
                }
            };
            volleyQueue.add(postRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestDeletePost(final Context context, final String auth_key, final String id_post)
    {
        if(checkInternetState(context))
        {
            String url = "http://" + IP + ":8080/v1/posts/del/" + id_post;

            final StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response)
                        {
                            if(listener != null)
                            {
                                posts.remove(getPostByID(Integer.parseInt(id_post)));
                                listener.onRefreshPosts(posts);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            Toast.makeText(context, "Post with Comments", Toast.LENGTH_SHORT).show();
                        }
                    }
            )
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
            };
            volleyQueue.add(deleteRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestGetPostsComments(final Context context, final String auth_key, final String post_id)
    {
        if(checkInternetState(context))
        {

            String url = "http://" + IP + ":8080/v1/postcomment/postcomment/" + post_id;

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>()
                    {
                        @Override
                        public void onResponse(JSONArray response)
                        {
                            if(listener != null)
                            {
                                postsComments = PostsCommentsModel.parserJsonPostComments(response, context);
                                listener.onRefreshPostsComments(postsComments);
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {

                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
            };
            volleyQueue.add(jsonArrayRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestAddPostComment(final Context context, final String auth_key, final PostsCommentsModel postsComment, final String id_post)
    {
        if(checkInternetState(context))
        {
            String url = "http://" + IP + ":8080/v1/postcomment";

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            PostsCommentsModel auxPostComment = PostsCommentsModel.parserJsonPostComment(response, context);
                            auxPostComment.setDisplayName(userProfileModel.getDisplayName());
                            postsComments.add(auxPostComment);
                            if(listener != null){
                                listener.onRefreshPostsComments(postsComments);
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            System.out.println("--> Erro ADD: " + error.getMessage());
                        }
                    }
            )
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
                @Override
                protected Map<String, String> getParams()
                {
                    Map <String, String> params = new HashMap<>();
                    Date date = new Date();
                    DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
                    params.put("content", postsComment.getContent());
                    params.put("date", dateFormat.format(date));
                    params.put("Posts_id", id_post);
                    params.put("User_id", String.valueOf(postsComment.getUser_id()));
                    return params;
                }
            };
            volleyQueue.add(postRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestGetNews(final Context context, final String auth_key)
    {
        if(checkInternetState(context))
        {

            String url = "http://" + IP + ":8080/v1/news";

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>()
                    {
                        @Override
                        public void onResponse(JSONArray response)
                        {
                            if(listener != null)
                            {
                                news  = NewsModel.parserJsonNews(response, context);
                                listener.onRefreshNews(news);
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {

                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
            };
            volleyQueue.add(jsonArrayRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestGetNewsComments(final Context context, final String auth_key, final String new_id)
    {
        if(checkInternetState(context))
        {

            String url = "http://" + IP + ":8080/v1/newscomment/newscomments/" + new_id;

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>()
                    {
                        @Override
                        public void onResponse(JSONArray response)
                        {
                            if(listener != null)
                            {
                                newsComments = NewsCommentsModel.parserJsonNewsComments(response, context);
                                listener.onRefreshNewsComments(newsComments);
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {

                        }
                    })
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
            };
            volleyQueue.add(jsonArrayRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    public void apiRequestAddNewComment(final Context context, final String auth_key, final NewsCommentsModel newsComment, final String id_news)
    {
        if(checkInternetState(context))
        {
            String url = "http://" + IP + ":8080/v1/newscomment";

            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response)
                        {
                            NewsCommentsModel auxNewsComment = NewsCommentsModel.parserJsonNewComment(response, context);
                            auxNewsComment.setDisplayName(userProfileModel.getDisplayName());
                            newsComments.add(auxNewsComment);
                            if(listener != null){
                                listener.onRefreshNewsComments(newsComments);
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            System.out.println("--> Erro ADD: " + error.getMessage());
                        }
                    }
            )
            {
                @Override
                public Map<String, String> getHeaders()
                {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", "Bearer " + auth_key);
                    return headers;
                }
                @Override
                protected Map<String, String> getParams()
                {
                    Map <String, String> params = new HashMap<>();
                    Date date = new Date();
                    DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
                    params.put("content", newsComment.getContent());
                    params.put("date", dateFormat.format(date));
                    params.put("News_id", id_news);
                    params.put("User_id", String.valueOf(newsComment.getUser_id()));
                    return params;
                }
            };
            volleyQueue.add(postRequest);
        }else
        {
            Toast.makeText(context, "Internet ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    private CharacterModel getCharacterByID(int id){
        for (CharacterModel charr : characters){
            if(charr.getId() == id){
                return charr;
            }
        }
        return null;
    }

    private TopicsModel getTopicByID(int id){
        for (TopicsModel topic : topics){
            if(topic.getId() == id){
                return topic;
            }
        }
        return null;
    }

    private PostsModel getPostByID(int id){
        for (PostsModel post : posts){
            if(post.getId() == id){
                return post;
            }
        }
        return null;
    }

    public ArrayList<CharacterModel> getAllCharacters() {
        return characters;
    }

    public boolean checkInternetState(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnected();
    }

    public void setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                float px = 500 * (listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int)px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);
            // Get padding
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();
        }
    }
}
