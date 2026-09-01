package com.sunrisedental.server.patient.resource;

import com.sunrisedental.server.patient.Patient;
import com.sunrisedental.server.patient.dao.PatientDAO;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/patients")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PatientResource {

    private final PatientDAO patientDAO = new PatientDAO();

    @GET
    @Path("/{id}")
    public Response getPatient(@PathParam("id") int id) {

        Patient patient = patientDAO.getPatientById(id);

        if (patient != null) {
            return Response.ok(patient).build();
        }

        return Response.status(Response.Status.NOT_FOUND)
                .entity("Patient not found")
                .build();
    }
}