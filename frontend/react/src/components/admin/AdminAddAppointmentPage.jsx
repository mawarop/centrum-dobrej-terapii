import React, {useState} from "react";
import "../../pages/Form.css";
import {Button, Container, Form} from "react-bootstrap";
import InfoToast from "../InfoToast";

function AdminAddAppointmentPage(props) {
  const [validated, setValidated] = useState(false);
  const [credentialsFeedback, setCredentialsFeedback] = useState("");
  const [redirect, setRedirect] = useState(false);
  const [showToast, setShowToast] = useState(false);
  const [isPatientEmailDisabled, setIsPatientEmailDisabled] = useState(false);

  function handleSubmit(event) {
    event.preventDefault();

    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      event.preventDefault();
      event.stopPropagation();
    } else {
      let formData = new FormData(event.target);
      let jsonFormData = JSON.stringify(Object.fromEntries(formData));
      console.log(jsonFormData);
      props
        .makeRequest(jsonFormData)
        .then((res) => {
          console.log(res.status);
          if (res.status >= 200 && res.status < 300) {
            setCredentialsFeedback("");
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
          id="appointment-form"
          className="appointment-form"
          onSubmit={(event) => handleSubmit(event)}
          method="Post"
        >
          <Form.Group>
            <Form.Label for="start">Data rozpoczęcia</Form.Label>
            <Form.Control
              required
              type="datetime-local"
              name="start"
              id="start"
              placeholder="Wprowadź datę rozpoczęcia"
            />
          </Form.Group>
          <Form.Group>
            <Form.Label for="end">Data zakończenia</Form.Label>
            <Form.Control
              required
              type="datetime-local"
              name="end"
              id="end"
              placeholder="Wprowadź datę zakończenia"
            />
          </Form.Group>

          <Form.Group>
            <Form.Label for="doctorEmail">Email doktora</Form.Label>
            <Form.Control
              required
              type="text"
              name="doctorEmail"
              id="doctorEmail"
              placeholder="Wprowadź email doktora"
            />
          </Form.Group>

          <Button className="mt-2" type="submit" variant="primary">
            Zapisz
          </Button>
          <div style={{ color: "#dc3545", textAlign: "center" }}>
            {credentialsFeedback}
          </div>
        </Form>
      </Container>
    </>
  );
}

export default AdminAddAppointmentPage;
