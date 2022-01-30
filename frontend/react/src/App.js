import logo from "./logo.svg";
import "./App.css";
import LoginForm from "./components/LoginForm";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import RegistrationForm from "./components/RegistrationForm";
import Userdashboard from "./components/UserDashboard/UserDashboard";
import Navbar from "./components/UserDashboard/Navbar";
import Sidebar from "./components/UserDashboard/Sidebar";
import Appointment from "./components/UserDashboard/Appointment";
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
          <Route path="/user-dashboard" element={<Userdashboard />} />
          <Route path="/add-appointment" element={<Appointment />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
