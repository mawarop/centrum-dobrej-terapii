import axios from "axios";
import axiosLoggedInConfig from "./AxiosLoggedInConfig";
class AdminService {
    getUsers(page){
        return axios.get("/api/admin/users/" + page, axiosLoggedInConfig);
    }

}

export default new AdminService();