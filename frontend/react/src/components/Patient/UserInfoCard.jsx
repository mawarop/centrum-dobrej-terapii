import {Card, Button} from "react-bootstrap";
import PickedDoctorFreeDates from "./PickedDoctorFreeDates";
import {useState} from "react";

function UserInfoCard(props) {

    return(
                <Card style={{width: '18rem'}} className={props.className}>
                    <Card.Img variant="top" src="holder.js/100px180"/>
                    <Card.Body>
                        <Card.Title>{props.firstname} {props.lastname} </Card.Title>
                        <Card.Text>
                            <p></p>
                        </Card.Text>
                        <Button variant="primary" onClick={() => props.onClick()}>Go somewhere</Button>
                    </Card.Body>
                </Card>
    );

}

export default  UserInfoCard;