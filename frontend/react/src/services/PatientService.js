import axios from "axios";
import axiosLoggedInConfig from "./AxiosLoggedInConfig";
class PatientService {
  getAppointments() {
    return axios.get("/api/patient/appointments", axiosLoggedInConfig);
  }

  appointmentSignUp(id) {
    return axios.patch("/api/patient/appointment/" + id, null, axiosLoggedInConfig);
  }

  getDoctorsBaseData(){
    return axios.get("/api/patient/doctors",axiosLoggedInConfig);
  }
  getDoctorFreeDates(email){
    return axios.get("/api/patient/doctor-appointments", {
      params:{email: email}, ...axiosLoggedInConfig} )
  }
  cancelAppointment(id){
    return axios.patch("/api/patient/cancel-appointment/" + id, null,axiosLoggedInConfig
    )
  }

}

export default new PatientService();
