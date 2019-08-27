import axios from "axios";

export default axios.create({
  baseURL: "https://api.unsplash.com",
  headers: {
    Authorization:
      "Client-ID 32d41fae50c452b00eb9366e7035893f4a6835a917fa34c3bbfb6540398f3ad5"
  }
});
