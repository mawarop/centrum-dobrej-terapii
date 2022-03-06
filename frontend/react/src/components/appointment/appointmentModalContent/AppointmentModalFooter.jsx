import {Button} from "react-bootstrap";
import {Role} from "../../../enums/role";
import PatientService from "../../../services/PatientService";
import DoctorService from "../../../services/DoctorService";
import React from "react";
import {appointmentStatus} from "../../../enums/appointmentStatus";
import {useNavigate} from "react-router-dom";
import AppointmentHelper from "../../../utilities/AppointmentHelper";

function  AppointmentModalFooter(props) {

    function makeCancelAppointmentRequest(role, appointmentId){
        console.log("role:" + role);
        switch (role){
            case Role.PATIENT: return PatientService.cancelAppointment(appointmentId);
            break;
            case Role.DOCTOR: return DoctorService.cancelAppointment(appointmentId);
            break;
            default: throw new Error("Nie mozesz anulowac wizyty");
        }
    }

    function chooseAndMakeAppointmentSignUpRequest(){
        console.log("appointmentIdtoChange: " + props.appointmentIdToChangeDate)
        if(props.appointmentIdToChangeDate)
            return PatientService.changeAppointment(props.appointmentIdToChangeDate, props.modalEvent.id);

        return PatientService.appointmentSignUp(props.modalEvent.id);
    }


    const isPatient = () => {return props.role===Role.PATIENT};

    const appointmentStatusConditionalFooterContentEnum={

    [appointmentStatus.ACCEPTED]: ( () => {return (AppointmentHelper.isAppointmentAfterTodayDate(props.modalEvent.start) &&
        <>
        <Button onClick={() => {
            let requestPromise = makeCancelAppointmentRequest(props.role, props.modalEvent.id)
                requestPromise.then((res) =>
            {   console.log(res);
                let isSuccess=true;
                props.onActionButtonClick(isSuccess);
            })
                .catch((error) =>{
                console.log(error);
                let isSuccess=false;
                props.onActionButtonClick(isSuccess);
            })
            props.onHide();
        }}>Zrezygnuj z wizyty </Button>
            {props.role===Role.DOCTOR && <Button type="submit" form="appointment-details-form" onClick={() =>{props.onHide();}} >Edytuj szczegóły</Button>}
            {props.role === Role.PATIENT && <Button onClick={() => {props.onAppointmentDateChange(props.modalEvent.id); props.onHide();}}>Zmień termin wizyty</Button>}
        </>
        );})(),


    [appointmentStatus.FREE_DATE]: (() => {if(props.role===Role.PATIENT && (Date.now() < props.modalEvent.start))
        return (<Button onClick={() => {
            // PatientService.appointmentSignUp(props.modalEvent.id)
                chooseAndMakeAppointmentSignUpRequest()
                .then((res) =>{
                    console.log(res);
                    let isSuccess=true;
                    props.onActionButtonClick(isSuccess);})
                .catch((error)=>{
                    console.log(error);
                    let isSuccess=false;
                    props.onActionButtonClick(isSuccess);
                    })
                    props.onHide()
                    ;}}>Zapisz się na wizytę</Button>);})()

    }

    return(
        <>
            {appointmentStatusConditionalFooterContentEnum[props.modalEvent.extendedProps.appointmentStatus]}
            <Button onClick={() => {props.onHide();}}>Zamknij</Button>
        </>
    )
}

export default AppointmentModalFooter;