/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sunrisedental.sunrisedentalserver.resources;

/**
 *
 * @author Sadhu
 */
import com.sunrisedental.sunrisedentalserver.config.DatabaseConnection;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.sql.Connection;

@Path("dbtest")
public class DatabaseTestResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String testDatabaseConnection() {
        try (Connection connection = DatabaseConnection.getConnection()) {

            if (connection != null && !connection.isClosed()) {
                return "MySQL Database Connection Successful!";
            }

        } catch (Exception e) {
            return "Database Connection Failed: " + e.getMessage();
        }

        return "Database Connection Failed!";
    }
}