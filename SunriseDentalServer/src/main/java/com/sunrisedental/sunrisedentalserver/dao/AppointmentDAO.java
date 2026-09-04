package com.sunrisedental.sunrisedentalserver.dao;

import com.sunrisedental.sunrisedentalserver.Appointment;
import com.sunrisedental.sunrisedentalserver.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class AppointmentDAO {


    public boolean registerAppointment(Appointment appointment) {

    String sql = "INSERT INTO appointment "
            + "(appointment_number, patient_id, dentist_id, treatment_id, "
            + "appointment_date, appointment_time, status) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, appointment.getAppointmentNumber());
        statement.setInt(2, appointment.getPatientId());
        statement.setInt(3, appointment.getDentistId());
        statement.setInt(4, appointment.getTreatmentId());
        statement.setDate(
                5,
                java.sql.Date.valueOf(appointment.getAppointmentDate())
        );
        statement.setTime(
                6,
                java.sql.Time.valueOf(appointment.getAppointmentTime())
        );
        statement.setString(7, appointment.getStatus());

        return statement.executeUpdate() > 0;

    } catch (SQLException e) {

        // Duplicate appointment number
        if (e.getErrorCode() == 1062) {
            return false;
        }

        e.printStackTrace();
        throw new RuntimeException("Database error while registering appointment.", e);
    }
}


    public Appointment getAppointment(String appointmentNumber) {

        String sql = "SELECT * FROM appointment "
                + "WHERE appointment_number = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, appointmentNumber);

            ResultSet result = statement.executeQuery();

            if (result.next()) {

                Appointment appointment = new Appointment();

                appointment.setAppointmentId(
                        result.getInt("appointment_id"));

                appointment.setAppointmentNumber(
                        result.getString("appointment_number"));

                appointment.setPatientId(
                        result.getInt("patient_id"));

                appointment.setDentistId(
                        result.getInt("dentist_id"));

                appointment.setTreatmentId(
                        result.getInt("treatment_id"));

                appointment.setAppointmentDate(
                        result.getDate("appointment_date").toString());

                appointment.setAppointmentTime(
                        result.getTime("appointment_time").toString());

                appointment.setStatus(
                        result.getString("status"));

                return appointment;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public boolean updateAppointment(Appointment appointment) {

        String sql = "UPDATE appointment SET "
                + "patient_id = ?, "
                + "dentist_id = ?, "
                + "treatment_id = ?, "
                + "appointment_date = ?, "
                + "appointment_time = ?, "
                + "status = ? "
                + "WHERE appointment_number = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, appointment.getPatientId());
            statement.setInt(2, appointment.getDentistId());
            statement.setInt(3, appointment.getTreatmentId());

            statement.setDate(4,
                    java.sql.Date.valueOf(appointment.getAppointmentDate()));

            statement.setTime(5,
                    java.sql.Time.valueOf(appointment.getAppointmentTime()));

            statement.setString(6, appointment.getStatus());

            statement.setString(7, appointment.getAppointmentNumber());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean deleteAppointment(String appointmentNumber) {

        String sql = "DELETE FROM appointment "
                + "WHERE appointment_number = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, appointmentNumber);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean cancelAppointment(String appointmentNumber) {

    String sql = "UPDATE appointment SET status = ? "
               + "WHERE appointment_number = ?";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, "Cancelled");
        statement.setString(2, appointmentNumber);

        return statement.executeUpdate() > 0;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
    public boolean isDentistAvailable(
        int dentistId,
        String appointmentDate,
        String appointmentTime) {

    String sql = "SELECT COUNT(*) FROM appointment "
               + "WHERE dentist_id = ? "
               + "AND appointment_date = ? "
               + "AND appointment_time = ? "
               + "AND status != 'Cancelled'";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setInt(1, dentistId);
        statement.setDate(2,
                java.sql.Date.valueOf(appointmentDate));
        statement.setTime(3,
                java.sql.Time.valueOf(appointmentTime));

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt(1) == 0;
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return false;
}
   public List<Appointment> getTodayAppointments() {

    List<Appointment> appointments = new ArrayList<>();

    String sql = "SELECT * FROM appointment "
               + "WHERE appointment_date = CURDATE() "
               + "ORDER BY appointment_time";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet result = statement.executeQuery()) {

        while (result.next()) {

            Appointment appointment = new Appointment();

            appointment.setAppointmentId(
                    result.getInt("appointment_id"));

            appointment.setAppointmentNumber(
                    result.getString("appointment_number"));

            appointment.setPatientId(
                    result.getInt("patient_id"));

            appointment.setDentistId(
                    result.getInt("dentist_id"));

            appointment.setTreatmentId(
                    result.getInt("treatment_id"));

            appointment.setAppointmentDate(
                    result.getDate("appointment_date").toString());

            appointment.setAppointmentTime(
                    result.getTime("appointment_time").toString());

            appointment.setStatus(
                    result.getString("status"));

            appointments.add(appointment);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return appointments;
}
   public List<Appointment> getAllAppointments() {

    List<Appointment> appointments = new ArrayList<>();

    String sql = "SELECT * FROM appointment ORDER BY appointment_date, appointment_time";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {

            Appointment appointment = new Appointment();

            appointment.setAppointmentId(rs.getInt("appointment_id"));
            appointment.setAppointmentNumber(rs.getString("appointment_number"));
            appointment.setPatientId(rs.getInt("patient_id"));
            appointment.setDentistId(rs.getInt("dentist_id"));
            appointment.setTreatmentId(rs.getInt("treatment_id"));
            appointment.setAppointmentDate(rs.getString("appointment_date"));
            appointment.setAppointmentTime(rs.getString("appointment_time"));
            appointment.setStatus(rs.getString("status"));

            appointments.add(appointment);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return appointments;
}
}