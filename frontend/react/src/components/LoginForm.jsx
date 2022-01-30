import React, { Component } from "react";
import FullCalendar from "@fullcalendar/react"; // must go before plugins
import dayGridPlugin from "@fullcalendar/daygrid"; // a plugin!
// import interactionPlugin from "@fullcalendar/interaction"; // needed for dayClick
import "./LoginForm.css";
import axios from "axios";
import UserService from "../services/UserService";

class LoginForm extends React.Component {
  constructor(props) {
    super(props);

    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleSubmit(event) {
    event.preventDefault();
    let email = event.target.elements.email.value;
    let password = event.target.elements.password.value;

    let loginPromise = UserService.login(email, password);

    loginPromise
      .then(function (response) {
        console.log("odpowiedź");
        if (response.status == 200) {
          console.log("zalogowano!");
          localStorage.setItem("logged-in", "true");
          console.log(response.data.role);
          localStorage.setItem("participant-role", response.data.role);
          localStorage.setItem("participant-email", response.data.email);
        } else if (response.status == 401) console.log("nie udalo sie zalogowac!");
        // if (response.data.redirect == "/") window.location = "/";
        // console.log(response);
      })
      .catch(function (error) {
        console.log(error);
      });
  }

  render() {
    return (
      <div className="container login-card mx-auto">
        <form className="login-form" onSubmit={this.handleSubmit}>
          <div className="form-group">
            <label for="exampleInputEmail1">Email address</label>
            <input
              type="email"
              name="email"
              className="form-control"
              id="exampleInputEmail1"
              aria-describedby="emailHelp"
              placeholder="Enter email"
            />
          </div>
          <div className="form-group">
            <label for="exampleInputPassword1">Password</label>
            <input type="password" name="password" className="form-control" id="exampleInputPassword1" placeholder="Password" />
          </div>
          <div className="form-check">
            <input type="checkbox" className="form-check-input" id="exampleCheck1" />
            <label className="form-check-label" for="exampleCheck1">
              Check me out
            </label>
          </div>
          <button type="submit" className="btn btn-primary">
            Submit
          </button>
        </form>

        <input
          type="submit"
          onClick={() => {
            UserService.testGet()
              .then(function (response) {
                console.log("odpowiedź2");
                console.log(response);
              })
              .catch(function (error) {
                console.log(error);
              });
          }}
        />
      </div>
    );
  }
}

export default LoginForm;
