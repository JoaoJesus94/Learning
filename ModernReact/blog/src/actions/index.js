import _ from "lodash";
import jsonPlaceholder from "../api/jsonPlaceholder";

export const fetchPostsAndUsers = () => async (dispatch, getState) => {
  await dispatch(fetchPosts());

  const userIds = _.uniq(_.map(getState().posts, 'userId'));

  userIds.forEach(userId => dispatch(fetchUser(userId)));
};

export const fetchPosts = () => async dispatch => {
  const response = await jsonPlaceholder.get("/posts");

  dispatch({ type: "FETCH_POSTS", payload: response.data });
};

export const fetchUser = userId => async dispatch => {
  const response = await jsonPlaceholder.get(`/users/${userId}`);

  dispatch({ type: "FETCH_USER", payload: response.data });
};

// Memoization
// export const fetchUser = userId => dispatch => _fetchUser(userId, dispatch);
//
// const _fetchUser = _.memoize(async (userId, dispatch) => {
//   const response = await jsonPlaceholder.get(`/users/${userId}`);
//   dispatch({ type: "FETCH_USER", payload: response.data });
// });
