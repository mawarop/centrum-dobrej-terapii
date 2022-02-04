import axios from "axios";

class PatientService {
  getAppointments() {
    return axios.get("/api/patient/appointments", {
      withCredentials: true,
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
        "Access-Control-Allow-Origin": "http://localhost:3000/",
      },
    });
  }
}

export default new PatientService();
