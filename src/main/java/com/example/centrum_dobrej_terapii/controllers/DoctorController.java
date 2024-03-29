package com.example.centrum_dobrej_terapii.controllers;


import com.example.centrum_dobrej_terapii.AppointmentStatus;
import com.example.centrum_dobrej_terapii.dtos.AppointmentRequest;
import com.example.centrum_dobrej_terapii.dtos.AppointmentResponse;
import com.example.centrum_dobrej_terapii.dtos.NamePathFileResponse;
import com.example.centrum_dobrej_terapii.entities.AppUser;
import com.example.centrum_dobrej_terapii.entities.Appointment;
import com.example.centrum_dobrej_terapii.services.AppointmentService;
import com.example.centrum_dobrej_terapii.services.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity addAppointment(@RequestBody AppointmentRequest appointmentRequest) {
        boolean createdAppointment = appointmentService.addAppointment(appointmentRequest, AppointmentStatus.FREE_DATE);
        if (createdAppointment) {
            new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("patient-documents-paths")
    public List<NamePathFileResponse> getPatientDocumentsPaths(@RequestBody Object pesel) {
//        return documentService.getAllPatientDocuments(pesel.toString());
        AppUser participantDoctor = (AppUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return documentService.getPatientDocumentsByLoggedInDoctor(pesel.toString(), participantDoctor.getPesel());
    }

    @GetMapping("appointments")
    public List<AppointmentResponse> getAppointmentsResponseWithPatientNames() {
        List<Appointment> appointments = appointmentService.getParticipantAppointments();
        return appointments.stream().map(a -> new AppointmentResponse(a.getId(), a.getStart(), a.getEnd(), a.getDetails(),
                a.getAppointmentStatus(), a.getPatient() != null ? a.getPatient().getFirstname() : null, a.getPatient() != null ? a.getPatient().getLastname() : null)).toList();
    }

//    @GetMapping("patients")
//    public List<AppUserBaseResponse> getPatients(){
//        return doctorService.getPatients();
//    }

    @PatchMapping("cancel-appointment/{id}")
    ResponseEntity cancelAppointment(@PathVariable("id") long id) {
        boolean canceledAppointment = appointmentService.cancelAppointment(id);
        if (canceledAppointment) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PatchMapping("appointment/{id}")
    ResponseEntity updateAppointmentDetails(@PathVariable("id") long id, @RequestBody AppointmentRequest appointmentRequest) {
        if (appointmentService.updateAppointment(id, appointmentRequest)) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
