import {Button, Container, Form} from "react-bootstrap";
import React, {useState} from "react";
import DoctorService from "../../services/DoctorService";
import InfoToast from "../InfoToast";

function UploadPatientFile() {
  const [validated, setValidated] = useState(false);
  const [showToast, setShowToast] = useState(false);
  const [isActionSuccess, setIsActionSuccess] = useState(false);

  const toggleShow = () => setShowToast(!showToast);

  function handleSubmit(event) {
    event.preventDefault();

    const form = event.currentTarget;
    if (form.checkValidity() === false) {
      event.preventDefault();
      event.stopPropagation();
    } else {
      let formData = new FormData(event.target);
      let fileUploadPromise = DoctorService.fileUpload(formData);
      fileUploadPromise
        .then((res) => {
          console.log(res.status);
          setShowToast(true);
          setIsActionSuccess(true);
        })
        .catch((error) => {
          console.log(error);
          setShowToast(true);
          setIsActionSuccess(false);
        });
    }
    setValidated(true);
  }

  return (
    <>
      <Container>
        <Form
          id="file-upload-form"
          noValidate
          validated={validated}
          onSubmit={handleSubmit}
          // feedback="Please provide a valid email and password"
          // feedbackType="invalid"
          method="Post"
        >
          <Form.Group className="mb-3">
            <Form.Label for="userPesel">Pesel pacjenta</Form.Label>
            <Form.Control
                required
                type="text"
                pattern="\d{11}"
                name="userPesel"
                id="userPesel"
                placeholder="Podaj pesel"
                maxLength={11}
                minLength={11}
            />
          </Form.Group>
          <Form.Group controlId="formFile" className="mb-3">
            <Form.Label>Plik pacjenta</Form.Label>
            <Form.Control
              required
              id="document-file"
              type="file"
              name={"file"}
            />
          </Form.Group>
          <Button className="mb-2" type="submit" variant="primary">
            Submit
          </Button>
        </Form>
      </Container>
      {/*<InfoToast bodyContent="Przesłano pomyślnie plik" headerContent = "Potwierdzenie akcji" showToast={showToast} onClose={() => handleToastClose()} />*/}
      <InfoToast
        showToast={showToast}
        isActionSuccess={isActionSuccess}
        onClose={() => {
          setShowToast(false);
          // setIsActionSuccess(false)
        }}
      />
    </>
  );

  function handleToastClose() {
    setShowToast(false);
  }
}

export default UploadPatientFile;
