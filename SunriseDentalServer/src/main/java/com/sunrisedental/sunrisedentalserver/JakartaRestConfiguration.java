package com.sunrisedental.sunrisedentalserver;

import com.sunrisedental.sunrisedentalserver.resources.BillResource;
import com.sunrisedental.sunrisedentalserver.resources.AppointmentResource;
import com.sunrisedental.sunrisedentalserver.resources.PatientResource;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import com.sunrisedental.sunrisedentalserver.resources.LoginResource;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("resources")
public class JakartaRestConfiguration extends Application {

    @Override
    public Set<Class<?>> getClasses() {

        Set<Class<?>> resources = new HashSet<>();

        resources.add(PatientResource.class);
        resources.add(AppointmentResource.class);
        resources.add(BillResource.class);
        resources.add(LoginResource.class);
        

        return resources;
    }
}