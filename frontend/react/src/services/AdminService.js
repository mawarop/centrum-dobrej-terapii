import axios from "axios";
import axiosJwtConfig from "./AxiosConfig";

class AdminService {
  getUsers(page, input) {
    if (input)
      return axios.get(
          "/api/admin/users" + "?page=" + page + "&" + "input=" + input,
          axiosJwtConfig()
      );
    else
      return axios.get("/api/admin/users" + "?page=" + page, axiosJwtConfig());
  }

  createUser(user, role) {
    return axios.post("/api/admin/users", user, {
      params: {userRoleParam: role},
      ...axiosJwtConfig(),
    });
  }

  updateUser(user, id) {
    return axios.patch("/api/admin/users/" + id, user, axiosJwtConfig());
  }
  blockUser(id) {
    return axios.patch(
        "/api/admin/users/" + id + "/block",
        null,
        axiosJwtConfig()
    );
  }
  addAppointment(data) {
    return axios.post("/api/admin/appointment/add", data, axiosJwtConfig());
  }
}

export default new AdminService();
