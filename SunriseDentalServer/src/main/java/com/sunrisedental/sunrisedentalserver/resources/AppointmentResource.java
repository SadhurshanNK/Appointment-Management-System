package com.sunrisedental.sunrisedentalserver.resources;

import com.sunrisedental.sunrisedentalserver.Appointment;
import com.sunrisedental.sunrisedentalserver.dao.AppointmentDAO;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.QueryParam;

@Path("appointments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AppointmentResource {

    private final AppointmentDAO appointmentDAO = new AppointmentDAO();


    @POST
public Response registerAppointment(Appointment appointment) {

    try {

        boolean registered = appointmentDAO.registerAppointment(appointment);

        if (registered) {

            return Response
                    .status(Response.Status.CREATED)
                    .entity(appointment)
                    .build();

        } else {

            return Response
                    .status(Response.Status.CONFLICT)
                    .entity("Appointment Number already exists.")
                    .build();
        }

    } catch (Exception e) {

        e.printStackTrace();

        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Error registering appointment.")
                .build();
    }
}
      @GET
@Path("/today")
public Response getTodayAppointments() {

    return Response.ok(
            appointmentDAO.getTodayAppointments()
    ).build();
}


    @GET
    @Path("/{number}")
    public Response getAppointment(
            @PathParam("number") String appointmentNumber) {

        Appointment appointment =
                appointmentDAO.getAppointment(appointmentNumber);

        if (appointment != null) {
            return Response.ok(appointment).build();
        }

        return Response.status(Response.Status.NOT_FOUND)
                .entity("Appointment not found.")
                .build();
    }



    @PUT
    @Path("/{number}")
    public Response updateAppointment(
            @PathParam("number") String appointmentNumber,
            Appointment appointment) {

        appointment.setAppointmentNumber(appointmentNumber);

        boolean success =
                appointmentDAO.updateAppointment(appointment);

        if (success) {
            return Response.ok(appointment).build();
        }

        return Response.status(Response.Status.NOT_FOUND)
                .entity("Appointment not found or update failed.")
                .build();
    }
    

    @DELETE
    @Path("/{number}")
    public Response deleteAppointment(
            @PathParam("number") String appointmentNumber) {

        boolean success =
                appointmentDAO.deleteAppointment(appointmentNumber);

        if (success) {
            return Response.ok(
                    "Appointment deleted successfully."
            ).build();
        }

        return Response.status(Response.Status.NOT_FOUND)
                .entity("Appointment not found.")
                .build();
    }
@PUT
@Path("/{number}/cancel")
public Response cancelAppointment(
        @PathParam("number") String appointmentNumber) {

    boolean success =
            appointmentDAO.cancelAppointment(appointmentNumber);

    if (success) {
        return Response.ok("Appointment cancelled successfully.")
                .build();
    }

    return Response.status(Response.Status.NOT_FOUND)
            .entity("Appointment not found or cancellation failed.")
            .build();
}
// CHECK DENTIST AVAILABILITY
@GET
@Path("/availability")
public Response checkDentistAvailability(
        @QueryParam("dentistId") int dentistId,
        @QueryParam("date") String date,
        @QueryParam("time") String time) {

    boolean available =
            appointmentDAO.isDentistAvailable(
                    dentistId,
                    date,
                    time);

    if (available) {
        return Response.ok("Dentist is available.")
                .build();
    }

    return Response.ok("Dentist is not available.")
            .build();
}

@GET
@Path("/all")
public Response getAllAppointments() {

    return Response.ok(
            appointmentDAO.getAllAppointments()
    ).build();
}

}