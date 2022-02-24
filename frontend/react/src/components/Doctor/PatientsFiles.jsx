import DoctorService from "../../services/DoctorService";
import React, {useState} from "react";
import {Button, Container, Form} from "react-bootstrap";
import {preventDefault} from "@fullcalendar/core";
import DocumentFile from "./DocumentFile";
import {map} from "react-bootstrap/ElementChildren";

function PatientsFiles(props){
    const [patients, setPatients] = useState("");
    const [validated, setValidated] = useState(false);
    const [documentsPaths, setDocumentsPaths] = useState([]);
    const [documentsPathsDownloaded, setDocumentsPathsDownloaded] = useState(false);
    const [credentialsFeedback, setCredentialsFeedback] = useState("");
    // useEffect(() => {
    //     preventDefault();
    //     let patientsPromise = DoctorService.getPatientsBasicData();
    //     patientsPromise.then( (res => {
    //         setPatients(res.data)
    //         })
    //
    //     ).catch();
    // });

        if(!documentsPathsDownloaded) {
            return(
                <Container className="text-center">
            <Form
                id="pesel-form"
                noValidate
                validated={validated}
                onSubmit={(event) =>handleSubmit(event)}
                method="Post"
            >
                <Form.Group>
                    <Form.Label for="pesel">Pesel</Form.Label>
                    <Form.Control required type="text" pattern="\d{11}" name="pesel" id="pesel" placeholder="Enter pesel"
                                  maxLength={11} minLength={11}/>
                </Form.Group>
                <Button className="mt-2" type="submit" variant="primary">
                    Submit
                </Button>
                <div style={{ color: "#dc3545", textAlign: "center" }}>{credentialsFeedback}</div>
            </Form>
                    </Container>
            )
        }
        else{
            return(
                <Container>
                    {documentsPaths.map( (d) => {
                        return <DocumentFile href = {d.path} name = {d.name} />
                    })}
                </Container>
            )
        }


    function handleSubmit(event) {
        event.preventDefault();
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        } else {
            // let formData = new FormData(event.target);
            // formData.get("pesel")
            let pesel = event.target.elements.pesel.value;
            console.log(pesel);
            let pathsPromise = DoctorService.getPatientDocumentsPaths(pesel);
            pathsPromise.then(
                (res) => {
                    console.log(res);
                    console.log(res.data)
                    setDocumentsPaths(res.data);
                    setDocumentsPathsDownloaded(true);
                }
            ).catch(error => {
                console.log(error);

                setValidated(false);
                form.reset();

                setCredentialsFeedback("Użytkownik nie posiada dokumentów lub podany pesel jest nieprawidłowy");
            })
        }
    }
}

export default PatientsFiles;