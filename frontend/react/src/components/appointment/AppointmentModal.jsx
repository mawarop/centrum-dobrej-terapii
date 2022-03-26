import React, { Component } from "react";
import { Button, Modal, Card, FormControl, InputGroup } from "react-bootstrap";
import moment from "moment";
import PatientService from "../../services/PatientService";
import DoctorService from "../../services/DoctorService";
import { Role } from "../../enums/role";
import "moment/locale/pl";
import AppointmentModalHeader from "./appointmentModalContent/AppointmentModalHeader";
import AppointmentModalBody from "./appointmentModalContent/AppointmentModalBody";
import AppointmentModalFooter from "./appointmentModalContent/AppointmentModalFooter";
import InfoToast from "../InfoToast";

class AppointmentModal extends Component {
  constructor() {
    super();
    moment.locale("pl");
  }

  render() {
    return (
      <Modal
        show={this.props.isModalOpen}
        onHide={() => {
          this.props.onHide();
        }}
        size="md"
        aria-labelledby="contained-modal-title-vcenter"
        centered
      >
        <Modal.Header closeButton>
          <AppointmentModalHeader
            modalEvent={this.props.modalEvent}
            role={this.props.role}
          />
        </Modal.Header>
        <Modal.Body>
          <div>
            <AppointmentModalBody
              modalEvent={this.props.modalEvent}
              role={this.props.role}
              onActionButtonClick={(isSuccess) =>
                this.props.onActionButtonClick(isSuccess)
              }
            />
          </div>
        </Modal.Body>
        <Modal.Footer>
          <AppointmentModalFooter
            onAppointmentDateChange={(id) =>
              this.props.onAppointmentDateChange(id)
            }
            modalEvent={this.props.modalEvent}
            role={this.props.role}
            onActionButtonClick={(isSuccess) =>
              this.props.onActionButtonClick(isSuccess)
            }
            onHide={() => this.props.onHide()}
            appointmentIdToChangeDate={this.props.appointmentIdToChangeDate}
          />
        </Modal.Footer>
      </Modal>
    );
  }
}

export default AppointmentModal;
