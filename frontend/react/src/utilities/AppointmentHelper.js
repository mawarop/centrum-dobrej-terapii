class AppointmentHelper {
    static isAppointmentAfterTodayDate(modalEventStart){
        return Date.now() < modalEventStart;
    }

}

export default AppointmentHelper;