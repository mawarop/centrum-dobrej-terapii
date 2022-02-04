import axios from "axios";
// import { Buffer } from "buffer";

// const defaultOptions = {
//   baseURL: "/",
//   headers: {
//     Accept: "application/json",
//     "Content-Type": "application/json",
//     "Access-Control-Allow-Origin": "*",
//   },
// };

// // Create instance
// let axiosInstance = axios.create(defaultOptions);

class UserService {
  // basic auth
  // login(email, password) {
  //   // return axios.post(PRODUCT_API_BASE_URL + "login", {
  //   //   username: email,
  //   //   password: password,
  //   // });
  //   // axios.defaults.withCredentials = true;
  //   let auth = Buffer.from(email + ":" + password).toString("base64");
  //   console.log(auth);
  //   axiosInstance.defaults.headers.common["Authorization"] = "Basic " + auth;
  //   return axiosInstance.post(
  //     "api/user",
  //     {
  //       headers: {
  //         Accept: "application/json",
  //         "Content-Type": "application/json",
  //         "Access-Control-Allow-Origin": "*",
  //       },
  //     },
  //     {
  //       auth: {
  //         username: email,
  //         password: password,
  //       },
  //     }
  //   );
  // }

  //form auth
  login(email, password) {
    let bodyFormData = new FormData();
    bodyFormData.append("username", email);
    bodyFormData.append("password", password);

    return axios.post("/login", bodyFormData, {
      withCredentials: true,
      headers: {
        "Content-Type": "multipart/form-data",
        "Access-Control-Allow-Origin": "http://localhost:3000/",
      },
    });
  }

  register(jsonFormData) {
    return axios.post("/api/registration", jsonFormData, {
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "http://localhost:3000/",
      },
    });
  }

  testGet() {
    return axios.get("/api/user", {
      withCredentials: true,
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "http://localhost:3000/",
      },
    });
  }
}

export default new UserService();
