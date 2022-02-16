import React, { Component } from "react";
import "./Sidebar.css";

class Sidebar extends Component {
  render() {
    return (// <nav className="navbar navbar-light bg-light navbar-fixed-left">
      // <a className="nav-link" href="#">
      //   Navbar
      // </a>
      // <a className="nav-link" href="#">
      //   Navbar2
      // </a>
      // <a className="nav-link" href="#">
      //   Navbar2
      // </a>
      // <a className="nav-link" href="#">
      //   Navbar2
      // </a>
      // </nav>
      <div className="navbar-fixed-left bg-light">
        <a className="nav-link" href="#">
          Navbar
        </a>
        <a className="nav-link" href="#">
          Navbar2
        </a>
        <a className="nav-link" href="#">
          Navbar2
        </a>
        <a className="nav-link" href="#">
          Navbar2
        </a>
      </div>
    );
  }

}

export default Sidebar;