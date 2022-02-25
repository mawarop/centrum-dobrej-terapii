// import "bootstrap/dist/css/bootstrap.css";
import "@fortawesome/fontawesome-free/css/all.css"; // needs additional webpack config!

import React, {Component} from 'react';
import FullCalendar from "@fullcalendar/react";
import plLocale from "@fullcalendar/core/locales/pl";
import dayGridPlugin from "@fullcalendar/daygrid";
import interactionPlugin from "@fullcalendar/interaction";
import timeGridPlugin from "@fullcalendar/timegrid";
import bootstrapPlugin from "@fullcalendar/bootstrap";
import {Button} from "react-bootstrap";

class AppointmentsCalendar extends Component {
    render() {
        return (
            <FullCalendar
                locale={plLocale}
                plugins={[dayGridPlugin, interactionPlugin, timeGridPlugin, bootstrapPlugin]}
                displayEventTime={true}
                themeSystem="bootstrap"
                initialView="dayGridMonth"
                height="auto"
                eventClick={ function (arg) {
                    this.props.eventClick(arg.event)
                }.bind(this)}
                headerToolbar={{
                    left: "prev,next today",
                    center: "title",
                    right: "dayGridMonth,timeGridWeek,timeGridDay",
                }}
                dayMaxEventRows={4}
                moreLinkClick="day"
                events={this.props.events}
                eventDisplay= 'block'

            />
        );
    }
}

export default AppointmentsCalendar;