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
                id="login-form"
                noValidate
                validated={validated}
                onSubmit={handleSubmit}
                method="Post"
            >
                <Form.Group>
                    <Form.Label for="pesel">Pesel</Form.Label>
                    <Form.Control required type="text" name="pesel" id="pesel" placeholder="Enter pesel"/>
                </Form.Group>
                <Button className="mb-2" type="submit" variant="primary">
                    Submit
                </Button>
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

    function handleSubmit(event){
        event.preventDefault();
        let pesel = event.target.elements.pesel.value;
        let pathsPromise = DoctorService.getPatientDocumentsPaths(pesel);
        pathsPromise.then(
            (res) =>{
                console.log(res.data)
                setDocumentsPaths(res.data);
                setDocumentsPathsDownloaded(true);
            }
        ).catch()
    }

}

export default PatientsFiles;