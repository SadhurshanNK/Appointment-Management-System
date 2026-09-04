/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sunrisedental.sunrisedentalserver.dao;

import com.sunrisedental.sunrisedentalserver.Staff;
import com.sunrisedental.sunrisedentalserver.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Sadhu
 */
public class StaffDAO {
    public Staff login(String username, String password) {

        String sql = "SELECT * FROM staff "
                   + "WHERE username = ? "
                   + "AND password_hash = ?";

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                Staff staff = new Staff();

                staff.setStaffId(
                        resultSet.getInt("staff_id")
                );

                staff.setUsername(
                        resultSet.getString("username")
                );

                staff.setPasswordHash(
                        resultSet.getString("password_hash")
                );

                return staff;
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }
    
}
