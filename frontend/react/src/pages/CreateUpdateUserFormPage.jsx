import React, { useState } from "react";
import "./Form.css";
import { Button, Container, Form } from "react-bootstrap";
import { Navigate } from "react-router-dom";
import InfoToast from "../components/InfoToast";

function CreateUpdateUserFormPage(props) {
  const [validated, setValidated] = useState(false);
  const [credentialsFeedback, setCredentialsFeedback] = useState("");
  const [redirect, setRedirect] = useState(false);
  const [showToast, setShowToast] = useState(false);

  function setInputValueIfExist(value) {
    if (props.formData !== undefined) return value;
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
      console.log("role elem:" + role);
      let roleValue;
      role ? (roleValue = role.value) : (roleValue = undefined);
      let jsonFormData = JSON.stringify(Object.fromEntries(formData));
      console.log(jsonFormData);
      props
        .makeRequest(
          jsonFormData,
          roleValue ? roleValue : props.formData ? props.formData.id : null
        )
        .then((res) => {
          console.log(res.status);
          if (res.status >= 200 && res.status < 300) {
            setCredentialsFeedback("");

            //TODO
            // if(props.onNavigate) {
            //   props.onNavigate();
            //   setRedirect(true);
            // }

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
      <InfoToast
        isActionSuccess={true}
        showToast={showToast}
        onClose={() => setShowToast(false)}
      />

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
            <Form.Label for="username">Nazwa użytkownika</Form.Label>
            <Form.Control
              required
              type="text"
              name="username"
              id="username"
              placeholder="Wprowadź nazwę użytkownika"
              defaultValue={
                props.formData
                  ? setInputValueIfExist(props.formData.username)
                  : ""
              }
            />
          </Form.Group>

          {props.hasPasswordInput && (
            <Form.Group>
              <Form.Label for="Password">Hasło</Form.Label>
              <Form.Control
                required
                type="password"
                name="password"
                id="password"
                placeholder="Wprowadź hasło"
              />
            </Form.Group>
          )}

          <Form.Group>
            <Form.Label for="email">Email</Form.Label>
            <Form.Control
              required
              type="email"
              name="email"
              id="email"
              placeholder="Wprowadź email"
              defaultValue={
                props.formData ? setInputValueIfExist(props.formData.email) : ""
              }
            />
          </Form.Group>

          {props.hasPeselInput && (
            <Form.Group>
              <Form.Label for="pesel">Pesel</Form.Label>
              <Form.Control
                required
                type="text"
                pattern="\d{11}"
                name="pesel"
                id="pesel"
                placeholder="Wprowadź pesel"
                maxLength={11}
                minLength={11}
              />
            </Form.Group>
          )}
          <Form.Group>
            <Form.Label for="firstname">Imię</Form.Label>
            <Form.Control
              required
              type="text"
              name="firstname"
              id="firstname"
              placeholder="Wprowadź imię"
              defaultValue={
                props.formData
                  ? setInputValueIfExist(props.formData.firstname)
                  : ""
              }
            />
          </Form.Group>

          <Form.Group>
            <Form.Label for="lastname">Nazwisko</Form.Label>
            <Form.Control
              required
              type="text"
              name="lastname"
              id="lastname"
              placeholder="Wprowadź nazwisko"
              defaultValue={
                props.formData
                  ? setInputValueIfExist(props.formData.lastname)
                  : ""
              }
            />
          </Form.Group>

          <Form.Group>
            <Form.Label for="phone_number">Numer telefonu</Form.Label>
            <Form.Control
              required
              type="text"
              pattern="\d{9}"
              name="phone_number"
              id="phone_number"
              placeholder="Wprowadź numer telefonu"
              maxLength={9}
              minLength={9}
              defaultValue={setInputValueIfExist(
                props.formData ? props.formData.phone_number : ""
              )}
            />
          </Form.Group>

          {props.hasRoleInput && (
            <Form.Group>
              <Form.Label for="role">Role</Form.Label>
              <Form.Select required name="role" id="role">
                <option value="PATIENT">PACJENT</option>
                <option value="DOCTOR">DOKTOR</option>
              </Form.Select>
            </Form.Group>
          )}

          {props.role === undefined &&
            localStorage.getItem("participant-role") !== "ADMIN" && (
              <Form.Group className="my-2">
                <Form.Check
                  required
                  type="checkbox"
                  id="exampleCheck1"
                  feedback="Musisz wyrazić zgodę na przetwarzanie danych osobowych"
                  feedbackType="invalid"
                  label="Wyrażam zgodę na przetwarzanie moich danych osobowych"
                />
              </Form.Group>
            )}

          <Button className="mt-2" type="submit" variant="primary">
            {localStorage.getItem("participant-role") === "ADMIN"
              ? "Zapisz"
              : "Zarejestruj"}
          </Button>
          <div style={{ color: "#dc3545", textAlign: "center" }}>
            {credentialsFeedback}
          </div>
        </Form>
      </Container>

      {props.onNavigate && (
        <div className="text-center my-2">
          <Button
            style={{ width: "200px" }}
            onClick={() => {
              props.onNavigate();
              setRedirect(true);
            }}
          >
            Powrót
          </Button>
        </div>
      )}

      {redirect && <Navigate to={props.redirectUrl} />}
    </>
  );
}

export default CreateUpdateUserFormPage;
