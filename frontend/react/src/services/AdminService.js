import axios from "axios";
import axiosLoggedInConfig from "./AxiosLoggedInConfig";

class AdminService {
  getUsers(page) {
    return axios.get("/api/admin/users/" + page, axiosLoggedInConfig);
  }
  createUser(user, role) {
    return axios.post("/api/admin/user", user, {
      params: { userRoleParam: role },
      ...axiosLoggedInConfig,
    });
  }
  updateUser(user, id) {
    return axios.patch("/api/admin/user/" + id, user, axiosLoggedInConfig);
  }
  blockUser(id) {
    return axios.patch(
      "/api/admin/user/" + id + "/block",
      null,
      axiosLoggedInConfig
    );
  }
  addAppointment(data) {
    return axios.post("/api/admin/appointment/add", data, axiosLoggedInConfig);
  }
}

export default new AdminService();
