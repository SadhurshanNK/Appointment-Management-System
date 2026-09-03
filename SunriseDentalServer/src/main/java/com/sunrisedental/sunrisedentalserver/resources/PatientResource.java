package com.sunrisedental.sunrisedentalserver.resources;

import com.sunrisedental.sunrisedentalserver.Patient;
import com.sunrisedental.sunrisedentalserver.dao.PatientDAO;

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

@Path("patients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientResource {

    private final PatientDAO patientDAO = new PatientDAO();

    // REGISTER PATIENT
    @POST
    public Response registerPatient(Patient patient) {

        boolean success = patientDAO.registerPatient(patient);

        if (success) {
            return Response.status(Response.Status.CREATED)
                    .entity(patient)
                    .build();
        }

        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Failed to register patient.")
                .build();
    }

    // SEARCH PATIENT
    @GET
    @Path("/{id}")
    public Response getPatient(@PathParam("id") int patientId) {

        Patient patient = patientDAO.getPatient(patientId);

        if (patient != null) {
            return Response.ok(patient).build();
        }

        return Response.status(Response.Status.NOT_FOUND)
                .entity("Patient not found.")
                .build();
    }

    // UPDATE PATIENT
    @PUT
    @Path("/{id}")
    public Response updatePatient(
            @PathParam("id") int patientId,
            Patient patient) {

        patient.setPatientId(patientId);

        boolean success = patientDAO.updatePatient(patient);

        if (success) {
            return Response.ok(patient).build();
        }

        return Response.status(Response.Status.NOT_FOUND)
                .entity("Patient not found or update failed.")
                .build();
    }

    // DELETE PATIENT
    @DELETE
    @Path("/{id}")
    public Response deletePatient(@PathParam("id") int patientId) {

        boolean success = patientDAO.deletePatient(patientId);

        if (success) {
            return Response.ok("Patient deleted successfully.")
                    .build();
        }

        return Response.status(Response.Status.NOT_FOUND)
                .entity("Patient not found.")
                .build();
    }
}