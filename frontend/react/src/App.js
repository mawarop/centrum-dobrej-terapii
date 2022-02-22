import logo from "./logo.svg";
import "./App.css";
import LoginForm from "./components/LoginForm";
import { Router, BrowserRouter, Routes, Route } from "react-router-dom";
import RegistrationForm from "./components/RegistrationForm";
import Userdashboard from "./components/old/UserDashboard";
import Navbar from "./components/MainNavbar";
import RequireAuth from "./components/RequireAuth";
import PatientsFiles from "./components/Doctor/PatientsFiles";
import UploadPatientFile from "./components/Doctor/UploadPatientFile";
import SignUpNewAppointment from "./components/Patient/SignUpNewAppointment";
import UserAppointments from "./components/UserAppointments";
import PatientService from "./services/PatientService";
import DoctorService from "./services/DoctorService";
import {role} from "./role";
import AdminUsersPanel from "./components/Admin/AdminUsersPanel";
import AdminService from "./services/AdminService";



function App() {
  return (

    <BrowserRouter>
      <Navbar />

      <Routes>
        <Route path="/login" element={<LoginForm />} />
        <Route
          path="/registration"
          element={
            <RequireAuth>
              <RegistrationForm />
            </RequireAuth>
          }
        />
        {/* <Route path="/user-dashboard" element={<Userdashboard />} /> */}
        <Route path="/patient-appointments" element={<UserAppointments role={role.PATIENT}
                                                                       makeRequest={() => {return PatientService.getAppointments()}}  />} />
        <Route path="/download-documents" element={<PatientsFiles />} />
        <Route path="/upload-documents" element={<UploadPatientFile />} />
        <Route path="/doctor-appointments" element={<UserAppointments role={role.DOCTOR}
                                                                      makeRequest={() => {return DoctorService.getAppointments()}}/>} />
        <Route path="/sign-up-appointment" element={<SignUpNewAppointment />} />
        <Route path="/show-users" element={<AdminUsersPanel makeRequest={(page) => {return AdminService.getUsers(page)}}/>}/>
      </Routes>
    </BrowserRouter>

  );
}

export default App;
