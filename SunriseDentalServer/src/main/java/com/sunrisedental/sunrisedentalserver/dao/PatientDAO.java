package com.sunrisedental.sunrisedentalserver.dao;

import com.sunrisedental.sunrisedentalserver.Patient;
import com.sunrisedental.sunrisedentalserver.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    // REGISTER PATIENT
    public boolean registerPatient(Patient patient) {

        String sql = "INSERT INTO patient "
            + "(patient_name, address, contact_number) "
            + "VALUES (?, ?, ?)";

    try (Connection connection = DatabaseConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql)) {

        statement.setString(1, patient.getName());
        statement.setString(2, patient.getAddress());
        statement.setString(3, patient.getContactNumber());

        return statement.executeUpdate() > 0;

    } catch (Exception e) {

        e.printStackTrace();
        throw new RuntimeException(e);
    }
}

    // SEARCH PATIENT
    public Patient getPatient(int patientId) {

        String sql = "SELECT * FROM patient WHERE patient_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, patientId);

            ResultSet result = statement.executeQuery();

            if (result.next()) {

                Patient patient = new Patient();

                patient.setPatientId(result.getInt("patient_id"));
                patient.setName(result.getString("patient_name"));
                patient.setAddress(result.getString("address"));
                patient.setContactNumber(
                        result.getString("contact_number")
                );

                return patient;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // UPDATE PATIENT
    public boolean updatePatient(Patient patient) {

        String sql = "UPDATE patient SET "
                + "patient_name = ?, "
                + "address = ?, "
                + "contact_number = ? "
                + "WHERE patient_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, patient.getName());
            statement.setString(2, patient.getAddress());
            statement.setString(3, patient.getContactNumber());
            statement.setInt(4, patient.getPatientId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE PATIENT
    public boolean deletePatient(int patientId) {

        String sql = "DELETE FROM patient WHERE patient_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, patientId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}