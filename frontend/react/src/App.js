import logo from "./logo.svg";
import "./App.css";
import LoginForm from "./components/LoginForm";
import { Router, BrowserRouter, Routes, Route } from "react-router-dom";
import RegistrationForm from "./components/RegistrationForm";
import Userdashboard from "./components/old/UserDashboard";
import Navbar from "./components/MainNavbar";
import Sidebar from "./components/old/Sidebar";
import PatientAppointments from "./components/Patient/PatientAppointments";
import PrivateRoute from "./components/RequireAuth";
import RequireAuth from "./components/RequireAuth";
import PatientsFiles from "./components/Doctor/PatientsFiles";
import UploadPatientFile from "./components/Doctor/UploadPatientFile";
import DoctorAppointments from "./components/Doctor/DoctorAppointments";
import SignUpNewAppointment from "./components/Patient/SignUpNewAppointment";

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
        <Route path="/add-appointment" element={<PatientAppointments />} />
        <Route path="/download-documents" element={<PatientsFiles />} />
        <Route path="/upload-documents" element={<UploadPatientFile />} />
        <Route path="/doctor-appointments" element={<DoctorAppointments />} />
        <Route path="/sign-up-appointment" element={<SignUpNewAppointment />} />
      </Routes>
    </BrowserRouter>

  );
}

export default App;
