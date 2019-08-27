import axios from "axios";

const KEY = "AIzaSyA2KcHuOgWcFbBUvv6X647kfoDp7fPn5bY";

export default axios.create({
  baseURL: "https://www.googleapis.com/youtube/v3",
  params: {
    part: "snippet",
    maxResults: 5,
    key: KEY
  }
});
