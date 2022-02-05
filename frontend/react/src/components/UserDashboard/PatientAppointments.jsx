import React, { Component } from "react";
import FullCalendar from "@fullcalendar/react"; // must go before plugins
import dayGridPlugin from "@fullcalendar/daygrid"; // a plugin!
import interactionPlugin from "@fullcalendar/interaction";
import timeGridPlugin from "@fullcalendar/timegrid";
// import bootstrapPlugin from "@fullcalendar/bootstrap";
// import jQuery from "jquery";

import "./PatientAppointments.css";
import PatientService from "../../services/PatientService";
import { Modal, Button } from "react-bootstrap";
class PatientAppointments extends Component {
  constructor(props) {
    super(props);
    this.state = {
      appointmentsStarts: "",
      appointmentsEnds: "",
      events: "",
      isModalOpen: false,
    };

    // $("#buttoncl").on("click", function () {
    //   console.log("kliknieto!");
    // });
  }

  componentDidMount() {
    PatientService.getAppointments()
      .then((response) => {
        //this.setState({ appointments: response.data });
        console.log(response.data);
        // let events = new Date(response.data[0].start);
        let eventsStarts = response.data.map(function (date) {
          return new Date(date.start);
        });
        let eventsEnds = response.data.map(function (date) {
          return new Date(date.end);
        });
        console.log(eventsStarts);
        console.log(eventsEnds);
        let events = response.data.map(function (d) {
          return {
            title: "Wizyta u lekarza " + d.secondParticipantFirstname + " " + d.secondParticipantLastname,
            start: new Date(d.start),
            end: new Date(d.end),
          };
        });
        this.setState({ events: events });
        this.setState({ appointmentsStarts: eventsStarts });
        this.setState({ appointmentsEnds: eventsEnds });
      })
      .catch(function (error) {
        console.log(error);
      });
  }
  render() {
    return (
      <React.Fragment>
        <FullCalendar
          plugins={[dayGridPlugin, interactionPlugin, timeGridPlugin]}
          initialView="dayGridMonth"
          height="auto"
          // dateClick={this.handleDateClick}
          eventClick={(arg) => {
            this.setState({ isModalOpen: true });
          }}
          headerToolbar={{
            left: "prev,next today",
            center: "title",
            right: "dayGridMonth,timeGridWeek,timeGridDay",
          }}
          events={this.state.events}
        />

        <Modal
          show={this.state.isModalOpen}
          onHide={() => {
            this.setState({ isModalOpen: false });
          }}
          size="lg"
          aria-labelledby="contained-modal-title-vcenter"
          centered
        >
          <Modal.Header closeButton>
            <Modal.Title id="contained-modal-title-vcenter">Modal heading</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <h4>Centered Modal</h4>
            <p>
              Cras mattis consectetur purus sit amet fermentum. Cras justo odio, dapibus ac facilisis in, egestas eget quam. Morbi leo risus, porta ac
              consectetur ac, vestibulum at eros.
            </p>
          </Modal.Body>
          <Modal.Footer>
            <Button
              onClick={() => {
                this.setState({ isModalOpen: false });
              }}
            >
              Close
            </Button>
          </Modal.Footer>
        </Modal>
      </React.Fragment>
    );
  }

  // handleDateClick = (arg) => {
  //   // bind with an arrow function
  //   alert(arg.dateStr);
  // };
}

export default PatientAppointments;
