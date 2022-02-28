import logo from "./logo.svg";
import "./App.css";
import LoginFormPage from "./pages/LoginFormPage";
import { Router, BrowserRouter, Routes, Route } from "react-router-dom";
import CreateUpdateUserFormPage from "./pages/CreateUpdateUserFormPage";
import Userdashboard from "./_old/UserDashboard";
import Navbar from "./components/MainNavbar";
import RequireAuth from "./components/auth/RequireAuth";
import PatientsFiles from "./components/doctor/PatientsFiles";
import UploadPatientFile from "./components/doctor/UploadPatientFile";
import SignUpNewAppointment from "./components/patient/SignUpNewAppointment";
import UserAppointmentsPage from "./pages/UserAppointmentsPage";
import PatientService from "./services/PatientService";
import DoctorService from "./services/DoctorService";
import {role} from "./enums/role";
import AdminUsersPanel from "./components/admin/AdminUsersPanel";
import AdminService from "./services/AdminService";
import UserService from "./services/UserService";
import React, {useState} from "react";


function App() {




    return (

    <BrowserRouter>
      <Navbar/>
      <Routes>
        <Route exact path="/" element={<LoginFormPage />} />
        <Route path="/login" element={<LoginFormPage />} />
        <Route
          path="/registration"
          element={
            // <RequireAuth>
              <CreateUpdateUserFormPage redirectUrl ="/login" role={role.PATIENT} hasPasswordInput={true} hasPeselInput={true} makeRequest={(jsonFormData) => {return UserService.register(jsonFormData)}} />
            // </RequireAuth>
          }
        />
        {/* <Route path="/user-dashboard" element={<Userdashboard />} /> */}
        <Route path="/patient-appointments" element={<UserAppointmentsPage role={role.PATIENT}
                                                                           makeRequest={() => {return PatientService.getAppointments()}}  />} />
        <Route path="/download-documents" element={<PatientsFiles />} />
        <Route path="/upload-documents" element={<UploadPatientFile />} />
        <Route path="/doctor-appointments" element={<UserAppointmentsPage role={role.DOCTOR}
                                                                          makeRequest={() => {return DoctorService.getAppointments()}}/>} />
        <Route path="/sign-up-appointment" element={<SignUpNewAppointment />} />
        <Route path="/show-users" element={<AdminUsersPanel makeRequest={(page) => {return AdminService.getUsers(page)}}/>}/>
        <Route path="/create-user" element={<CreateUpdateUserFormPage hasRoleInput={true} hasPasswordInput={true} hasPeselInput={true} redirectUrl ="/show-users" makeRequest={(jsonFormData, role) => {return AdminService.createUser(jsonFormData, role)}}/>}/>

        {/*<Route path="/update-user/:id" element={<CreateUpdateUserFormPage hasRoleInput={true} redirectUrl ="/show-users" makeRequest={(jsonFormData, role) => {return AdminService.createUser(jsonFormData, role)}}/>}/>*/}

      </Routes>
    </BrowserRouter>

  );
}

export default App;
