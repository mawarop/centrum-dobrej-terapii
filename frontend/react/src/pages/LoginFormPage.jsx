import React, {useState} from "react";
// import interactionPlugin from "@fullcalendar/interaction"; // needed for dayClick
import "./Form.css";
import UserService from "../services/UserService";
import {Button, Container, Form} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import userMainPath from "../utilities/pagePath/userMainPath";

function LoginFormPage(props) {
  // this.state = {
  //   validated: false,
  //   credentialsFeedback: "",
  // };
  // props.onNavbarUpdate();
  const [validated, setValidated] = useState(false);
  const [credentialsFeedback, setCredentialsFeedback] = useState("");
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
            setCredentialsFeedback("");
            console.log("zalogowano!");
            localStorage.setItem("logged-in", "true");
            console.log(response.data.role);
            localStorage.setItem("participant-role", response.data.role);
            sessionStorage.setItem("jwt-token", response.headers.authorization);
            // localStorage.setItem("participant-email", response.data.email);
            let redirectPath = userMainPath.getPath();
            navigate(redirectPath);
          }
        })
        .catch((error) => {
          console.log(error);
          setValidated(false);
          form.reset();

          setCredentialsFeedback("Podano niepoprawne dane logowania");
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
        method="Post"
      >
        <Form.Group className="mb-2">
          <Form.Label for="emailInput">Email</Form.Label>
          <Form.Control
            required
            type="email"
            name="email"
            className="form-control"
            id="emailInput"
            aria-describedby="emailHelp"
            placeholder="Wprowadź email"
          />
        </Form.Group>
        <Form.Group className="mb-2">
          <Form.Label for="passwordInput">Hasło</Form.Label>
          <Form.Control
            required
            type="password"
            name="password"
            className="form-control"
            id="passwordInput"
            placeholder="Wprowadź hasło"
          />
        </Form.Group>
        {/* <div className="form-check"> */}
        {/*<Form.Group className="mb-2">*/}
        {/*  <Form.Check*/}
        {/*    required*/}
        {/*    type="checkbox"*/}
        {/*    label="Check me out"*/}
        {/*    feedback="Musisz wyrazić zgodę przed zalogowaniem"*/}
        {/*    feedbackType="invalid"*/}
        {/*  />*/}
        {/*  /!* </div> *!/*/}
        {/*</Form.Group>*/}

        <Button className="mb-2" type="submit" variant="primary">
          Zaloguj
        </Button>
        <div style={{ color: "#dc3545", textAlign: "center" }}>
          {credentialsFeedback}
        </div>
        {/* <Form.Control. */}
      </Form>
    </Container>
  );
}

export default LoginFormPage;
