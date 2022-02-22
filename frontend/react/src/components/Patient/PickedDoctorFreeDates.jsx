import "bootstrap/dist/css/bootstrap.css";
import "@fortawesome/fontawesome-free/css/all.css"; // needs additional webpack config!

import React, { Component } from "react";
import FullCalendar from "@fullcalendar/react"; // must go before plugins
import dayGridPlugin from "@fullcalendar/daygrid"; // a plugin!
import interactionPlugin from "@fullcalendar/interaction";
import timeGridPlugin from "@fullcalendar/timegrid";

import bootstrapPlugin from "@fullcalendar/bootstrap";
import plLocale from '@fullcalendar/core/locales/pl'

import PatientService from "../../services/PatientService";
import { Modal, Button, Spinner } from "react-bootstrap";
import {APPOINTMENT_BG_COL, TEXT_COL} from "../../ConditionalEnums/FullCalendarEnum";
import moment from "moment";
import DoctorService from "../../services/DoctorService";
class PickedDoctorFreeDates extends Component {
    constructor(props) {
        super(props);
        this.state = {
            events: null,
            isModalOpen: false,
            modalEvent: null,
        };

    }

    componentDidMount() {
        // console.log(this.prop.doctorEmail)
        PatientService.getDoctorFreeDates(this.props.doctorEmail)
            .then((response) => {
                console.log(response.data);

                let events = response.data.map(function (d) {
                    return {
                        id: d.id,
                        title: "Wizyta u doktora " + d.secondParticipantFirstname + " " + d.secondParticipantLastname,
                        start: new Date(d.start),
                        end: new Date(d.end),
                        backgroundColor : APPOINTMENT_BG_COL[d.appointmentStatus],
                        details: d.details.toString(),
                        textColor: TEXT_COL["BLACK"],
                        appointmentStatus: d.appointmentStatus,
                    };
                });
                console.log(events);
                this.setState({ events: events });

            })
            .catch(function (error) {
                console.log(error);
            });
    }

    render() {
        return (
            <React.Fragment>
                {this.state.events === null &&
                    <Spinner animation="border" role="status" style={{position: "absolute",left: "50%", top: "50%"}}>
                        <span className="visually-hidden">Loading...</span>
                    </Spinner>
                }

                {this.state.events !== null &&
                    <React.Fragment>
                    <FullCalendar
                        locale={plLocale}
                        plugins={[dayGridPlugin, interactionPlugin, timeGridPlugin, bootstrapPlugin]}
                        displayEventTime={true}
                        // eventTimeFormat="H:mm"
                        themeSystem="bootstrap"
                        initialView="dayGridMonth"
                        height="auto"

                        eventClick={function (arg) {
                            this.setState({modalEvent: arg.event});
                            this.setState({isModalOpen: true});
                        }.bind(this)}
                        headerToolbar={{
                            left: "prev,next today",
                            center: "title",
                            right: "dayGridMonth,timeGridWeek,timeGridDay",
                        }}
                        events={this.state.events}
                        eventDisplay='block'
                    />
                    <Button id="btn-go-back" onClick={() => this.props.onBackClick()}>Powrót</Button>
                    </React.Fragment>
                }
                {this.state.modalEvent !== null &&
                    <Modal
                        show={this.state.isModalOpen}
                        onHide={() => {
                            this.setState({ isModalOpen: false });
                        }}
                        size="md"
                        aria-labelledby="contained-modal-title-vcenter"
                        centered
                    >
                        <Modal.Header closeButton className="text-center w-100">
                            <Modal.Title id="contained-modal-title-vcenter">
                                {(() =>{
                                    switch(this.state.modalEvent.extendedProps.appointmentStatus){
                                        case "ACCEPTED": return this.state.modalEvent.title;
                                        case "FREE_DATE": return "Wolny blok czasowy";
                                        case "CANCELED": return "Wizyta anulowana";
                                    }})()
                                }
                            </Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            {(()=>{
                                switch(this.state.modalEvent.extendedProps.appointmentStatus){
                                    case "ACCEPTED": return (<div>
                                        <p>Start: {moment(this.state.modalEvent.start).format('LLLL')} </p>
                                        <p>Koniec: {moment(this.state.modalEvent.end).format('LLLL')} </p>
                                        <p>Szczegóły: {this.state.modalEvent.extendedProps.details} </p>
                                        <p>Czy odwołać wizytę?</p>
                                    </div>)
                                    case "FREE_DATE": return "Czy chcesz zapisać sie na wizytę o podanej godzinie?";
                                    case "CANCELED": return "Wizyta została anulowana";
                                }
                            })()}

                        </Modal.Body>

                        <Modal.Footer>
                            {
                                this.state.modalEvent.extendedProps.appointmentStatus === "ACCEPTED" &&
                                <Button onClick={() => {
                                    let appointmentSignUp = DoctorService.cancelAppointment(this.state.modalEvent.id);
                                    console.log("anulowanie wykonane");
                                }}>Zrezygnuj z wizyty </Button>

                                || this.state.modalEvent.extendedProps.appointmentStatus === "FREE_DATE" &&
                                <Button
                                    onClick={() => {
                                        let appointmentSignUp = PatientService.appointmentSignUp(this.state.modalEvent.id);
                                        console.log("patch wykonany");
                                    }}
                                >
                                    Zapisz się na wizytę
                                </Button>
                            }
                            <Button onClick={() => {
                                this.setState({isModalOpen: false});
                            }}>Close</Button>
                        </Modal.Footer>
                    </Modal>
                }
            </React.Fragment>
        );

    }

}

export default PickedDoctorFreeDates;
