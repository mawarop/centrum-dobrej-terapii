import logo from "./logo.svg";
import "./App.css";
import LoginForm from "./components/LoginForm";
import { Router, BrowserRouter, Routes, Route } from "react-router-dom";
import CreateUpdateUserForm from "./components/CreateUpdateUserForm";
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
import UserService from "./services/UserService";
import React, {useState} from "react";


function App() {




    return (

    <BrowserRouter>
      <Navbar/>
      <Routes>
        <Route exact path="/" element={<LoginForm />} />
        <Route path="/login" element={<LoginForm />} />
        <Route
          path="/registration"
          element={
            <RequireAuth>
              <CreateUpdateUserForm redirectUrl ="/login" hasPasswordInput={true} hasPeselInput={true} makeRequest={(jsonFormData) => {return UserService.register(jsonFormData)}} />
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
        <Route path="/create-user" element={<CreateUpdateUserForm hasRoleInput={true} hasPasswordInput={true} hasPeselInput={true} redirectUrl ="/show-users" makeRequest={(jsonFormData, role) => {return AdminService.createUser(jsonFormData, role)}}/>}/>

        {/*<Route path="/update-user/:id" element={<CreateUpdateUserForm hasRoleInput={true} redirectUrl ="/show-users" makeRequest={(jsonFormData, role) => {return AdminService.createUser(jsonFormData, role)}}/>}/>*/}

      </Routes>
    </BrowserRouter>

  );
}

export default App;
