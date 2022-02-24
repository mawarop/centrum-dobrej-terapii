import React, {useContext, useState} from "react";
import FullCalendar from "@fullcalendar/react"; // must go before plugins
import dayGridPlugin from "@fullcalendar/daygrid"; // a plugin!
// import interactionPlugin from "@fullcalendar/interaction"; // needed for dayClick
import "./Form.css";
import axios from "axios";
import UserService from "../services/UserService";
import { Form, Container, FormControl, Button, Alert } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

function LoginForm(props) {
  // this.state = {
  //   validated: false,
  //   credentialsFeedback: "",
  // };

  const [validated, setValidated] = useState(false);
  const [credentialsFeedback, setcredentialsFeedback] = useState("");
  let navigate = useNavigate();


  function handleSubmit(event) {
    event.preventDefault();

    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      event.preventDefault();
      event.stopPropagation();
    } else {
      let email = event.target.elements.email.value;
      let password = event.target.elements.password.value;

      let loginPromise = UserService.login(email, password);

      loginPromise
        .then((response) => {
          console.log("odpowiedź");
          if (response.status == 200) {
            setcredentialsFeedback("");
            console.log("zalogowano!");
            localStorage.setItem("logged-in", "true");
            console.log(response.data.role);
            localStorage.setItem("participant-role", response.data.role);
            // localStorage.setItem("participant-email", response.data.email);
            console.log("xd");
            navigate("/patient-appointments");
          }

          // if (response.data.redirect == "/") window.location = "/";
          // console.log(response);
        })
        .catch((error) => {
          console.log(error);
          // document.getElementById("login-form").reset();
          setValidated(false);
          form.reset();

          setcredentialsFeedback("Podano niepoprawne dane logowania");
        });
    }
    setValidated(true);
  }

  return (
    <Container className="form-card mx-auto">
      <Form
        id="login-form"
        noValidate
        validated={validated}
        onSubmit={handleSubmit}
        // feedback="Please provide a valid email and password"
        // feedbackType="invalid"
        method="Post"
      >
        <Form.Group className="mb-2">
          <Form.Label for="exampleInputEmail1">Email address</Form.Label>
          <Form.Control
            required
            type="email"
            name="email"
            className="form-control"
            id="exampleInputEmail1"
            aria-describedby="emailHelp"
            placeholder="Enter email"
          />
        </Form.Group>
        <Form.Group className="mb-2">
          <Form.Label for="exampleInputPassword1">Password</Form.Label>
          <Form.Control required type="password" name="password" className="form-control" id="exampleInputPassword1" placeholder="Password" />
        </Form.Group>
        {/* <div className="form-check"> */}
        <Form.Group className="mb-2">
          <Form.Check
            required
            id="exampleCheck1"
            type="checkbox"
            label="Check me out"
            feedback="You must agree before submitting."
            feedbackType="invalid"
          />
          {/* </div> */}
        </Form.Group>

        <Button className="mb-2" type="submit" variant="primary">
          Submit
        </Button>
        <div style={{ color: "#dc3545", textAlign: "center" }}>{credentialsFeedback}</div>
        {/* <Form.Control. */}
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

export default LoginForm;
