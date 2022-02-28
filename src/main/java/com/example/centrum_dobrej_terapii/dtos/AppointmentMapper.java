package com.example.centrum_dobrej_terapii.dtos;

import com.example.centrum_dobrej_terapii.entities.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AppointmentMapper {
    void updateAppointmentFromAppointmentRequest(AppointmentRequest appointmentRequest, @MappingTarget Appointment appointment);
}
