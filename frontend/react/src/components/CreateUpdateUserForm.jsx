import React, {Component, useState} from "react";
import $ from "jquery";
import UserService from "../services/UserService";
import "./Form.css";
import { Form, Container, FormControl, Button, FormForm } from "react-bootstrap";
import {Navigate} from "react-router-dom";
import InfoToast from "./InfoToast";

function CreateUpdateUserForm(props) {
  const [validated, setValidated] = useState(false);
  const [credentialsFeedback, setCredentialsFeedback] = useState("");
  const [redirect, setRedirect] = useState(false);
  const [showToast, setShowToast] = useState(false);

  function setInputValueIfExist(value)
  {
    if (props.formData !== undefined)
      return value;
    return "";
  }

  function handleSubmit(event) {
    event.preventDefault();

    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      event.preventDefault();
      event.stopPropagation();
    } else {
      let formData = new FormData(event.target);
      let role = event.target.elements.role;
      let roleValue;
      role ? roleValue= role.value : roleValue = undefined;
      let jsonFormData = JSON.stringify(Object.fromEntries(formData));
      console.log(jsonFormData);
      props.makeRequest(jsonFormData, roleValue ? roleValue : props.formData.id )
        .then((res) => {
          console.log(res.status);
          if (res.status >= 200 && res.status < 300) {
            setCredentialsFeedback("");
            if(props.onNavigate) {
              props.onNavigate();
              setRedirect(true);
            }
            setShowToast(true);
          }
        })
        .catch((error) => {
          console.log(error);
          console.log(error.status);
          setValidated(false);
          form.reset();
          setCredentialsFeedback("Podano niepoprawne dane");
        });
    }
    setValidated(true);
  }
 
    return (
        <>
          <InfoToast bodyContent="Pomyślnie wykonano operację" headerContent = "Potwierdzenie akcji" showToast={showToast} onClose={() => setShowToast(false)} />

      <Container className="form-card mx-auto">
        <Form
          noValidate
          validated={validated}
          id="registration-form"
          className="registration-form"
          onSubmit={(event) => handleSubmit(event)}
          method="Post"
        >
          <Form.Group>
            <Form.Label for="username">Username</Form.Label>
            <Form.Control required type="text" name="username" id="username" placeholder="Enter username" defaultValue={props.formData? setInputValueIfExist(props.formData.username): ""} />
          </Form.Group>

          { props.hasPasswordInput &&
            <Form.Group>
              <Form.Label for="Password">Password</Form.Label>
              <Form.Control required type="password" name="password" id="password" placeholder="Password"/>
            </Form.Group>
          }

          <Form.Group>
            <Form.Label for="email">Email address</Form.Label>
            <Form.Control required type="email" name="email" id="email" placeholder="Enter email" defaultValue={props.formData? setInputValueIfExist(props.formData.email): ""}/>
          </Form.Group>

          {props.hasPeselInput &&
              <Form.Group>
                <Form.Label for="pesel">Pesel</Form.Label>
                <Form.Control required type="text" pattern="\d{11}" name="pesel" id="pesel" placeholder="Enter pesel"
                              maxLength={11} minLength={11}/>
              </Form.Group>
          }
          <Form.Group>
            <Form.Label for="firstname">Firstname</Form.Label>
            <Form.Control required type="text" name="firstname" id="firstname" placeholder="Enter firstname" defaultValue={props.formData? setInputValueIfExist(props.formData.firstname): ""}/>
          </Form.Group>

          <Form.Group>
            <Form.Label for="lastname">Lastname</Form.Label>
            <Form.Control required type="text" name="lastname" id="lastname" placeholder="Enter lastname" defaultValue={props.formData? setInputValueIfExist(props.formData.lastname): ""}/>
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
              defaultValue={setInputValueIfExist(props.formData? props.formData.phone_number: "")}
            />
          </Form.Group>

          {props.hasRoleInput &&
              <Form.Group>
                <Form.Label for="role">Role</Form.Label>
                <Form.Select required name="role" id="role">
                  <option value="PATIENT">PATIENT</option>
                  <option value="DOCTOR">DOCTOR</option>
                </Form.Select>
              </Form.Group>
          }

          <Form.Group className="my-2">
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
          <div style={{ color: "#dc3545", textAlign: "center" }}>{credentialsFeedback}</div>
        </Form>

      </Container>

          {props.onNavigate && <div className="text-center my-2"><Button style={{width: "200px"}} onClick={() => {
            props.onNavigate();
            setRedirect(true);
          }}>Powrót</Button></div>
          }

          {redirect && <Navigate to={props.redirectUrl}/> }
    </>
    );
  }

export default CreateUpdateUserForm;
