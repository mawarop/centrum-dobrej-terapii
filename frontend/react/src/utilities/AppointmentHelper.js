class AppointmentHelper {
    static isAppointmentAfterTodayDate(modalEventStart) {
        return Date.now() < modalEventStart;
    }

    static addDayToDate(date) {
        let newDate = new Date(date);
        newDate.setDate(date.getDate() + 1);
        return newDate;
    }
}

export default AppointmentHelper;
