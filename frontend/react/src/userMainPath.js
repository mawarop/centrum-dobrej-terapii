import {role} from "./role";

class userMainPath{
getPath(){
let redirectPath;
switch (localStorage.getItem("participant-role")){
    case role.PATIENT: redirectPath = "/patient-appointments";
        break;
    case role.DOCTOR: redirectPath = "/doctor-appointments";
        break;
    case role.ADMIN: redirectPath = "/show-users"
        break;
    default: redirectPath = "/login";
}
    return redirectPath;
}
}

export default new userMainPath();


