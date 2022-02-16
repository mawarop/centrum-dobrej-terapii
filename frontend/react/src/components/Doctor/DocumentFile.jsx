// import "bootstrap-icons";
import {Download} from "react-bootstrap-icons"
import {ListGroup} from "react-bootstrap";
function DocumentFile(props) {


    return(
        <div>
                <ListGroup className={"my-2"}>
                <ListGroup.Item> <a href={props.href} style={{"text-decoration": "none"}}><Download
                size={28} color={"black"}/> {props.name}</a>
                </ListGroup.Item>
                </ListGroup>
        </div>
    );

}
export default DocumentFile;