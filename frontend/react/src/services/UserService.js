import axios from "axios";

class UserService {
  //form auth
  // login(email, password) {
  //   let bodyFormData = new FormData();
  //   bodyFormData.append("username", email);
  //   bodyFormData.append("password", password);
  //
  //   return axios.post("/login", bodyFormData, {
  //     withCredentials: true,
  //     headers: {
  //       "Content-Type": "multipart/form-data",
  //       "Access-Control-Allow-Origin": "http://localhost:3000/",
  //     },
  //   });
  // }
  //
  // register(jsonFormData) {
  //   return axios.post("/api/registration", jsonFormData, {
  //     headers: {
  //       "Content-Type": "application/json",
  //       "Access-Control-Allow-Origin": "http://localhost:3000/",
  //     },
  //   });
  // }
  //
  // logout() {
  //   localStorage.clear();
  //   return axios.get("/logout", _oldAxiosLoggedInFormConfig);
  // }

  login(email, password) {
    return axios.post("/login", {username: email, password: password});
  }

  register(jsonFormData) {
    return axios.post("/api/registration", jsonFormData, {
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "http://localhost:3000/",
      },
    });
  }

  logout() {
    localStorage.clear();
    sessionStorage.clear();
  }

  AxiosJwtConfig;
}

export default new UserService();
