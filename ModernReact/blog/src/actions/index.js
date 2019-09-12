import jsonPlaceholder from "../api/jsonPlaceholder";

export const fetchPosts = () => async dispatch => {
    const response = await jsonPlaceholder.get("/posts");

    dispatch({ type: "FETCH_POSTS", payload: response.data });
};

export const fetchUsers = () => async dispatch => {
    const response = await jsonPlaceholder.get('/users');

    dispatch({ type: "FETCH_USERS", payload: response.data})
}