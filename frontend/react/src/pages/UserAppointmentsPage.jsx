import React, { Component } from "react";
import PatientService from "../services/PatientService";
import {
  APPOINTMENT_BG_COL,
  TEXT_COL,
} from "../enums/conditionalEnums/FullCalendarEnum";
import AppointmentsCalendar from "../components/appointment/AppointmentsCalendar";
import AppointmentModal from "../components/appointment/AppointmentModal";
import { Button } from "react-bootstrap";
import "./UserAppointmentsPage.css";
import InfoToast from "../components/InfoToast";
import CenteredSpinner from "../components/CenteredSpinner";
import SignUpNewAppointmentPage from "../components/patient/SignUpNewAppointmentPage";

const eventTitlePrefix = {
  DOCTOR: "Wizyta pacjenta ",
  PATIENT: "Wizyta u doktora ",
};

class UserAppointmentsPage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      events: null,
      isModalOpen: false,
      modalEvent: null,
      showToast: false,
      isActionSuccess: false,
      // isAppointmentDateChange: false,
      appointmentIdToChangeDate: null,
      showAppointmentsCalendar: true,
    };
    this.eventClickHandler = this.eventClickHandler.bind(this);
    this.hideModalHandler = this.hideModalHandler.bind(this);
    this.fetchAndSetAppointments = this.fetchAndSetAppointments.bind(this);
    this.actionButtonClickHandler = this.actionButtonClickHandler.bind(this);
    this.onAppointmentDateChangeHandler =
      this.onAppointmentDateChangeHandler.bind(this);
  }

  fetchAndSetAppointments() {
    this.props
      .makeRequest()
      .then((response) => {
        console.log(response.data);
        let events = response.data.map((d) => {
          return {
            id: d.id,
            title: d.secondParticipantFirstname
              ? eventTitlePrefix[this.props.role] +
                d.secondParticipantFirstname +
                " " +
                d.secondParticipantLastname
              : "Wolny blok",
            start: new Date(d.start),
            end: new Date(d.end),
            backgroundColor: APPOINTMENT_BG_COL[d.appointmentStatus],
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
  componentDidMount() {
    this.fetchAndSetAppointments();
  }

  render() {
    return (
      <React.Fragment>
        {this.state.events !== null && this.state.showAppointmentsCalendar && (
          <AppointmentsCalendar
            eventClick={(e) => this.eventClickHandler(e)}
            events={this.state.events}
          />
        )}

        {this.state.modalEvent !== null && (
          <AppointmentModal
            role={this.props.role}
            isModalOpen={this.state.isModalOpen}
            onHide={() => this.hideModalHandler()}
            onActionButtonClick={(isSuccess) =>
              this.actionButtonClickHandler(isSuccess)
            }
            onAppointmentDateChange={(id) =>
              this.onAppointmentDateChangeHandler(id)
            }
            appointmentIdToChangeDate={this.props.appointmentIdToChangeDate}
            modalEvent={this.state.modalEvent}
          />
        )}

        {!this.state.events && <CenteredSpinner />}
        {this.props.onBackClick !== undefined && (
          <Button id="btn-go-back" onClick={() => this.props.onBackClick()}>
            Powrót
          </Button>
        )}

        {/*{this.state.showToast &&*/}
        {/*<InfoToast/>}*/}
        {this.state.showToast && (
          <InfoToast
            show={this.state.showToast}
            isActionSuccess={this.state.isActionSuccess}
            onClose={() =>
              this.setState({ showToast: false, isActionSuccess: false })
            }
          />
        )}

        {this.state.appointmentIdToChangeDate && (
          <SignUpNewAppointmentPage
            appointmentIdToChangeDate={this.state.appointmentIdToChangeDate}
            makeRequest={(chosenDoctorEmail) => {
              return PatientService.getDoctorFreeDates(chosenDoctorEmail);
            }}
          />
        )}
      </React.Fragment>
    );
  }

  eventClickHandler(e) {
    this.setState({ modalEvent: e });
    this.setState({ isModalOpen: true });
  }
  hideModalHandler() {
    this.setState({ isModalOpen: false });
  }
  actionButtonClickHandler(isSuccess) {
    console.log("siccess: " + isSuccess);
    if (isSuccess) {
      this.fetchAndSetAppointments();
    }
    this.setState({ isActionSuccess: isSuccess });
    this.setState({ showToast: true });
  }
  onAppointmentDateChangeHandler(id) {
    this.setState({ showAppointmentsCalendar: false });
    this.setState({ appointmentIdToChangeDate: id });
    // this.setState({isAppointmentDateChange: true});
  }
}

export default UserAppointmentsPage;
