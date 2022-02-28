import moment from "moment";
import {Role} from "../../../enums/role";
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

    function isAppointmentAfterTodayDate(){
        return Date.now() < props.modalEvent.start;
    }

    const appointmentStatusConditionalBodyContentEnum={
        [appointmentStatus.ACCEPTED]:
            (()=> {return (<div>
            {props.role=== Role.DOCTOR &&
                <Card body>
                    <Form id="appointment-details-form" onSubmit={handleDetailsFormSubmit}>
                    <InputGroup>
                        <InputGroup.Text> Szczegóły odnośnie wizyty:</InputGroup.Text>
                        <FormControl name="details" as="textarea" defaultValue={props.modalEvent.extendedProps.details}  disabled={!isAppointmentAfterTodayDate()}/>
                    </InputGroup>
                    </Form>
                    </Card>
            }
            {props.role !== Role.DOCTOR &&
                <p>Szczegóły: {props.modalEvent.extendedProps.details} </p>
            }
            {isAppointmentAfterTodayDate() && <p>Czy odwołać wizytę?</p>}
            </div>);})(),

        [appointmentStatus.FREE_DATE]:
            (()=>{if(props.role=== Role.PATIENT) {return (<div>
            {isAppointmentAfterTodayDate() ? <p>Czy chcesz zapisać sie na wizytę o podanej godzinie?</p>:''}
        </div>)}
        else if (props.role === Role.DOCTOR) return "Przedział czasowy do zapisu wizyty";})(),

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