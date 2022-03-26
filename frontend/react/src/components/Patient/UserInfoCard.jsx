import {Button, Card} from "react-bootstrap";
import {specializationEngToPl} from "../../enums/specializationEngToPl";

function UserInfoCard(props) {
  return (
      <Card style={{width: "18rem"}} className={props.className}>
        {/*<Card.Img variant="top" src="holder.js/100px180"/>*/}
        <Card.Body>
          <Card.Title>
            {props.firstname} {props.lastname}
          </Card.Title>
          <Card.Text>
            <p>{specializationEngToPl[props.specialization]}</p>
          </Card.Text>
          <Button variant="primary" onClick={() => props.onClick()}>
            Wybierz specjalistę
          </Button>
      </Card.Body>
    </Card>
  );
}

export default UserInfoCard;
