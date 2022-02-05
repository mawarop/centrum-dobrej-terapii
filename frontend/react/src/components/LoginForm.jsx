import React, { Component } from "react";
import FullCalendar from "@fullcalendar/react"; // must go before plugins
import dayGridPlugin from "@fullcalendar/daygrid"; // a plugin!
// import interactionPlugin from "@fullcalendar/interaction"; // needed for dayClick
import "./Form.css";
import axios from "axios";
import UserService from "../services/UserService";
import { Form, Container, FormControl, Button } from "react-bootstrap";

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
          sessionStorage.setItem("logged-in", "true");
          console.log(response.data.role);
          sessionStorage.setItem("participant-role", response.data.role);
          sessionStorage.setItem("participant-email", response.data.email);
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
      <Container className="login-card mx-auto">
        <Form className="login-form" onSubmit={this.handleSubmit}>
          <Form.Group>
            <Form.Label for="exampleInputEmail1">Email address</Form.Label>
            <Form.Control
              type="email"
              name="email"
              className="form-control"
              id="exampleInputEmail1"
              aria-describedby="emailHelp"
              placeholder="Enter email"
            />
          </Form.Group>
          <Form.Group>
            <Form.Label for="exampleInputPassword1">Password</Form.Label>
            <Form.Control type="password" name="password" className="form-control" id="exampleInputPassword1" placeholder="Password" />
          </Form.Group>
          {/* <div className="form-check"> */}
          <Form.Group>
            <Form.Check id="exampleCheck1" type="checkbox" label="Check me out" />
            {/* </div> */}
          </Form.Group>

          <Button type="submit" variant="primary">
            Submit
          </Button>
        </Form>

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
      </Container>
    );
  }
}

export default LoginForm;
