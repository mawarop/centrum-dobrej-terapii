// import "bootstrap/dist/css/bootstrap.css";
// import "@fortawesome/fontawesome-free/css/all.css"; // needs additional webpack config!
//
// import React, { Component } from "react";
// import FullCalendar from "@fullcalendar/react"; // must go before plugins
// import dayGridPlugin from "@fullcalendar/daygrid"; // a plugin!
// import interactionPlugin from "@fullcalendar/interaction";
// import timeGridPlugin from "@fullcalendar/timegrid";
//
// import bootstrapPlugin from "@fullcalendar/bootstrap";
// // import plLocale from "@fullcalendar/core/locales/pl"
//
// import plLocale from '@fullcalendar/core/locales/pl'
//
//
// import PatientService from "../../services/PatientService";
// import { Modal, Button, Toast, ToastContainer } from "react-bootstrap";
// import "./PatientAppointments.css";
// import {APPOINTMENT_BG_COL, TEXT_COL} from "../../ConditionalEnums/FullCalendarEnum";
// import moment from "moment";
// import DoctorService from "../../services/DoctorService";
// class PatientAppointments extends Component {
//   constructor(props) {
//     super(props);
//     this.state = {
//       events: null,
//       isModalOpen: false,
//       modalEvent: null,
//     };
//
//   }
//
//   componentDidMount() {
//     PatientService.getAppointments()
//       .then((response) => {
//
//         console.log(response.data);
//
//         let events = response.data.map(function (d) {
//           return {
//               id: d.id,
//               title: "Wizyta u doktora " + d.secondParticipantFirstname + " " + d.secondParticipantLastname,
//               start: new Date(d.start),
//               end: new Date(d.end),
//               backgroundColor : APPOINTMENT_BG_COL[d.appointmentStatus],
//               details: d.details.toString(),
//               textColor: TEXT_COL["BLACK"],
//               appointmentStatus: d.appointmentStatus,
//           };
//         });
//         console.log(events);
//         this.setState({ events: events });
//
//       })
//       .catch(function (error) {
//         console.log(error);
//       });
//   }
//
//   render() {
//     return (
//       <React.Fragment>
//
//           {this.state.modalEvent !== null &&
//
//           }
//       </React.Fragment>
//     );
//
//   }
//
// }
//
// export default PatientAppointments;
