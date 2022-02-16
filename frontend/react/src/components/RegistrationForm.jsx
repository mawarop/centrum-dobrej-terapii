import React, { Component } from "react";
import $ from "jquery";
import UserService from "../services/UserService";
import "./Form.css";
import { Form, Container, FormControl, Button, FormForm } from "react-bootstrap";
class RegistrationForm extends Component {
  constructor(props) {
    super(props);

    this.handleSubmit = this.handleSubmit.bind(this);
    this.state = { validated: false, credentialsFeedback: "" };
  }

  handleSubmit(event) {
    event.preventDefault();

    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      event.preventDefault();
      event.stopPropagation();
    } else {
      let formData = new FormData(event.target);

      let jsonFormData = JSON.stringify(Object.fromEntries(formData));
      console.log(jsonFormData);
      UserService.register(jsonFormData)
        .then((res) => {
          console.log(res.status);
          if (res.status == 201) {
            this.setState({ credentialsFeedback: "" });
          }
        })
        .catch((error) => {
          console.log(error.status);
          this.setState({ validated: false });
          form.reset();

          this.setState({ credentialsFeedback: "Podano niepoprawne dane" });
        });
    }
    this.setState({ validated: true });
  }
  render() {
    return (
      <Container className="form-card mx-auto">
        <Form
          noValidate
          validated={this.state.validated}
          id="registration-form"
          className="registration-form"
          onSubmit={this.handleSubmit}
          method="Post"
        >
          <Form.Group>
            <Form.Label for="username">Username</Form.Label>
            <Form.Control required type="text" name="username" id="username" placeholder="Enter username" />
          </Form.Group>

          <Form.Group>
            <Form.Label for="Password">Password</Form.Label>
            <Form.Control required type="password" name="password" id="password" placeholder="Password" />
          </Form.Group>

          <Form.Group>
            <Form.Label for="email">Email address</Form.Label>
            <Form.Control required type="email" name="email" id="email" placeholder="Enter email" />
          </Form.Group>

          <Form.Group>
            <Form.Label for="pesel">Pesel</Form.Label>
            <Form.Control required type="text" pattern="\d{11}" name="pesel" id="pesel" placeholder="Enter pesel" maxLength={11} minLength={11} />
          </Form.Group>

          <Form.Group>
            <Form.Label for="firstname">Firstname</Form.Label>
            <Form.Control required type="text" name="firstname" id="firstname" placeholder="Enter firstname" />
          </Form.Group>

          <Form.Group>
            <Form.Label for="lastname">Lastname</Form.Label>
            <Form.Control required type="text" name="lastname" id="lastname" placeholder="Enter lastname" />
          </Form.Group>

          <Form.Group>
            <Form.Label for="phone_number">Phone number</Form.Label>
            <Form.Control
              required
              type="text"
              pattern="\d{9}"
              name="phone_number"
              id="phone_number"
              placeholder="Enter phone_number"
              maxLength={9}
              minLength={9}
            />
          </Form.Group>

          <Form.Group>
            <Form.Check
              required
              type="checkbox"
              id="exampleCheck1"
              feedback="You must agree before submitting."
              feedbackType="invalid"
              label="Check me out"
            />
          </Form.Group>

          <Button type="submit" variant="primary">
            Submit
          </Button>
          <div style={{ color: "#dc3545", textAlign: "center" }}>{this.state.credentialsFeedback}</div>
        </Form>
      </Container>
    );
  }
}

export default RegistrationForm;
