import axios from "axios";
// import * as AxiosConfig from "./AxiosConfig";
import axiosJwtConfig from "./AxiosConfig";

class DoctorService {
  // getPatientsBasicData(){
  //     return axios.get("/api/doctor/patients", axiosJwtConfig());
  // }

  getPatientDocumentsPaths(pesel) {
    return axios.post(
        "/api/doctor/patient-documents-paths",
        pesel,
        axiosJwtConfig()
    );
  }

  fileUpload(formData) {
    return axios.post("/api/documents/upload", formData, axiosJwtConfig());
  }

  getAppointments() {
    return axios.get("/api/doctor/appointments", axiosJwtConfig());
  }
  cancelAppointment(id) {
    return axios.patch(
      "/api/doctor/cancel-appointment/" + id,
      null,
        axiosJwtConfig()
    );
  }
  updateAppointmentDetails(id, details) {
    return axios.patch(
      "/api/doctor/appointment/" + id,
      { details: details },
        axiosJwtConfig()
    );
  }
}
export default new DoctorService();
