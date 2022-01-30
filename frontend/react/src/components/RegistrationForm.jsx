import React, { Component } from "react";
import $ from "jquery";
import UserService from "../services/UserService";
import "./LoginForm.css";

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
      <div className="container login-card mx-auto">
        <form id="registration-form" className="registration-form" onSubmit={this.handleSubmit}>
          <div className="form-group">
            <label for="username">username</label>
            <input type="text" name="username" className="form-control" id="username" placeholder="Enter username" />
          </div>

          <div className="form-group">
            <label for="Password">Password</label>
            <input type="password" name="password" className="form-control" id="password" placeholder="Password" />
          </div>

          <div className="form-group">
            <label for="username">Email address</label>
            <input type="text" name="email" className="form-control" id="email" placeholder="Enter email" />
          </div>

          <div className="form-group">
            <label for="pesel">Pesel</label>
            <input type="text" name="pesel" className="form-control" id="pesel" placeholder="Enter pesel" />
          </div>

          <div className="form-group">
            <label for="firstname">Firstname</label>
            <input type="text" name="firstname" className="form-control" id="firstname" placeholder="Enter firstname" />
          </div>

          <div className="form-group">
            <label for="lastname">Lastname</label>
            <input type="text" name="lastname" className="form-control" id="lastname" placeholder="Enter lastname" />
          </div>

          <div className="form-group">
            <label for="phone_number">Phone number</label>
            <input type="text" name="phone_number" className="form-control" id="phone_number" placeholder="Enter phone_number" />
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
      </div>
    );
  }
}

export default RegistrationForm;
