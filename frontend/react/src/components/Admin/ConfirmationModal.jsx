import {Button, Modal} from "react-bootstrap";
import {Back} from "react-bootstrap-icons";

function ConfirmationModal(props) {


    return(
        <Modal show={props.show} onHide={() => props.onHide()}>
            <Modal.Header closeButton>
                <Modal.Title>{props.title}</Modal.Title>
            </Modal.Header>

            <Modal.Body>
                <p>{props.body}</p>
            </Modal.Body>

            <Modal.Footer>
                <Button variant="danger" onClick={() => props.onBlock(props.chosenUserId)}>{props.footerButtonText}</Button>
                <Button variant="secondary" onClick={() => props.onHide()}>Zamknij</Button>
            </Modal.Footer>
        </Modal>
    )
}

export default ConfirmationModal;