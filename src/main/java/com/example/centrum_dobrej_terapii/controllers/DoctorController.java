package com.example.centrum_dobrej_terapii.controllers;


import com.example.centrum_dobrej_terapii.AppointmentStatus;
import com.example.centrum_dobrej_terapii.dtos.AppointmentRequest;
import com.example.centrum_dobrej_terapii.dtos.AppointmentResponse;
import com.example.centrum_dobrej_terapii.dtos.NamePathFileResponse;
import com.example.centrum_dobrej_terapii.entities.Appointment;
import com.example.centrum_dobrej_terapii.services.AppointmentService;
import com.example.centrum_dobrej_terapii.services.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/doctor")
@AllArgsConstructor
//@PreAuthorize("hasRole('DOCTOR')")
public class DoctorController {
private final DocumentService documentService;
private final AppointmentService appointmentService;

    @PostMapping("appointment/add")
    public ResponseEntity addAppointment(@RequestBody AppointmentRequest appointmentRequest){
        boolean createdAppointment = appointmentService.addAppointment(appointmentRequest, AppointmentStatus.FREE_DATE);
        if(createdAppointment){
            new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @PostMapping("patient-documents-paths")
    public List<NamePathFileResponse> getPatientDocumentsPaths(@RequestBody String pesel)
    {
        return documentService.getPatientDocuments(pesel);
    }

    @GetMapping("appointments")
    public List<AppointmentResponse> getAppointmentsResponseWithPatientNames()
    {
        List<Appointment> appointments = appointmentService.getParticipantAppointments();
        return appointments.stream().map(a -> new AppointmentResponse(a.getId(), a.getStart(), a.getEnd(), a.getDetails(),
                a.getAppointmentStatus(), a.getPatient().getFirstname(), a.getPatient().getLastname())).toList();
    }

//    @GetMapping("patients")
//    public List<AppUserBaseResponse> getPatients(){
//        return doctorService.getPatients();
//    }

    @PatchMapping("cancel-appointment/{id}")
    ResponseEntity cancelAppointment(@PathVariable("id") long id){
        boolean canceledAppointment = appointmentService.cancelAppointment(id);
        if (canceledAppointment){
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
