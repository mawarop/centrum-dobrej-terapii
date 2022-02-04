import React, { Component } from "react";
import FullCalendar from "@fullcalendar/react"; // must go before plugins
import dayGridPlugin from "@fullcalendar/daygrid"; // a plugin!
import interactionPlugin from "@fullcalendar/interaction";
import timeGridPlugin from "@fullcalendar/timegrid";
import bootstrapPlugin from "@fullcalendar/bootstrap";

import "./PatientAppointments.css";
import PatientService from "../../services/PatientService";
class PatientAppointments extends Component {
  constructor(props) {
    super(props);
    this.state = {
      appointmentsStarts: "",
      appointmentsEnds: "",
      events: "",
    };
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
      <FullCalendar
        plugins={[dayGridPlugin, interactionPlugin, timeGridPlugin]}
        initialView="dayGridMonth"
        height="auto"
        dateClick={this.handleDateClick}
        headerToolbar={{
          left: "prev,next today",
          center: "title",
          right: "dayGridMonth,timeGridWeek,timeGridDay",
        }}
        events={this.state.events}
      />
    );
  }

  handleDateClick = (arg) => {
    // bind with an arrow function
    alert(arg.dateStr);
  };
}

export default PatientAppointments;
