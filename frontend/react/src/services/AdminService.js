import axios from "axios";
import axiosLoggedInConfig from "./AxiosLoggedInConfig";

class AdminService {
  getUsers(page, input) {
    if (input)
      return axios.get(
          "/api/admin/users" + "?page=" + page + "&" + "input=" + input,
          axiosLoggedInConfig
      );
    else
      return axios.get(
          "/api/admin/users" + "?page=" + page,
          axiosLoggedInConfig
      );
  }

  createUser(user, role) {
    return axios.post("/api/admin/users", user, {
      params: {userRoleParam: role},
      ...axiosLoggedInConfig,
    });
  }

  updateUser(user, id) {
    return axios.patch("/api/admin/users/" + id, user, axiosLoggedInConfig);
  }
  blockUser(id) {
    return axios.patch(
        "/api/admin/users/" + id + "/block",
        null,
        axiosLoggedInConfig
    );
  }
  addAppointment(data) {
    return axios.post("/api/admin/appointment/add", data, axiosLoggedInConfig);
  }
}

export default new AdminService();
