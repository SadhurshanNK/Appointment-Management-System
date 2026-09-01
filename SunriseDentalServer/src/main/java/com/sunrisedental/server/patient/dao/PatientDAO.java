package com.sunrisedental.server.patient.dao;

import com.sunrisedental.server.config.DatabaseConnection;
import com.sunrisedental.server.patient.Patient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDAO {

    // Register a new patient
    public boolean addPatient(Patient patient) {

        String sql = "INSERT INTO patient (name, address, contact_number) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, patient.getName());
            statement.setString(2, patient.getAddress());
            statement.setString(3, patient.getContactNumber());

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Find patient by ID
    public Patient getPatientById(int patientId) {

        String sql = "SELECT * FROM patient WHERE patient_id = ?";

        try (Connection connection = DatabaseConnection.getConnection()) {

    java.lang.System.out.println(
        "Connected to: " + connection.getCatalog()
    );

    PreparedStatement statement = connection.prepareStatement(sql);

    statement.setInt(1, patientId);

    ResultSet result = statement.executeQuery();

            if (result.next()) {

                return new Patient(
                    result.getInt("patient_id"),
                    result.getString("patient_name"),
                    result.getString("address"),
                    result.getString("contact_number")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}