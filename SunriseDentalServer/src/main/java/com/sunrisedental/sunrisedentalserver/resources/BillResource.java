package com.sunrisedental.sunrisedentalserver.resources;

import com.sunrisedental.sunrisedentalserver.Bill;
import com.sunrisedental.sunrisedentalserver.dao.BillDAO;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("bills")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BillResource {

    private final BillDAO billDAO = new BillDAO();


    // CREATE BILL
    @POST
    public Response createBill(Bill bill) {

        try {

            boolean created =
                    billDAO.createBill(bill);

            if (created) {

                return Response
                        .status(Response.Status.CREATED)
                        .entity(bill)
                        .build();

            }

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Bill could not be created.")
                    .build();

        } catch (Exception e) {

            e.printStackTrace();

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error creating bill.")
                    .build();
        }
    }


    // GET BILL BY APPOINTMENT ID
    @GET
    @Path("/appointment/{appointmentId}")
    public Response getBillByAppointmentId(
            @PathParam("appointmentId") int appointmentId) {

        try {

            Bill bill =
                    billDAO.getBillByAppointmentId(
                            appointmentId);

            if (bill != null) {

                return Response
                        .ok(bill)
                        .build();
            }

            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Bill not found.")
                    .build();

        } catch (Exception e) {

            e.printStackTrace();

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving bill.")
                    .build();
        }
    }
}