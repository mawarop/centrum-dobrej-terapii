import axios from "axios";
import axiosJwtConfig from "./AxiosConfig";

class PatientService {
  getAppointments() {
    return axios.get("/api/patient/appointments", axiosJwtConfig());
  }

  appointmentSignUp(id) {
    return axios.patch(
        "/api/patient/appointment/" + id,
        null,
        axiosJwtConfig()
    );
  }

  getDoctorsBaseData() {
    return axios.get("/api/patient/doctors", axiosJwtConfig());
  }
  getDoctorFreeDates(email) {
    return axios.get("/api/patient/doctor-appointments", {
      params: {email: email},
      ...axiosJwtConfig(),
    });
  }
  cancelAppointment(id) {
    return axios.patch(
      "/api/patient/cancel-appointment/" + id,
      null,
        axiosJwtConfig()
    );
  }

  changeAppointment(appointmentIdToCancel, freeDateAppointmentId) {
    console.log(
      "toCancelId: " +
        appointmentIdToCancel +
        " freeDateid: " +
        freeDateAppointmentId
    );
    return axios.post("api/patient/change-appointment", null, {
      params: {
        appointmentIdToCancel: appointmentIdToCancel,
        freeDateAppointmentId: freeDateAppointmentId,
      },
      ...axiosJwtConfig(),
    });
  }
}

export default new PatientService();
