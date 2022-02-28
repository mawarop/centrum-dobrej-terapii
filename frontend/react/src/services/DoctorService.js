import axios from "axios";
import axiosLoggedInConfig from "./AxiosLoggedInConfig";
class DoctorService {
    // getPatientsBasicData(){
    //     return axios.get("/api/doctor/patients", axiosLoggedInConfig);
    // }

    getPatientDocumentsPaths(pesel){
        return axios.post("/api/doctor/patient-documents-paths",pesel, axiosLoggedInConfig);
    }

    fileUpload(formData){
        return axios.post("/api/documents/upload",formData,
            axiosLoggedInConfig);
    }

    getAppointments() {
        return axios.get("/api/doctor/appointments", axiosLoggedInConfig);
    }
    cancelAppointment(id){
        return axios.patch("/api/doctor/cancel-appointment/" + id, null,axiosLoggedInConfig
            )
    }
    updateAppointmentDetails(id, details){
        return axios.patch("/api/doctor/appointment/" + id, {details: details}, axiosLoggedInConfig);
    }
}
export default new DoctorService();