import {Toast, ToastContainer} from "react-bootstrap";
import React from "react";
function InfoToast(props) {


    return (<ToastContainer position="top-end" className="p-3">
        <Toast show={props.showToast} onClose={() => props.onClose()} delay={3000} autohide>
            <Toast.Header>
                <strong className="me-auto">{props.headerContent}</strong>
            </Toast.Header>
            <Toast.Body>{props.bodyContent}</Toast.Body>
        </Toast>
    </ToastContainer>);

}

export default InfoToast;