package com.example.centrum_dobrej_terapii.data;

import com.example.centrum_dobrej_terapii.entities.AppUser;
import com.example.centrum_dobrej_terapii.entities.Appointment;

public class AbstractTestDataFactoryProvider {
    public static AbstractTestDataFactory getAbstractFactory(Class<?> cl){
        if (cl.equals(AppUser.class)) {
            return new AppUserFactoryAbstract();
        }else if(cl.equals(Appointment.class)){
            return new AppointmentFactoryAbstract();
        }
        else throw new IllegalStateException("No factory implemented for this class");
    }
}
