package projetopsi.overcomeguildapp.listener;

import java.util.ArrayList;

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

public interface Listener {

    void onRefreshUser(UserModel userModel);

    void onUpdateUserProfile();

    void onRefreshUserProfileList(UserProfileModel userProfile);

    void onRefreshCharacterList(ArrayList<CharacterModel> characterList);

    void onRefreshApply(ApplyModel apply);

    void onRefreshRoster(ArrayList<UserProfileModel> userProfileList);

    void onRefreshRosterProfile(RosterModel roster);

    void onRefreshOPTable(ArrayList<OverpointModel> overpoint);

    void onRefreshEvents(ArrayList<EventModel> events);

    void onRefreshUserProfileHasEvents(ArrayList<UserProfileHasEventModel> UserProfileHasEvents);

    void onRefreshSubjects(ArrayList<SubjectModel> subjects);

    void onRefreshTopics(ArrayList<TopicsModel> topics);

    void onRefreshPosts(ArrayList<PostsModel> posts);

    void onRefreshPostsComments(ArrayList<PostsCommentsModel> postsComments);

    void onRefreshNews(ArrayList<NewsModel> news);

    void onRefreshNewsComments(ArrayList<NewsCommentsModel> newsComments);

}
