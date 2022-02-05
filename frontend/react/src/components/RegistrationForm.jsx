import React, { Component } from "react";
import $ from "jquery";
import UserService from "../services/UserService";
import "./Form.css";
import { Form, Container, FormControl, Button, FormForm } from "react-bootstrap";
class RegistrationForm extends Component {
  constructor(props) {
    super(props);

    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleSubmit(event) {
    // let jsonFormData = JSON.stringify($("#registration-form").serializeArray());
    //console.log(jsonFormData);
    // let formData = new FormData($("#registration-form"));

    // let jsonFormData = JSON.stringify(Object.fromEntries(formData));
    // console.log(jsonFormData);
    let formData = new FormData(event.target);
    //let formElements = event.target.elements;
    let jsonFormData = JSON.stringify(Object.fromEntries(formData));
    console.log(jsonFormData);
    UserService.register(jsonFormData);
  }
  render() {
    return (
      <Container className="login-card mx-auto">
        <Form id="registration-form" className="registration-form" onSubmit={this.handleSubmit}>
          <Form.Group>
            <Form.Label for="username">Username</Form.Label>
            <Form.Control type="text" name="username" id="username" placeholder="Enter username" />
          </Form.Group>

          <Form.Group>
            <Form.Label for="Password">Password</Form.Label>
            <Form.Control type="password" name="password" id="password" placeholder="Password" />
          </Form.Group>

          <Form.Group>
            <Form.Label for="username">Email address</Form.Label>
            <Form.Control type="text" name="email" id="email" placeholder="Enter email" />
          </Form.Group>

          <Form.Group>
            <Form.Label for="pesel">Pesel</Form.Label>
            <Form.Control type="text" name="pesel" id="pesel" placeholder="Enter pesel" />
          </Form.Group>

          <Form.Group>
            <Form.Label for="firstname">Firstname</Form.Label>
            <Form.Control type="text" name="firstname" id="firstname" placeholder="Enter firstname" />
          </Form.Group>

          <Form.Group>
            <Form.Label for="lastname">Lastname</Form.Label>
            <Form.Control type="text" name="lastname" id="lastname" placeholder="Enter lastname" />
          </Form.Group>

          <Form.Group>
            <Form.Label for="phone_number">Phone number</Form.Label>
            <Form.Control type="text" name="phone_number" id="phone_number" placeholder="Enter phone_number" />
          </Form.Group>

          <div className="form-check">
            <Form.Check type="checkbox" id="exampleCheck1" />
            <Form.Label for="exampleCheck1">Check me out</Form.Label>
          </div>

          <Button type="submit" variant="primary">
            Submit
          </Button>
        </Form>
      </Container>
    );
  }
}

export default RegistrationForm;
