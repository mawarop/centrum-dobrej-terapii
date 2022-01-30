import React, { Component } from "react";
import FullCalendar from "@fullcalendar/react"; // must go before plugins
import dayGridPlugin from "@fullcalendar/daygrid"; // a plugin!
import bootstrapPlugin from "@fullcalendar/bootstrap";
import "./Appointment.css";
class Appointment extends Component {
  constructor(props) {
    super(props);
  }
  render() {
    return <FullCalendar plugins={[dayGridPlugin]} initialView="dayGridMonth" height="auto" dateClick={this.handleDateClick} />;
  }
}

export default Appointment;
