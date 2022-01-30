import React, { Component } from "react";
class Navbar extends Component {
  render() {
    return (
      <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
          <a class="navbar-brand" href="#">
            Centrum dobrej terapii
          </a>
          <button
            class="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarNav"
            aria-controls="navbarNav"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span class="navbar-toggler-icon"></span>
          </button>
          <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
              <li class="nav-item">
                <a class="nav-link active" aria-current="page" href="#">
                  Home
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">
                  Features
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">
                  Pricing
                </a>
              </li>
              <li class="nav-item">
                <a class="nav-link disabled">Disabled</a>
              </li>
            </ul>
          </div>
        </div>
      </nav>
      // <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
      //   <a class="navbar-brand" href="#">
      //     Navbar
      //   </a>
      //   <button
      //     class="navbar-toggler"
      //     type="button"
      //     data-toggle="collapse"
      //     data-target="#navbarNav"
      //     aria-controls="navbarNav"
      //     aria-expanded="false"
      //     aria-label="Toggle navigation"
      //   >
      //     <span class="navbar-toggler-icon"></span>
      //   </button>
      //   <div class="collapse navbar-collapse" id="navbarNav">
      //     <ul class="navbar-nav">
      //       <li class="nav-item active">
      //         <a class="nav-link" href="#">
      //           Home <span class="sr-only">(current)</span>
      //         </a>
      //       </li>
      //       <li class="nav-item">
      //         <a class="nav-link" href="#">
      //           Features
      //         </a>
      //       </li>
      //       <li class="nav-item">
      //         <a class="nav-link" href="#">
      //           Pricing
      //         </a>
      //       </li>
      //       <li class="nav-item">
      //         <a class="nav-link disabled" href="#">
      //           Disabled
      //         </a>
      //       </li>
      //     </ul>
      //   </div>
      // </nav>
    );
  }
}

export default Navbar;
