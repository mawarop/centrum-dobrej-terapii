import React, {Component} from 'react';
import PatientService from "../services/PatientService";
import {APPOINTMENT_BG_COL, TEXT_COL} from "../ConditionalEnums/FullCalendarEnum";
import AppointmentsCalendar from "./AppointmentsCalendar";
import AppointmentModal from "./AppointmentModal";
import {Button} from "react-bootstrap";
import "./UserAppointments.css";
import InfoToast from "./InfoToast";
import CenteredSpinner from "./CenteredSpinner";

const eventTitlePrefix ={
    "DOCTOR": "Wizyta pacjenta " ,
    "PATIENT": "Wizyta u doktora "
}

class UserAppointments extends Component {
    constructor(props) {
        super(props);
        this.state = {
            events: null,
            isModalOpen: false,
            modalEvent: null,
        };
        this.eventClickHandler = this.eventClickHandler.bind(this);
        this.hideModalHandler = this.hideModalHandler.bind(this);
    }

    componentDidMount() {
        this.props.makeRequest()
            .then((response) => {
                // console.log(response.data);
                let events = response.data.map((d) => {
                    return {
                        id: d.id,
                        title: d.secondParticipantFirstname ? eventTitlePrefix[this.props.role] +
                            d.secondParticipantFirstname + " " + d.secondParticipantLastname : "Wolny blok",
                        start: new Date(d.start),
                        end: new Date(d.end),
                        backgroundColor : APPOINTMENT_BG_COL[d.appointmentStatus],
                        details: d.details.toString(),
                        textColor: TEXT_COL["BLACK"],
                        appointmentStatus: d.appointmentStatus,
                    };
                });
                // console.log(events);
                this.setState({ events: events });

            })
            .catch(function (error) {
                console.log(error);
            });
    }

    render() {
        return (
            <React.Fragment>


                {this.state.events !== null &&
                    <AppointmentsCalendar eventClick={(e) => this.eventClickHandler(e)} events={this.state.events}/>
                }

                {this.state.modalEvent !== null &&
                    <AppointmentModal role ={this.props.role} isModalOpen={this.state.isModalOpen} onHide={() => this.hideModalHandler()}
                    modalEvent ={this.state.modalEvent}/>
                }

                {!this.state.events &&
                    <CenteredSpinner/>
                }
                {this.props.onBackClick !== undefined &&
                    <Button id="btn-go-back" onClick={() => this.props.onBackClick()}>Powrót</Button>}

            </React.Fragment>
        );
    }

    eventClickHandler(e){
        this.setState({ modalEvent: e });
        this.setState({ isModalOpen: true });
    }
    hideModalHandler(){
        this.setState({isModalOpen: false})
    }
}

export default UserAppointments;