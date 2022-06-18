// import "bootstrap-icons";
import {Download} from "react-bootstrap-icons";
import {ListGroup} from "react-bootstrap";
import doctorService from "../../services/DoctorService";
import fileDownload from "js-file-download";

function DocumentFile(props) {
    return (
        <div>
            <ListGroup className={"my-2"}>
                <ListGroup.Item>
                    {" "}
                    <a
                        href="#"
                        onClick={() => {
                            doctorService.fileDownload(props.name).then((res) => {
                                fileDownload(res.data, props.name);
                            });
                        }}
                        style={{"text-decoration": "none"}}
                    >
                        <Download size={28} color={"black"}/> {props.name}
                    </a>
                </ListGroup.Item>
            </ListGroup>
        </div>
    );
}
export default DocumentFile;
