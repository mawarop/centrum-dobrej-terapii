import {Spinner} from "react-bootstrap";

function CenteredSpinner(props) {
    let mainStyle ={position: "absolute",left: "50%", top: "50%"}

    return(<Spinner animation="border" role="status" style={mainStyle}>
        <span className="visually-hidden">Loading...</span>
    </Spinner>);
}

export default CenteredSpinner;

