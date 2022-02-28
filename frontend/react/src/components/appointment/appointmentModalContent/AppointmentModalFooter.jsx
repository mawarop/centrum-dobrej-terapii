import {Button} from "react-bootstrap";
import {role} from "../../../enums/role";
import PatientService from "../../../services/PatientService";
import DoctorService from "../../../services/DoctorService";
import React from "react";
import {appointmentStatus} from "../../../enums/appointmentStatus";

function AppointmentModalFooter(props) {

    const appointmentStatusConditionalFooterContentEnum={

    [appointmentStatus.ACCEPTED]: (()=> {return (Date.now() < props.modalEvent.start &&
        <>
        <Button onClick={() => {
            switch (props.role){
                case role.PATIENT: PatientService.cancelAppointment(props.modalEvent.id).then((res) =>{console.log(res);props.onActionButtonClick();} ).catch((error)=>console.log(error));
                    break;
                case role.DOCTOR: DoctorService.cancelAppointment(props.modalEvent.id).then((res) =>{console.log(res);props.onActionButtonClick();}).catch((error)=>console.log(error));
            }
            props.onHide();
        }}>Zrezygnuj z wizyty </Button>
            {props.role===role.DOCTOR && <Button type="submit" form="appointment-details-form">Edytuj szczegóły</Button>}
        </>
        );})(),

    [appointmentStatus.FREE_DATE]: (() => {if(props.role===role.PATIENT && (Date.now() < props.modalEvent.start))
        return (<Button onClick={() => {
            PatientService.appointmentSignUp(props.modalEvent.id)
                .then((res) =>{
                    console.log(res);
                    props.onActionButtonClick();})
                .catch((error)=>
                    console.log(error));
                    props.onHide();}}>Zapisz się na wizytę</Button>);})()

    }

    return(
        <>
            {appointmentStatusConditionalFooterContentEnum[props.modalEvent.extendedProps.appointmentStatus]}
            <Button onClick={() => {props.onHide();}}>Zamknij</Button>
        </>
    )
}

export default AppointmentModalFooter;