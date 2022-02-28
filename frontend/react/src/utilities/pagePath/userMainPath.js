import {Role} from "../../enums/role";

class userMainPath{
getPath(){
let redirectPath;
switch (localStorage.getItem("participant-role")){
    case Role.PATIENT: redirectPath = "/patient-appointments";
        break;
    case Role.DOCTOR: redirectPath = "/doctor-appointments";
        break;
    case Role.ADMIN: redirectPath = "/show-users"
        break;
    default: redirectPath = "/login";
}
    return redirectPath;
}
}

export default new userMainPath();


