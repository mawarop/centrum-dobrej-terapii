import logo from "./logo.svg";
import "./App.css";
import LoginForm from "./components/LoginForm";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import RegistrationForm from "./components/RegistrationForm";
import Userdashboard from "./components/UserDashboard/UserDashboard";
import Navbar from "./components/UserDashboard/MainNavbar";
import Sidebar from "./components/UserDashboard/Sidebar";
import PatientAppointments from "./components/UserDashboard/PatientAppointments";
import PrivateRoute from "./components/RequireAuth";
import RequireAuth from "./components/RequireAuth";

function App() {
  return (
    <Router>
      <Navbar />
      <div className="container">
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
        </Routes>
      </div>
    </Router>
  );
}

export default App;
