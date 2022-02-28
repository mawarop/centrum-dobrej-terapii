import {appointmentStatus} from "../../../enums/appointmentStatus";
function AppointmentModalHeader(props) {

    const headerTitleEnum={
        [appointmentStatus.FINALIZED] : "Wizyta zakończona",
        [appointmentStatus.ACCEPTED]: props.modalEvent.title,
        [appointmentStatus.FREE_DATE]: "Wolny blok czasowy",
        [appointmentStatus.CANCELED]: "Wizyta anulowana"
    }

    return(
        headerTitleEnum[props.modalEvent.extendedProps.appointmentStatus]
    );
}

export default AppointmentModalHeader;