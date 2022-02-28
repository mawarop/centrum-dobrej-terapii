import {Toast, ToastContainer} from "react-bootstrap";
import React from "react";
function InfoToast(props) {


    return (<ToastContainer  position="top-end" className="p-3">
        <Toast show={props.showToast} bg={props.isActionSuccess ? "success" : "danger"} onClose={() => props.onClose()} delay={3000} autohide>
            <Toast.Header>
                <strong className="me-auto">{props.headerContent}</strong>
            </Toast.Header>
            <Toast.Body>{props.bodyContent ? props.bodyContent :
                props.isActionSuccess ? "Akcja zakończona powodzeniem": "Nie udało się wykonać akcji"}</Toast.Body>
        </Toast>
    </ToastContainer>);

}

InfoToast.defaultProps={
    isActionSuccess: false,
    headerContent: "Status akcji",
    // bodyContent: this.props.isActionSuccess ? "Akcja zakończona powodzeniem" : "Nie udało się wykonać akcji"
}

export default InfoToast;