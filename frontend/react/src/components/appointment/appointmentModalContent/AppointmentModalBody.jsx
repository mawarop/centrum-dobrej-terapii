import moment from "moment";
import {role} from "../../../enums/role";
import {Card, Form, FormControl, InputGroup} from "react-bootstrap";
import React from "react";
import {appointmentStatus} from "../../../enums/appointmentStatus";
import DoctorService from "../../../services/DoctorService";

function AppointmentModalBody(props) {

    function handleDetailsFormSubmit(event){
        event.preventDefault();
        // event.stopPropagation();
        let details = event.target.elements.details.value;
        console.log(details);
        DoctorService.updateAppointmentDetails(props.modalEvent.id, details).then((res)=> console.log(res));
    }

    const appointmentStatusConditionalBodyContentEnum={
        [appointmentStatus.ACCEPTED]:
            (()=> {return (<div>
            {props.role=== role.DOCTOR &&
                <Card body>
                    <Form id="appointment-details-form" onSubmit={handleDetailsFormSubmit}>
                    <InputGroup>
                        <InputGroup.Text> Szczegóły odnośnie wizyty:</InputGroup.Text>
                        <FormControl name="details" as="textarea" defaultValue={props.modalEvent.extendedProps.details}/>
                    </InputGroup>
                    </Form>
                    </Card>
            }
            {props.role !== role.DOCTOR &&
                <p>Szczegóły: {props.modalEvent.extendedProps.details} </p>
            }
            {Date.now() < props.modalEvent.start && <p>Czy odwołać wizytę?</p>}
            </div>);})(),

        [appointmentStatus.FREE_DATE]:
            (()=>{if(props.role=== role.PATIENT) {return (<div>
            {Date.now() < props.modalEvent.start ? <p>Czy chcesz zapisać sie na wizytę o podanej godzinie?</p>:''}
        </div>)}
        else if (props.role === role.DOCTOR) return "Przedział czasowy do zapisu wizyty";})(),

        [appointmentStatus.CANCELED]:
            (()=>{return (<div>
            <p>Szczegóły: {props.modalEvent.extendedProps.details} </p>
            <p>Wizyta została anulowana</p>
        </div>);})(),

        [appointmentStatus.FINALIZED]:
            (()=>{return (<div>
            <p>Szczegóły: {props.modalEvent.extendedProps.details} </p>
        </div>);})(),
    }

    return (
        <>
            <p>Start: {moment(props.modalEvent.start).format('LLLL')} </p>
            <p>Koniec: {moment(props.modalEvent.end).format('LLLL')} </p>
            {appointmentStatusConditionalBodyContentEnum[props.modalEvent.extendedProps.appointmentStatus]}
        </>
    )
}

export default AppointmentModalBody;