import React, {Component} from 'react';
import {Button, Modal} from "react-bootstrap";
import moment from "moment";
import PatientService from "../services/PatientService";
import DoctorService from "../services/DoctorService";
import {role} from "../role";
import 'moment/locale/pl'

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
                    this.props.onHide()
                }}
                size="md"
                aria-labelledby="contained-modal-title-vcenter"
                centered
            >
                <Modal.Header closeButton>

                    { (() =>{
                        switch(this.props.modalEvent.extendedProps.appointmentStatus){
                            case "ACCEPTED": return this.props.modalEvent.title;
                            case "FREE_DATE": return "Wolny blok czasowy";
                            case "CANCELED": return "Wizyta anulowana";
                            case "FINALIZED": return "Wizyta zakończona";
                        }})()
                    }
                </Modal.Header>
                <Modal.Body><div>
                    {(()=>{
                        switch(this.props.modalEvent.extendedProps.appointmentStatus){
                            case "ACCEPTED": return (<div>
                                <p>Start: {moment(this.props.modalEvent.start).format('LLLL')} </p>
                                <p>Koniec: {moment(this.props.modalEvent.end).format('LLLL')} </p>
                                <p>Szczegóły: {this.props.modalEvent.extendedProps.details} </p>
                                {Date.now() < this.props.modalEvent.start && <p>Czy odwołać wizytę?</p>}
                            </div>);
                            // case "FREE_DATE": return "Przedział czasowy do zapisu wizyty";
                            case "FREE_DATE": if(this.props.role=== role.PATIENT) {return (<div>
                                <p>Start: {moment(this.props.modalEvent.start).format('LLLL')} </p>
                                <p>Koniec: {moment(this.props.modalEvent.end).format('LLLL')} </p>
                                {Date.now() < this.props.modalEvent.start ? <p>Czy chcesz zapisać sie na wizytę o podanej godzinie?</p>:''}
                            </div>)}
                            else if (this.props.role === role.DOCTOR) return "Przedział czasowy do zapisu wizyty";
                            case "CANCELED": return (<div>
                                <p>Start: {moment(this.props.modalEvent.start).format('LLLL')} </p>
                                <p>Koniec: {moment(this.props.modalEvent.end).format('LLLL')} </p>
                                <p>Szczegóły: {this.props.modalEvent.extendedProps.details} </p>
                                <p>Wizyta została anulowana</p>
                            </div>);
                            case "FINALIZED": return (<div>
                                <p>Start: {moment(this.props.modalEvent.start).format('LLLL')} </p>
                                <p>Koniec: {moment(this.props.modalEvent.end).format('LLLL')} </p>
                                <p>Szczegóły: {this.props.modalEvent.extendedProps.details} </p>
                            </div>);
                        }
                    })()}
                </div></Modal.Body>
                <Modal.Footer>
                    {/*{*/}
                    {/*    this.props.modalEvent.extendedProps.appointmentStatus === "ACCEPTED" &&*/}
                    {/*    <Button onClick={() => {*/}
                    {/*        let appointmentSignUp = PatientService.cancelAppointment(this.props.modalEvent.id);*/}
                    {/*        console.log("anulowanie wykonane");*/}
                    {/*    }}>Zrezygnuj z wizyty </Button>*/}
                    {/*}*/}

                    {(()=>{
                        switch(this.props.modalEvent.extendedProps.appointmentStatus) {
                            case "ACCEPTED":
                                return (Date.now() < this.props.modalEvent.start && <Button onClick={() => {
                                    switch (this.props.role){
                                        case role.PATIENT: PatientService.cancelAppointment(this.props.modalEvent.id);
                                        break;
                                        case role.DOCTOR: DoctorService.cancelAppointment(this.props.modalEvent.id);
                                        break;
                                    }
                                    console.log("anulowanie wykonane")
                                    this.props.onHide();
                                }}>Zrezygnuj z wizyty </Button>);
                            case "FREE_DATE":
                                if(this.props.role===role.PATIENT && (Date.now() < this.props.modalEvent.start))
                                return (<Button
                                    onClick={() => {
                                        let appointmentSignUp = PatientService.appointmentSignUp(this.props.modalEvent.id);
                                        console.log("patch wykonany");
                                        this.props.onHide();
                                    }}
                                >
                                    Zapisz się na wizytę
                                </Button>);
                        }
                    })()}
                    <Button
                        onClick={() => {
                            this.props.onHide();
                        }}
                    >
                        Zamknij
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }
}

export default AppointmentModal;