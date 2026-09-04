/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sunrisedental.sunrisedentalserver.resources;

import com.sunrisedental.sunrisedentalserver.Staff;
import com.sunrisedental.sunrisedentalserver.dao.StaffDAO;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {
    private final StaffDAO staffDAO = new StaffDAO();

    @POST
    public Response login(Staff staff) {

        try {

            Staff loggedInStaff =
                    staffDAO.login(
                            staff.getUsername(),
                            staff.getPasswordHash()
                    );

            if (loggedInStaff != null) {

                return Response.ok(loggedInStaff).build();
            }

            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("Invalid username or password.")
                    .build();

        } catch (Exception e) {

            e.printStackTrace();

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error during login.")
                    .build();
        }
    }
}
